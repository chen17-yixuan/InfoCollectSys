//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.chen17.web.controller;

import com.chen17.domain.Dayerrorwork;
import com.chen17.domain.UpLoadDayErrorWork;
import com.chen17.domain.temporary.TemporaryWorkDomain;
import com.chen17.service.DayWorkService;
import com.chen17.utils.Excel.TemporaryWorkExcelUtils;
import com.chen17.utils.FileUtil;
import com.chen17.utils.JacksonUtil;
import com.chen17.utils.Excel.ExcelUtils;
import com.chen17.utils.Excel.TitleMap;
import com.chen17.utils.Excel.UploadUtil;
import com.chen17.utils.TemporaryWork;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
@RequestMapping({"/daywork"})
public class DayWorkController {
    private static final Logger log = LoggerFactory.getLogger(DayWorkController.class);
    DayWorkService ds;
    TemporaryWork temporaryWork;

    @Autowired
    public DayWorkController(DayWorkService ds,TemporaryWork temporaryWork) {
        this.ds = ds;
        this.temporaryWork = temporaryWork;
    }

    @RequestMapping({"selectall"})
    public String selectAll() {
        return JacksonUtil.toJSon(this.ds.selectAll());
    }

    @RequestMapping({"selectabyid"})
    public String selectaById(@RequestParam("id") Integer id) {
        return JacksonUtil.toJSon(this.ds.selectByPrimaryKey(id));
    }

    @RequestMapping({"querybycompany"})
    public String getListByCompany(@RequestParam("company") String companyName) {
        return null;
    }

    @RequestMapping(
            value = {"uploadnewerrortable"},
            method = {RequestMethod.POST}
    )
    public String receiveFile(HttpServletRequest request) throws IOException {
        if (!(request instanceof MultipartHttpServletRequest)) {
            return "Not A Right File";
        } else {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
            MultipartFile file = multipartRequest.getFile("file");
            InputStream receiveExcelStream = file.getInputStream();
            List<Dayerrorwork> dayerrorworks = UploadUtil.upFillToMod(ExcelUtils.simpleRead(receiveExcelStream), this.ds.getAllIpList(), this.ds.getUpLoadLastDate());
            this.ds.cleanAllNewCreate();
            Iterator var6 = dayerrorworks.iterator();

            while(var6.hasNext()) {
                Dayerrorwork dw = (Dayerrorwork)var6.next();
                this.ds.insertUpLoaded(dw);
            }

            String name = multipartRequest.getParameter("name");
            String content = multipartRequest.getParameter("content");
            System.out.println("name:" + name);
            System.out.println("content:" + content);
            return "" + dayerrorworks.size();
        }
    }

    @RequestMapping({"checkanddelete"})
    public String checkAndDelete(HttpServletRequest request ,HttpServletResponse httpServletResponse) throws IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
        String ips = multipartRequest.getParameter("ips");
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Integer> integers = (ArrayList)mapper.readValue(ips, ArrayList.class);
        Iterator var6 = integers.iterator();
        List<Dayerrorwork> list = new ArrayList<>();
        try{
            while(var6.hasNext()) {
                Integer i = (Integer)var6.next();
                Dayerrorwork dw2 = ds.selectByPrimaryKey(i);
                if(dw2 != null){
                    list.add(dw2);
                }
                ds.deleteByPrimaryKey(i);
            }

            Map<String,List<Dayerrorwork>> map = new HashMap();

            map.put("已修复",list);

            System.out.println(map);
            ExcelUtils.exportExcel3(httpServletResponse, map, "Repaired" + FileUtil.getTime() + ".xls");
        }
        catch (Exception e){
            log.warn(e.getMessage());
        }
        return null;
    }

    @RequestMapping({"downloadips"})
    public String downLoadIps(HttpServletResponse httpServletResponse) throws IOException {
        FileUtil.dealIps(httpServletResponse, this.ds.selectAll());
        return "1";
    }

    @RequestMapping({"downloadfilemodel"})
    public String download(HttpServletResponse httpServletResponse, String companyname, String ifinpro, String reason) throws FileNotFoundException {
        Map<String, List<Dayerrorwork>> map = new HashMap();
        if (companyname.equals("null") || companyname.equals("")) {
            companyname = null;
        }

        if (ifinpro.equals("null") || ifinpro.equals("")) {
            ifinpro = null;
        }

        if (reason.equals("null") || reason.equals("")) {
            reason = null;
        }

        List<Dayerrorwork> companyList = this.ds.selectByCompanyInProResaon(companyname, ifinpro, reason);
        List<Dayerrorwork> dealend = new ArrayList();
        Iterator var8 = companyList.iterator();

        while(var8.hasNext()) {
            Dayerrorwork dw = (Dayerrorwork)var8.next();

                dealend.add(dw);

        }
        if(companyname == null ){
            companyname = "All";
        }

        map.put(companyname, dealend);
        System.out.println(map);
        System.out.println(companyname+"SS");


        ExcelUtils.exportExcel(httpServletResponse, map, companyname + FileUtil.getTime() + ".xls");
        return "成功";
    }

    @RequestMapping({"downloadfilemodelcounty"})
    public String downloadByCounty(HttpServletResponse httpServletResponse, String county) throws FileNotFoundException {
        Map<String, List<Dayerrorwork>> map = new HashMap();
        List<Dayerrorwork> companyList = this.ds.selectByCounty(county);
        map.put(county, companyList);
        ExcelUtils.exportExcel3(httpServletResponse, map, county + "全部" + FileUtil.getTime() + ".xls");
        return "成功";
    }

    @RequestMapping({"downloadfilemodelifinpro"})
    public String downloadIfInPro(HttpServletResponse httpServletResponse, String companyclass) throws FileNotFoundException {
        Map<String, List<Dayerrorwork>> map = new HashMap();
        List<String> allServerCompany = TitleMap.getTitleorderMap();
        if (companyclass.equals("已出质保")) {
            List<Dayerrorwork> companyList = this.ds.selectByCompanyInProResaon("已出质保", (String)null, "正在维修");
            map.put(companyclass, companyList);
        } else {
            int index = 0;
            Iterator var6 = allServerCompany.iterator();

            while(var6.hasNext()) {
                String serverCompanyName = (String)var6.next();
                if (serverCompanyName != null && !serverCompanyName.equals("已出质保")) {
                    List<Dayerrorwork> companyList = this.ds.selectByCompanyInProResaon(serverCompanyName, (String)null, "正在维修");
                    map.put(serverCompanyName, companyList);
                }
            }
        }

        ExcelUtils.exportExcelOderByCompany(httpServletResponse, map, companyclass + FileUtil.getTime() + ".xls");
        return "成功";
    }

    @RequestMapping({"downloaderrtypemodel"})
    public String downloadErrorReason(HttpServletResponse httpServletResponse, String reason) throws FileNotFoundException {
        Map<String, List<Dayerrorwork>> map = new HashMap();
        if (reason.equals("网络")) {
            map.put("移动", this.ds.selectByCompanyInProResaon((String)null, (String)null, "网络移动"));
            map.put("联通", this.ds.selectByCompanyInProResaon((String)null, (String)null, "网络联通"));
            map.put("广电", this.ds.selectByCompanyInProResaon((String)null, (String)null, "网络广电"));
        } else {
            List<String> allServerCompany = this.ds.getAllServerCompany();
            Iterator var5 = allServerCompany.iterator();

            while(var5.hasNext()) {
                String serverCompanyName = (String)var5.next();
                if (serverCompanyName != null) {
                    List<Dayerrorwork> companyList = this.ds.selectByCompanyInProResaon(serverCompanyName, (String)null, reason);
                    map.put(serverCompanyName, companyList);
                }
            }
        }

        ExcelUtils.exportExcel(httpServletResponse, map, reason + FileUtil.getTime() + ".xls");
        return "成功";
    }

    @RequestMapping(
            value = {"uploadbasetable"},
            method = {RequestMethod.POST}
    )
    public String receiveFileBase(HttpServletRequest request) throws IOException {
        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
            MultipartFile file = multipartRequest.getFile("basefile");
            InputStream receiveExcelStream = file.getInputStream();
            List<Dayerrorwork> dayerrorworks = ExcelUtils.simpleReadBase(receiveExcelStream);
            Iterator var6 = dayerrorworks.iterator();

            while(var6.hasNext()) {
                Dayerrorwork dw = (Dayerrorwork)var6.next();
                if (dw.getErrortableCounty() != null) {
                    this.ds.insertUpLoaded(dw);
                }
            }

            System.out.println(JacksonUtil.toJSon(dayerrorworks));
            System.out.println("上传的文件名称:" + file.getOriginalFilename());
            System.out.println("上传的文件大小:" + file.getSize());
            String name = multipartRequest.getParameter("name");
            String content = multipartRequest.getParameter("content");
            System.out.println("name:" + name);
            System.out.println("content:" + content);
            return "" + dayerrorworks.size();
        } else {
            return "Not A Right File";
        }
    }

    @RequestMapping({"downloadmaintable"})
    public String downloadMainTable(HttpServletResponse httpServletResponse) {
        Map<String, List<Dayerrorwork>> map = new HashMap();
        List<String> allServerCompany = TitleMap.getTitleorderMap();
        Iterator var4 = allServerCompany.iterator();

        while(var4.hasNext()) {
            String serverCompanyName = (String)var4.next();
            if (serverCompanyName != null) {
                List<Dayerrorwork> companyList = this.ds.selectByCompanyInProResaon(serverCompanyName, (String)null, (String)null);
                map.put(serverCompanyName, companyList);
            }
        }

        ExcelUtils.exportMainExcel(httpServletResponse, map, "主表" + FileUtil.getTime() + ".xls");
        return null;
    }

    @RequestMapping(
            value = {"allsearch"},
            method = {RequestMethod.POST}
    )
    public String searchByTable(HttpServletRequest request) {
        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
            String county = multipartRequest.getParameter("county");
            String hkname = multipartRequest.getParameter("hkname");
            String sbip = multipartRequest.getParameter("sbip");
            String wbdw = multipartRequest.getParameter("wbdw");
            String gzfl = multipartRequest.getParameter("gzfl");
            String fxpt = multipartRequest.getParameter("fxpt");
            String sfxz = multipartRequest.getParameter("sfxz");
            String dwname = multipartRequest.getParameter("dwname");
            List<Dayerrorwork> dayerrorworks = this.ds.selectForInfoTable(county, dwname, hkname, sbip, wbdw, gzfl, fxpt, sfxz);
            return JacksonUtil.toJSon(dayerrorworks);
        } else {
            return "shibai";
        }
    }

    @RequestMapping(
            value = {"updatetablevalue"},
            method = {RequestMethod.POST}
    )

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public String updateById(HttpServletRequest request) throws JsonProcessingException {
        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
            String s = multipartRequest.getParameter("updatedata");
            ObjectMapper mapper = new ObjectMapper();
            Dayerrorwork dw = (Dayerrorwork)mapper.readValue(s, Dayerrorwork.class);
            this.ds.updateByPrimaryKey(dw);
            System.out.println("成功");
            return "成功";
        } else {
            return "失败";
        }
    }
    @RequestMapping(
            value = {"updateByIds"},
            method = {RequestMethod.POST}
    )
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public String updateByIds(HttpServletRequest request) throws JsonProcessingException {
        if (request instanceof MultipartHttpServletRequest) {

            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
            String ids = multipartRequest.getParameter("ids");
            String s = multipartRequest.getParameter("updatedata");
            ObjectMapper mapper = new ObjectMapper();
            ObjectMapper idmappers = new ObjectMapper();
            ArrayList<Integer> list = (ArrayList)idmappers.readValue(ids, ArrayList.class);
            Dayerrorwork dw = (Dayerrorwork)mapper.readValue(s, Dayerrorwork.class);

            for(int id : list ){
                Dayerrorwork dayerrorwork = this.ds.selectByPrimaryKey(id);


                dayerrorwork.setErrortableRepairStatus(dw.getErrortableRepairStatus());
                dayerrorwork.setErrortableNote(dw.getErrortableNote());
                dayerrorwork.setErrortableFaultclassification(dw.getErrortableFaultclassification());
                dayerrorwork.setErrortableDeviceExpriation(dw.getErrortableDeviceExpriation());
                dayerrorwork.setErrortableServercompany(dw.getErrortableServercompany());

                Boolean fail1 = (dw.getErrortableFaultclassification()) != null;
                Boolean fail2 = (dw.getErrortableServercompany()) != null;
                Boolean fail3 = (dw.getErrortableDeviceExpriation()) != null;

                if(fail1&&fail2&&fail3){
                    this.ds.updateByPrimaryKey(dayerrorwork);
                }else {
                    return "fail1";
                }
            }
            System.out.println("成功");
            return "成功";
        } else {
            return "失败";
        }
    }

    @RequestMapping(
            value = {"deletetablevalue"},
            method = {RequestMethod.POST}
    )
    public String deleteById(HttpServletRequest request) throws JsonProcessingException {
        if (!(request instanceof MultipartHttpServletRequest)) {
            return "失败";
        } else {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
            String deldata = multipartRequest.getParameter("deldata");
            String singleid = multipartRequest.getParameter("singleid");
            ObjectMapper mapper = new ObjectMapper();
            if (singleid != null) {
                this.ds.deleteByPrimaryKey(Integer.parseInt(singleid));
            } else {
                ArrayList<Integer> list = (ArrayList)mapper.readValue(deldata, ArrayList.class);
                Iterator var7 = list.iterator();

                while(var7.hasNext()) {
                    Integer s = (Integer)var7.next();
                    this.ds.deleteByPrimaryKey(s);
                }
            }

            return "成功";
        }
    }
    @RequestMapping(
            value = {"uploadpmalltable"},
            method = {RequestMethod.POST}
    )
    public String uploadpmalltable(HttpServletRequest request,HttpServletResponse httpServletResponse) throws IOException, InvalidFormatException {
        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
            MultipartFile file = multipartRequest.getFile("file");
            InputStream reciveInputStream = file.getInputStream();
            List<UpLoadDayErrorWork> list = ExcelUtils.simpleRead(reciveInputStream);

            List<TemporaryWorkDomain> temporaryWorkDomainList = temporaryWork.dealOnlineRateTable(list);

            System.out.println(JacksonUtil.toJSon(temporaryWorkDomainList));

            TemporaryWorkExcelUtils.onlineRateTable(httpServletResponse,temporaryWorkDomainList,FileUtil.getDate());


            System.out.println(JacksonUtil.toJSon(temporaryWorkDomainList));

            System.out.println("上传的文件名称:" + file.getOriginalFilename());
            System.out.println("上传的文件大小:" + file.getSize());
            String name = multipartRequest.getParameter("name");
            String content = multipartRequest.getParameter("content");
            System.out.println("name:" + name);
            System.out.println("content:" + content);
            //return "" + dayerrorworks.size();
            return "成功";
        } else {
            return "Not A Right File";
        }
    }
}
