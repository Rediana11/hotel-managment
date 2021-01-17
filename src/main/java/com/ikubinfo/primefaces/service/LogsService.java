package com.ikubinfo.primefaces.service;

import com.ikubinfo.primefaces.model.Logs;

import java.util.List;

public interface LogsService {


    boolean addSuccessfulLog (Logs log, String detail);

    boolean addErrorLog(Logs log,String detail) ;

    List<Logs> getLogs();
}
