/*
 * Copyright (©) 2011 FortMoon Consulting
 * All Rights Reserved.
 *
 * This software is the confidential and proprietary information of FortMoon
 * Consulting Corporation ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with FortMoon.
 *
 * FORTMOON MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE, OR NON-INFRINGEMENT. FORTMOON SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING
 * THIS SOFTWARE OR ITS DERIVATIVES.
 */

package com.fortmoon.cvsdbloader.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.apache.log4j.Logger;

import com.fortmoon.utils.ColumnBean;

/**
 * @author Christopher Steel - FortMoon Consulting
 * 
 * @since Dec 21, 2011 8:49:03 AM
 * @version 1.0
 */
public class ColumnModel extends ArrayList<ColumnBean> implements ListModel {
	private static final long serialVersionUID = 1L;
	ArrayList<ColumnBean> columns = new ArrayList<ColumnBean>();
	ArrayList listeners = new ArrayList();
	private transient Logger log = Logger.getLogger(ColumnModel.class.getName());

	public ColumnModel() {
	}

	public Object getElementAt(int index) {
		return get(index);
	}

	public int getSize() {
		return size();
	}

	public void removeListDataListener(javax.swing.event.ListDataListener l) {
		listeners.remove(l);
	}

	public void addListDataListener(javax.swing.event.ListDataListener l) {
		listeners.add(l);
	}

	void notifyListeners() {
		// no attempt at optimziation
		ListDataEvent le = new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0, getSize());
		for (int i = 0; i < listeners.size(); i++) {
			((ListDataListener) listeners.get(i)).contentsChanged(le);
		}
	}

	// REMAINDER ARE OVERRIDES JUST TO CALL NOTIFYLISTENERS
	@Override
	public boolean add(ColumnBean o) {
		boolean b = super.add(o);
		if (b)
			notifyListeners();
		return b;
	}

	public void add(int index, ColumnBean element) {
		super.add(index, element);
		notifyListeners();
	}

	public boolean addAll(Collection o) {
		boolean b = super.addAll(o);
		if (b)
			notifyListeners();
		return b;
	}

	public void clear() {
		super.clear();
		notifyListeners();
	}

	public ColumnBean remove(int i) {
		ColumnBean o = super.remove(i);
		notifyListeners();
		return o;
	}

	public boolean remove(Object o) {
		boolean b = super.remove(o);
		if (b)
			notifyListeners();
		return b;
	}

	public ColumnBean set(int index, ColumnBean element) {
		ColumnBean o = super.set(index, element);
		notifyListeners();
		return o;
	}

}
