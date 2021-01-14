package com.ikubinfo.primefaces.service;

import com.ikubinfo.primefaces.model.Logs;

import java.util.List;

public interface LogsService {


    boolean addSuccessfulLog ( String detail);

    boolean addErrorLog(String detail) ;

    List<Logs> getLogs();
}
