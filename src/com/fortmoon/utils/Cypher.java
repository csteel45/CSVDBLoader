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
public interface Cypher {

	public abstract String decypher(String s);

	public abstract String encypher(String s);
}
