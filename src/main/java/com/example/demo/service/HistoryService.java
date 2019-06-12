package com.example.demo.service;

import com.example.demo.mapper.HistoryMapper;
import com.example.demo.model.History;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoryService {
    @Autowired
    private HistoryMapper historyMapper;

    public void insertHistory(History history) throws Exception{
        historyMapper.insertHistory(history);
    }
}
