package com.mrazek.fim.controller;


import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.mrazek.fim.bean.Clanky;
import com.mrazek.fim.bean.Diskuse;
import com.mrazek.fim.bean.Hodnoceni;
import com.mrazek.fim.bean.Kategorie;
import com.mrazek.fim.bean.Uzivatele;
import com.mrazek.fim.dao.ClankyDAO;
import com.mrazek.fim.dao.DiskuseDAO;
import com.mrazek.fim.dao.HodnoceniDAO;
import com.mrazek.fim.dao.KategorieDAO;
import com.mrazek.fim.dao.UzivateleDAO;
import com.mrazek.fim.utils.ClankyFormValidator;
import com.mrazek.fim.utils.DiskuseFormValidator;
import com.mrazek.fim.utils.KategorieFormValidator;
import com.mrazek.fim.utils.UzivateleFormValidator;

@Controller
public class HlavniController {
	
	@Autowired
	private DiskuseDAO diskuseDAO;
	
	@Autowired
	private ClankyDAO clankyDAO;
	
	@Autowired
	private HodnoceniDAO hodnoceniDAO;
	
	@Autowired
	private KategorieDAO kategorieDAO;
	
	@Autowired
	private UzivateleDAO uzivateleDAO; 
	
	
	
	@Autowired
	private UzivateleFormValidator uvalidator;
	
	@Autowired
	private KategorieFormValidator kvalidator;
	
	@Autowired
	private ClankyFormValidator cvalidator;
	
	@Autowired
	private DiskuseFormValidator dvalidator;

	
	@RequestMapping("/index")
	public String indexjsp(HttpSession session)
	{
		return "redirect:seznamclanku.do?kategorie=0&page=0";
	}
	
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder) 
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@RequestMapping("/smazclanek")
	public String getSmazatClanek(HttpSession session, @RequestParam("clanekid")Integer clanekid)
	{
		if ((session.getAttribute("admin") != null) && (!(session.getAttribute("admin").equals(0)))) {
		List<Diskuse> diskuse = diskuseDAO.getClanekDiskuse(clanekid) ;
		for (int i = 0; i < diskuse.size(); i++) {
		    Diskuse d = diskuse.get(i);
		    diskuseDAO.delete(d.getId());
		}
			clankyDAO.delete(clanekid);
		return "redirect:seznamclanku.do?kategorie=0&page=0";
		} else {
			return "redirect:seznamclanku.do?kategorie=0&page=0";
		}	
	}
	
	@RequestMapping("/smazdiskuse")
	public String getSmazDiskusi(HttpSession session, @RequestParam("did")Integer did)
	{
		int returnid = diskuseDAO.getByDiskuseID(did).getClanek().getId();
		if ((session.getAttribute("admin") != null) && (!(session.getAttribute("admin").equals(0)))) {
			diskuseDAO.delete(did);
		}
		return "redirect:diskuse.do?diskuseid="+returnid+"";
	}

	@RequestMapping("/uzadmin")
	public String datOpravneni(HttpSession session, @RequestParam("uzid")Integer uzid)
	{
		if ((session.getAttribute("admin") != null) && (!(session.getAttribute("admin").equals(0)))) {
		Uzivatele u = uzivateleDAO.getByUzivateleID(uzid);
				u.setAdmin(1);
				uzivateleDAO.update(u);
		}
		return "redirect:uzivatel.do?uid="+uzid+"";
	}
	
	@RequestMapping("/uzneadmin")
	public String odebrat(HttpSession session, @RequestParam("uzid")Integer uzid)
	{
		if ((session.getAttribute("admin") != null) && (!(session.getAttribute("admin").equals(0)))) {
			Uzivatele u = uzivateleDAO.getByUzivateleID(uzid);
					u.setAdmin(0);
					uzivateleDAO.update(u);
			}
			return "redirect:uzivatel.do?uid="+uzid+"";
	}

	
	@RequestMapping(value="/seznamclanku")
	public ModelAndView getSeznamClanku(@RequestParam(value = "page") int page, HttpSession session, @RequestParam("kategorie")Integer kategorie)
	{
		ModelAndView mav = new ModelAndView("showseznamclanku");
		menu(mav, session);
		List<Clanky> clanky = null;
		List<Clanky> clankyx = null;
		if (kategorie==0) {
		clanky = clankyDAO.listAllClanky(page);
		clankyx = clankyDAO.getAllClanky();
		} else {
		clanky = clankyDAO.getAllClankyKategorie(kategorie,page);
		clankyx = clankyDAO.getAllClankyKategorie(kategorie);
		}
		double clankysize = clankyx.size();
		double maxclanku = clankysize/5;
		mav.addObject("cl", clanky);
		int pagea = page - 1;
		int pageb = page + 1;
		if (pageb>=maxclanku) pageb=-1;
		mav.addObject("stranaa", pagea);
		mav.addObject("stranab", pageb);
		mav.addObject("kategorie", kategorie);
		return mav;	
	}

	@RequestMapping(value="/uzivatele")
	public ModelAndView getSeznamuzivatelu(HttpSession session)
	{
		if ((session.getAttribute("admin") != null) && (!(session.getAttribute("admin").equals(0)))) {
		ModelAndView mav = new ModelAndView("showuzivatelelist");
		menu(mav, session);
		List<Uzivatele> uzivatele = uzivateleDAO.getAllUzivatele();
		mav.addObject("uzivatelex", uzivatele);
		return mav;
		} else {
			ModelAndView mav = new ModelAndView("showopravneni");
			menu(mav, session);
			return mav;
		}
	}
	
	@RequestMapping(value="/uzivatel")
	public ModelAndView getUzivatel(@RequestParam("uid")Integer uid, HttpSession session)
	{
		ModelAndView mav = new ModelAndView("showuzivatel");
		menu(mav, session);
		mav.addObject("userid", uid);
		Uzivatele uzivatele = uzivateleDAO.getByUzivateleID(uid);
		mav.addObject("uz", uzivatele);
		return mav;
	}
	
	@RequestMapping(value="/clanek", method=RequestMethod.GET)
	public ModelAndView getClanek(@RequestParam("clanekid")Integer clanekid, HttpSession session)
	{		
		ModelAndView mav = new ModelAndView("showclanek");
		menu(mav, session);
		mav.addObject("clid", clanekid);
		Clanky clanek = clankyDAO.getByClankyId(clanekid);
		mav.addObject("clanektext", clanek);
		int kategorie = clanek.getKategorie().getId();
		List<Hodnoceni> hx = hodnoceniDAO.getHodnocenix(clanekid);
		List<Clanky> c = clankyDAO.podobneClanky(clanekid, kategorie, hx); 
		Collections.reverse(c);
		mav.addObject("podobneclanky", c);		
		
		Hodnoceni hodnoceni = new Hodnoceni();
		mav.addObject("showclanek", hodnoceni);	
		Double znamka = hodnoceniDAO.getPrumerneHodnoceni(clanekid);
		mav.addObject("znamkax", znamka);
		long pocethodnoceni = hodnoceniDAO.getPocetHodnoceni(clanekid);
		mav.addObject("pocet", pocethodnoceni);
		int hodnotitx=0;
		if ((session.getAttribute("login") != null) && (session.getAttribute("login")!="")) {
		int logid = (Integer) session.getAttribute("loginid");
		List<Hodnoceni> h = hodnoceniDAO.getHodnoceniIdClanek(logid, clanekid);
		if (h.isEmpty()) {
			hodnotitx=1;
		} 
		} 
		mav.addObject("hodnotit", hodnotitx);
		return mav;
	}	
	
	@RequestMapping(value="/clanek", method=RequestMethod.POST)
	public String getClaneka(@ModelAttribute("showclanek")Hodnoceni hodnoceni, BindingResult result, SessionStatus status, @RequestParam("clanekid")Integer clanekid, HttpSession session)
	{		
		if ((session.getAttribute("login") != null) && (session.getAttribute("login")!="")) {
			int logid = (Integer) session.getAttribute("loginid");
			List<Hodnoceni> h = hodnoceniDAO.getHodnoceniIdClanek(logid, clanekid);
			if (h.isEmpty()) {
				hodnoceni.setClanek(clankyDAO.getByClankyId(clanekid));
				hodnoceni.setAutor(uzivateleDAO.getByUzivateleID(logid));
				hodnoceniDAO.update(hodnoceni);
			}	
	}	
		return "redirect:clanek.do?clanekid="+clanekid+"";
	}	
	
	@RequestMapping(value="/novyclanek", method=RequestMethod.GET)
	public ModelAndView getNovyClanek(HttpSession session)
	{
		if ((session.getAttribute("admin") != null) && (!(session.getAttribute("admin").equals(0)))) {
		ModelAndView mav = new ModelAndView("shownovyclanek");
		Clanky clanky = new Clanky();
		mav.getModelMap().put("shownovyclanek", clanky);
		menu(mav, session);
		List<Kategorie> seznamkategorii = kategorieDAO.getAllKategorie();
		Map<Integer,String> country = new LinkedHashMap<Integer,String>();
		for (int i = 0; i < seznamkategorii.size(); i++) {
		    Kategorie k = seznamkategorii.get(i);
		    country.put(k.getId(), k.getNazev());

		}
		mav.addObject("kategorielist", seznamkategorii);
		return mav;
		} else {
			ModelAndView mav = new ModelAndView("showopravneni");
			menu(mav, session);
			return mav;
		}
	}
	
	@RequestMapping(value="/novyclanek", method=RequestMethod.POST)
	public ModelAndView getNovyClaneka(@ModelAttribute("shownovyclanek")Clanky clanky, BindingResult result, SessionStatus status, @RequestParam("kategorieid") Integer kategorieid, Model model, HttpSession session)
	{
		if ((session.getAttribute("admin") != null) && (!(session.getAttribute("admin").equals(0)))) {
		Kategorie k = new Kategorie();
		k.setId(kategorieid);
		clanky.setKategorie(k);
		Date datum = new Date();
		clanky.setDatum(datum);
		int logid = (Integer) session.getAttribute("loginid");
		clanky.setAutor(uzivateleDAO.getByUzivateleID(logid));
		cvalidator.validate(clanky, result);
		if (result.hasErrors()) 
		{				
			ModelAndView mav = new ModelAndView("shownovyclanek");
			Clanky clankyx = new Clanky();
			mav.getModelMap().put("shownovyclanek", clankyx);
			menu(mav, session);
			List<Kategorie> seznamkategorii = kategorieDAO.getAllKategorie();
			Map<Integer,String> country = new LinkedHashMap<Integer,String>();
			for (int i = 0; i < seznamkategorii.size(); i++) {
			    Kategorie kx = seznamkategorii.get(i);
			    country.put(kx.getId(), kx.getNazev());
			}
			mav.addObject("kategorielist", seznamkategorii);
			return mav;
		} else {
		clankyDAO.save(clanky);
		}
		}
		ModelAndView mav = new ModelAndView("shownovyclanek");
		Clanky clankyx = new Clanky();
		mav.getModelMap().put("shownovyclanek", clankyx);
		menu(mav, session);
		List<Kategorie> seznamkategorii = kategorieDAO.getAllKategorie();
		Map<Integer,String> country = new LinkedHashMap<Integer,String>();
		for (int i = 0; i < seznamkategorii.size(); i++) {
		    Kategorie kx = seznamkategorii.get(i);
		    country.put(kx.getId(), kx.getNazev());
		}
		mav.addObject("kategorielist", seznamkategorii);
		return mav;
	}
	
	@RequestMapping(value="/novyuzivatel", method=RequestMethod.GET)
	public ModelAndView getNovyUzivatel(HttpSession session, @RequestParam(value = "par") int par)
	{
		ModelAndView mav = new ModelAndView("showregistrace");
		menu(mav, session);
		Uzivatele uzivatel = new Uzivatele();
		mav.getModelMap().put("showregistrace", uzivatel);
		mav.addObject("error", par);
		return mav;
	}
	
	@RequestMapping(value="/novyuzivatel", method=RequestMethod.POST)
	public ModelAndView getNovyUzivatela(@ModelAttribute("showregistrace")Uzivatele uzivatel, BindingResult result, SessionStatus status, HttpSession session)
	{
		uvalidator.validate(uzivatel, result);
		if (result.hasErrors()) 
		{				
			ModelAndView mav = new ModelAndView("showregistrace");
			menu(mav, session);
			return mav;
		} else {
			uzivatel.setHeslo(hash(uzivatel.getHeslo()));
		uzivateleDAO.save(uzivatel);
		ModelAndView mav = new ModelAndView("showregistrace");
		menu(mav, session);
		mav.addObject("error", 1);
		return mav;
		}
	}
	
	@RequestMapping(value="/diskuse", method=RequestMethod.GET)
	public ModelAndView getDiskuse(@RequestParam("diskuseid")Integer diskuseid, HttpSession session)
	{
		ModelAndView mav = new ModelAndView("showdiskuse");
		menu(mav, session);
		Diskuse diskuse = new Diskuse();
		mav.getModelMap().put("showdiskuse", diskuse);
		List<Diskuse> diskuselist = diskuseDAO.getClanekDiskuse(diskuseid);
		mav.addObject("prispevky", diskuselist);
		mav.addObject("diskuseid", diskuseid);
		mav.addObject("loginx", session.getAttribute("login"));
		return mav;
	}
	
	@RequestMapping(value="/diskuse", method=RequestMethod.POST)
	public ModelAndView getDiskusea(@ModelAttribute("showdiskuse")Diskuse diskuse, BindingResult result, SessionStatus status, HttpSession session, @RequestParam("diskuseid")String diskuseid)	
	{
		if ((session.getAttribute("login") != null) && (session.getAttribute("login")!="")) {
		int diskuseidx=Integer.parseInt(diskuseid);
		dvalidator.validate(diskuse, result);
		if (result.hasErrors()) 
		{				
			ModelAndView mav = new ModelAndView("showdiskuse");
			menu(mav, session);
			List<Diskuse> diskuselist = diskuseDAO.getClanekDiskuse(diskuseidx);
			mav.addObject("prispevky", diskuselist);
			mav.addObject("diskuseid", diskuseid);
			mav.addObject("loginx", session.getAttribute("login"));
			return mav;
		} else {
			Date date = new Date();
			diskuse.setDatum(date);
			Clanky c = clankyDAO.getByClankyId(diskuseidx);
			diskuse.setClanek(c);
			int uzid = (Integer) session.getAttribute("loginid");
			diskuse.setAutor(uzivateleDAO.getByUzivateleID(uzid));
		diskuseDAO.save(diskuse);
		ModelAndView mav = new ModelAndView("showdiskuse");
		menu(mav, session);
		List<Diskuse> diskuselist = diskuseDAO.getClanekDiskuse(diskuseidx);
		mav.addObject("prispevky", diskuselist);
		mav.addObject("diskuseid", diskuseid);
		mav.addObject("loginx", session.getAttribute("login"));
		return mav;
		}
		}else {
			ModelAndView mav = new ModelAndView("showdiskuse");
			menu(mav, session);
			int diskuseidx=Integer.parseInt(diskuseid);
			List<Diskuse> diskuselist = diskuseDAO.getClanekDiskuse(diskuseidx);
			mav.addObject("prispevky", diskuselist);
			mav.addObject("diskuseid", diskuseid);
			mav.addObject("loginx", session.getAttribute("login"));
			return mav;
	}
	}
	
	
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public ModelAndView getLogin(HttpSession session, @RequestParam("par")Integer par)
	{
		ModelAndView mav = new ModelAndView("showlogin");
		menu(mav, session);
		Uzivatele u = new Uzivatele();
		mav.getModelMap().put("showlogin", u);
		mav.addObject("error",par);
		mav.addObject("loginx", session.getAttribute("login"));
		return mav;
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String getLogina(@ModelAttribute("showlogin")Uzivatele uzivatel, BindingResult result, SessionStatus status, HttpSession session)
	{
		uzivatel.setHeslo(hash(uzivatel.getHeslo()));
		List<Uzivatele> list = uzivateleDAO.getByUzivateleNickHeslo(uzivatel.getNick(),uzivatel.getHeslo());	
		if ((list != null) && !list.isEmpty()) {
			Uzivatele a = list.get(0);
			session.setAttribute("login", a.getNick());
			session.setAttribute("loginid", a.getId());
			session.setAttribute("admin", a.getAdmin());
			return "redirect:login.do?par=0";
		} else {
			return "redirect:login.do?par=1";
		}
	}

	@RequestMapping(value="/editclanek", method=RequestMethod.GET)
	public ModelAndView editclanek(@RequestParam("clanekid")Integer clanekid, HttpSession session)
	{
		ModelAndView mav = null;
		if ((session.getAttribute("admin") != null) && (!(session.getAttribute("admin").equals(0)))) {
		mav = new ModelAndView("showclanekedit");
		menu(mav, session);
		Clanky clanek = clankyDAO.getByClankyId(clanekid);
		mav.addObject("showclanekedit", clanek);
		List<Kategorie> seznamkategorii = kategorieDAO.getAllKategorie();
		Map<Integer,String> country = new LinkedHashMap<Integer,String>();
		for (int i = 0; i < seznamkategorii.size(); i++) {
		    Kategorie k = seznamkategorii.get(i);
		    country.put(k.getId(), k.getNazev());
		}
		mav.addObject("kategorielist", seznamkategorii);
		int kategoriex = clanek.getKategorie().getId();
		mav.addObject("kategorieid", kategoriex);
		} else {
			mav = new ModelAndView("showopravneni");
			menu(mav, session);
		}		
		return mav;
		
	}
	
	@RequestMapping(value="/editclanek", method=RequestMethod.POST)
	public String updateclanek(@ModelAttribute("showclanekedit") Clanky clanek, BindingResult result, SessionStatus status, HttpSession session,@RequestParam("kategorieid")Integer kategorieid)
	{
		if ((session.getAttribute("admin") != null) && (!(session.getAttribute("admin").equals(0)))) {
			clanek.setAutor(clankyDAO.getByClankyId(clanek.getId()).getAutor());
			clanek.setDatum(clankyDAO.getByClankyId(clanek.getId()).getDatum());
			
		cvalidator.validate(clanek, result);
		if (result.hasErrors()) {
			return "showclanekedit";
		}
		clanek.setKategorie(kategorieDAO.getByKategorieID(kategorieid));
		clankyDAO.update(clanek);
		status.setComplete();
		}
		int clanekid = clanek.getId();
		return "redirect:clanek.do?clanekid="+clanekid+"";
	}

	@RequestMapping(value="/diskuseedit", method=RequestMethod.GET)
	public ModelAndView editdiskuse(@RequestParam("diskuseid")Integer diskuseid, HttpSession session)
	{
		ModelAndView mav = null;
		if ((session.getAttribute("admin") != null) && (!(session.getAttribute("admin").equals(0)))) {
		mav = new ModelAndView("showdiskuseedit");
		menu(mav, session);
		
		Diskuse diskuse = diskuseDAO.getByDiskuseID(diskuseid);
		mav.addObject("showdiskuseedit", diskuse);
		} else {
			mav = new ModelAndView("showopravneni");
			menu(mav, session);
		} 
		return mav;
	}
	
	@RequestMapping(value="/diskuseedit", method=RequestMethod.POST)
	public String editdiskusea(@ModelAttribute("showdiskuseedit") Diskuse diskuse, BindingResult result, SessionStatus status, HttpSession session)
	{
		if ((session.getAttribute("admin") != null) && (!(session.getAttribute("admin").equals(0)))) {
		Diskuse d = diskuseDAO.getByDiskuseID(diskuse.getId());
		diskuse.setAutor(d.getAutor());
		diskuse.setDatum(d.getDatum());
		diskuse.setClanek(d.getClanek());
			diskuseDAO.update(diskuse);
		status.setComplete();
		}
		int diskuseid = diskuse.getClanek().getId();
		return "redirect:diskuse.do?diskuseid="+diskuseid+"";
	}
	
	public String hash(String text)
	{
		MessageDigest m = null;
		try {
			m = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		m.reset();
		m.update(text.getBytes());
		byte[] digest = m.digest();
		BigInteger bigInt = new BigInteger(1,digest);
		String hashtext = bigInt.toString(16);
		while(hashtext.length() < 32 ){
		  hashtext = "0"+hashtext;
		}
		return hashtext;
	}
	
	public ModelAndView menu(ModelAndView mav, HttpSession session)
	{
		List<Kategorie> k = kategorieDAO.getAllKategorie();
		mav.addObject("kategoriemenu", k);
		mav.addObject("loginx", session.getAttribute("login"));
		mav.addObject("adminlog", session.getAttribute("admin"));		
		return mav;
	}
	
	
	
	@RequestMapping(value="/uzivateledit", method=RequestMethod.GET)
	public ModelAndView editUzivatel(HttpSession session, @RequestParam("par")Integer par)
	{
		ModelAndView mav = null;
		if ((session.getAttribute("login") != null) && (session.getAttribute("login")!="")) {
		mav = new ModelAndView("showedituzivatel");
		menu(mav, session);
		mav.addObject("error",par);
		} else {
			mav = new ModelAndView("showopravneni");
			menu(mav, session);
		}
		return mav;
	}

	@RequestMapping(value="/uzivateledit", method=RequestMethod.POST)
	public String updateUzivatel(@RequestParam("hesloa")String hesloa, @RequestParam("heslob")String heslob, SessionStatus status, HttpSession session)
	{
		if ((session.getAttribute("login") != null) && (session.getAttribute("login")!="")) {
		int id = (Integer) session.getAttribute("loginid");
		Uzivatele uzivatel= uzivateleDAO.getByUzivateleID(id);
		if (hesloa.equals(heslob)) {	
			hesloa = hash(hesloa);
		uzivatel.setHeslo(hesloa);
		uzivateleDAO.update(uzivatel);
		status.setComplete();
		return "redirect:uzivateledit.do?par=2";
		} else {
			return "redirect:uzivateledit.do?par=1";
		}
		} else {
			return "redirect:uzivateledit.do?par=0";
		}
	}
	
	
	@RequestMapping(value="/vyhledat", method=RequestMethod.GET)
	public ModelAndView hledej(@RequestParam(value = "page") int page, HttpSession session)
	{
		ModelAndView mav = new ModelAndView("showvyhledatclanky");
		menu(mav, session);
		List<Clanky> clanky = clankyDAO.vyhledejClanky("", page);
		mav.addObject("clankylist", clanky);
		mav.addObject("strana", page);
		int pagea = page - 1;
		int pageb = page + 1;
		List<Clanky> clankyx = clankyDAO.getAllClanky();
		double clankysize = clankyx.size();
		double maxclanku = clankysize/5;
		if (pageb>=maxclanku) pageb=-1;
		mav.addObject("stranaa", pagea);
		mav.addObject("stranab", pageb);
		return mav;
	}

	@RequestMapping(value="/vyhledat", method=RequestMethod.POST)
	public ModelAndView hledeja(@RequestParam("hledanytext")String hledanytext,@RequestParam(value = "page") int page, SessionStatus status, HttpSession session)
	{		
		List<Clanky> clanky = clankyDAO.vyhledejClanky(hledanytext, page);	
		ModelAndView mav = new ModelAndView("showvyhledatclanky");
		mav.addObject("clankylist", clanky);
		mav.addObject("strana", page);
		int pagea = page - 1;
		int pageb = page + 1;
		List<Clanky> clankyx = clankyDAO.getAllClanky();
		double clankysize = clankyx.size();
		double maxclanku = clankysize/5;
		if (pageb>=maxclanku) pageb=-1;
		mav.addObject("stranaa", pagea);
		mav.addObject("stranab", pageb);
		return mav;
	}
	
	@RequestMapping(value="/logout")
	public String logout(HttpSession session)
	{
		session.removeAttribute("login");
		session.removeAttribute("loginid");
		session.removeAttribute("admin");	
		return "redirect:login.do?par=0";
	}
	
	@RequestMapping(value="/novakategorie", method=RequestMethod.GET)
	public ModelAndView getNovaKategorie(HttpSession session)
	{
		ModelAndView mav = null;
		if ((session.getAttribute("admin") != null) && (!(session.getAttribute("admin").equals(0)))) {
		mav = new ModelAndView("shownovakategorie");
		menu(mav, session);
		Kategorie kategorie = new Kategorie();
		mav.getModelMap().put("shownovakategorie", kategorie);
		} else {
			mav = new ModelAndView("showopravneni");
			menu(mav, session);
		}
		return mav;
	}
	
	@RequestMapping(value="/novakategorie", method=RequestMethod.POST)
	public ModelAndView getNovaKategorie(@ModelAttribute("shownovakategorie")Kategorie kategorie, BindingResult result, SessionStatus status, HttpSession session)
	{
		ModelAndView mav = null;
		kvalidator.validate(kategorie, result);
		if (result.hasErrors()) 
		{				
			mav = new ModelAndView("shownovakategorie");
			menu(mav, session);
			Kategorie kategoriex = new Kategorie();
			mav.getModelMap().put("shownovakategorie", kategoriex);
		} else {
		kategorieDAO.save(kategorie);		
		mav = new ModelAndView("shownovakategorie");
		menu(mav, session);
		Kategorie kategoriex = new Kategorie();
		mav.getModelMap().put("shownovakategorie", kategoriex);
		}
		return mav;
	}

}
