package com.example.rentsummary.server;


import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class RentContextGet {

    public static void main(String[] args) {
//        getRentFromDomain();
        getRentFromAllHomes();
//        getRentFromRealestate();  //有问题，查询参数待确认
//        getRentFromzango();
    }

    public static String getRentFromAllHomes() {
        //1.生成httpclient，相当于该打开一个浏览器
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        //2.创建get请求，相当于在浏览器地址栏输入 网址

        HttpPost request = new HttpPost("https://www.allhomes.com.au/wsvc/search/rent-residential");
        request.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:86.0) Gecko/20100101 Firefox/86.0");
        try {
            //添加查询参数
//            POST /wsvc/search/rent-residential HTTP/1.1
//            Host: www.allhomes.com.au
//            User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:86.0) Gecko/20100101 Firefox/86.0
//            Accept: */*
//Accept-Language: zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2
//Accept-Encoding: gzip, deflate, br
//Referer: https://www.allhomes.com.au/rent/search?region=canberra-act&district=greater-queanbeyan-queanbeyan-region-nsw
//Content-Type: application/json
//Origin: https://www.allhomes.com.au
//Content-Length: 254
//Connection: keep-alive
//Cookie: _ah-live-bucket=72; _gcl_au=1.1.1335834284.1615377572; _ga=GA1.3.187038493.1615377581; __gads=ID=23d4c4387ad95d78-225bd3f959c6007a:T=1615377839:S=ALNI_MbNOJATdkN905PF52RldOD8I8th4w; _hjid=20837137-d863-4c5f-8051-a2053b26811b; _cc=CN; _eu=false; _hjTLDTest=1; _gid=GA1.3.2081782438.1615479965; _dc_gtm_UA-3422343-2=1; JSESSIONID=1btDt5BUYrbKfIvI9K8O69Np; AWSELB=F92791F910AE93E62FB7EF142FB37F859DEAC6E13022F61AD463AB290FADE73C8CC09B90FB0D32A0717A31E99DD972934FCD4B0B427D10EA130154773DF42688C3440D0A573BD2012491AB38668450D6AEA78976AE; _hjIncludedInPageviewSample=1; _hjIncludedInSessionSample=0; _gat_UA-3422343-2=1
//Pragma: no-cache
//Cache-Control: no-cache

            //allhomes
            String paraJson="{\"page\":1,\"pageSize\":50,\"sort\":{\"criteria\":\"PRICE\",\"order\":\"ASC\"},\"filters\":{\"localities\":[{\"type\":\"REGION\",\"slug\":\"canberra-act\"},{\"type\":\"DISTRICT\",\"slug\":\"greater-queanbeyan-queanbeyan-region-nsw\"}],\"beds\":{\"min\":4,\"max\":4}},\"results\":{\"type\":\"LIST\"}}";
            StringEntity entity=new StringEntity(paraJson,"UTF-8");
            request.setEntity(entity);

            //3.执行get请求，相当于在输入地址栏后敲回车键
            response = httpClient.execute(request);

            //4.判断响应状态为200，进行处理
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                //5.获取响应内容
                HttpEntity httpEntity = response.getEntity();
                String html = EntityUtils.toString(httpEntity, "utf-8");
                System.out.println(html);
                return html;
            } else {
                //如果返回状态不是200，比如404（页面不存在）等，根据情况做处理，这里略
                System.out.println("返回状态不是200");
                System.out.println(EntityUtils.toString(response.getEntity(), "utf-8"));
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //6.关闭
            HttpClientUtils.closeQuietly(response);
            HttpClientUtils.closeQuietly(httpClient);
        }
        return null;
    }
    public static String getRentFromDomain() {
        //1.生成httpclient，相当于该打开一个浏览器
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        //2.创建get请求，相当于在浏览器地址栏输入 网址

        HttpGet request = new HttpGet("https://www.domain.com.au/rent/indented-head-vic-3223/?bedrooms=2-any&excludedeposittaken=1");
        request.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:86.0) Gecko/20100101 Firefox/86.0");
        try {
            //3.执行get请求，相当于在输入地址栏后敲回车键
            response = httpClient.execute(request);

            //4.判断响应状态为200，进行处理
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                //5.获取响应内容
                HttpEntity httpEntity = response.getEntity();
                String html = EntityUtils.toString(httpEntity, "utf-8");
                html=html.substring(html.indexOf("APP_PROPS"),html.indexOf("APP_PAGE"));

                System.out.println(html);
                return html;
            } else {
                //如果返回状态不是200，比如404（页面不存在）等，根据情况做处理，这里略
                System.out.println("返回状态不是200");
                System.out.println(EntityUtils.toString(response.getEntity(), "utf-8"));
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //6.关闭
            HttpClientUtils.closeQuietly(response);
            HttpClientUtils.closeQuietly(httpClient);
        }
        return null;
    }
    public static String getRentFromRealestate() {
        //1.生成httpclient，相当于该打开一个浏览器
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        //2.创建get请求，相当于在浏览器地址栏输入 网址

        HttpGet request = new HttpGet("hhttps://www.realestate.com.au/rent/with-1-bedroom-between-0-5000/list-1?maxBeds=2");
        request.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:86.0) Gecko/20100101 Firefox/86.0");
        try {
            //3.执行get请求，相当于在输入地址栏后敲回车键
            response = httpClient.execute(request);

            //4.判断响应状态为200，进行处理
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                //5.获取响应内容
                HttpEntity httpEntity = response.getEntity();
                String html = EntityUtils.toString(httpEntity, "utf-8");
                System.out.println(html);
                return html;
            } else {
                //如果返回状态不是200，比如404（页面不存在）等，根据情况做处理，这里略
                System.out.println("返回状态不是200");
                System.out.println(EntityUtils.toString(response.getEntity(), "utf-8"));
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //6.关闭
            HttpClientUtils.closeQuietly(response);
            HttpClientUtils.closeQuietly(httpClient);
        }
        return null;
    }
    public static String getRentFromzango() {
        //1.生成httpclient，相当于该打开一个浏览器
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        //2.创建get请求，相当于在浏览器地址栏输入 网址

        HttpGet request = new HttpGet("https://zango.com.au/api/pages/70/?property_class=rental&listing_type=lease&surrounding=true&bedrooms__gte=1&price__gte=50&price__lte=10000&order_by=price&property_status_groups=current%2CunderOffer%2CincludePrivate&bedrooms__lte=1&filters=1&page=1");
        request.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:86.0) Gecko/20100101 Firefox/86.0");
        try {

            //3.执行get请求，相当于在输入地址栏后敲回车键
            response = httpClient.execute(request);

            //4.判断响应状态为200，进行处理
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                //5.获取响应内容
                HttpEntity httpEntity = response.getEntity();
                String html = EntityUtils.toString(httpEntity, "utf-8");
                System.out.println(html);
                return html;
            } else {
                //如果返回状态不是200，比如404（页面不存在）等，根据情况做处理，这里略
                System.out.println("返回状态不是200");
                System.out.println(EntityUtils.toString(response.getEntity(), "utf-8"));
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //6.关闭
            HttpClientUtils.closeQuietly(response);
            HttpClientUtils.closeQuietly(httpClient);
        }
        return null;
    }
}
