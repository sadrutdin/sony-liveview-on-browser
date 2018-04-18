package com.zaynukov.dev;

import com.example.sony.cameraremote.utils.SimpleLiveviewSlicer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;

public class MyServlet extends javax.servlet.http.HttpServlet {
    @SuppressWarnings("FieldCanBeLocal")
    private String liveStreamUrl = "http://192.168.122.1:60152/liveviewstream?%211234%21%2a%3a%2a%3aimage%2fjpeg%3a%2a%21%21%21%21%21";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("multipart/x-mixed-replace; boundary=--BoundaryString");

        OutputStream os = response.getOutputStream();
        SimpleLiveviewSlicer slicer = new SimpleLiveviewSlicer();
        slicer.open(liveStreamUrl);
        //noinspection InfiniteLoopStatement
        while (true) {
            byte[] image = slicer.nextPayload().jpegData;
            os.write(("--BoundaryString\r\nContent-type: image/jpeg\r\nContent-Length: " +
                    image.length +
                    "\r\n\r\n").getBytes());
            os.write(image);
            os.write("\r\n\r\n".getBytes());
            os.flush();
        }
    }
}
