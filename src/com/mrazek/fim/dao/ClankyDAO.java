package com.mrazek.fim.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mrazek.fim.bean.Clanky;
import com.mrazek.fim.bean.Hodnoceni;
import com.mrazek.fim.bean.Uzivatele;

@Repository
@Transactional
public class ClankyDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	private static final int stranka = 5;
	
	public Clanky getByClankyId(int id)
	{
		return (Clanky) sessionFactory.getCurrentSession().get(Clanky.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Clanky> getAllClanky()
	{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Clanky.class);
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Clanky> getAllClankyKategorie(int kategorie)
	{
		 Query query = sessionFactory.getCurrentSession().createQuery("from Clanky where kategorie='"+kategorie+"' ORDER BY id desc");
		    return (List<Clanky>) query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Clanky> getAllClankyKategorie(int kategorie, int page)
	{
		 Query query = sessionFactory.getCurrentSession().createQuery("from Clanky where kategorie='"+kategorie+"' ORDER BY id desc");
		    query.setMaxResults(stranka);
		    query.setFirstResult(page * stranka);
		    return (List<Clanky>) query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Clanky> listAllClanky(int page) {

	    Query query = sessionFactory.getCurrentSession().createQuery("from Clanky ORDER BY id desc");
	    query.setMaxResults(stranka);
	    query.setFirstResult(page * stranka);
	    return (List<Clanky>) query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Clanky> vyhledejClanky(String text, int page)
	{
	    Query query = sessionFactory.getCurrentSession().createQuery("from Clanky where text LIKE '%"+text+"%' ORDER BY id desc");
	    query.setMaxResults(stranka);
	    query.setFirstResult(page * stranka);
	    return (List<Clanky>) query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Clanky> podobneClanky(int clanek, int kategorie, List<Hodnoceni> h)
	{
	  List<Uzivatele> u = new ArrayList<Uzivatele>(); // seznam uživatelù, kteøí hodnotili èlánek 1 nebo 2
	  LinkedHashMap<Integer,Integer> map = new LinkedHashMap<Integer,Integer> ();
	  for (int i = 0; i < h.size(); i++) {
		    u.add(h.get(i).getAutor());
		}
	  List<Clanky> clanky =  getAllClanky();
	  for (int i = 0; i < clanky.size(); i++) {
		    map.put(clanky.get(i).getId(),0);
		}  

		  for (int j = 0; j < u.size(); j++) {
			  int uzivatel = u.get(j).getId();
			  List<Hodnoceni> hodnoceni = (List<Hodnoceni>) sessionFactory.getCurrentSession().createQuery("from Hodnoceni where autor='"+uzivatel+"' and (znamka='1' OR znamka='2')").list();
			  for (int x = 0; x < hodnoceni.size(); x++) {
			  	    map.put(hodnoceni.get(x).getClanek().getId(), map.get(hodnoceni.get(x).getClanek().getId()) + 1);
			  }
		  }
		  LinkedHashMap<Integer, Integer> mapa = sortByValues(map);
		  List<Clanky> vrat = new ArrayList<Clanky>();
		  Set set2 = mapa.entrySet();
	         Iterator iterator2 = set2.iterator();
	         while(iterator2.hasNext()) {
	              Map.Entry me2 = (Map.Entry)iterator2.next();
	              int a = (Integer) me2.getKey();
	              if (clanek!=a) {vrat.add(getByClankyId(a));}
	              if (vrat.size()>5) {vrat.remove(0);}
	         }
		  return vrat;
	}
		
	 public LinkedHashMap sortByValues(Map<Integer, Integer> map) { 
	       List list = new LinkedList(map.entrySet());
	       // Defined Custom Comparator here
	       Collections.sort(list, new Comparator() {
	            public int compare(Object o1, Object o2) {
	               return ((Comparable) ((Map.Entry) (o1)).getValue())
	                  .compareTo(((Map.Entry) (o2)).getValue());
	            }
	       });
	       LinkedHashMap sortedHashMap = new LinkedHashMap();
	       for (Iterator it = list.iterator(); it.hasNext();) {
	              Map.Entry entry = (Map.Entry) it.next();
	              sortedHashMap.put(entry.getKey(), entry.getValue());
	       } 
	       return sortedHashMap;
	  }
	
	public int save(Clanky clanek)
	{
		return (Integer) sessionFactory.getCurrentSession().save(clanek);
	}
	
	public void update(Clanky clanek)
	{
		sessionFactory.getCurrentSession().merge(clanek);
	}
	
		
	public void view(Clanky clanek)
	{
		sessionFactory.getCurrentSession().merge(clanek);
	}
	
	public void delete(int id)
	{
		Clanky s = getByClankyId(id);
		Query querya = sessionFactory.getCurrentSession().createQuery("DELETE from Hodnoceni where clanek='1'");
		querya.executeUpdate();
		Query queryb = sessionFactory.getCurrentSession().createQuery("DELETE from Diskuse where clanek='1'");
		queryb.executeUpdate();
		sessionFactory.getCurrentSession().delete(s);
	}
}
