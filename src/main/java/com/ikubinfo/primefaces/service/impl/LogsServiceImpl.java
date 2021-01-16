package com.ikubinfo.primefaces.service.impl;

import com.ikubinfo.primefaces.model.Logs;
import com.ikubinfo.primefaces.repository.BookingRepository;
import com.ikubinfo.primefaces.repository.LogsRepository;
import com.ikubinfo.primefaces.service.LogsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("logsService")
public class LogsServiceImpl implements LogsService {

    private  LogsRepository logsRepository;

    public LogsServiceImpl(LogsRepository logsRepository) {
        super();
        this.logsRepository = logsRepository;
    }

    @Override
    public boolean addSuccessfulLog(Logs log,String detail) {
        return logsRepository.addSuccessfulLog(log,detail);
    }

    @Override
    public  boolean addErrorLog(Logs log,String detail) {
        return logsRepository.addErrorLog(log,detail);
    }

    @Override
    public List<Logs> getLogs() {
        return logsRepository.getLogs();
    }
}
