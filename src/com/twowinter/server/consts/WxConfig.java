package com.twowinter.server.consts;

import java.io.InputStream;
import java.util.Properties;

/**
 * 微信公众账号开发者配置
 */
public abstract class WxConfig {

	public static final String APPID;
	public static final String APPSECRET;
	public static final String TOKEN;

	static {
		try {
			InputStream in = WxConfig.class.getClassLoader()
					.getResourceAsStream("config.properties");
			Properties props = new Properties();
			props.load(in);
			
			APPID = props.getProperty("appID", "");
			APPSECRET = props.getProperty("appsecret", "");
			TOKEN = props.getProperty("token", "");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("load config error " + e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		System.out.println(WxConfig.APPID);
	}

}
