package com.ikubinfo.primefaces.managedbean;

import com.ikubinfo.primefaces.model.Logs;
import com.ikubinfo.primefaces.service.LogsService;
import com.ikubinfo.primefaces.service.impl.RoomCategoryServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@ViewScoped
public class LogsManagedBean implements Serializable {

    Logger logger = LoggerFactory.getLogger(RoomCategoryServiceImpl.class);

    private Logs log;

    private List<Logs> logs;

    @ManagedProperty(value = "#{logsService}")
    private LogsService logsService;


    @PostConstruct
    public void init()
    {
        logs = logsService.getLogs();
        log = new Logs();

    }

    public Logs getLog() {
        return log;
    }

    public void setLog(Logs log) {
        this.log = log;
    }

    public List<Logs> getLogs() {
        return logs;
    }

    public void setLogs(List<Logs> logs) {
        this.logs = logs;
    }

    public LogsService getLogsService() {
        return logsService;
    }

    public void setLogsService(LogsService logsService) {
        this.logsService = logsService;
    }
}
