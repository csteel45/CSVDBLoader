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

import java.io.Reader;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Properties;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import org.apache.log4j.Logger;


/**
 * This class loads properties from the user preferences implementation and encrypts/decrypts
 * properties with property names containing 'password'.
 * @author Christopher Steel - Director of Engineering Services, JackBe Corporation
 * @since 3 December 2010
 */
public class SecureProperties extends Properties {
	private static final long serialVersionUID = 1L;
	public static final String ENCRYPTION_PREFIX = "{ENC}";
	private transient Preferences prefs = Preferences.userNodeForPackage(getClass());
	private transient Cypher cypher;
	private transient Logger log = Logger.getLogger(getClass());
	private PropertyList propertyList;

	public SecureProperties() {
		log.debug("called.");

		cypher = new SymmetricCypher();
		propertyList = new PropertyList();
		try {
			prefs.sync();
		}
		catch (BackingStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void load(Reader reader) {
		propertyList.read(reader);
	}
	
	public void load() throws Exception {
		log.debug("called");
			
		prefs.sync();
		propertyList.clear();
		
		for(String key : prefs.keys()) {
			this.propertyList.setProperty(key, prefs.get(key, ""));
		}
		Collections.sort(propertyList); 			

	}
	
	public void store() throws Exception {
//		prefs.sync();
		prefs.clear();

		for(Object key : propertyList) {
			String name = (String)key;
			log.info("Getting index for: " + name);
			int index = propertyList.getIndexOf(name);
			String value = propertyList.get(name);
			prefs.put(name.substring(0, name.indexOf("=")), value);
		}
		prefs.flush();
		
	}
	
	public String getProperty(String name) {
		if(propertyList.getProperty(name) == null)
			return null;
		return decrypt(propertyList.getProperty(name));
	}
	
	public Object setProperty(String name, String value) {
		if(name.toLowerCase().contains("password")) {
			value = encrypt(value);
		}
System.out.println("SecureProperties setProperty: " + name  + "=" + value);
		propertyList.setProperty(name, value);
		// Should return old value but who really ever did anything with this?
		return null;
	}

	protected String encrypt(String valueToEncrypt) {
		String encVal = cypher.encypher(valueToEncrypt);
		String resultVal = (new StringBuilder(String.valueOf("{ENC}"))).append(
				encVal).toString();
		return resultVal;
	}
	
	protected String decrypt(String encryptedValue) {
		if (encryptedValue.startsWith("{ENC}")) {
			String strippedValue = encryptedValue.substring("{ENC}".length());
			log.debug((new StringBuilder("stripped value = ")).append(
					strippedValue).toString());
			return cypher.decypher(strippedValue);
		}
		log.debug((new StringBuilder("Nothing to decrypt: ")).append(
				encryptedValue).toString());
		return encryptedValue;
	}
	
	public Enumeration propertyNames() {
		return this.keys();
	}

	public void setCypher(Cypher cypher) {
		log.debug((new StringBuilder("setCypher called with: ")).append(
				cypher.getClass().getName()).toString());
		this.cypher = cypher;
	}
	
	public String toString() {
		return this.propertyList.toString();
	}
	
	public void clear() {
		this.propertyList.clear();
	}

	public static void main(String args[]) throws Exception {
		SecureProperties props = new SecureProperties();
		System.out.println("Loaded properties: " + props);
		props.setProperty("Name1", "Val1");
		props.setProperty("Name2Password", "secret");
		props.setProperty("Name2", "Val2");
		System.out.println("Set properties: " + props);
		props.store();
		props.clear();
		System.out.println("Cleared properties: " + props);
		props.load();
		System.out.println("ReLoaded properties: " + props);
		System.out.println("Name2Password getProperty = : " + props.getProperty("Name2Password"));
		props.clear();
		props.store();
		props.load();
		System.out.println("Cleared stored properties: " + props);
	}

}
