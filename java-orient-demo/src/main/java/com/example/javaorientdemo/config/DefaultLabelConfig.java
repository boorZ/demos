package com.example.javaorientdemo.config;

import com.example.javaorientdemo.utils.YamlUtils;
import lombok.Data;

/**
 * OrientDefaultConfig
 *
 * @author 周林
 * @version 1.0
 * @date 2021/6/7 16:38
 */
@Data
public class DefaultLabelConfig {
    public static final String URL = YamlUtils.getProperty("noask.orient.url");
    public static final String USERNAME = YamlUtils.getProperty("noask.orient.username");
    public static final String PASSWORD = YamlUtils.getProperty("noask.orient.password");
}
