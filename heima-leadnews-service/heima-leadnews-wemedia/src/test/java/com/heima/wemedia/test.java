package com.heima.wemedia;

import com.baidu.aip.contentcensor.AipContentCensor;
import com.heima.wemedia.baidu.MyBaiduCensor;
import com.heima.wemedia.service.WmNewsAutoScanService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class test {


//    baidu:
//    appId: 45238159
//    apiKey: 5We2PdCwjokdp3DTuykzEPaM
//    secretKey: 1ZnonNPTMdEQpyKbGgbqsju9d5n2wkCM

    @Test
    void testText(){
        String text = "上门服务，包小姐";

        AipContentCensor contentCensor = new AipContentCensor(
                "45238159",
                "5We2PdCwjokdp3DTuykzEPaM",
                "1ZnonNPTMdEQpyKbGgbqsju9d5n2wkCM"
        );

        JSONObject jsonObject = contentCensor.textCensorUserDefined(text);

        System.out.println(jsonObject);
    }




}
