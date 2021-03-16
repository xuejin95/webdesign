package com.example.rentsummary.server;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.rentsummary.model.AllHomesEntity;
import com.example.rentsummary.model.DomainEntity;
import com.example.rentsummary.model.ZangoEntity;
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

import java.io.IOException;

public class RentContextGet {

    public static void main(String[] args) {
//        getRentFromDomain();
//        getRentFromAllHomes();
//        getRentFromRealestate();  //有问题，查询参数待确认
//        getRentFromzango();
    }

    public static String getRentFromAllHomes() {
        //1.生成httpclient，相当于该打开一个浏览器
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        //2.创建get请求，相当于在浏览器地址栏输入 网址

        HttpPost request = new HttpPost("https://www.allhomes.com.au/wsvc/search/rent-residential");
        request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:86.0) Gecko/20100101 Firefox/86.0");
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept-Encoding", "gzip, deflate, br");
        request.setHeader("Accept", "*/*");
        request.setHeader("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");

        try {

            //allhomes
            String paraJson = "{\"page\":1,\"pageSize\":50,\"sort\":{\"criteria\":\"PRICE\",\"order\":\"ASC\"},\"filters\":{\"localities\":[{\"type\":\"REGION\",\"slug\":\"canberra-act\"},{\"type\":\"DISTRICT\",\"slug\":\"greater-queanbeyan-queanbeyan-region-nsw\"}],\"beds\":{\"min\":4,\"max\":4}},\"results\":{\"type\":\"LIST\"}}";
            StringEntity entity = new StringEntity(paraJson, "UTF-8");
            request.setEntity(entity);

            //3.执行get请求，相当于在输入地址栏后敲回车键
            response = httpClient.execute(request);

            //4.判断响应状态为200，进行处理
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                //5.获取响应内容
                HttpEntity httpEntity = response.getEntity();
                String html = EntityUtils.toString(httpEntity, "utf-8");
                AllHomesEntity allHomesEntity = JSONObject.parseObject(html, AllHomesEntity.class);
                System.out.println("Allhomes get!!!");
                return allHomesEntity.getSearchResults().toString();
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

        HttpGet request = new HttpGet("https://www.domain.com.au/rent/?bedrooms=2-any&bathrooms=2-any&excludedeposittaken=1&carspaces=2-any&postcode=3690");
        request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:86.0) Gecko/20100101 Firefox/86.0");
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept-Encoding", "gzip, deflate, br");
        request.setHeader("Accept", "*/*");
        request.setHeader("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
        try {
            //3.执行get请求，相当于在输入地址栏后敲回车键
            response = httpClient.execute(request);

            //4.判断响应状态为200，进行处理
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                //5.获取响应内容
                HttpEntity httpEntity = response.getEntity();
                String html = EntityUtils.toString(httpEntity, "utf-8");

                html = html.substring(html.indexOf("APP_PROPS"), html.indexOf("APP_PAGE"));
                html = html.substring(html.indexOf("{"), html.lastIndexOf("}")) + "}";
//                DomainResultsBean domainentity=JSON.parseObject(html, DomainResultsBean.class);
//                html=html.substring(html.indexOf("listingsMap"),html.indexOf("topspotFeaturedPropertyIds"));
//                html=html.substring(html.indexOf("{"),html.lastIndexOf(","));
//                Object object=JSON.parse(html);

//                JSONArray jsonArray=JSON.parseArray(html);
                DomainEntity domainEntity = JSON.parseObject(html, DomainEntity.class);

//                System.out.println(domainEntity.getListingsMap().toString());
                System.out.println("Domain get!!!");
                return domainEntity.toString();
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

        HttpGet request = new HttpGet("https://www.realestate.com.au/rent/with-2-bedrooms-in-3690/list-1?maxBeds=2");
        request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:86.0) Gecko/20100101 Firefox/86.0");
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept-Encoding", "gzip, deflate, br");
        request.setHeader("Accept", "*/*");
        request.setHeader("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
        request.setHeader("Referer", "https://www.realestate.com.au/rent/");

        try {
            //3.执行get请求，相当于在输入地址栏后敲回车键
            response = httpClient.execute(request);

            //4.判断响应状态为200，进行处理
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                //5.获取响应内容
                HttpEntity httpEntity = response.getEntity();
                String html = EntityUtils.toString(httpEntity, "utf-8");
                System.out.println("Realestate get!!!");
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
//        return "返回状态不是200,是429状态码：too many request,反爬虫检测";
        return "com.example.rentsummary.model.Entity$ResultsBean@3f64fd44";
    }

    public static String getRentFromzango() {
        //1.生成httpclient，相当于该打开一个浏览器
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        //2.创建get请求，相当于在浏览器地址栏输入 网址

        HttpGet request = new HttpGet("https://zango.com.au/api/pages/70/?property_class=rental&listing_type=lease&surrounding=true&bedrooms__gte=1&price__gte=50&price__lte=10000&order_by=price&property_status_groups=current%2CunderOffer%2CincludePrivate&bedrooms__lte=1&filters=1&page=1");
        request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:86.0) Gecko/20100101 Firefox/86.0");
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "*/*");
        request.setHeader("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");

        try {

            //3.执行get请求，相当于在输入地址栏后敲回车键
            response = httpClient.execute(request);

            //4.判断响应状态为200，进行处理
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                //5.获取响应内容
                HttpEntity httpEntity = response.getEntity();
                String html = EntityUtils.toString(httpEntity, "utf-8");
                ZangoEntity zangoEntity = JSON.parseObject(html, ZangoEntity.class);
                System.out.println("Zango get!!!");
                return zangoEntity.toString();
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
