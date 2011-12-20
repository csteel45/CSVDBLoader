/*
 * @(#)SQLTYPE.java $Date: Dec 16, 2011 10:50:30 PM $
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

public enum SQLTYPE {
	DATE("DATE"), INTEGER("INTEGER"), TIME("TIME"), BOOL("BOOL"), CHAR("CHAR"), FLOAT("FLOAT"), DOUBLE("DOUBLE"), DATETIME("DATETIME"), BIGINT("BIGINT"), VARCHAR("VARCHAR"), NULL("NULL"), BLOB("BLOB"), LONGBLOB("LONGBLOB");
	private String str;
	
	private SQLTYPE(String str) {
		this.str = str;
	}
	
	public int getValue() {
		switch (this) {
			case DATETIME:
				return 44;
			case DATE:
				return 40;
			case TIME:
				return 36;
			case INTEGER:
				return 32;
			case BIGINT:
				return 28;
			case FLOAT:
				return 24;
			case DOUBLE:
				return 20;
			case BOOL:
				return 16;
			case CHAR:
				return 12;
			case VARCHAR:
				return 8;
			case BLOB:
				return 4;
			case LONGBLOB:
				return 1;
			case NULL:
				return 100;
		}
		return 100;
	}

	public String toString() {
		return this.str;
	}

}