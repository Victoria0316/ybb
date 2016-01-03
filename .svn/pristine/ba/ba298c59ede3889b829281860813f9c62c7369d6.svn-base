package com.bluemobi.ybb.ps.network.model;

/**
 * Created by wangzhijun on 2015/9/12.
 */
public class CheckVersionModel {
    private String type;//"trendVersion",
    private String content;//"版本详情",
    private String ctime;//"创建时间",
    private String fileSize;//"安装包大小",
    private String filepath;//"文件路径，即版本下载地址",
    private String filepathTdc;//"二维码图片路径",
    private String id;//"主键",
    private String mtime;//"最后一次更新时间",
    private String platform;//"平台类型（web,iphone,aphone,ipad,ios,android,apad）",
    private String productName;//"产品名称（医患端，商户端，配送端）",
    private String status;//"逻辑删除字段(0:正常数据，1:已删除数据)",
    private String vType;//"版本类型（1，测试版，2正式版）",
    private String vcode;//"版本号，比如：1、2、3...",
    private String vname;//"版本名称，比如：1.21、3.42",
    private int updateState;//（1，无更新，2普通更新，3强制更新）

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFilepath() {

        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getFilepathTdc() {
        return filepathTdc;
    }

    public void setFilepathTdc(String filepathTdc) {
        this.filepathTdc = filepathTdc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMtime() {
        return mtime;
    }

    public void setMtime(String mtime) {
        this.mtime = mtime;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getvType() {
        return vType;
    }

    public void setvType(String vType) {
        this.vType = vType;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

    public String getVname() {
        return vname;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }

    public boolean getForceUpdate() {
        //（1，无更新，2普通更新，3强制更新）

        if(3 == updateState){
            return true;
        }
        return false;
    }

    public int getUpdateState() {
        return updateState;
    }

    public void setUpdateState(int updateState) {
        this.updateState = updateState;
    }
}
