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
        //�õ�PrintWriter����
        PrintWriter out=resp.getWriter();
        //��ͻ��˷����ַ����ݡ�
        out.println("Hello World");
        out.close();
    }
}