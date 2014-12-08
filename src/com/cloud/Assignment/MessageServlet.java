package com.cloud.Assignment;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MessageServlet extends HttpServlet {

	/**
	 * 
	 */
	
	
	private static final long serialVersionUID = 1L;
	
	private QueueSender queuesender=null;
	
	
	
	public MessageServlet() {
		queuesender=new QueueSender();
	}

	protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
		boolean isMsgSent=false;
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter( );
		String queueName = request.getParameter("queue");
		System.out.println("Queue1: " + queueName);
		String message=request.getParameter("message");
		System.out.println("Message :"+message);
		out.println("<html>");
        out.println("<head>");
        out.println("<title>Queue Message Response</title>");  
        out.println("</head>");
        out.println("<body>");
		try {
			isMsgSent=queuesender.sendToQueue(queueName,message);
			System.out.println("Message Status :"+isMsgSent);
			out.println("<p>Your message has been successfully sent</p>");
			out.println("<p>Message Posted to the queue "+queueName+" Successfully</p>");
			out.println("<p>Message Status to the "+queueName+" : "+isMsgSent +"</p>");
	    } catch (Exception e) {
	    	out.println("<p>Unable to post a message to the queue "+queueName+" \n"+e.getMessage()+"</p>");
			e.printStackTrace();
			System.out.println("Error in Sending Queue");
		}
		 out.println("</body>");
         out.println("</html>");
    } 
}
