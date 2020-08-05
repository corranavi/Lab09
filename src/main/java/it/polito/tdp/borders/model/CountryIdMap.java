package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.Map;

public class CountryIdMap {

	Map<Integer, Country> map;
	
	public CountryIdMap() {
		map=new HashMap<>();
	}
	
	public Country get(Integer i) {
		return map.get(i);
	}
	
	public Country get(Country country) {
		Country old= map.get(country.getCod());
		if(old==null) {
			map.put(country.getCod(), country);
			return country;
		}
		return old;
	}
	
	public void put(int cod, Country country) {
		map.put(cod, country);
	}
}
