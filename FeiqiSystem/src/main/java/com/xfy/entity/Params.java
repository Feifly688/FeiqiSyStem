/**
 * Params
 *
 * @author Feiqi
 * @date 2024/10/07  上午 10:32
 */
package com.xfy.entity;

public class Params {
    private Integer pageNum;
    private Integer pageSize;
    private String username;
    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
