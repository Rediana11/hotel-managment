package com.ikubinfo.primefaces.repository;

import com.ikubinfo.primefaces.model.Logs;

import java.util.List;

public interface LogsRepository {

    boolean create(String name, String detail);
    public List<Logs> getLogs();

}
