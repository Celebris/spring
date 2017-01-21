package com.mrazek.fim.bean;

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
@Table(name="hodnoceni")
public class Hodnoceni {

	@Id
	@SequenceGenerator(name = "seq_contacts", sequenceName = "seq_contacts")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_contacts")
	private int id;
	
	@Column	
	private int znamka;	
	
	@ManyToOne
	@JoinColumn(name ="autor")
	private Uzivatele autor;
	
	@ManyToOne
	@JoinColumn(name ="clanek")
	private Clanky clanek;
	
	
	
	public Hodnoceni(){
		
	}
	
	public Hodnoceni(int id, Uzivatele autor, Clanky clanek, int hodnoceni)
	{
		super();
		this.id = id;
		this.autor = autor;
		this.clanek = clanek;
		this.znamka = hodnoceni;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getZnamka() {
		return znamka;
	}

	public void setZnamka(int znamka) {
		this.znamka = znamka;
	}

	public Uzivatele getAutor() {
		return autor;
	}

	public void setAutor(Uzivatele autor) {
		this.autor = autor;
	}

	public Clanky getClanek() {
		return clanek;
	}

	public void setClanek(Clanky clanek) {
		this.clanek = clanek;
	}
	
	
	
	
	
}

