package com.google.map.entities;

import java.io.Serializable;

public class LatLangBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double lat;
	private double lang;

	public LatLangBean(double lat, double lang) {
		this.lat = lat;
		this.lang = lang;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLang() {
		return lang;
	}

	public void setLang(double lang) {
		this.lang = lang;
	}

}
