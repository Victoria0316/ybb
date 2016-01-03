package com.bluemobi.ybb.db.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Finder;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.sqlite.FinderLazyLoader;

@Table(name = "Message")
public class Message  {
	@Id
	@Column(column = "id")
	private int id;
	@Column(column = "isread")//是否已读  0未读  1 已读
	private String isread;
	//0：预览订单	//1：未付款	//2：已付款(待发货)	//3：已付款（已发货，待收货）
	//4：已收货(待评价)	//5：已完成	//6：已退单
	@Column(column = "createTime")
	private String createTime;
	@Column(column = "orderNo")
	private String orderNo;
	@Column(column = "orderStatus")
	private String orderStatus;
	@Column(column = "orderId")
	private String orderId;
	@Column(column = "msg")
	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getIsread() {
		return isread;
	}

	public void setIsread(String isread) {
		this.isread = isread;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
