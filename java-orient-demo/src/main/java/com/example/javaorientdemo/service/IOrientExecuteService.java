package com.example.javaorientdemo.service;

import java.util.List;
import java.util.Map;

/**
 * 描述
 *
 * @author 周林
 * @version 1.0
 * @date 2021/6/9 19:09
 */
public interface IOrientExecuteService {
    List<Map<String, Object>> executeQuery(String sql, String database);
}
