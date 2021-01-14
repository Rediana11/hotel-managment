package com.ikubinfo.primefaces.repository;

import com.ikubinfo.primefaces.model.Logs;

import java.util.List;

public interface LogsRepository {

    boolean addSuccessfulLog ( String detail);

    boolean addErrorLog ( String detail);

    List<Logs> getLogs();

}
