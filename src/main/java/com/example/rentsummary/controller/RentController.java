package com.example.rentsummary.controller;

import com.example.rentsummary.model.RentRequestParaForAllhomes;
import com.example.rentsummary.server.RentContextGet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class RentController {

    @GetMapping(value = "getrent")
    public String getRent(HttpServletRequest request, Model model) {
        RentRequestParaForAllhomes rentRequestParaForAllhomes=parseParaForAllhomes(request);
        String queryParaForDomain=parseParaForDomain(request);

        model.addAttribute("allhomesSearchList",RentContextGet.getRentFromAllHomes(rentRequestParaForAllhomes));
        model.addAttribute("domain",RentContextGet.getRentFromDomain(queryParaForDomain));
        model.addAttribute("zango",RentContextGet.getRentFromzango());
        model.addAttribute("realestate",RentContextGet.getRentFromRealestate());

        return "rentsummary";
    }

    @GetMapping(value = "/")
    public String toIndex(){
        return "rentsummary.html";
    }

    @GetMapping(value = "/test")
    @ResponseBody
    public String test(){
        return "rentsummary";
    }

    @GetMapping(value = "/allhomes")
    public String allhomes(){
        return "allhomes";
    }
    @GetMapping(value = "/domain")
    public String domain(){
        return "domain";
    }
    @GetMapping(value = "/realestate")
    public String realestate(){
        return "realestate";
    }
    @GetMapping(value = "/zango")
    public String zango(){
        return "zango";
    }


    //解析请求参数
    public RentRequestParaForAllhomes parseParaForAllhomes(HttpServletRequest request){
        //para
        String region=request.getParameter("REGION");
        String district=request.getParameter("DISTRICT");
        String[] propertyTypes=request.getParameterValues("propertyTypes");
        String anyminprice=request.getParameter("anyminprice");
        String anymaxprice=request.getParameter("anymaxprice");

        String anydate=request.getParameter("anydate");
        String datetype=request.getParameter("datetype");
        String date=request.getParameter("date");

        String[] bedrooms=request.getParameterValues("bedrooms");
        String[] bathrooms=request.getParameterValues("bathrooms");
        String[] parkings=request.getParameterValues("parking");
        String minSize=request.getParameter("minsize");
        String maxsize=request.getParameter("maxsize");
        String keywords=request.getParameter("keywords");

        //组装rentrequestparaforallhomes
        RentRequestParaForAllhomes para=new RentRequestParaForAllhomes();
        RentRequestParaForAllhomes.FiltersBean filtersBean=new RentRequestParaForAllhomes.FiltersBean();
        para.setFilters(filtersBean);
        //设置租金
        RentRequestParaForAllhomes.FiltersBean.PriceBean priceBean=new RentRequestParaForAllhomes.FiltersBean.PriceBean();
        para.getFilters().setPrice(priceBean);
        para.getFilters().getPrice().setMin(null);
        para.getFilters().getPrice().setMax(null);
        if (!anyminprice.equals("anyminprice") ){
            int minprice=Integer.parseInt(request.getParameter("min"));
            para.getFilters().getPrice().setMin(minprice);
        }
        if (!anymaxprice.equals("anymaxprice") ){
            int maxprice=Integer.parseInt(request.getParameter("max"));
            para.getFilters().getPrice().setMax(maxprice);
        }
        //设置床数
        int[] bedarray = Arrays.stream(bedrooms).mapToInt(Integer::parseInt).toArray();
        int bedminNum = Arrays.stream(bedarray).min().getAsInt();
        int bedmaxNum = Arrays.stream(bedarray).max().getAsInt();
        RentRequestParaForAllhomes.FiltersBean.BedsBean bedsBean=new RentRequestParaForAllhomes.FiltersBean.BedsBean();
        para.getFilters().setBeds(bedsBean);
        if (bedminNum==0){
            para.getFilters().getBeds().setMin(null);
            para.getFilters().getBeds().setMax(null);
        }else {
            para.getFilters().getBeds().setMin(bedminNum);
            para.getFilters().getBeds().setMax(bedmaxNum);
        }

        //设置浴室
        int[] batharray = Arrays.stream(bathrooms).mapToInt(Integer::parseInt).toArray();
        int bathminNum = Arrays.stream(bedarray).min().getAsInt();
        int bathmaxNum = Arrays.stream(bedarray).max().getAsInt();
        RentRequestParaForAllhomes.FiltersBean.BathsBean bathsBean=new RentRequestParaForAllhomes.FiltersBean.BathsBean();
        para.getFilters().setBaths(bathsBean);
        if (bathminNum==0){
            para.getFilters().getBaths().setMin(null);
            para.getFilters().getBaths().setMax(null);
        }else {
            para.getFilters().getBaths().setMin(bathminNum);
            para.getFilters().getBaths().setMax(bathmaxNum);
        }

        //设置车位
        int[] parkingsarray = Arrays.stream(parkings).mapToInt(Integer::parseInt).toArray();
        int parkingsminNum = Arrays.stream(parkingsarray).min().getAsInt();
        int parkingsmaxNum = Arrays.stream(parkingsarray).max().getAsInt();
        RentRequestParaForAllhomes.FiltersBean.ParkingBean parkingBean=new RentRequestParaForAllhomes.FiltersBean.ParkingBean();
        para.getFilters().setParking(parkingBean);
        if (parkingsminNum==0){
            para.getFilters().getParking().setMin(null);
            para.getFilters().getParking().setMax(null);
        }else {
            para.getFilters().getParking().setMin(parkingsminNum);
            para.getFilters().getParking().setMax(parkingsmaxNum);
        }

        //设置区域大小
        RentRequestParaForAllhomes.FiltersBean.BlockSizeBean blockSizeBean=new RentRequestParaForAllhomes.FiltersBean.BlockSizeBean();
        para.getFilters().setBlockSize(blockSizeBean);
        para.getFilters().getBlockSize().setMin(null);
        para.getFilters().getBlockSize().setMax(null);
        if (minSize.length()!=0){
            para.getFilters().getBlockSize().setMin(Integer.parseInt(minSize));
        }
        if (maxsize.length()!=0){
            para.getFilters().getBlockSize().setMax(Integer.parseInt(maxsize));
        }

        //设置关键字
        para.getFilters().setKeywords(null);
        if(keywords.length()!=0){
            para.getFilters().setKeywords(keywords);
        }

        //设置可用日期
        RentRequestParaForAllhomes.FiltersBean.AvailabilityBean availabilityBean=new RentRequestParaForAllhomes.FiltersBean.AvailabilityBean();
        para.getFilters().setAvailability(availabilityBean);
        para.getFilters().getAvailability().setMin(null);
        para.getFilters().getAvailability().setMax(null);
        if(!anydate.equals("anydate")){
            if (datetype.equals("before")){
                para.getFilters().getAvailability().setMax(date);
            }else {
                para.getFilters().getAvailability().setMin(date);
            }
        }

        //设置地区
        List<RentRequestParaForAllhomes.FiltersBean.LocalitiesBean>  localitiesBeanslist=new ArrayList<RentRequestParaForAllhomes.FiltersBean.LocalitiesBean>();
        para.getFilters().setLocalities(localitiesBeanslist);
        RentRequestParaForAllhomes.FiltersBean.LocalitiesBean localitiesBean=new RentRequestParaForAllhomes.FiltersBean.LocalitiesBean();
        localitiesBean.setType("REGION");
        localitiesBean.setSlug(region);
//        RentRequestParaForAllhomes.FiltersBean.LocalitiesBean localitiesBean1=new RentRequestParaForAllhomes.FiltersBean.LocalitiesBean();
//        localitiesBean1.setType("DISTRICT");
//        localitiesBean1.setSlug(district);
        para.getFilters().getLocalities().add(localitiesBean);
//        para.getFilters().getLocalities().add(localitiesBean1);

        //设置propertyTypes
        List<String> propertiesList=Arrays.asList(propertyTypes);
        para.getFilters().setPropertyTypes(null);
        if (!propertiesList.contains("__ALL__")){
            para.getFilters().setPropertyTypes(propertiesList);
        }
        return  para;
    }

    //解析请求参数
    public String parseParaForDomain(HttpServletRequest request){
        //para
        String region=request.getParameter("REGION");
        String district=request.getParameter("DISTRICT");
        String[] propertyTypes=request.getParameterValues("propertyTypes");
        String anyminprice=request.getParameter("anyminprice");
        String anymaxprice=request.getParameter("anymaxprice");

        String anydate=request.getParameter("anydate");
        String datetype=request.getParameter("datetype");
        String date=request.getParameter("date");

        String[] bedrooms=request.getParameterValues("bedrooms");
        String[] bathrooms=request.getParameterValues("bathrooms");
        String[] parkings=request.getParameterValues("parking");
        String minSize=request.getParameter("minsize");
        String maxsize=request.getParameter("maxsize");
        String keywords=request.getParameter("keywords");

        //拼装get请求参数
        StringBuffer para=new StringBuffer();

        //组装地区参数
        para.append(region+"/?");

        //组装房屋类型
        //设置propertyTypes
        List<String> propertiesList=Arrays.asList(propertyTypes);
        if (!propertiesList.contains("__ALL__")){

        }else {
            para.append("ptype=apartment-unit-flat,block-of-units,duplex,free-standing,new-apartments,new-home-designs,new-house-land,pent-house,semi-detached,studio,terrace,town-house,villa&excludedeposittaken=1");
        }
        para.append("&");


        //设置租金
        int minprice=0;
        int maxprice=0;
        if (!anyminprice.equals("anyminprice") ){
            minprice=Integer.parseInt(request.getParameter("min"));
        }
        if (!anymaxprice.equals("anymaxprice") ){
            maxprice =Integer.parseInt(request.getParameter("max"));
        }
        if (maxprice==0){
            para.append("price="+minprice+"-any");
        }else {
            para.append("price="+minprice+"-"+maxprice);
        }
        para.append("&");

        //设置床数
        int[] bedarray = Arrays.stream(bedrooms).mapToInt(Integer::parseInt).toArray();
        int bedminNum = Arrays.stream(bedarray).min().getAsInt();
        int bedmaxNum = Arrays.stream(bedarray).max().getAsInt();
        if (bedmaxNum==0){
            para.append("bedrooms="+bedminNum+"-any");
        }else {
            para.append("bedrooms="+bedminNum+"-"+bedmaxNum);
        }
        para.append("&");

        //设置浴室
        int[] batharray = Arrays.stream(bathrooms).mapToInt(Integer::parseInt).toArray();
        int bathminNum = Arrays.stream(bedarray).min().getAsInt();
        int bathmaxNum = Arrays.stream(bedarray).max().getAsInt();
        if (maxprice==0){
            para.append("bathrooms="+bathminNum+"-any");
        }else {
            para.append("bathrooms="+bathminNum+"-"+bathmaxNum);
        }
        para.append("&");

        //设置车位
        int[] parkingsarray = Arrays.stream(parkings).mapToInt(Integer::parseInt).toArray();
        int parkingsminNum = Arrays.stream(parkingsarray).min().getAsInt();
        int parkingsmaxNum = Arrays.stream(parkingsarray).max().getAsInt();
        if (maxprice==0){
            para.append("carspaces="+parkingsminNum+"-any");
        }else {
            para.append("carspaces="+parkingsminNum+"-"+parkingsmaxNum);
        }
        para.append("&");


        //设置区域大小
//        para.getFilters().setBlockSize(blockSizeBean);
//        para.getFilters().getBlockSize().setMin(null);
//        para.getFilters().getBlockSize().setMax(null);
//        if (minSize.length()!=0){
//            para.getFilters().getBlockSize().setMin(Integer.parseInt(minSize));
//        }
//        if (maxsize.length()!=0){
//            para.getFilters().getBlockSize().setMax(Integer.parseInt(maxsize));
//        }

        //设置关键字
        if(keywords.length()!=0){
            para.append("keywords="+keywords);
        }

        //设置可用日期
//        RentRequestParaForAllhomes.FiltersBean.AvailabilityBean availabilityBean=new RentRequestParaForAllhomes.FiltersBean.AvailabilityBean();
//        para.getFilters().setAvailability(availabilityBean);
//        para.getFilters().getAvailability().setMin(null);
//        para.getFilters().getAvailability().setMax(null);
//        if(!anydate.equals("anydate")){
//            if (datetype.equals("before")){
//                para.getFilters().getAvailability().setMax(date);
//            }else {
//                para.getFilters().getAvailability().setMin(date);
//            }
//        }
//
//        //设置地区
//        List<RentRequestParaForAllhomes.FiltersBean.LocalitiesBean>  localitiesBeanslist=new ArrayList<RentRequestParaForAllhomes.FiltersBean.LocalitiesBean>();
//        para.getFilters().setLocalities(localitiesBeanslist);
//        RentRequestParaForAllhomes.FiltersBean.LocalitiesBean localitiesBean=new RentRequestParaForAllhomes.FiltersBean.LocalitiesBean();
//        localitiesBean.setType("REGION");
//        localitiesBean.setSlug(region);
//        RentRequestParaForAllhomes.FiltersBean.LocalitiesBean localitiesBean1=new RentRequestParaForAllhomes.FiltersBean.LocalitiesBean();
//        localitiesBean1.setType("DISTRICT");
//        localitiesBean1.setSlug(district);
//        para.getFilters().getLocalities().add(localitiesBean);
//        para.getFilters().getLocalities().add(localitiesBean1);

        return  para.toString();
    }
}
