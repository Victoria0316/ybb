package com.bluemobi.ybb.network.model;

/**
 * 用户信息
 */
public class UserInfo {

        String userId;//用户ID
        String userName;//用户名（手机号）
        String nickName;//昵称
        String headPicUrl;//头像地址
        String ssid;//登陆唯一标识（token）
         String typeId;//：用户类型ID
        String typeName;//用户类型名称
        String activateStatus;//用户状态：0未激活，1已激活
        String isBind;//是否绑定地址：1已绑定地址、0未绑定"

        public String getUserId() {
                return userId;
        }

        public void setUserId(String userId) {
                this.userId = userId;
        }

        public String getUserName() {
                return userName;
        }

        public void setUserName(String userName) {
                this.userName = userName;
        }

        public String getNickName() {
                return nickName;
        }

        public void setNickName(String nickName) {
                this.nickName = nickName;
        }

        public String getHeadPicUrl() {
                return headPicUrl;
        }

        public void setHeadPicUrl(String headPicUrl) {
                this.headPicUrl = headPicUrl;
        }

        public String getSsid() {
                return ssid;
        }

        public void setSsid(String ssid) {
                this.ssid = ssid;
        }

        public String getTypeId() {
                return typeId;
        }

        public void setTypeId(String typeId) {
                this.typeId = typeId;
        }

        public String getTypeName() {
                return typeName;
        }

        public void setTypeName(String typeName) {
                this.typeName = typeName;
        }

        public String getActivateStatus() {
                return activateStatus;
        }

        public void setActivateStatus(String activateStatus) {
                this.activateStatus = activateStatus;
        }

        public String getIsBind() {
                return isBind;
        }

        public void setIsBind(String isBind) {
                this.isBind = isBind;
        }
}
