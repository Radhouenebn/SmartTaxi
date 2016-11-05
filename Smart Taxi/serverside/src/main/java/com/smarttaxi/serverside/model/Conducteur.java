package com.smarttaxi.serverside.model;

public class Conducteur extends User{
	
private	int num_permis;
private String Statut_compte;
private	int est_dispo;
private	int en_service;
private	Taxi taxi;
private String lat;
private String lon;	
	
	public Conducteur() {
		super();
	}
	
	
	
	public Conducteur(String username, String password, String fullname, String email, long phone, int num_permis,
			String statut_compte, int est_dispo, int en_service, Taxi taxi, String lat, String lon) {
		super(username, password, fullname, email, phone);
		this.num_permis = num_permis;
		this.Statut_compte = statut_compte;
		this.est_dispo = est_dispo;
		this.en_service = en_service;
		this.taxi = taxi;
		this.lat = lat;
		this.lon = lon;
	}



	public String getLat() {
		return lat;
	}



	public void setLat(String lat) {
		this.lat = lat;
	}



	public String getLon() {
		return lon;
	}



	public void setLon(String lon) {
		this.lon = lon;
	}



	public int getNum_permis() {
		return num_permis;
	}
	public void setNum_permis(int num_permis) {
		this.num_permis = num_permis;
	}
	public String getStatut_compte() {
		return Statut_compte;
	}
	public void setStatut_compte(String statut_compte) {
		this.Statut_compte = statut_compte;
	}
	public int getEst_dispo() {
		return est_dispo;
	}
	public void setEst_dispo(int est_dispo) {
		this.est_dispo = est_dispo;
	}
	public int getEn_service() {
		return en_service;
	}
	public void setEn_service(int en_service) {
		this.en_service = en_service;
	}
	public Taxi getTaxi() {
		return taxi;
	}
	public void setTaxi(Taxi taxi) {
		this.taxi = taxi;
	}



}
