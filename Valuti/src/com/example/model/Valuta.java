package com.example.model;


public class Valuta {

	private Long id;

	private String drzava;
	private int vrednost;

	public Valuta() {
	}

	public Valuta(String name, int v) {
		super();
		this.drzava = name;
		this.vrednost = v;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDrzava() {
		return drzava;
	}

	public void setDrzava(String name) {
		this.drzava = name;
	}

	public int getVrednost() {
		return vrednost;
	}

	public void setVrednost(int v) {
		this.vrednost = v;
	}
}
