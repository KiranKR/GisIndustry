package com.google.map.entities;

import java.io.Serializable;
import java.util.List;

public class MapModelBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IndustBean industBean;
	private List<LatLangBean> latLangBeans;
	public IndustBean getIndustBean() {
		return industBean;
	}
	public void setIndustBean(IndustBean industBean) {
		this.industBean = industBean;
	}
	public List<LatLangBean> getLatLangBeans() {
		return latLangBeans;
	}
	public void setLatLangBeans(List<LatLangBean> latLangBeans) {
		this.latLangBeans = latLangBeans;
	}
}
