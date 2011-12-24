/*
 * Copyright (c) 2006
 * JackBe Corporation, 4600 North Park Avenue, Suite 200, Chevy Chase, MD 20815.
 * All Rights Reserved.
 *
 * This software is the confidential and proprietary information of JackBe
 * Corporation ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with JackBe.
 *
 * JACKBE MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE, OR NON-INFRINGEMENT. JACKBE SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING
 * THIS SOFTWARE OR ITS DERIVATIVES.
 */
package com.fortmoon.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 * @author Christopher Steel - Director of Engineering Services, JackBe Corporation
 * @since 3 December 2010
 */
public class PropertyList extends ArrayList<String> {

	private static final long serialVersionUID = 1L;
	private String fileName = null;

	public PropertyList() {
		super();
	}

	public String getProperty(String name) {
		int index = getIndexOf(name);
		if (index >= 0) {
			String item = this.get(index);
			String value = item.substring(item.indexOf("=") + 1);
			return value;
		}
		return null;
	}
	
	public void setProperty(String name, String value) {
		System.out.println("PropertyList.setProperty: " + name + "=" + value);
		int index = getIndexOf(name);
		System.out.println("PropertyList.setProperty index: " + index);
		if (index >= 0) {
			this.remove(index);
			System.out.println("ADDING: " + name + "=" + value);

			this.add(index, (name + "=" + value));
		} 
		else {
			System.out.println("Adding: " + name + "=" + value);
			this.add(name + "=" + value);
		}
	}

	protected int getIndexOf(String name) {
		ListIterator<String> li = this.listIterator();
		while (li.hasNext()) {
			String item = li.next();
			if (item.contains("=") && !item.startsWith("#")) {
				String propName = item.substring(0, item.indexOf("="));
				if (propName.equals(name)) {
					int index = this.lastIndexOf(item);
					// System.out.println("index = " + index);
					return index;
				}
			}
		}
		return -1;
	}
	
	public String get(String str) {
		ListIterator<String> li = this.listIterator();
		while (li.hasNext()) {
			String item = li.next();
			if(str.equals(item)) {
				return item.substring(item.indexOf("=") + 1);
			}
		}
		return null;
	}

	protected int getXmlIndexOf(String name) {
		ListIterator<String> li = this.listIterator();
		while (li.hasNext()) {
			String item = li.next();
			if (!item.trim().startsWith("<!--") && item.contains(name)) {
				return this.lastIndexOf(item);
			}
		}
		return -1;
	}

	public void changeXmlClass(String oldClass, String newClass) {
		System.out.println("Searching for line:    " + oldClass);

		int index = getXmlIndexOf(oldClass);
		while (index >= 0) {
			String line = this.remove(index);
			System.out.println("Changing old class line:    " + line);
			line = line.replaceAll(oldClass, newClass);
			System.out.println("Changing to new class line: " + line);
			this.add(index, line);
			index = getXmlIndexOf(oldClass);
		}
//		save();

	}

	protected void read(InputStream in) {
		read(new InputStreamReader(in));
	}
	
	protected void read(Reader r) {
		String line = null;
		BufferedReader in = null;
		try {
			in = new BufferedReader(r);
			while ((line = in.readLine()) != null) {
				add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				/* ignore */
			}
		}

	}

	public String toString() {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("[");
		ListIterator<String> li = this.listIterator();
		while (li.hasNext()) {
			String item = li.next();
			if (item.contains("=") && !item.startsWith("#")) {
				strBuf.append(item + ", ");
			}
		}
		strBuf.append("]");
		return strBuf.toString();
	}

}
