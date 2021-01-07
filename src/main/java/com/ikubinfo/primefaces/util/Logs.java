package com.ikubinfo.primefaces.util;

import com.ikubinfo.primefaces.repository.LogsRepository;
import com.ikubinfo.primefaces.service.LogsService;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

@Component
public class Logs {

    private LogsService logsService;

    public Logs(LogsService logsService) {
        super();
        this.logsService = logsService;
    }


    public void addErrorLog(String detail) {
        logsService.create("Error",detail);
    }

    public void addSuccessfulLog(String detail) {
        logsService.create("Succesful",detail);
    }

    public LogsService getLogsService() {
        return logsService;
    }

    public void setLogsService(LogsService logsService) {
        this.logsService = logsService;
    }
}
