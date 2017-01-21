package com.mrazek.fim.bean;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

@Entity
@Table(name="kategorie")
public class Kategorie
{
	
	@Id
	@SequenceGenerator(name = "seq_contacts", sequenceName = "seq_contacts")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_contacts")
	
	private int id;	
	@Column	
	private String nazev;

	@Transient
	private List<String> clanky;
	
	public Kategorie()
	{}
	
	public Kategorie(int id, String nazev, List<String> clanky)
	{
		super();
		this.id = id;
		this.nazev = nazev;
		this.clanky = clanky;

		
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
	public List<String> getClanky() {
		return clanky;
	}
	public void setClanky(List<String> clanky) {
		this.clanky = clanky;
	}

}
