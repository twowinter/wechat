package com.twowinter.server.service;

import java.util.Map;


import com.twowinter.server.consts.MsgType;
import com.twowinter.server.consts.XmlResp;

/**
 * 回调业务处理
 */
public class CallbackService {

	public String handle(Map<String, String> reqMap) throws Exception {
		String msgType = reqMap.get("MsgType");
		String fromUser = reqMap.get("FromUserName");
		String toUser = reqMap.get("ToUserName");

		// 针对不同类型的消息和事件进行处理

		// 文本消息
		if (MsgType.TEXT.equals(msgType)) {
			// 可以在此处进行关键字自动回复
			String content = "收到文本消息: " + reqMap.get("Content");
			return XmlResp.buildText(fromUser, toUser, content);
		}
		

		// 未处理的情况返回空字符串
		return "";
	}
}
