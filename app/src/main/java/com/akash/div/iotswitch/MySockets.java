package com.akash.div.iotswitch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class MySockets{
    private String ip;
    private int port;
    private Socket s;
    private PrintWriter out;
    private BufferedReader in;
    public MySockets(String ip,int port){
        this.ip=ip;
        this.port=port;
    }
    public void connect(){
        try {
            s = new Socket(ip,port);
            out = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(s.getOutputStream())),
                    true);
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String sendReceive(String msg) {
        String read=null;
        if(out!=null&&in!=null){
            out.println(msg);
            try {
                read= in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return read;
    }
    public void close(){
        if(s!=null){
            try {
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public boolean isConnected(){
        return s.isConnected();
    }
}