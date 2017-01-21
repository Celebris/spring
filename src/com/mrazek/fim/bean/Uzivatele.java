package com.mrazek.fim.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="Uzivatele")
public class Uzivatele
{
	
	@Id
	@SequenceGenerator(name = "seq_contacts", sequenceName = "seq_contacts")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_contacts")
	private int id;	
	@Column(name="nick")
	private String nick;
	@Column(name="heslo")
	private String heslo; 
	@Column(name="email")
	private String email;
	@Column(name="admin")
	private int admin;
	
	
	@Transient
    private String heslo2;

	

	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	} 
	
	public Uzivatele()
	{}
	
	public Uzivatele(int id, String nick, String heslo, String email, int admin)
	{
		super();
		this.id = id;
		this.nick = nick;
		this.heslo = heslo;
		this.email = email;
		this.admin = admin;

		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getHeslo() {
		return heslo;
	}
	public void setHeslo(String heslo) {
		this.heslo = heslo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getHeslo2() {
		return heslo2;
	}

	public void setHeslo2(String heslo2) {
		this.heslo2 = heslo2;
	}

	public int getAdmin() {
		return admin;
	}

	public void setAdmin(int admin) {
		this.admin = admin;
	}

	
	
}

