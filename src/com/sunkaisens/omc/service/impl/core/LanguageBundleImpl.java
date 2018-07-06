package com.sunkaisens.omc.service.impl.core;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.sunkaisens.omc.service.core.LanguageBundle;

/**
 *LanguageBundle接口实现类
 */
@Service
public class LanguageBundleImpl implements LanguageBundle{
    private Locale Locale;
	@Override
	public ResourceBundle getBundle(HttpServletRequest request ,HttpSession session) {
		ResourceBundle resourceBundle; 
		Locale = (Locale)session.getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
		if(Locale != null){
			resourceBundle = ResourceBundle.getBundle("language.omc-language", Locale);
			return resourceBundle;
		}
		Locale = request.getLocale();
		resourceBundle = ResourceBundle.getBundle("language.omc-language", Locale);
		return resourceBundle;
	}

}
