package com.example.noasksldemo.utils;

import com.orientechnologies.orient.core.config.OGlobalConfiguration;
import com.orientechnologies.orient.core.db.ODatabasePool;
import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.db.OrientDB;
import com.orientechnologies.orient.core.db.OrientDBConfig;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;


/**
 * 描述
 *
 * @author 周林
 * @version 1.0
 * @date 2021/6/3 14:57
 */
@Slf4j
public class OrientUtils {
    protected static final String URL = YamlUtils.getProperty("noask.orient.url");
    protected static final String USERNAME = YamlUtils.getProperty("noask.orient.username");
    protected static final String PASSWORD = YamlUtils.getProperty("noask.orient.password");

    public static void execute(Consumer<ODatabaseSession> databaseSessionConsumer, String database) {
        OrientDB orient = new OrientDB(URL, OrientDBConfig.defaultConfig());
        OrientDBConfig configuration = OrientDBConfig.builder()
                .addConfig(OGlobalConfiguration.DB_POOL_MIN, 5)
                .addConfig(OGlobalConfiguration.DB_POOL_MAX, 10)
                .build();
        ODatabasePool pool = new ODatabasePool(orient, database, USERNAME, PASSWORD, configuration);
        try (ODatabaseSession db = pool.acquire()) {
//            ODatabaseRecordThreadLocal.instance().set((ODatabaseDocumentInternal) db);
            databaseSessionConsumer.accept(db);
        }
        pool.close();
        orient.close();
    }
}
