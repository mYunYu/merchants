package com.jju.passbook.service;

import com.alibaba.fastjson.JSON;
import com.jju.passbook.vo.CreateMerchantsRequest;
import com.jju.passbook.vo.PassTemplate;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 *  商户服务测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MerchantsServiceTest {

    @Autowired
    private IMerchantsService merchantsService;

    /**
     *  {"data":{"id":17},"errorCode":0,"errorMsg":""}
     *  @Transactional: 加这个注解主要是为了测试是否会返回成功信息，但不会真正添加数据到数据库中
     */
    @Test
//    @Transactional
    public void testCreateMerchantsService(){
        CreateMerchantsRequest request = new CreateMerchantsRequest();
        request.setName("九院");
        request.setLogoUrl("www.jju.com");
        request.setBusinessLicenseUrl("www.jju.com");
        request.setPhone("123456789");
        request.setAddress("江西九江");

        System.out.println(JSON.toJSON(merchantsService.createMerchants(request)));
    }

    /**
     *  {"data":{"address":"江西九江","phone":"123456789","businessLicenseUrl":"www.jju.com","isAudit":false,"name":"九院","id":19,"logoUrl":"www.jju.com"},"errorCode":0,"errorMsg":""}
     */
    @Test
    public void testBuildMerchantsInfoById(){
        System.out.println(JSON.toJSON(merchantsService.buildMerchantsInfoById(19)));
    }

    @Test
    public void testDropPassTemplate(){
        PassTemplate passTemplate = new PassTemplate();
        passTemplate.setId(19);
        passTemplate.setTitle("title: 九院");
        passTemplate.setSummary("简介: 九院");
        passTemplate.setDesc("详情: 九院");
        passTemplate.setLimit(10000L);
        passTemplate.setHasToken(false);
        passTemplate.setBackground(2);
        passTemplate.setStart(new Date());
        passTemplate.setEnd(DateUtils.addDays(new Date(), 10));

        System.out.println(JSON.toJSONString(merchantsService.dropPassTemplate(passTemplate)));
    }

}
