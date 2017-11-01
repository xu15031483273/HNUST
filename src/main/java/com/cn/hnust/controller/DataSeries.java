package com.cn.hnust.controller;

import java.io.Serializable;
import java.util.List;

public class DataSeries implements Serializable {
    /**
     * 数据系列上层名称，不为空，UTF-8
     */
    private String catergory1 = null;

    /**
     * 数据系列下层名称，可为空，UTF-8
     */
    private String catergory2 = null;

    /**
     * 数据系列位置，从1开始
     */
    private Integer position = null;

    /**
     * 数据系列类型
     */
    private String type = null;

    /**
     * 数据系列单位
     */
    private String unit = null;

    /**
     * 数据
     */
    protected List<Object[]> data = null;

    /**
     * 数据长度
     */

    private Integer dataLength = null;

    public String getCatergory1() {
        return catergory1;
    }

    public void setCatergory1(String catergory1) {
        this.catergory1 = catergory1;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<Object[]> getData() {
        return data;
    }

    public void setData(List<Object[]> data) {
        this.data = data;
    }

    public Integer getDataLength() {
        return dataLength;
    }

    public void setDataLength(Integer dataLength) {
        this.dataLength = dataLength;
    }

    public String getCatergory2() {
        return catergory2;
    }

    public void setCatergory2(String catergory2) {
        this.catergory2 = catergory2;
    }
}
