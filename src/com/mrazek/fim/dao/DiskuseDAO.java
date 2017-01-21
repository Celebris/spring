package com.mrazek.fim.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mrazek.fim.bean.Diskuse;

@Repository
@Transactional
public class DiskuseDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	public Diskuse getByDiskuseID(int id)
	{
		return (Diskuse) sessionFactory.getCurrentSession().get(Diskuse.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Diskuse> getAllDiskuse()
	{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Diskuse.class);
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Diskuse> getClanekDiskuse(int clanek)
	{
		return sessionFactory.getCurrentSession().createQuery("from Diskuse where clanek = '"+clanek+"'").list();
	}

	public int save(Diskuse diskuse)
	{
		return (Integer) sessionFactory.getCurrentSession().save(diskuse);
	}
	
	public void update(Diskuse diskuse)
	{
		sessionFactory.getCurrentSession().merge(diskuse);
	}
	
		
	public void view(Diskuse diskuse)
	{
		sessionFactory.getCurrentSession().merge(diskuse);
	}
	
	public void delete(int id)
	{
		Diskuse d = getByDiskuseID(id);
		sessionFactory.getCurrentSession().delete(d);
	}
}


