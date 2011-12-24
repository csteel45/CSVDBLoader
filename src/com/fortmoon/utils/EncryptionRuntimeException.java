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

/**
 * @author Christopher Steel - Director of Engineering Services, JackBe Corporation
 * @since 3 December 2010
 */
public class EncryptionRuntimeException extends RuntimeException {

	public EncryptionRuntimeException(String string,
			GeneralSecurityException gse) {
		super(string, gse);
	}

	private static final long serialVersionUID = 1L;
}
