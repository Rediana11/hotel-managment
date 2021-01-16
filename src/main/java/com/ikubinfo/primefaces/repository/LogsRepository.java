package com.ikubinfo.primefaces.repository;

import com.ikubinfo.primefaces.model.Logs;

import java.util.List;

public interface LogsRepository {

    boolean addSuccessfulLog (Logs log,String detail);

    boolean addErrorLog (Logs log, String detail);

    List<Logs> getLogs();

}
