package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class myThread extends Thread{
    
    Socket s;
    myThread(Socket s){
        this.s = s;
    }
    
    //versione stato desc content-type

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            String tmp;

            String firstLine = in.readLine();
            String[] request = firstLine.split(" ");
            
            String method = request[0];
            String resource = request[1];
            String version = request[2];

            String header;

            do{
                tmp = in.readLine();
                System.out.println(tmp + "\n");
            }while(!tmp.isEmpty());

            if (resource.equals("/index.html") || resource.equals("file.txt") || resource.equals("/")) {

                File file = new File("httdocs/index.html");
                InputStream input = new FileInputStream(file);
                out.writeBytes("HTTP/1.1 200 ok\n");
                out.writeBytes("content-type" + getContentType(file));
                out.writeBytes("content-lenght: " + file.length() + "\n");
                out.writeBytes("\n");
                byte[] buf = new byte[8192];
                int n;
                while ((n=input.read(buf)) != -1) {
                    out.write(buf,0,n);
                }
                input.close();


            }else{
                String responsebody = "ERRORE file non trovato";
                out.writeBytes("HTTP/1.1 404 not found\n");
                out.writeBytes("content-type: text/plain\n");
                out.writeBytes("content-lenght: " + responsebody.length() + "\n");
                out.writeBytes("\n");
                out.writeBytes(responsebody);
            }
            s.close();
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }      
    }

    private static String getContentType(File f){
            String[] s = f.getName().split("\\.");
            String ext = s[s.length - 1];
            switch (ext) {
                case "html":
                case "htm":
                    return "text/html";    
                case "png":
                    return "image/png";
                case "css":
                    return "text/css";
                default:
                    return "";
            }
    }

}
