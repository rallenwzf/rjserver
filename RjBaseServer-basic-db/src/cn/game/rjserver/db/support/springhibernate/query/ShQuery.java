// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space
// Source File Name: Query.java

package cn.game.rjserver.db.support.springhibernate.query;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.type.Type;

public class ShQuery {

	private Class refClass;
	private List paraNames;
	private List paraValues;
	private List paraTypes;
	private List paraOprators;
	private List orderBys;
	private List wheres;
	private boolean defaultOrderBy = true;

	public ShQuery(Class refClass) {
		paraNames = new LinkedList();
		paraValues = new LinkedList();
		paraTypes = new LinkedList();
		paraOprators = new LinkedList();
		orderBys = new LinkedList();
		wheres = new LinkedList();
		setRefClass(refClass);
	}

	public ShQuery() {
		paraNames = new LinkedList();
		paraValues = new LinkedList();
		paraTypes = new LinkedList();
		paraOprators = new LinkedList();
		orderBys = new LinkedList();
	}

	public List getParaNames() {
		return paraNames;
	}

	public void setParaNames(List paraNames) {
		this.paraNames = paraNames;
	}

	public List getParaTypes() {
		return paraTypes;
	}

	public void setParaTypes(List paraTypes) {
		this.paraTypes = paraTypes;
	}

	public List getParaValues() {
		return paraValues;
	}

	public void setParaValues(List paraValues) {
		this.paraValues = paraValues;
	}

	public List getParaOprators() {
		return paraOprators;
	}

	public void setParaOprators(List paraOprators) {
		this.paraOprators = paraOprators;
	}

	public Class getRefClass() {
		return refClass;
	}

	public void setRefClass(Class refClass) {
		this.refClass = refClass;
	}

	public List getWheres() {
		return wheres;
	}

	public void setWheres(List wheres) {
		this.wheres = wheres;
	}

	public void addWhere(String where) {
		wheres.add(where);
	}

	public void addParaName(String paraName) {
		paraNames.add(paraName);
	}

	public void addParaValue(Object paraValue) {
		paraValues.add(paraValue);
	}

	public void addParaType(Type paraType) {
		paraTypes.add(paraType);
	}

	public void addParaOprator(String paraOprator) {
		paraOprators.add(paraOprator);
	}

	public void addOrderBy(String orderBy) {
		orderBys.add(orderBy);
	}

	public List getOrderBys() {
		return orderBys;
	}

	public boolean isDefaultOrderBy() {
		return defaultOrderBy;
	}

	public void setDefaultOrderBy(boolean defaultOrderBy) {
		this.defaultOrderBy = defaultOrderBy;
	}
	
}
