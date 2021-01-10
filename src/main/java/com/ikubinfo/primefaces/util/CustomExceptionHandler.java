package com.ikubinfo.primefaces.util;

import com.ikubinfo.primefaces.repository.LogsRepository;
import com.ikubinfo.primefaces.service.LogsService;
import com.ikubinfo.primefaces.service.impl.LogsServiceImpl;

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


    private LogsServiceImpl logsService=new LogsServiceImpl();

    public CustomExceptionHandler(ExceptionHandler wrapped) {
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
            ExceptionQueuedEvent event = (ExceptionQueuedEvent) iterator.next();
            ExceptionQueuedEventContext context = (ExceptionQueuedEventContext)event.getSource();

            Throwable throwable = context.getException();

            FacesContext fc = FacesContext.getCurrentInstance();

            try {
                Flash flash = fc.getExternalContext().getFlash();

                flash.put("errorDetails", throwable.getMessage());

                System.out.println("the error is put in the flash: " + throwable.getMessage());
                logsService.addErrorLog("An error occured" + throwable.getMessage());

                NavigationHandler navigationHandler = fc.getApplication().getNavigationHandler();

                navigationHandler.handleNavigation(fc, null, "generalError");

                fc.renderResponse();
            } finally {
                iterator.remove();
            }
        }
        getWrapped().handle();
    }
}
