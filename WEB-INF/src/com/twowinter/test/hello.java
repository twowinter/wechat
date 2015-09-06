package com.twowinter.test;

import javax.servlet.ServletException;
import java.io.*;
import javax.servlet.http.*;

public class hello extends HttpServlet
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
               throws ServletException,IOException
    {
        //得到PrintWriter对象。
        PrintWriter out=resp.getWriter();
        //向客户端发送字符数据。
        out.println("Hello World");
        out.close();
    }
}