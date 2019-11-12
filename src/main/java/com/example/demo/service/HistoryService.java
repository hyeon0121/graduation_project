package com.example.demo.service;

import com.example.demo.mapper.HistoryMapper;
import com.example.demo.model.StuCliScore;
import com.example.demo.model.StuCoAPScenario;
import com.example.demo.model.StuServerInfo;
import com.example.demo.model.StuServerInfoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryService {
    @Autowired
    private HistoryMapper historyMapper;

    public void insertHistory(StuCoAPScenario history) throws Exception{
        historyMapper.insertHistory(history);
    }

    public void insertWebServerSce(StuServerInfo stuServerInfo) throws Exception {
        historyMapper.insertWebServerSce(stuServerInfo);
    }

    public List<StuServerInfoDAO> selectWebServerSce(String sno) throws Exception {
        return historyMapper.selectWebServerSce(sno);
    }

    public void insertWebClientSce(StuCliScore stuCliScore) throws Exception {
        historyMapper.insertWebClientSce(stuCliScore);
    }

}
