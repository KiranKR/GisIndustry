package com.google.map.demo;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.primefaces.model.map.Polygon;
import org.primefaces.model.map.Polyline;

import com.google.map.dao.MapManagedDao;
import com.google.map.entities.IndustBean;
import com.google.map.entities.LatLangBean;
import com.google.map.entities.MapModelBean;

@ManagedBean(name = "mapBean")
@SessionScoped
public class MapManagedBean implements Serializable {

	
	private static final long serialVersionUID = 3746357493688537447L;
	
	public MapManagedDao dao = new MapManagedDao();
	private MapModel polygonModel;
	private Polygon selectedPolygon;
	private List<Polygon> polygons;
	private IndustBean bean = null;
	private ArrayList<IndustBean> beans = new ArrayList<IndustBean>();
	private List<MapModelBean> modelBeans = new ArrayList<MapModelBean>();
	private String zoom = "13";
	private String cntLat = "12.971598700000000000";
	private String cntLang = "77.594562699999980000";
	private String search = "";
	private String userName;
	private String passward;
	private String error = "";
	private String distId = "";
	private String areaId = "";
	private String catId = "";
	private String typeId = "";
	private List distList = new ArrayList();
	private List areaList = new ArrayList();
	private List catList = new ArrayList();
	private List typeList = new ArrayList();
	private String color = "#FF9900";

	public MapManagedBean() {
		try {
			distList = dao.getDropDownList("district", "DIST_ID", "DIST_ID", "DIST_NAME", "", 1);
			catList = dao.getDropDownList("category", "CATG_ID", "CATG_ID", "CATG_NAME", "where CATG_LEVEL = 2", 1);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void getAreasList(){
		try {
			areaList = dao.getDropDownList("area", "AREA_ID", "AREA_ID", "AREA_NAME", "where AREA_DIST_ID = " + distId, 1);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void getTypList(){
		try {
			typeList = dao.getDropDownList("category", "CATG_ID", "CATG_ID", "CATG_NAME", "where CATG_PARENT = " + catId, 1);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public String showOnMap() {
		String whereClause = "";
		polygonModel = null;
		polygonModel = new DefaultMapModel();
		if (distId.equals("0")) {
			whereClause = "";
		} else if (areaId.equals("0")) {
			whereClause = " where a.AREA_DIST_ID = " + distId;
		}else{
			whereClause = " where a.AREA_DIST_ID = "+distId+" and a.AREA_ID = " + areaId;
		}

		if (catId.equals("0")) {
			
		} else if (typeId.equals("0")) {
			if(!whereClause.equals("")){
				whereClause = whereClause + " and c.CATG_PARENT = " + catId;
			}else{
				whereClause = " where c.CATG_ID = " + catId;
			}
		}else{
			if(!whereClause.equals("")){
				whereClause = whereClause + " and c.CATG_ID = " + typeId;
			}else{
				whereClause = " where c.CATG_ID = " + typeId;
			}
		}
		
		modelBeans = dao.getCatWise(whereClause);
		IndustBean industBean = null;
		for (MapModelBean bean : modelBeans) {
			List<LatLng> latLngs = new ArrayList<LatLng>();
			industBean = bean.getIndustBean();
			for (LatLangBean latLng : bean.getLatLangBeans()) {
				latLngs.add(new LatLng(latLng.getLat(), latLng.getLang()));
			}
			if (industBean.getType().equals("0")) {
				Polygon polygon = new Polygon(latLngs, industBean);
				color = "#FF0033";
				polygon.setStrokeColor(color);
				polygon.setFillColor(color);
				polygon.setStrokeOpacity(0.7);
				polygon.setFillOpacity(0.5);
				polygon.setId(industBean.getName());
				polygonModel.addOverlay(polygon);
			} else if (industBean.getType().equals("1")) {
				color = "#424242";
				Polyline polyline = new Polyline(latLngs);
				polyline.setStrokeColor(color);
				polyline.setStrokeWeight(8);
				polyline.setStrokeOpacity(0.7);
				polygonModel.addOverlay(polyline);
			} else if (industBean.getType().equals("2")) {
				Marker marker = new Marker(new LatLng(latLngs.get(0).getLat(),
						latLngs.get(0).getLng()), industBean.getName());
				polygonModel.addOverlay(marker);
			}
		}
		return "/googlemappage.xhtml";
	}
	
	
	
	public String checkUser() {
		polygonModel = new DefaultMapModel();
		if (userName.equals("admin") && passward.equals("admin")) {
			error = "";
			return "/googlemappage.xhtml";
		} else {
			error = "Enter Correct Username and Passward";
			return "/login.xhtml";
		}
	}

	public String logout() {
		userName = "";
		passward = "";
		error = "";
		return "/login.xhtml";
	}

	public String getCat(String cat) {
		polygonModel = new DefaultMapModel();
		if (cat.equals("CAT-1")) {
			cntLat = "12.917892";
			cntLang = "76.932154";
			zoom = "18";
			color = "#FF0033";
			polygonModel.addOverlay(new Marker(new LatLng(12.917257, 76.931924), "Marker"));
		} else if (cat.equals("CAT-2")) {
			cntLat = "12.917892";
			cntLang = "76.932154";
			zoom = "18";
			color = "#9966FF";
		}
		modelBeans = dao.getCatWise(cat);
		IndustBean industBean = null;
		for (MapModelBean bean : modelBeans) {
			List<LatLng> latLngs = new ArrayList<LatLng>();
			industBean = bean.getIndustBean();
			for (LatLangBean latLng : bean.getLatLangBeans()) {
				latLngs.add(new LatLng(latLng.getLat(), latLng.getLang()));
			}
			Polygon polygon = new Polygon(latLngs, industBean);
			if (industBean.getType().equals("CAT-1")) {
				cntLat = "12.917892";
				cntLang = "76.932154";
				zoom = "18";
				color = "#FF0033";
			} else if (industBean.getType().equals("CAT-2")) {
				cntLat = "12.917892";
				cntLang = "76.932154";
				zoom = "18";
				color = "#9966FF";
			}
			polygon.setStrokeColor(color);
			polygon.setFillColor(color);
			polygon.setStrokeOpacity(0.7);
			polygon.setFillOpacity(0.5);
			polygon.setId(industBean.getName());
			polygonModel.addOverlay(polygon);
		}
		return "/googlemappage.xhtml";
	}

	
	
	

	/*public String searchByName() {
		getLake(search);
		return "/googlemappage.xhtml";
	}*/

	public void onPolygonSelect(OverlaySelectEvent event) {
		selectedPolygon = (Polygon) event.getOverlay();
		bean = (IndustBean) selectedPolygon.getData();
	}

	public MapModel getPolygonModel() {
		return polygonModel;
	}

	public void setPolygonModel(MapModel polygonModel) {
		this.polygonModel = polygonModel;
	}

	public void setSelectedPolygon(Polygon selectedPolygon) {
		this.selectedPolygon = selectedPolygon;
	}

	public Polygon getSelectedPolygon() {
		return selectedPolygon;
	}

	public void setPolygons(List<Polygon> polygons) {
		this.polygons = polygons;
	}

	public List<Polygon> getPolygons() {
		return polygons;
	}

	

	public String getCntLat() {
		return cntLat;
	}

	public void setCntLat(String cntLat) {
		this.cntLat = cntLat;
	}

	public String getCntLang() {
		return cntLang;
	}

	public void setCntLang(String cntLang) {
		this.cntLang = cntLang;
	}

	public List<MapModelBean> getModelBeans() {
		return modelBeans;
	}

	public void setModelBeans(List<MapModelBean> modelBeans) {
		this.modelBeans = modelBeans;
	}

	public String getZoom() {
		return zoom;
	}

	public void setZoom(String zoom) {
		this.zoom = zoom;
	}

	

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassward() {
		return passward;
	}

	public void setPassward(String passward) {
		this.passward = passward;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getDistId() {
		return distId;
	}

	public void setDistId(String distId) {
		this.distId = distId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getCatId() {
		return catId;
	}

	public void setCatId(String catId) {
		this.catId = catId;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public List getDistList() {
		return distList;
	}

	public void setDistList(List distList) {
		this.distList = distList;
	}

	public List getAreaList() {
		return areaList;
	}

	public void setAreaList(List areaList) {
		this.areaList = areaList;
	}

	public List getCatList() {
		return catList;
	}

	public void setCatList(List catList) {
		this.catList = catList;
	}

	public List getTypeList() {
		return typeList;
	}

	public void setTypeList(List typeList) {
		this.typeList = typeList;
	}

	public IndustBean getBean() {
		return bean;
	}

	public void setBean(IndustBean bean) {
		this.bean = bean;
	}

	public ArrayList<IndustBean> getBeans() {
		return beans;
	}

	public void setBeans(ArrayList<IndustBean> beans) {
		this.beans = beans;
	}

}
