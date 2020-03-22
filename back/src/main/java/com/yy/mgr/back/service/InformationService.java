package com.yy.mgr.back.service;

import com.yy.mgr.back.exception.InformationException;
import com.yy.mgr.back.model.Information;

import java.util.List;

/**
 * @author yang
 * @date 2020/03/18 23:58
 */
public interface InformationService {
    /**
     * 查询所有信息列表
     * @return
     */
    List<Information> queryAll() throws InformationException;

    /**
     * 添加信息
     * @param information
     * @return
     */
    Boolean add(Information information) throws InformationException;

    /**
     * 删除单条信息
     *
     * @param id
     * @return
     */
    Boolean delete(Integer id) throws InformationException;

    /**
     * 更新信息
     *
     * @param information
     * @return
     */
    Boolean update(Information information) throws InformationException;
}
