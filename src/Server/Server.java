package Server;

import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server {
	
	Socket socket;
	static ArrayList<String> list = new ArrayList<String>();  //记录在线人数
	public void connect(){
		ServerSocket server = null;
		System.out.println("服务器已启动...");
		try {
			server = new ServerSocket(2017);
			while(true){
				//接收客户端的连接
				this.socket = server.accept();
				new Thread(new ServerTask(this,this.socket)).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
			
		
	}
	
	public static void main(String[] args) {
		Server server =new Server();
		server.connect();
	}

}
