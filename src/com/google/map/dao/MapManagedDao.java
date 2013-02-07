package com.google.map.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import com.google.map.entities.IndustBean;
import com.google.map.entities.LatLangBean;
import com.google.map.entities.MapModelBean;
import com.google.map.util.MysqlConnectionProvider;

public class MapManagedDao {

	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet rs = null;

	

	public List<MapModelBean> getCatWise(String whereClause) {
		String sql = "SELECT p.P_ID,p.P_NAME,c.CATG_NAME,p.P_YEAR_CONST,p.P_AREA,p.P_OPEN_SPACE,p.P_OWNER,p.P_PRODUCT,p.P_TYPE FROM property p join marker_head mh on mh.MRKR_HD_PROP_ID = p.P_ID join area a on mh.MRKR_HD_AREA_ID = a.AREA_ID join district d on a.AREA_DIST_ID = d.DIST_ID join category c on mh.MRKR_HD_CATG_ID = c.CATG_ID "+ whereClause +" order by p.P_ID";
		
		IndustBean bean = null;
		MapModelBean mapModel = null;
		List<MapModelBean> mapModelBeans = new ArrayList<MapModelBean>();
		List<LatLangBean> latLngs = null;
		PreparedStatement preparedStatement1 = null;
		try {
			connection = MysqlConnectionProvider.getNewConnection();
			preparedStatement = connection.prepareStatement(sql);
			rs = preparedStatement.executeQuery();
			ResultSet resultSet = null;
			while (rs.next()) {
				mapModel = new MapModelBean();
				bean = new IndustBean();
				bean.setId(rs.getString(1));
				bean.setName(rs.getString(2));
				bean.setCatType(rs.getString(3));
				bean.setYearConst(rs.getString(4));
				bean.setArea(rs.getString(5));
				bean.setOpenSpace(rs.getString(6));
				bean.setOwner(rs.getString(7));
				bean.setProduct(rs.getString(8));
				bean.setType(rs.getString(9));

				String sql1 = "select l.LL_LAT,l.LL_LANG from latlang l where l.LL_P_ID = " + rs.getInt(1);
				latLngs = new ArrayList<LatLangBean>();
				preparedStatement1 = connection.prepareStatement(sql1);

				resultSet = preparedStatement1.executeQuery();
				while (resultSet.next()) {
					latLngs.add(new LatLangBean(resultSet.getDouble(1), resultSet.getDouble(2)));
				}
				mapModel.setIndustBean(bean);
				mapModel.setLatLangBeans(latLngs);
				mapModelBeans.add(mapModel);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return mapModelBeans;
	}

	
	
	
	public List getDropDownList(String tableName, String orderById, String dbFldId, String dbFldValue, String whrClause, int skpDefaultVal) throws SQLException, ClassNotFoundException {
		String dbName = "gisindustry";
		String qry = "SELECT * FROM " + dbName + "." + tableName + " "
				+ whrClause + " ORDER BY " + orderById + "";
		// System.out.println(qry);
		List lsDDList = new ArrayList();
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			con = MysqlConnectionProvider.getNewConnection();
			pst = con.prepareStatement(qry);
			rs = pst.executeQuery();
			SelectItem noneItem = new SelectItem("0", "All");

			if (skpDefaultVal != 1) {
				lsDDList.add(noneItem);
			}
			while (rs.next()) {
				noneItem = new SelectItem(rs.getString(dbFldId),
						rs.getString(dbFldValue));
				lsDDList.add(noneItem);
			}
		} catch (SQLException sqle) {
			throw sqle;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		} finally {
			new MysqlConnectionProvider().releaseConnection(rs, null, pst, con);
		}
		return lsDDList;
	}
}
