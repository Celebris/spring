package com.mrazek.fim.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mrazek.fim.bean.Kategorie;



@Repository
@Transactional
public class KategorieDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	public Kategorie getByKategorieID(int id)
	{
		return (Kategorie) sessionFactory.getCurrentSession().get(Kategorie.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Kategorie> getAllKategorie()
	{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Kategorie.class);
		return criteria.list();
	}
		
	public int save(Kategorie kategorie)
	{
		return (Integer) sessionFactory.getCurrentSession().save(kategorie);
	}
	
	public void update(Kategorie kategorie)
	{
		sessionFactory.getCurrentSession().merge(kategorie);
	}
	
		
	public void view(Kategorie kategorie)
	{
		sessionFactory.getCurrentSession().merge(kategorie);
	}
	
	public void delete(int id)
	{
		Kategorie s = getByKategorieID(id);
		sessionFactory.getCurrentSession().delete(s);
	}
}