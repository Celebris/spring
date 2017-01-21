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
@Table(name="diskuse")
public class Diskuse {

	@Id
	@SequenceGenerator(name = "seq_contacts", sequenceName = "seq_contacts")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_contacts")
	private int id;
	
	@Column	
	private String text;	
	
	@Column	
	private Date datum;	 
	

	@ManyToOne
	@JoinColumn(name ="autor")
	private Uzivatele autor;
	
	@ManyToOne
	@JoinColumn(name ="clanek")
	private Clanky clanek;
	
	
	
	public Diskuse(){
		
	}
	
	public Diskuse(int id, Uzivatele autor, Clanky clanek, String text, Date date)
	{
		super();
		this.id = id;
		this.autor = autor;
		this.clanek = clanek;
		this.text = text;
		this.datum = date;
	}
	
	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Clanky getClanek() {
		return clanek;
	}

	public void setClanek(Clanky clanek) {
		this.clanek = clanek;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}
}

