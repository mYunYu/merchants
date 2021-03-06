package com.jju.passbook.service.impl;

import com.alibaba.fastjson.JSON;
import com.jju.passbook.constant.Constants;
import com.jju.passbook.constant.ErrorCode;
import com.jju.passbook.dao.MerchantsDao;
import com.jju.passbook.entity.Merchants;
import com.jju.passbook.service.IMerchantsService;
import com.jju.passbook.vo.CreateMerchantsRequest;
import com.jju.passbook.vo.CreateMerchantsResponse;
import com.jju.passbook.vo.PassTemplate;
import com.jju.passbook.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *  商户服务器接口实现
 */
@Slf4j
@Service
public class MerchantsServiceImpl implements IMerchantsService {

    //数据库接口，通过构造函数形式注入
    private final MerchantsDao merchantsDao;

    //kafka 客户端
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public MerchantsServiceImpl(MerchantsDao merchantsDao, KafkaTemplate<String, String> kafkaTemplate){
        this.merchantsDao = merchantsDao;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    @Transactional
    public Response createMerchants(CreateMerchantsRequest request) {
        Response response = new Response();
        CreateMerchantsResponse merchantsResponse = new CreateMerchantsResponse();

        ErrorCode errorCode = request.validate(merchantsDao);
        if(errorCode != ErrorCode.SUCCESS){
            merchantsResponse.setId(-1);
            response.setErrorCode(errorCode.getCode());
            response.setErrorMsg(errorCode.getDesc());
        }
        else{
            merchantsResponse.setId(merchantsDao.save(request.toMerchants()).getId());
        }

        response.setData(merchantsResponse);

        return response;
    }

    @Override
    public Response buildMerchantsInfoById(Integer id) {
        Response response = new Response();

        Merchants merchants = merchantsDao.findById(id);
        if(null == merchants){
            response.setErrorCode(ErrorCode.MERCHANTS_NOT_EXIST.getCode());
            response.setErrorMsg(ErrorCode.MERCHANTS_NOT_EXIST.getDesc());
        }

        response.setData(merchants);

        return response;
    }

    @Override
    public Response dropPassTemplate(PassTemplate template) {
        Response response = new Response();
        ErrorCode errorCode = template.validate(merchantsDao);
        if(errorCode != ErrorCode.SUCCESS){
            response.setErrorCode(errorCode.getCode());
            response.setErrorMsg(errorCode.getDesc());
        }

        //Producer   kafka-console-producer.bat --broker-list localhost:9092 --topic merchants-template
        //消费端查看(当前)消费信息Consumer   kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic merchants-template
        //消费端查看(所有)消费信息：kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic merchants-template --from-beginning
        else{
            String passTemplate = JSON.toJSONString(template);
            kafkaTemplate.send(
                    Constants.TEMPLATE_TOPIC,
                    Constants.TEMPLATE_TOPIC,
                    passTemplate
            );
            log.info("DropPassTemplate: {}", passTemplate);
        }

        return response;
    }
}
