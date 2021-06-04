package com.chen17.web.controller;

import com.chen17.domain.Dayerrorwork;
import com.chen17.domain.HkDeviceList;
import com.chen17.domain.HkIncident;
import com.chen17.domain.temporary.HkMainPageSearch;
import com.chen17.service.DayWorkService;
import com.chen17.service.HkDeviceListService;
import com.chen17.service.HkIncidentServer;
import com.chen17.utils.Excel.ExcelUtils;
import com.chen17.utils.Excel.HkExcelUtils;
import com.chen17.utils.Excel.UploadUtil;
import com.chen17.utils.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author yd
 * @version 1.0
 * @date 2021-03-26 16:19
 */
@RestController
@RequestMapping({"/hkabout"})
public class HKAboutController {

    private static final Logger log = LoggerFactory.getLogger(HKAboutController.class);
    HkDeviceListService hks;
    DayWorkService ds;
    HkIncidentServer his;
    @Autowired
    public HKAboutController(DayWorkService ds,HkDeviceListService hks,HkIncidentServer his){this.hks = hks; this.ds = ds;this.his = his;}

    @RequestMapping("/docheck")
    public String doCheck(){
        System.out.println("Check Done");
        return  "成功";
    }

    @RequestMapping("/initRefresh")
    public String initRefresh(){
        System.out.println("Check Done");
        List<String> allArea = hks.getAllArea();
        List<HkMainPageSearch> hkMainPageSearches = new ArrayList<>();
        for(String OrgName : allArea){
            HkMainPageSearch hkMainPageSearch = new HkMainPageSearch();
            hkMainPageSearch.setOrgname(OrgName);
            int errnum = hks.getORGErrNum(OrgName);
            int allnum = hks.getORGAllNum(OrgName);
            int nornum = hks.getORGNorNum(OrgName);

            System.out.println(allnum);
            System.out.println(nornum);
            System.out.println(errnum);

            int donenum = errnum+nornum;
            hkMainPageSearch.setDonenum(donenum);
            hkMainPageSearch.setAllnum(allnum);
            double donenumd = donenum;
            double allnumd = allnum;
            DecimalFormat df = new DecimalFormat("0.00");
            String reten = df.format(donenumd/allnumd*100);

            hkMainPageSearch.setRate(reten+"%");
            hkMainPageSearch.setErrnum(errnum);
            hkMainPageSearch.setNornum(nornum);
            hkMainPageSearches.add(hkMainPageSearch);

        }
        return JacksonUtil.toJSon(hkMainPageSearches);
    }

    @RequestMapping("/getSingleRecord")
    public String getSingleRecord(HttpServletRequest request){
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
        String pointid = multipartRequest.getParameter("pointid");
        String pointname = multipartRequest.getParameter("pointname");
        String pointerrmainreason = multipartRequest.getParameter("pointerrmainreason");
        String pointerrsubreason = multipartRequest.getParameter("pointerrsubreason");
        String pointnote = multipartRequest.getParameter("pointnote");
        String pointorg = multipartRequest.getParameter("pointorg");

        if(!pointname.equals("点击下一步开始任务")){
            if(pointerrsubreason.equals("正常")){
                HkDeviceList hkDeviceList = hks.selectByPrimaryKey(pointid);
                hkDeviceList.setDeviceIspriority("1");
                hkDeviceList.setSearchDatatime(new Date());
                hks.updateByPrimaryKeySelective(hkDeviceList);
                System.out.println("OK");
            }else {
                HkDeviceList hkDeviceList = hks.selectByPrimaryKey(pointid);
                hkDeviceList.setDeviceIspriority("2");
                hkDeviceList.setSearchDatatime(new Date());
                hks.updateByPrimaryKeySelective(hkDeviceList);
                HkIncident hic = new HkIncident();
                hic.setDeviceSn(pointid);
                hic.setIncidentDevname(pointname);
                hic.setIncidentProblem(pointerrmainreason);
                hic.setIncidentSubproblem(pointerrsubreason);
                hic.setIncidentNote(pointnote);
                hic.setIncidentFindtime(new Date());
                hic.setIncidentRepairStatus("0");
                if(hic.getIncidentNote().equals("null")){
                    hic.setIncidentNote("");
                }
                his.insert(hic);
            }
        }
        return  JacksonUtil.toJSon(hks.selectByORGLimitOne(pointorg));
    }

    @RequestMapping("/getSingleErrRecord")
    public String getSingleErrRecord(HttpServletRequest request){
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
        String pointid = multipartRequest.getParameter("pointid");
        String pointname = multipartRequest.getParameter("pointname");
        String pointerrmainreason = multipartRequest.getParameter("pointerrmainreason");
        String pointerrsubreason = multipartRequest.getParameter("pointerrsubreason");
        String pointnote = multipartRequest.getParameter("pointnote");
        String pointorg = multipartRequest.getParameter("pointorg");

        System.out.println(his.selectByErrLimitOne());

        if(!pointname.equals("点击下一步开始任务")){
            if(pointerrsubreason.equals("正常")){
                HkDeviceList hkDeviceList = hks.selectByPrimaryKey(pointid);
                hkDeviceList.setDeviceSn(pointid);
                hkDeviceList.setDeviceIspriority("1");
                hkDeviceList.setSearchDatatime(new Date());
                hks.updateByPrimaryKeySelective(hkDeviceList);
                his.deleteBySN(pointid);
            }else {
                HkIncident hkIncident = new HkIncident();
                hkIncident.setDeviceSn(pointid);
                hkIncident.setIncidentFindtime(new Date());
                hkIncident.setIncidentProblem(pointerrmainreason);
                hkIncident.setIncidentSubproblem(pointerrsubreason);
                hkIncident.setIncidentNote(pointnote);
                his.updateBySN(hkIncident);
            }
        }
        HkIncident hkIncident = his.selectByErrLimitOne();
        return  JacksonUtil.toJSon(hkIncident);
    }

    @RequestMapping(
            value = {"uploadHKallDev"},
            method = {RequestMethod.POST}
    )
    public String receiveFile(HttpServletRequest request) throws IOException {

        List<String> erred = his.getAllIncidentSn();
        hks.truncateList();
        if (!(request instanceof MultipartHttpServletRequest)) {
            return "Not A Right File";
        } else {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
            MultipartFile file = multipartRequest.getFile("file");
            InputStream receiveExcelStream = file.getInputStream();
            List<HkDeviceList> hkDeviceLists = HkExcelUtils.simpleReadBase(receiveExcelStream,erred);
            List<String> hkDeviceSn = new ArrayList<>();
            for(HkDeviceList hdl : hkDeviceLists){
                hkDeviceSn.add(hdl.getDeviceSn());
                hks.insert(hdl);
            }
            for(String errDevSn : erred){
                if(!hkDeviceSn.contains(errDevSn)){
                    his.deleteBySN(errDevSn);
                }
            }

            System.out.println("上传的文件名称:" + file.getOriginalFilename());
            System.out.println("上传的文件大小:" + file.getSize());

        }
        return null;
    }
}
