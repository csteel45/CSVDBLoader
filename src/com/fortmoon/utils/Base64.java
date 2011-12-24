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

/**
 * @author Christopher Steel - Director of Engineering Services, JackBe Corporation
 * @since 3 December 2010
 */
public class Base64 {
	
	public Base64() {
	}

	public static String encode(String s) {
		return getString(encode(getBinaryBytes(s)));
	}

	public static byte[] encode(byte abyte0[]) {
		int i = abyte0.length;
		StringBuffer stringbuffer = new StringBuffer((i / 3 + 1) * 4);
		for (int j = 0; j < i; j++) {
			int k = abyte0[j] >> 2 & 0x3f;
			stringbuffer
					.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
							.charAt(k));
			k = abyte0[j] << 4 & 0x3f;
			if (++j < i)
				k |= abyte0[j] >> 4 & 0xf;
			stringbuffer
					.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
							.charAt(k));
			if (j < i) {
				int l = abyte0[j] << 2 & 0x3f;
				if (++j < i)
					l |= abyte0[j] >> 6 & 3;
				stringbuffer
						.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
								.charAt(l));
			} else {
				j++;
				stringbuffer.append('=');
			}
			if (j < i) {
				int i1 = abyte0[j] & 0x3f;
				stringbuffer
						.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
								.charAt(i1));
			} else {
				stringbuffer.append('=');
			}
		}

		return getBinaryBytes(stringbuffer.toString());
	}

	public static String decode(String s) {
		return getString(decode(getBinaryBytes(s)));
	}

	public static byte[] decode(byte abyte0[]) {
		int i = abyte0.length;
		StringBuffer stringbuffer = new StringBuffer((i * 3) / 4);
		for (int j = 0; j < i; j++) {
			int k = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
					.indexOf(abyte0[j]);
			j++;
			int l = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
					.indexOf(abyte0[j]);
			k = k << 2 | l >> 4 & 3;
			stringbuffer.append((char) k);
			if (++j < i) {
				k = abyte0[j];
				if (61 == k)
					break;
				k = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
						.indexOf((char) k);
				l = l << 4 & 0xf0 | k >> 2 & 0xf;
				stringbuffer.append((char) l);
			}
			if (++j >= i)
				continue;
			l = abyte0[j];
			if (61 == l)
				break;
			l = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
					.indexOf((char) l);
			k = k << 6 & 0xc0 | l;
			stringbuffer.append((char) k);
		}

		return getBinaryBytes(stringbuffer.toString());
	}

	private static String getString(byte abyte0[]) {
		StringBuffer stringbuffer = new StringBuffer();
		for (int i = 0; i < abyte0.length; i++)
			stringbuffer.append((char) abyte0[i]);

		return stringbuffer.toString();
	}

	private static byte[] getBinaryBytes(String s) {
		byte abyte0[] = new byte[s.length()];
		for (int i = 0; i < abyte0.length; i++)
			abyte0[i] = (byte) s.charAt(i);

		return abyte0;
	}

	public static void main(String args[]) {
		String s;
		if (args.length > 0)
			s = args[0];
		else
			s = "Now is the time for all good men";
		System.out.println("Encoding string [" + s + "]");
		s = encode(s);
		System.out.println("Encoded string  [" + s + "]");
		s = decode(s);
		System.out.println("Decoded string  [" + s + "]");
		System.out.println();
		byte abyte0[] = getBinaryBytes(s);
		System.out.println("Encoding bytes  [" + getString(abyte0) + "]");
		abyte0 = encode(abyte0);
		System.out.println("Encoded bytes   [" + getString(abyte0) + "]");
		abyte0 = decode(abyte0);
		System.out.println("Decoded bytes   [" + getString(abyte0) + "]");
	}


}
