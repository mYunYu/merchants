package com.jju.passbook.controller;

import com.alibaba.fastjson.JSON;
import com.jju.passbook.service.IMerchantsService;
import com.jju.passbook.vo.CreateMerchantsRequest;
import com.jju.passbook.vo.PassTemplate;
import com.jju.passbook.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *  商户服务Controller
 */
@Slf4j
@RestController
@RequestMapping("/merchants")
public class MerchantsController {

    //商户服务接口
    private final IMerchantsService merchantsService;

    @Autowired
    public MerchantsController(IMerchantsService merchantsService) {
        this.merchantsService = merchantsService;
    }

    @ResponseBody
    @PostMapping("/create")
    public Response createMerchants(@RequestBody CreateMerchantsRequest request){
        log.info("CreateMerchants：{}", JSON.toJSONString(request));
        return merchantsService.createMerchants(request);
    }

    @ResponseBody
    @GetMapping("/{id}")
    public Response buildMerchantsInfo(@PathVariable Integer id){
        log.info("BuildMerchantsInfo：{}", id);
        return merchantsService.buildMerchantsInfoById(id);
    }

    @ResponseBody
    @PostMapping("/drop")
    public Response dropPassTemplate(@RequestBody PassTemplate passTemplate){
        log.info("DropPassTemplate：{}", JSON.toJSONString(passTemplate));
        return merchantsService.dropPassTemplate(passTemplate);
    }

}
