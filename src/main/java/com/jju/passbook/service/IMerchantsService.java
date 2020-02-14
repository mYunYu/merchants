package com.jju.passbook.service;

import com.jju.passbook.vo.CreateMerchantsRequest;
import com.jju.passbook.vo.PassTemplate;
import com.jju.passbook.vo.Response;

/**
 *  对商户服务接口定义
 */
public interface IMerchantsService {

    /**
     *  创建商户服务
     * @param request       商户请求对象
     * @return              Response
     */
    Response createMerchants(CreateMerchantsRequest request);

    /**
     *  根据id构造商户信息
     * @param id    商户id
     * @return      Response
     */
    Response buildMerchantsInfoById(Integer id);

    /**
     *  投放优惠券
     * @param passTemplate      优惠券对象
     * @return                  Response
     */
    Response dropPassTemplate(PassTemplate passTemplate);
}
