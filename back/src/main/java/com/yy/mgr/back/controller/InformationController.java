package com.yy.mgr.back.controller;

import com.yy.mgr.back.common.Constants;
import com.yy.mgr.back.exception.ExceptionEnum;
import com.yy.mgr.back.exception.InformationException;
import com.yy.mgr.back.model.CommonReturn;
import com.yy.mgr.back.model.Information;
import com.yy.mgr.back.service.InformationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author yang
 * @date 2020/03/18 23:48
 */
@Controller
@RequestMapping("/information")
public class InformationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InformationController.class);

    @Autowired
    private InformationService informationService;

    @RequestMapping("/index")
    public String index() {
        return "aa.html";
    }


    @ResponseBody
    @RequestMapping(value = "/queryAll", method = RequestMethod.GET)
    public CommonReturn queryAll() throws InformationException {
        LOGGER.info("Begin query all...");
        List<Information> informationList = informationService.queryAll();
        LOGGER.info("Query all finish...");
        return CommonReturn.create(informationList);
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonReturn add(@RequestBody Information information) throws InformationException {
        LOGGER.info("Begin to add information...");
        Boolean result = informationService.add(information);
        if (result) {
            LOGGER.info("Add information success...");
            return CommonReturn.create(Constants.ADD_SUCCESS);
        } else {
            LOGGER.error("Add information fail...");
            throw new InformationException(ExceptionEnum.ADD_INFORMATION_ERROR);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public CommonReturn delete(@RequestBody Information information) throws InformationException {
        Integer id = information.getId();
        LOGGER.info("Begin to delete information where id is {}...", id);
        Boolean result = informationService.delete(id);
        if (result) {
            LOGGER.info("Delete information where id is {} success...", id);
            return CommonReturn.create(Constants.DELETE_SUCCESS);
        } else {
            LOGGER.error("Delete information where id is {} fail...", id);
            throw new InformationException(ExceptionEnum.DELETE_INFORMATION_FAIL);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public CommonReturn edit(@RequestBody Information information) throws InformationException {
        LOGGER.info("Begin to edit information {}", information.getName());
        Boolean result = informationService.update(information);
        if (result) {
            LOGGER.info("Edit information {} success", information.getName());
            return CommonReturn.create(Constants.EDIT_SUCCESS);
        } else {
            LOGGER.error("Edit information {} fail", information.getName());
            throw new InformationException(ExceptionEnum.EDIT_INFORMATION_FAIL);
        }
    }
}
