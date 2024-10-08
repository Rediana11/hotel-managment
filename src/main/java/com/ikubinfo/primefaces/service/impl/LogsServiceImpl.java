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
    public List<Logs> getLogs() {
        return logsRepository.getLogs();
    }
}
