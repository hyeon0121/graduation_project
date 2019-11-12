package com.example.demo.mapper;

import com.example.demo.model.StuCliScore;
import com.example.demo.model.StuCoAPScenario;
import com.example.demo.model.StuServerInfo;
import com.example.demo.model.StuServerInfoDAO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HistoryMapper {
    void insertHistory(StuCoAPScenario history)throws Exception;

    // web server scenario test
    void insertWebServerSce(StuServerInfo stuServerInfo)throws Exception;

    List<StuServerInfoDAO> selectWebServerSce(String sno)throws Exception;

    void insertWebClientSce(StuCliScore stuCliScore)throws Exception;
}
