package com.smarttaxi.serverside.model;

public class Taxi {

	private int id_taxi;
	private String nomenclatute;
	private String marque;
	private int age;
	
	public Taxi()
	{
		
	}
	
	public Taxi(int id_taxi, String nomenclatute, String marque, int age) {
		super();
		this.id_taxi = id_taxi;
		this.nomenclatute = nomenclatute;
		this.marque = marque;
		this.age = age;
	}
	public int getId_taxi() {
		return id_taxi;
	}
	public void setId_taxi(int id_taxi) {
		this.id_taxi = id_taxi;
	}
	public String getNomenclatute() {
		return nomenclatute;
	}
	public void setNomenclatute(String nomenclatute) {
		this.nomenclatute = nomenclatute;
	}
	public String getMarque() {
		return marque;
	}
	public void setMarque(String marque) {
		this.marque = marque;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	
	
}
