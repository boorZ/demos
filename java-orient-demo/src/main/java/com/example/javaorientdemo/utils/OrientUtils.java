package com.example.javaorientdemo.utils;

import com.orientechnologies.orient.core.config.OGlobalConfiguration;
import com.orientechnologies.orient.core.db.ODatabasePool;
import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.db.OrientDB;
import com.orientechnologies.orient.core.db.OrientDBConfig;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.Properties;
import java.util.function.Consumer;

import static com.example.javaorientdemo.config.DefaultLabelConfig.*;


/**
 * 描述
 *
 * @author 周林
 * @version 1.0
 * @date 2021/6/3 14:57
 */
@Slf4j
public class OrientUtils {

    @Deprecated
    public static Connection getConn() throws SQLException {
        Properties info = new Properties();
        info.put("user", "admin");
        info.put("password", "admin");

        // USE THE POOL
//        info.put("db.usePool", "true");
        // MINIMUM POOL SIZE
//        info.put("db.pool.min", "3");
        return DriverManager.getConnection("jdbc:orient:remote:10.8.0.9/precise_push", info);
    }

    @Deprecated
    public static void testConn() throws SQLException {
        String sql = "";
        Connection conn = getConn();
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        String labelName = rs.getString("labelName");
        System.out.println("labelName: " + labelName);
        rs.close();
        statement.close();
        conn.close();
    }

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
