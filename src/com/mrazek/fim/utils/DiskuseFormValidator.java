package com.mrazek.fim.utils;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.mrazek.fim.bean.Uzivatele;


@Component("diskuseFormValidator")
public class DiskuseFormValidator implements Validator
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
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "text","required.text", "Nezadali jste text pøíspìvku.");
	}

}

