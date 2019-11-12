package com.example.demo.service;

import com.example.demo.mapper.ServerScenarioMapper;
import com.example.demo.model.StuServerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServerScenarioService {
    @Autowired
    private ServerScenarioMapper serverScenarioMapper;

    public void insertStuServerInfo(StuServerInfo stuServerInfo) throws Exception{
        serverScenarioMapper.insertStuServerInfo(stuServerInfo);
    }
}

