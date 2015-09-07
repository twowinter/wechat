package com.twowinter.server.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.twowinter.server.consts.WxConfig;
import com.twowinter.server.util.SHA1;

/**
 * 微信回调请求处理
 */
public class CallbackServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			// 开发者接入验证
			String timestamp = req.getParameter("timestamp");
			String nonce = req.getParameter("nonce");
			String signature = req.getParameter("signature");
			String echostr = req.getParameter("echostr");

			if (signature.equals(SHA1.gen(WxConfig.TOKEN, timestamp, nonce))) {
				out(echostr, resp);
			} else {
				out("", resp);
			}
		} catch (Throwable e) {
			e.printStackTrace();
			out("", resp);
		}
	}
	
	/**
	 * 输出字符串
	 */
	protected void out(String str, HttpServletResponse response) {
		Writer out = null;
		try {
			response.setContentType("text/xml;charset=UTF-8");
			out = response.getWriter();
			out.append(str);
			out.flush();
		} catch (IOException e) {
			// ignore
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}
	}

}
