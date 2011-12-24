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

import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;


/**
 * @author Christopher Steel - Director of Engineering Services, JackBe Corporation
 * @since 3 December 2010
 */
public class SymmetricCypher implements Cypher {

	private String cipherTransformation;
	private String keyAlgorithm;
	private static String salt = "hmsjQrbbMlWZ3ajIape55w==";
	private SecretKeySpec secretKeySpec;

	public SymmetricCypher() {
		cipherTransformation = "AES";
		keyAlgorithm = "AES";
		secretKeySpec = new SecretKeySpec(Base64.decodeBase64(salt.getBytes()), keyAlgorithm);
	}

	public String decypher(String cipher) {
		try {
			Cipher jceCipher = Cipher.getInstance(cipherTransformation);
			jceCipher.init(2, secretKeySpec);
			byte ciphertext[] = cipher.getBytes();
			byte decodedText[] = Base64.decodeBase64(ciphertext);
			byte cleartext[] = jceCipher.doFinal(decodedText);
			String res = new String(cleartext);
			
			return res;
			
		} catch (GeneralSecurityException gse) {
			throw new EncryptionRuntimeException((new StringBuilder(
					"Failed to decrypt: ")).append(cipher).append(
					" exception: ").append(gse.getMessage()).toString(), gse);
		}
	}

	public String encypher(String data) {
		byte cleartext[] = data.getBytes();
		try {
			Cipher jceCipher = Cipher.getInstance(cipherTransformation);
			jceCipher.init(1, secretKeySpec);
			byte ciphertext[] = jceCipher.doFinal(cleartext);
			byte encodedText[] = Base64.encodeBase64(ciphertext);
			
			return new String(encodedText);
			
		} catch (GeneralSecurityException gse) {
			throw new EncryptionRuntimeException("Failed to encrypt", gse);
		}
	}

	public static void main(String args[]) throws Exception {
		SymmetricCypher cypher = new SymmetricCypher();
		String str = "ThisIsAPasswordString";
		String encryptedStr = cypher.encypher(str);
		System.out.println((new StringBuilder("encrypted string = ")).append(
				encryptedStr).toString());
		String decryptedStr = cypher.decypher(encryptedStr);
		System.out.println((new StringBuilder("Decrypted str = ")).append(
				decryptedStr).toString());
	}

}
