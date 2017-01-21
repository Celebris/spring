package com.mrazek.fim.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.mrazek.fim.bean.Uzivatele;
import com.mrazek.fim.dao.UzivateleDAO;


@Component("uzivateleFormValidator")
public class UzivateleFormValidator implements Validator
{
	
	@Autowired
	private UzivateleDAO uzivateleDAO; 
	

	@SuppressWarnings("unchecked")
	@Override
	public boolean supports(Class clazz)
	{
		return Uzivatele.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object model, Errors errors)
	{
		String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		Uzivatele uzivatel = (Uzivatele) model;
		if(!(uzivatel.getHeslo().equals(uzivatel.getHeslo2()))) { errors.rejectValue("heslo","nesouhlasi.heslo","Hesla se neshodují."); }
		if(uzivatel.getHeslo().length()<5) { errors.rejectValue("heslo","kratke.heslo","Heslo je pøíliš krátké."); }
		if(uzivatel.getNick().length()<5) { errors.rejectValue("nick","kratky.nick","Nick je pøíliš krátký."); }
		uzivatel.setNick(uzivatel.getNick().trim());
		uzivatel.setEmail(uzivatel.getEmail().trim());
		if(!(uzivatel.getEmail().matches(EMAIL_REGEX))) { errors.rejectValue("email","spatny.email","Email je zadán špatnì."); }
		List<Uzivatele> uzivatele = uzivateleDAO.getBynick(uzivatel.getNick());
		if (!(uzivatele.isEmpty()))  { errors.rejectValue("nick","unikatni.nick","Tento nick je již zaregistrován."); }
		List<Uzivatele> u = uzivateleDAO.getByEmail(uzivatel.getEmail());
		if (!(u.isEmpty()))  { errors.rejectValue("email","unikatni.email","Tento nick je již zaregistrován."); }
	}

}

