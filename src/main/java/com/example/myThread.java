package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class myThread extends Thread{
    
    Socket s;
    myThread(Socket s){
        this.s = s;
    }

        
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            String stringa;
            do{
                stringa = in.readLine();
                System.out.println(stringa + "\n");
            }while(!stringa.equals(null));
            String finale = "HTTP/1.1 404 Not found \r\n" + "Content-Lenght: 0 \r\n" + "\r\n";
            out.writeBytes(finale);
            s.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
      
    }
}
