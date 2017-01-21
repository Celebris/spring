package com.mrazek.fim.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mrazek.fim.bean.Uzivatele;

@Repository
@Transactional
public class UzivateleDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	public Uzivatele getByUzivateleID(int id)
	{
		return (Uzivatele) sessionFactory.getCurrentSession().get(Uzivatele.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Uzivatele> getByUzivateleNickHeslo(String nick, String heslo)
	{
		return sessionFactory.getCurrentSession().createQuery("from Uzivatele where nick = '"+nick+"' AND heslo='"+heslo+"'").list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Uzivatele> getBynick(String nick)
	{
		return sessionFactory.getCurrentSession().createQuery("from Uzivatele where nick = '"+nick+"'").list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Uzivatele> getByEmail(String email)
	{
		return sessionFactory.getCurrentSession().createQuery("from Uzivatele where email = '"+email+"'").list();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Uzivatele> getAllUzivatele()
	{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Uzivatele.class);
		return criteria.list();
	}
		
	public int save(Uzivatele uzivatel)
	{
		return (Integer) sessionFactory.getCurrentSession().save(uzivatel);
	}
	
	public void update(Uzivatele uzivatel)
	{
		sessionFactory.getCurrentSession().merge(uzivatel);
	}
	
		
	public void view(Uzivatele uzivatel)
	{
		sessionFactory.getCurrentSession().merge(uzivatel);
	}
	
	public void delete(int id)
	{
		Uzivatele s = getByUzivateleID(id);
		sessionFactory.getCurrentSession().delete(s);
	}
}