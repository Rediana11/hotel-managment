package com.ikubinfo.primefaces.util;


import com.ikubinfo.primefaces.managedbean.LoggedUserMangedBean;
import com.ikubinfo.primefaces.model.Logs;
import com.ikubinfo.primefaces.repository.LogsRepository;
import com.ikubinfo.primefaces.repository.impl.LogsRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import java.util.Iterator;


public class CustomExceptionHandler extends ExceptionHandlerWrapper {


    private ExceptionHandler wrapped;
    private LogsRepository logsRepository;
    @Autowired
    private LoggedUserMangedBean userMangedBean;

    public CustomExceptionHandler(){}

    public CustomExceptionHandler(ExceptionHandler wrapped) {
        logsRepository=new LogsRepositoryImpl();
        this.wrapped = wrapped;
    }

    @Override
    public ExceptionHandler getWrapped() {
        return wrapped;
    }

    @Override
    public void handle() throws FacesException {
        Iterator iterator = getUnhandledExceptionQueuedEvents().iterator();
        while (iterator.hasNext()) {
            Logs log=new Logs();
            ExceptionQueuedEvent event = (ExceptionQueuedEvent) iterator.next();
            ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();

            Throwable throwable = context.getException();
            FacesContext fc = FacesContext.getCurrentInstance();

            try {
                Flash flash = fc.getExternalContext().getFlash();
                log.setDetails( throwable.getLocalizedMessage());
                log.setLocation_exception("");
                log.setMessage(throwable.getMessage());
                log.setCreatedBy(userMangedBean.getUser());
                logsRepository.addErrorLog(log);
                flash.put("errorDetails", throwable.getMessage());
                System.out.println("the error is put in the flash: " + throwable.getMessage());
                NavigationHandler navigationHandler = fc.getApplication().getNavigationHandler();
                navigationHandler.handleNavigation(fc, null, "generalError");

                fc.renderResponse();
            } finally {
                iterator.remove();
            }
        }
        getWrapped().handle();
    }

    public void setWrapped(ExceptionHandler wrapped) {
        this.wrapped = wrapped;
    }

}
