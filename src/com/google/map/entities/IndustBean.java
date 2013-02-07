package com.google.map.entities;

import java.io.Serializable;

public class IndustBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String catType;
	private String type;
	private String yearConst;
	private String area;
	private String openSpace;
	private String owner;
	private String product;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getYearConst() {
		return yearConst;
	}
	public void setYearConst(String yearConst) {
		this.yearConst = yearConst;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getOpenSpace() {
		return openSpace;
	}
	public void setOpenSpace(String openSpace) {
		this.openSpace = openSpace;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getCatType() {
		return catType;
	}
	public void setCatType(String catType) {
		this.catType = catType;
	}

}