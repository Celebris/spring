package com.mrazek.fim.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="clanky")
public class Clanky {

	@Id
	@SequenceGenerator(name = "seq_contacts", sequenceName = "seq_contacts")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_contacts")
	private int id;
	

	@Column	
	private String nazev;	
	
	@Column	
	private String text;	
	
	@Column	
	private Date datum;	
	
	
	@ManyToOne
	@JoinColumn(name ="autor")
	private Uzivatele autor;
	
	@ManyToOne
	@JoinColumn(name ="kategorie")
	private Kategorie kategorie;
	
	
	
	public Clanky(){
		
	}
	
	public Clanky(int id, Uzivatele autor, String nazev, String text, Kategorie kategorie, Date datum)
	{
		super();
		this.id = id;
		this.autor = autor;
		this.nazev = nazev;
		this.text = text;
		this.kategorie = kategorie;
		this.datum = datum;
	}
	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNazev() {
		return nazev;
	}

	public void setNazev(String nazev) {
		this.nazev = nazev;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Uzivatele getAutor() {
		return autor;
	}

	public void setAutor(Uzivatele autor) {
		this.autor = autor;
	}

	public Kategorie getKategorie() {
		return kategorie;
	}

	public void setKategorie(Kategorie kategorie) {
		this.kategorie = kategorie;
	}
	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}
	
}

