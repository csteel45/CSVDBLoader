/*
 * @(#)CSVDBLoader.java $Date: Dec 7, 2011 9:32:20 AM $
 * 
 * Copyright © 2011 FortMoon Consulting, Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of FortMoon
 * Consulting, Inc. ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the license agreement you entered into with FortMoon Consulting.
 * 
 * FORTMOON MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 * NON-INFRINGEMENT. FORTMOON SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */
package com.fortmoon.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.log4j.Logger;


/**
 * @author Christopher Steel - FortMoon Consulting, Inc.
 *
 * @since Dec 7, 2011 9:32:20 AM
 */
public class CSVDBLoader {
	
	protected String token = "\t"; //FIXME: Add user define tokens
	protected File file = null;
	protected String fileName = null;
	protected Connection con = null;
	protected Statement st = null;
	protected ResultSet rs = null;
	protected int batchSize = 200;
	protected ArrayList<String> columnNames = new ArrayList<String>();
	protected HashMap<String, ColumnBean> columns;
	protected String url = "jdbc:mysql://presto270db.coksdj9a4svg.us-east-1.rds.amazonaws.com/BigData";
	protected String user = "presto";
	protected String password = "presto";
	protected FileReader reader;
	protected LineNumberReader lr;
	protected String tableName;
	protected String insertPreamble;
	protected long numLines = 0;
	protected MessageDigest digest;
	protected BlockingQueue<ArrayList<String>> queue = new LinkedBlockingQueue<ArrayList<String>>(1);
	protected boolean skipBlobs = true;
    private Logger log = Logger.getLogger(CSVDBLoader.class.getName());
    
	
    
    public CSVDBLoader() {
    	log.info("called");
    	try {
			digest = MessageDigest.getInstance("MD5");
		}
		catch (NoSuchAlgorithmException e) {
			log.error("Exception getting instance of MD5 MessageDigest: " + e.getMessage(), e);
			throw new RuntimeException("Can't get instance of MD5, aborting.");
		}

    }
    
    public void load() throws InterruptedException {
    	file = new File(fileName);
    	tableName = fileName.substring(0, fileName.indexOf('.'));
    	String line = null;
    	String statement = null;
    	int counter = 0;
    	try {
			reader = new FileReader(file);
			lr = new LineNumberReader(reader);
			line = lr.readLine();
			if(line != null)
				getColumns(line); //Now we read the header, mark the file position for future resets
			//lr.mark(999999);
			getColumnClasses();
			getInsertPreamble();
			createTable();
			resetReader();
			
			Runnable r = new Runnable() {
				@Override
				public void run() {
					executeBatch();
				}				
			};
			Thread t = new Thread(r, "BatchRunner");
			t.start();
			ArrayList<String> statements = new ArrayList<String>(batchSize);
			line = lr.readLine();
			while(line != null) {
				counter++;
				statement = getStatement(line, counter);
				//this.statements.add(statement);
				statements.add(statement);
				// update every 1000 records
				if(counter % batchSize == 0) {
					log.info("Executing batch for line " + counter + " of " + this.numLines);
					//executeBatch();
					queue.put(statements);
					statements = new ArrayList<String>(batchSize);
/*					try {
						Thread.sleep(100);
					}
					catch(InterruptedException ie ) {}
*/				}
				line = lr.readLine();
			}
			// get the last ones
			log.info("Executing final batch");
			queue.put(statements);
			//executeBatch();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	finally {
    		try {
				reader.close();
			}
			catch (IOException e) {
				log.error("Non-Fatal Exception closing FileReader: " + e.getMessage(), e);
			}
    	}
    }
    
    private void getColumns(String line) {
    	log.trace("called");
    	StringTokenizer tokenizer = new StringTokenizer(line, this.token);
    	while(tokenizer.hasMoreTokens()) {
    		String name = tokenizer.nextToken();
    		name = name.replace(' ', '_');
    		name = name.replace('-', '_');
    		this.columnNames.add(name);
    	}
    	log.info("Column names: " + this.columnNames);

    	this.columns = new HashMap<String, ColumnBean>(this.columnNames.size());
    	for(String name : this.columnNames) {
    		ColumnBean column = new ColumnBean();
    		column.setName(name);
    		columns.put(name, column);
    	}
    }
    
    private void getColumnClasses() throws IOException {
		log.trace("called");
		//TODO: Scan file x times for x columns. Change to once if performance is hit.
		for(int i = 0; i < this.columnNames.size(); i++) {
			if(log.isInfoEnabled())
				log.info("Getting column class for column: " + this.columnNames.get(i));
			getColumnClass(i);
			resetReader(); //reset to 2 line of file
		}

		// Let's just get use the first unique Int/BigInt as the PK
		boolean foundPK = false;
		// Set all the null columns to VARCHAR
		for(String col : this.columnNames) {
			ColumnBean column = this.columns.get(col);
			SQLTYPE type = column.getType();
			if(type.equals(SQLTYPE.NULL)) {
				column.setType(SQLTYPE.VARCHAR);
			}
			if((type.equals(SQLTYPE.INTEGER) || type.equals(SQLTYPE.BIGINT)) 
					&& column.isUnique() && !foundPK) {
				column.setPrimaryKey(true);
				foundPK = true;
			}
			if(type == SQLTYPE.BIGINT || type == SQLTYPE.INTEGER || type == SQLTYPE.FLOAT || type == SQLTYPE.DOUBLE) {
				column.setCharBased(false);
			}
		}
		log.info("Column Types: " + this.columns);
    }
	
	private void getColumnClass(int columnNum) throws IOException {
		log.trace("called");
		// Let's try and find uniques without running out of memory. Dump the list as soon as we find a dup.
		
		TreeSet<String> uniques = new TreeSet<String>();
		numLines = 1;
		String line = lr.readLine();
		while (line != null) {
			String[] result = line.split("\t");
			String val = result[columnNum];
			String colName = this.columnNames.get(columnNum);
			if (val.isEmpty()) {
				if (log.isDebugEnabled())
					log.debug("********NULL Value for column: " + this.columnNames.get(columnNum) + "*********");
				this.columns.get(colName).setNullable(true);
				columns.get(colName).setUnique(false);
			} 
			else {
				if (log.isDebugEnabled())
					log.debug("Value for column: " + colName + " = " + val + " line = " + line);
				SQLTYPE colType = SQLUtil.getType(val);
				if(skipBlobs && (colType == SQLTYPE.BLOB || colType == SQLTYPE.LONGBLOB))
					colType = SQLTYPE.VARCHAR;

				log.debug("SQL Type: " + colType);
				if (columns.get(colName).getType().getValue() > colType.getValue()) {
					columns.get(colName).setType(colType);
				}
				int valSize = val.length();
				if(skipBlobs && valSize > 255)
					valSize = 255;
				if (columns.get(colName).getSize() < valSize) {
					columns.get(colName).setSize(valSize);
					log.debug("Size for column: " + columns.get(colName).getName() + " = " + columns.get(colName).getSize());
				}
		    	digest.update(val.getBytes());
		    	String hash = new String(digest.digest());
				if (uniques != null && !uniques.contains(hash))
					uniques.add(hash);
				else {
					uniques = null;
					columns.get(colName).setUnique(false);
				}
					
			}
			line = lr.readLine();
			numLines++;
			if(numLines % 100000 == 0)
				log.debug("Finished processing number of lines in first pass scan: " + numLines);
		}
	}	

	public void resetReader() {
    	try {
    		// lr.mark and lr.reset don't work for very large files (failed after 500000 lines)
    		reader.close();
			reader = new FileReader(file);
    		lr = new LineNumberReader(reader);
			// reread the header row to skip.
			lr.readLine();
			//lr.reset();
		} catch (IOException e) {
			log.error("Exception resetting reader: " + e, e);
		}
    }

	private void getInsertPreamble() {
		boolean first = true;
		StringBuffer buf = new StringBuffer("INSERT INTO " + tableName + " (");
		for(String name : this.columnNames) {
			if(!first)
				buf.append(", ");
			buf.append(name);
			first = false;
		}
		buf.append(") VALUES(");
		this.insertPreamble = buf.toString();
	}
	/**
	 * @param line
	 * @return
	 */
	private String getStatement(String line, long lineNum) throws IllegalArgumentException {
		boolean first = true;
		String[] result = line.split("\t");
		StringBuffer buf = new StringBuffer(this.insertPreamble);
		int i = 0;
		for(String val : result) {
			if(skipBlobs && val.length() > 255)
				val = val.substring(0, 254);
			if(val.contains("\"")) {
				log.error("*****WARNING****** Found '\"' in data, replacing with '" + " at column: " + this.columnNames.get(i) + " line: " + lineNum + " index: " + line.indexOf("\""));
				val = val.replace('"', '\'');
			}
			boolean charBased = this.columns.get(this.columnNames.get(i)).getCharBased();
			if(!first)
				buf.append(",");
			if(!charBased) {
				if(val.isEmpty())
					buf.append("NULL");
				else
					buf.append(val);
			}
			else {
				buf.append("\"" + val + "\"");
			}
			first = false;
			i++;
		}
		buf.append(")");
		//log.info("Statement: " + buf.toString());
		//"INSERT INTO batch_table(id, name) VALUES('11', 'A')"
		return buf.toString();
	}

	public void createTable() {
        try {
			con = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			log.error("Exception getConnection: " + e ,e);
			return;
		}
        boolean first = true; 
        Statement stmt = null;
		StringBuffer cs;
		cs = new StringBuffer("create table " + tableName + " (");
		for(String colName : this.columnNames) {
			if(first) {
				first = false;
			}
			else {
			 cs.append(", ");
			}
			ColumnBean column = this.columns.get(colName);
			cs.append(colName);
			cs.append(" " + column.getType().toString());
			if(column.getType() == SQLTYPE.VARCHAR)
				cs.append("(" + column.getSize() + ")");
			if(!column.isNullable())
				cs.append(" NOT NULL");
			if(column.isUnique())
				cs.append(" UNIQUE");
			if(column.isPrimaryKey())
				cs.append(" PRIMARY KEY");

		}
		cs.append(") TYPE=innodb");
		log.info("Table create string: " + cs);
		final String createString = cs.toString();
		try {
			stmt = con.createStatement();
			stmt.executeUpdate(createString);
		}
		catch (SQLException ex) {
			log.error("SQLException: " + ex.getMessage(), ex);
		}
		finally {
			try {
				stmt.close();
			}
			catch (SQLException e) {
				log.error("Exception closing statement: " + e.getMessage(), e);
			}
			try {
				con.close();
			}
			catch (SQLException e) {
				log.error("Exception closing DB connection: " + e.getMessage(), e);
			}
		}

    }
    
	public void executeBatch() {
		PreparedStatement stmt = null;
		//Statement stmt = null;

	    boolean first = true;
	    StringBuffer insert = new StringBuffer("insert into " + this.tableName + " (");
		for (String col : this.columnNames) {
			if (first)
				insert.append(col);
			else
				insert.append(", " + col);
			first = false;
		}
		insert.append(") values(");
		first = true;
		for (String col : this.columnNames) {
			if (first)
				insert.append("?");
			else
				insert.append(", ?");
			first = false;
		}
		insert.append(")");
		log.info("Insert statement: " + insert);
	    
		try {
			con = DriverManager.getConnection(url, user, password);

			//stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		    stmt = con.prepareStatement(insert.toString());

		    con.setAutoCommit(false);
			TimeUnit SECONDS = TimeUnit.SECONDS;
			ArrayList<String> statements = null;
			boolean complete = false;
			while (!complete) {
				try {
					statements = (ArrayList<String>) queue.poll(10, SECONDS);
					if(statements == null) {
						return;
					}
				}
				catch (InterruptedException e) {
					log.error("Poll timed out");
					e.printStackTrace();
					complete = true;
					return;
				}
	
				for (String statement : statements) {
					stmt.addBatch(statement);
				}
				//log.info("Starting execute.");
				int[] updateCounts = stmt.executeBatch();
				//log.info("Starting commit.");
				con.commit();
				stmt.clearBatch();
				log.info("Committed number of rows: " + updateCounts.length);
			}
		}
		catch (SQLException ex) {
			log.error(ex.getMessage(), ex);
		}
		finally {
			try {
				if(stmt != null)
					stmt.close();
				//if (pst != null)
				//	pst.close();
				if (con != null)
					con.close();
			}
			catch (SQLException ex) {
				log.error(ex.getMessage(), ex);
			}
			// statements.clear();
		}
		log.info("\nLoad complete. Goodbye.\n");
	}

	public String getVersion() {
        try {
            con = DriverManager.getConnection(url, user, password);
            st = con.createStatement();
            rs = st.executeQuery("SELECT VERSION()");

            if (rs.next()) {
                log.debug("Database version number: " + rs.getString(1));
                return rs.getString(1);
            }

        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                log.error(ex.getMessage(), ex);
            }
        }
        return null;
		
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the batchSize
	 */
	public int getBatchSize() {
		return batchSize;
	}

	/**
	 * @param batchSize the batchSize to set
	 */
	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public static void main(String[] args) throws InterruptedException, ParseException {
		CSVDBLoader loader = new CSVDBLoader();
		loader.getVersion();

		Options options = new Options();

		// add t option
		options.addOption("f", true, "REQUIRED: Filename to load");
		options.addOption("t", true, "token delimiter(s). Defaults to comma.");
		options.addOption("u", true, "Database username. Defaults to presto.");
		options.addOption("p", true, "Database password. Defaults to presto.");
		options.addOption("d", true, "Database connection string  Defaults to jdbc:mysql://presto270db.coksdj9a4svg.us-east-1.rds.amazonaws.com/BigData.");
		options.addOption("s", true, "Batch size for row inserts. Defaults to 1000.");
		options.addOption("n", true, "Database table name to create. Defaults to filename without the extension (i.e. Myfile.csv would create a table Myfile.");
		options.addOption("nb", false, "Truncate entries to 255 character VARCHARs (no blobs)");
		options.addOption("v", false, "Verbose. Turns on debug level logging.");

		CommandLineParser parser = new PosixParser();
		CommandLine cmd = parser.parse( options, args);
		if(cmd.hasOption("f")) {
			loader.setFileName(cmd.getOptionValue("f"));
		}
		else {
		    System.err.println("USAGE");
		}

		loader.load();

	}

}
