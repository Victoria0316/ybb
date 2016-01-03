package com.bluemobi.ybb.ps.network;

import java.util.Map;

public interface YbbHttpBase<T> {

	public final int mCurrentTimeoutMs = 30000;//30 秒超时

	/** The maximum number of attempts. */
	public final float mMaxNumRetries = 1f;

	/**
	 * url
	 * 
	 * @return
	 */
	public String requestUrl();

	/**
	 * part url
	 * 
	 * @return
	 */
	abstract public String GetApiPath();

	/**
	 * 参数列表
	 * 
	 * @return
	 */
	abstract public Map<String, String> GetParameters();

	/**
	 * 应答对象，范型
	 * 
	 * @return
	 */
	abstract public Class<T> getResponseClass();


	abstract String getParamJson();

}
