package it.polito.tdp.borders.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();

		System.out.println("TestModel -- TODO");
		
//		System.out.println("Creo il grafo relativo al 2000");
		model.creaGrafo(2000);
		List<Country> componente=model.visitaGrafo(model.countries.get(0));
		System.out.println("I paesi nello stesso componente di "+model.countries.get(0)+" sono: ");
		for(Country c: componente) {
			if(c!=componente.get(0)) {
				System.out.println(c);
			}
		}
		
//		List<Country> countries = model.getCountries();
//		System.out.format("Trovate %d nazioni\n", countries.size());

//		System.out.format("Numero componenti connesse: %d\n", model.getNumberOfConnectedComponents());
		
//		Map<Country, Integer> stats = model.getCountryCounts();
//		for (Country country : stats.keySet())
//			System.out.format("%s %d\n", country, stats.get(country));		
		
	}

}
