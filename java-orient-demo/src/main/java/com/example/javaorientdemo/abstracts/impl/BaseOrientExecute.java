package com.example.javaorientdemo.abstracts.impl;

import com.example.javaorientdemo.abstracts.OrientExecute;
import lombok.extern.slf4j.Slf4j;


/**
 * 描述
 *
 * @author 周林
 * @version 1.0
 * @date 2021/6/9 16:58
 */
@Slf4j
public class BaseOrientExecute<T> extends OrientExecute<T> {

    //    public void init(IOrientExecuteService service, String database) {
//        super(service, database);
//    }
//    @Override
//    public T findByRid(String rid) {
//        String sql = String.format("SELECT FROM %s", rid);
//        List<Map<String, Object>> maps = service.executeQuery(sql, database);
//        if (maps == null || maps.size() == 0) {
//            return null;
//        }
//        List<Object> collect = maps.stream().map(m -> new JSONObject(m).toJavaObject(Object.class)).collect(Collectors.toList());
//        return null;
//        return maps.stream().map(m -> new JSONObject(m).toJavaObject(Object.class)).collect(Collectors.toList());
//    }

}
