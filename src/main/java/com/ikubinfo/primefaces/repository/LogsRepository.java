package com.ikubinfo.primefaces.repository;

import com.ikubinfo.primefaces.model.Logs;

import java.util.List;

public interface LogsRepository {


    boolean addErrorLog (Logs log);

    List<Logs> getLogs();

}
