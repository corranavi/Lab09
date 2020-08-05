package it.polito.tdp.borders.model;

public class Country {
	private int cod;
	private String stateAbb;
	private String stateNme;
	
	public Country(int cod, String stateAbb, String stateNme) {
		super();
		this.cod = cod;
		this.stateAbb = stateAbb;
		this.stateNme = stateNme;
	}

	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public String getStateAbb() {
		return stateAbb;
	}

	public void setStateAbb(String stateAbb) {
		this.stateAbb = stateAbb;
	}

	public String getStateNme() {
		return stateNme;
	}

	public void setStateNme(String stateNme) {
		this.stateNme = stateNme;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cod;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		if (cod != other.cod)
			return false;
		return true;
	}
	
	public String toString() {
		return this.cod+" "+this.stateNme+" ("+this.stateAbb+")";
	}
	
	
}
