package com.example.javaorientdemo;

import com.example.javaorientdemo.abstracts.ITestOrrr;
import com.example.javaorientdemo.abstracts.impl.TestOrrr;
import com.example.javaorientdemo.config.DefaultLabelConfig;
import com.example.javaorientdemo.vo.LabelVertexRepVO;
import org.junit.jupiter.api.Test;

/**
 * 描述
 *
 * @author 周林
 * @version 1.0
 * @date 2021/6/9 18:01
 */
public class OrientTest {
    @Test
    public void asfd() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Object o = Class.forName("com.example.javaorientdemo.vo.LabelVertexRepVO").newInstance();
        System.out.println();
    }
    @Test
    public void testYamlUtils() {
        String url = DefaultLabelConfig.URL;
        System.out.println(url);
    }

    public static void main(String[] args) {
        ITestOrrr<LabelVertexRepVO> orientExecute = new TestOrrr<LabelVertexRepVO>();
//        OrientExecute<LabelVertexRepVO> orientExecute = new BaseOrientExecute<>(executeService, "precise_push");
    }
    @Test
    public void testOrientConnect() {
//        String sql = "select FROM LabelVertex where labelName = '玉米种植';";
//        IOrientExecuteService executeService = new OrientExecuteServiceImpl();
//        OrientExecute<LabelVertexRepVO> orientExecute = new BaseOrientExecute<LabelVertexRepVO>(executeService, "precise_push");
//
//        LabelVertexRepVO labelVertex = orientExecute.findByRid("#131:38");
//
//        System.out.println(labelVertex);
//        System.out.println();
    }

}
