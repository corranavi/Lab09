package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.CountryIdMap;

public class BordersDAO {

	public void loadAllCountries(CountryIdMap idMap) {

		String sql = "SELECT ccode, StateAbb, StateNme FROM country ORDER BY StateAbb";
		List<Country> result = new ArrayList<Country>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
					Country country=new Country(rs.getInt("ccode"), rs.getString("StateAbb"), rs.getString("StateNme"));
					idMap.get(country);
			}
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Border> getCountryPairs(CountryIdMap idMap, int anno) {
		List<Border> result=new LinkedList<>();
		String sql="SELECT state1no, state2no "+
					"FROM contiguity "+
				    "WHERE conttype=1 and year<=?";
		
		try {
			Connection conn= ConnectDB.getConnection();
			PreparedStatement st=conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs=st.executeQuery();
			while(rs.next()) {
				int c1Code= rs.getInt("state1no");
				int c2Code=rs.getInt("state2no");
				Country c1= idMap.get(c1Code);
				Country c2=idMap.get(c2Code);
				if(c1==null||c2==null) {
					throw new RuntimeException("Errore nel recupero dei country");
				}
				Border border=new Border(c1,c2);
				result.add(border);
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}


	public boolean countryPresenteNellAnno(Country c, int year) {
		String sql="SELECT COUNT(*) as tot " + 
				"FROM contiguity AS c " + 
				"WHERE conttype=1 AND YEAR<=? AND (c.state1no=?|| c.state2no=?)";
		try {
			Connection conn= ConnectDB.getConnection();	
			PreparedStatement st=conn.prepareStatement(sql);
			st.setInt(1, year);
			st.setInt(2, c.getCod());
			st.setInt(3, c.getCod());
			ResultSet rs= st.executeQuery();
			if(rs.next()) {
				if(rs.getInt("tot")>0)
					return true;
			}
			conn.close();
		} catch (SQLException e) {
			System.out.println("QUALCOSA E' ANDATO STORTO");
			e.printStackTrace();
		}
		return false;
	}
}
