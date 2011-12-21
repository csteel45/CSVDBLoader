/*
 * @(#)ColumnBean.java $Date: Dec 16, 2011 10:45:02 PM $
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

import java.awt.Component;
import java.io.Serializable;

/**
 * @author Christopher Steel - FortMoon Consulting, Inc.
 *
 * @since Dec 16, 2011 10:45:02 PM
 */
public class ColumnBean extends Component implements Serializable {
	public static final String COLUMN_SIZE = "COLUMN_SIZE";
	public static final String IS_UNIQUE = "IS_UNIQUE";
	public static final String IS_INDEXED = "IS_INDEXED";
	public static final String IS_NULLABLE = "IS_NULLABLE";
	public static final String IS_PRIMARY_KEY = "IS_PRIMARY_KEY";
	public static final String SQL_TYPE = "SQL_TYPE";
	public static final String NAME = "NAME";
	public static final String IS_CHAR_BASED = "IS_CHAR_BASED";
	private int columnSize = 1;
	private boolean isUnique = true;
	private boolean isIndexed = false;
	private boolean isNullable = false;
	private boolean isPrimaryKey = false;
	private SQLTYPE type = SQLTYPE.NULL;
	private String name;
	private boolean isCharBased = true;
	
	public ColumnBean() {
		
	}
	
	/**
	 * @return the columnSize
	 */
	public int getColumnSize() {
		return columnSize;
	}

	/**
	 * @param columnSize the columnSize to set
	 */
	public void setColumnSize(int columnSize) {
		this.firePropertyChange(COLUMN_SIZE, this.columnSize, columnSize);
		this.columnSize = columnSize;
	}

	/**
	 * @return the isUnique
	 */
	public boolean isUnique() {
		return isUnique;
	}

	/**
	 * @param isUnique the isUnique to set
	 */
	public void setUnique(boolean isUnique) {
		this.firePropertyChange(IS_UNIQUE, this.isUnique, isUnique);
		this.isUnique = isUnique;
	}

	/**
	 * @return the isIndexed
	 */
	public boolean isIndexed() {
		return isIndexed;
	}

	/**
	 * @param isIndexed the isIndexed to set
	 */
	public void setIndexed(boolean isIndexed) {
		this.firePropertyChange(IS_INDEXED, this.isIndexed, isIndexed);
		this.isIndexed = isIndexed;
	}

	/**
	 * @return the isNullable
	 */
	public boolean isNullable() {
		return isNullable;
	}

	/**
	 * @param isNullable the isNullable to set
	 */
	public void setNullable(boolean isNullable) {
		this.firePropertyChange(IS_NULLABLE, this.isNullable, isNullable);
		this.isNullable = isNullable;
	}

	/**
	 * @return the isPrimaryKey
	 */
	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}

	/**
	 * @param isPrimaryKey the isPrimaryKey to set
	 */
	public void setPrimaryKey(boolean isPrimaryKey) {
		this.firePropertyChange(IS_PRIMARY_KEY, this.isPrimaryKey, isPrimaryKey);
		this.isPrimaryKey = isPrimaryKey;
	}

	/**
	 * @return the type
	 */
	public SQLTYPE getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(SQLTYPE type) {
		this.firePropertyChange(SQL_TYPE, this.type, type);
		this.type = type;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.firePropertyChange(NAME, this.name, name);
		this.name = name;		
	}
	
	/**
	 * @return
	 */
	public boolean getCharBased() {
		return this.isCharBased;
	}
	
	/**
	 * @param charBased the charBased to set
	 */
	public void setCharBased(boolean isCharBased) {
		this.firePropertyChange(IS_CHAR_BASED, this.isCharBased, isCharBased);
		this.isCharBased = isCharBased;
	}
	
	public String toString() {
		return "Name: " + name + " Type: " + type + " Size: " + columnSize + " Unique: " + isUnique + " PK: " + isPrimaryKey +  " CharBased: " + isCharBased + "\n";
	}

}
