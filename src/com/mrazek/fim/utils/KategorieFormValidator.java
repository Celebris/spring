package com.mrazek.fim.utils;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.mrazek.fim.bean.Uzivatele;


@Component("kategorieFormValidator")
public class KategorieFormValidator implements Validator
{
	

	@SuppressWarnings("unchecked")
	@Override
	public boolean supports(Class clazz)
	{
		return Uzivatele.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object model, Errors errors)
	{
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nazev","kategorie.nazev", "Nebyl zadán název kategorie.");
	}

}

