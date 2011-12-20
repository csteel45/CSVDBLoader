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

/**
 * @author Christopher Steel - FortMoon Consulting, Inc.
 *
 * @since Dec 16, 2011 10:45:02 PM
 */
public class ColumnBean {
	private int size = 1;
	private boolean isUnique = true;
	private boolean isIndexed = false;
	private boolean isNullable = false;
	private boolean isPrimaryKey = false;
	private SQLTYPE type = SQLTYPE.NULL;
	private String name;
	private boolean isCharBased = true;
	

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
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
		this.name = name;
		
	}
	
	public String toString() {
		return "Name: " + name + " Type: " + type + " Size: " + size + " Unique: " + isUnique + " PK: " + isPrimaryKey +  " CharBased: " + isCharBased + "\n";
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
	public void setCharBased(boolean charBased) {
		this.isCharBased = charBased;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}




}
