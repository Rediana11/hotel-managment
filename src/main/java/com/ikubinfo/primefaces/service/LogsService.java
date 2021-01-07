package com.ikubinfo.primefaces.service;

import com.ikubinfo.primefaces.model.Logs;

import java.util.List;

public interface LogsService {

    boolean create(String name, String detail);

    public List<Logs> getLogs();
}
