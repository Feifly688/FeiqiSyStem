/**
 * Params
 *
 * @author Feiqi
 * @date 2024/10/07  上午 10:32
 */
package com.xfy.entity;

public class Params {
    private String username;
    private Integer pageNum;
    private Integer pageSize;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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
}
