package com.zaynukov.dev;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Main {
    private static int port = 1533;
    private static String url = "http://localhost:" + port + "/stream";

    public static void main(String[] args) throws URISyntaxException, InterruptedException, IOException {

        new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Open " + url);
                if (Desktop.isDesktopSupported()) {
                    try {
                        Desktop.getDesktop().browse(new URI(url));
                    } catch (IOException | URISyntaxException e) {
                        e.printStackTrace();
                    }
                } else try {
                    Runtime.getRuntime().exec("xdg-open " + url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.run();


        initServer(port);


    }

    private static void initServer(int port) {
        Server server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        context.addServlet(MyServlet.class, "/stream");


        server.setHandler(context);
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
