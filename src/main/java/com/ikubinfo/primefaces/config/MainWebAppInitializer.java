package com.ikubinfo.primefaces.config;

import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.sun.faces.config.FacesInitializer;

public class MainWebAppInitializer extends FacesInitializer implements WebApplicationInitializer {
	public void onStartup(ServletContext servletContext) throws ServletException {

		ServletRegistration.Dynamic facesServlet = servletContext.addServlet("Faces Servlet", FacesServlet.class);
		facesServlet.setLoadOnStartup(1);
		facesServlet.addMapping("*.xhtml");

		AnnotationConfigWebApplicationContext root = new AnnotationConfigWebApplicationContext();
		root.register(AppConfiguration.class);
		servletContext.addListener(new ContextLoaderListener(root));
	}
}