package com.example.javaorientdemo.service.impl;

import com.example.javaorientdemo.service.IOrientExecuteService;
import com.example.javaorientdemo.utils.OrientUtils;
import com.orientechnologies.orient.core.sql.executor.OResult;
import com.orientechnologies.orient.core.sql.executor.OResultSet;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述
 *
 * @author 周林
 * @version 1.0
 * @date 2021/6/9 19:10
 */
@Slf4j
public class OrientExecuteServiceImpl implements IOrientExecuteService {
    @Override
    public List<Map<String, Object>> executeQuery(String sql, String database) {
        log.info("\nOrient execute sql：{}", sql);
        List<Map<String, Object>> result = new ArrayList<>();
        OrientUtils.execute(db -> {
            OResultSet rs = db.query(sql);
            OResult next;
            while (rs.hasNext()) {
                next = rs.next();
                Map<String, Object> map = new HashMap<>();
                next.getIdentity().ifPresent(x -> {
                    map.put("rid", x);
                });
                for (String propertyName : next.getPropertyNames()) {
                    String k = propertyName.replaceAll("_", "");
                    map.put(k, next.getProperty(propertyName));
                }
                result.add(map);
            }
            rs.close();
        }, database);
        return result;
    }
}
