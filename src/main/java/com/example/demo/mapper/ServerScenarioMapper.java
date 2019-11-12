package com.example.demo.mapper;

import com.example.demo.model.StuServerInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ServerScenarioMapper {
    void insertStuServerInfo(StuServerInfo stuServerInfo)throws Exception;

}
