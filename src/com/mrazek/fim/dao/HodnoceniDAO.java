package com.mrazek.fim.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mrazek.fim.bean.Hodnoceni;



@Repository
@Transactional
public class HodnoceniDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	public Hodnoceni getByHodnoceniID(int id)
	{
		return (Hodnoceni) sessionFactory.getCurrentSession().get(Hodnoceni.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Hodnoceni> getAllHodnoceni()
	{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Hodnoceni.class);
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Hodnoceni> getHodnoceniIdClanek(int autor, int clanek)
	{
		return sessionFactory.getCurrentSession().createQuery("FROM Hodnoceni where autor='"+autor+"' and clanek='"+clanek+"'").list();
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<Hodnoceni> getHodnocenix(int clanek)
	{
	return	(List<Hodnoceni>) sessionFactory.getCurrentSession().createQuery("from Hodnoceni where clanek='"+clanek+"' and (znamka='1' OR znamka='2')").list();
	}

	@SuppressWarnings("unchecked")
	public Double getPrumerneHodnoceni(int clanek)
	{
		Query q = sessionFactory.getCurrentSession().createQuery("select avg(znamka) from Hodnoceni where clanek='"+clanek+"'");
	    Double count = (Double) q.uniqueResult();
	    return count;
	}

	@SuppressWarnings("unchecked")
	public long getPocetHodnoceni(int clanek)
	{
		Query q = sessionFactory.getCurrentSession().createQuery("select count(znamka) from Hodnoceni where clanek='"+clanek+"'");
	    long count = (Long) q.uniqueResult();
	    return count;
	}
	
	public int save(Hodnoceni hodnoceni)
	{
		return (Integer) sessionFactory.getCurrentSession().save(hodnoceni);
	}
	
	public void update(Hodnoceni hodnoceni)
	{
		sessionFactory.getCurrentSession().merge(hodnoceni);
	}
	
		
	public void view(Hodnoceni hodnoceni)
	{
		sessionFactory.getCurrentSession().merge(hodnoceni);
	}
	
	public void delete(int id)
	{
		Hodnoceni s = getByHodnoceniID(id);
		sessionFactory.getCurrentSession().delete(s);
	}
}

