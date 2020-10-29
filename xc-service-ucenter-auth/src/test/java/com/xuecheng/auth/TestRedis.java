package com.xuecheng.auth;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRedis {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    //创建jwt令牌
    @Test
    public void testRedis(){
        //定义key
        String key = "user_token:c8ecdd77-c43e-4702-bd3c-71a88b31fe18";
        //定义value
        Map<String,String> value = new HashMap<>();
        value.put("jwt","eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJjb21wYW55SWQiOm51bGwsInVzZXJwaWMiOm51bGwsInVzZXJfbmFtZSI6Iml0Y2FzdCIsInNjb3BlIjpbImFwcCJdLCJuYW1lIjpudWxsLCJ1dHlwZSI6bnVsbCwiaWQiOm51bGwsImV4cCI6MTYwMzI4OTE4MCwianRpIjoiYzhlY2RkNzctYzQzZS00NzAyLWJkM2MtNzFhODhiMzFmZTE4IiwiY2xpZW50X2lkIjoiWGNXZWJBcHAifQ.H5stH6BrCor_uuAYbiolb1um2pkYqrNTl_XEyjD31n018sGqi9CCNWgeXFXnPsSar9z3CahtDzme4ljDk6SK_7K22gYggTLEGVP-oT2v37_VN_mPA88G7dL1RPOIXY2AZNYYuS-nLzUMQ6WEYV19AYoooSum4WhVt0yTxWMAbzSGMBuk6y0vfjrhXRE-aSspN9c2pl0G2dvwWFr67on56L7z15hVbAvVBStRCdztl5XkzldBQgSMLP16WNurhM_6fCaLMrPTwCm3Ua07UQidxgzpI-tRftBVzbmgsrMkgwUrhkTDr6i65fhYPlyT6mctIESjZ3vMCH9psFpknUDmrQ");
        value.put("refresh_token","eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJjb21wYW55SWQiOm51bGwsInVzZXJwaWMiOm51bGwsInVzZXJfbmFtZSI6Iml0Y2FzdCIsInNjb3BlIjpbImFwcCJdLCJhdGkiOiJjOGVjZGQ3Ny1jNDNlLTQ3MDItYmQzYy03MWE4OGIzMWZlMTgiLCJuYW1lIjpudWxsLCJ1dHlwZSI6bnVsbCwiaWQiOm51bGwsImV4cCI6MTYwMzI4OTE4MCwianRpIjoiZjU1YzAzNTctOTI4ZC00ZDMwLThlYTgtZDY3ZDBiMDE2NzU3IiwiY2xpZW50X2lkIjoiWGNXZWJBcHAifQ.V_klZ2Hf-y3plTfcQ6i91SLAuuvuYeo4oOkZoHDF_Z6PmwOojdamdNTQIMQAabuDHsc2dFRS3-AR0HHuyx4faWGJYfF8Bnix6YMXg6HAmOUkXwM0SsR7UFSES--aPV7qIIBTxO_D_u_8hMi0rKSa6AdatYAPFvatWzu0N4MODKS1B4KPv4S0v5DndwkLtbFfFNKc88CiUrD6mk-_0o4nhQymh3L3pCdY8GwXCXS3XX5jZPT-lZ60bmQxGFfsm8XPxP3xCGR8YzQrHsV-bzVGLC9NjbJh1hsNrOdKMQkns802BqPOuOLNE7HWY56FbS3iyoScclAbLZgWI2NAD890_Q");
        String jsonString = JSON.toJSONString(value);
        //校验key是否存在，如果不存在则返回-2
        Long expire = stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
        System.out.println(expire);
        //存储数据
        stringRedisTemplate.boundValueOps(key).set(jsonString,30, TimeUnit.SECONDS);
        //获取数据
        String string = stringRedisTemplate.opsForValue().get(key);
        System.out.println(string);


    }


}
