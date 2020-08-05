package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {

	CountryIdMap idMap;
	BordersDAO dao;
	List<Country> countries;
	Graph<Country, DefaultEdge> grafo;
	List<Set<Country>> components;
	
	public Model() {
		dao=new BordersDAO();
	}

	public void creaGrafo( int year) {
		idMap=new CountryIdMap();
		dao.loadAllCountries(idMap);
		
		List<Border> confini=dao.getCountryPairs(idMap, year);
		if(confini.isEmpty())
			throw new RuntimeException("No country pairs for specified year");
		
		grafo=new SimpleGraph<>(DefaultEdge.class);
		for(Border b: confini) {
			grafo.addVertex(b.getC1());
			grafo.addVertex(b.getC2());
			grafo.addEdge(b.getC1(), b.getC2());
		}
		System.out.println("Ci sono "+grafo.vertexSet().size()+" vertici e "+grafo.edgeSet().size()+" archi.");
		countries=new LinkedList<>(grafo.vertexSet());
		ConnectivityInspector ci= new ConnectivityInspector(grafo);
		components= ci.connectedSets();
		System.out.println("Ci sono "+components.size()+" components.");
	}
	
	public List<Country> visitaGrafo(Country source){
		BreadthFirstIterator<Country,DefaultEdge> bfv=new BreadthFirstIterator<>(grafo,source);
		List<Country> result=new LinkedList<>();
		while(bfv.hasNext()) {
			result.add(bfv.next());
		}
		return result;
	}
	
	public List<Country> getCountries(){
		return this.countries;
	}
	
	public Graph<Country, DefaultEdge> getGrafo(){
		return this.grafo;
	}
	
	public int numComponents() {
		return this.components.size();
	}
}
