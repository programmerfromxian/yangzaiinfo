package com.yy.common;

import java.io.Serializable;
import java.util.List;

/**
 * set方法提供: currentPage, totalRows, pageSize, list, navigateNumbers, queryString
 * 构造方法提供: public PageBean(Integer pageNum, Integer pageSize, navigateNumbers){}
 *
 * @author yang
 */
public class PageBean<T> implements Serializable {

    // 当前页数
    private Integer currentPage;
    // 查询的总条数
    private Integer totalRows;
    // 总页数
    private Integer totalPages;
    // 分页查询第一个参数
    private Integer startRow;
    // 分页查询第二个参数
    private Integer pageSize;
    // 实体类型
    private List<T> list;
    // 导航栏第一页页数
    private Integer navigateFirstPage;
    // 导航栏最后一页页数
    private Integer navigateLastPage;
    // 导航栏显示的页数
    private Integer navigateNumbers;
    // 下一页页数
    private Integer nextPage;
    // 上一页页数
    private Integer prePage;
    // 查询条件: 保留字段
    private String queryString;

    public void setTotal(Integer totalRows) {
        this.totalRows = totalRows;
        this.totalPages = totalRows % this.pageSize == 0 ? totalRows / this.pageSize : totalRows / this.pageSize + 1;
        this.nextPage = (currentPage + 1) >= totalPages ? totalPages : (currentPage + 1);
        this.prePage = (currentPage - 1) <= 1 ? 1 : (currentPage - 1);
        // 分页栏前五后四效果
        if (currentPage <= 5 || totalPages <= 10) {
            this.navigateFirstPage = 1;
            this.navigateLastPage = totalPages <= 10 ? totalPages : 10;
        } else {
            if (this.currentPage + 4 > totalPages) {
                this.navigateLastPage = totalPages;
                this.navigateFirstPage = this.navigateLastPage - 9;
            } else {
                this.navigateLastPage = this.currentPage + 4;
                this.navigateFirstPage = this.currentPage - 5;
            }
        }
    }

    private PageBean() {

    }


    public PageBean(Integer currentPage, Integer pageSize, Integer navigateNumbers) {
        if (currentPage == null || currentPage <= 0) {
            this.currentPage = 1;
        } else {
            this.currentPage = currentPage;
        }
        if (pageSize == null || pageSize <= 0) {
            this.pageSize = 5;
        } else {
            this.pageSize = pageSize;
        }
        if (navigateNumbers == null || navigateNumbers <= 0) {
            this.navigateNumbers = 10;
        } else {
            this.navigateNumbers = navigateNumbers;
        }
        this.startRow = (this.currentPage - 1) * this.pageSize;
    }

    public PageBean(Integer currentPage, Integer pageSize) {
        if (currentPage == null || currentPage <= 0) {
            this.currentPage = 1;
        } else {
            this.currentPage = currentPage;
        }
        if (pageSize == null || pageSize <= 0) {
            this.pageSize = 5;
        } else {
            this.pageSize = pageSize;
        }
        this.navigateNumbers = 10;
        this.startRow = (this.currentPage - 1) * this.pageSize;
    }


    public Integer getStartRow() {
        return startRow;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public void setTotalRows(Integer totalRows) {
        this.totalRows = totalRows;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public void setNavigateNumbers(Integer navigateNumbers) {
        this.navigateNumbers = navigateNumbers;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public Integer getTotalRows() {
        return totalRows;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public List<T> getList() {
        return list;
    }

    public Integer getNavigateFirstPage() {
        return navigateFirstPage;
    }

    public Integer getNavigateLastPage() {
        return navigateLastPage;
    }

    public Integer getNavigateNumbers() {
        return navigateNumbers;
    }

    public Integer getNextPage() {
        return nextPage;
    }

    public Integer getPrePage() {
        return prePage;
    }

    public String getQueryString() {
        return queryString;
    }
}
