package com.example.sentineldemo.ctrl;

import com.example.sentineldemo.service.GoodsQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 描述
 *
 * @author 周林
 * @version 1.0
 * @date 2021/4/30 15:37
 */
// 测试类
@Controller
@RequestMapping("goods")
public class GoodsController {

    @Autowired
    private GoodsQueryService goodsQueryService;

    @RequestMapping("/queryGoodsInfo")
    @ResponseBody
    public String queryGoodsInfo(@RequestParam("spuId") String spuId) {
        String res = goodsQueryService.queryGoodsInfo(spuId);
        return res;
    }
}
