package com;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap; 
import java.util.Map; 
import java.util.Scanner;

/**
 * Servlet implementation class CardsAPI
 */
@WebServlet("/CardsAPI")
public class CardsAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	Card cardObj = new Card();
	
	private static Map getParasMap(HttpServletRequest request) 
	{ 
	 Map<String, String> map = new HashMap<String, String>(); 
	try
	 { 
	 Scanner scanner = new Scanner(request.getInputStream(), "UTF-8"); 
	 String queryString = scanner.hasNext() ? 
	 scanner.useDelimiter("\\A").next() : ""; 
	 scanner.close(); 
	 String[] params = queryString.split("&"); 
	 for (String param : params) 
	 { 
	 String[] p = param.split("="); 
	 map.put(p[0], p[1]); 
	 } 
	 } 
	catch (Exception e) 
	 { 
	 } 
	return map; 
	}

	
    public CardsAPI() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		System.out.println(request.getParameter("txtAccNo"));
		 String output = cardObj.insertCard(
				 request.getParameter("txtAccNo"), 
				 request.getParameter("txtCardNo"), 
				 request.getParameter("txtExpDate"), 
				 request.getParameter("txtCVC")); 
		 
			response.getWriter().write(output); 
	}


	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		 Map paras = getParasMap(request); 
		 
		 System.out.println(paras.get("hidcardIDSave").toString());
		 
		 String output = cardObj.updateCard(
				paras.get("hidcardIDSave").toString(),
				paras.get("txtAccNo").toString(), 
				paras.get("txtCardNo").toString(), 
				paras.get("txtExpDate").toString(), 
				paras.get("txtCVC").toString()); 
		 
		response.getWriter().write(output); 
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		{ 
			 Map paras = getParasMap(request); 
			 String output = cardObj.deleteCard(paras.get("id").toString()); 
			response.getWriter().write(output); 
			}
	}

}
