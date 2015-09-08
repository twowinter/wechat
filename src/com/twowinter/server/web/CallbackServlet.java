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

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.twowinter.server.consts.WxConfig;
import com.twowinter.server.service.CallbackService;
import com.twowinter.server.util.SHA1;

/**
 * 微信回调请求处理
 */
public class CallbackServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private CallbackService service = new CallbackService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			// 开发者接入验�
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
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			// 编码格式
			req.setCharacterEncoding("UTF-8");

			// 验证签名
			String timestamp = req.getParameter("timestamp");
			String nonce = req.getParameter("nonce");
			String signature = req.getParameter("signature");
			if (!signature.equals(SHA1.gen(WxConfig.TOKEN, timestamp, nonce))) {
				out("", resp);
				return;
			}

			// 解析xml
			Map<String, String> reqMap = parseXml(req.getInputStream());
			System.out.println("reqMap=" + reqMap);

			// 处理请求
			String xmlStr = service.handle(reqMap);

			System.out.println("xmlStr=" + xmlStr);

			// null 转为空字符串
			xmlStr = xmlStr == null ? "" : xmlStr;

			out(xmlStr, resp);
		} catch (Throwable e) {
			e.printStackTrace();
			// 异常时响应空�
			out("", resp);
		}
	}

	/**
	 * 输出字符�
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

	/**
	 * 解析请求中的xml元素为Map
	 */
	@SuppressWarnings("unchecked")
	private static Map<String, String> parseXml(InputStream in)
			throws DocumentException, IOException {
		Map<String, String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();
		Document document = reader.read(in);
		Element root = document.getRootElement();
		List<Element> elementList = root.elements();
		for (Element e : elementList) {
			map.put(e.getName(), e.getText());
		}
		return map;
	}

}
