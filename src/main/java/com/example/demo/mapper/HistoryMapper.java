package com.example.demo.mapper;

import com.example.demo.model.History;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HistoryMapper {
    void insertHistory(History history)throws Exception;

}
