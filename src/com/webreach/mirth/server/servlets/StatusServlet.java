package com.webreach.mirth.server.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.webreach.mirth.model.converters.ObjectSerializer;
import com.webreach.mirth.server.managers.ManagerException;
import com.webreach.mirth.server.managers.StatusManager;

public class StatusServlet extends MirthServlet {
	private StatusManager statusManager = new StatusManager();

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!isLoggedIn(request.getSession())) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		} else {
			try {
				ObjectSerializer serializer = new ObjectSerializer();
				PrintWriter out = response.getWriter();
				String operation = request.getParameter("op");
				int channelId = Integer.parseInt(request.getParameter("id"));

				if (operation.equals("startChannel")) {
					statusManager.startChannel(channelId);
				} else if (operation.equals("stopChannel")) {
					statusManager.stopChannel(channelId);
				} else if (operation.equals("pauseChannel")) {
					statusManager.pauseChannel(channelId);
				} else if (operation.equals("resumeChannel")) {
					statusManager.resumeChannel(channelId);
				} else if (operation.equals("getStatusList")) {
					response.setContentType("application/xml");
					out.print(serializer.toXML(statusManager.getStatusList()));
				}
			} catch (ManagerException e) {
				throw new ServletException(e);
			}
		}
	}
}
