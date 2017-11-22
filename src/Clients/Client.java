package Clients;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JTextArea;

import UI.Chat;
import UI.TestPane;

public class Client implements Runnable{//user:andy, nickname:牛哥, mark:牛犊不怕虎,img:1
	public Socket socket;
	public String name,nickname,mark,img,userList;
	int selfPort;
	public TestPane tp;
	public Chat nowChat;
	ArrayList<String> arrayList;
	SimpleDateFormat format = new SimpleDateFormat(" yyyy.MM.dd   HH:mm:ss");
	public Client( Socket socket, String name ,String nickname,String mark,String img ){
		this.socket = socket;
		this.name = name;
		this.nickname = nickname;
		this.mark = mark;
		this.img = img;
		this.selfPort =this.socket.getLocalPort();
	}
	//用udp发送聊天信息
	public void sendMsg(String ip,String port,String msg){
		String add = "port:{"+getPort()+"}";
		msg = add+msg;
		DatagramSocket ds = null;
		try {
			InetAddress ia = InetAddress.getByName(ip);
			DatagramPacket dp = new DatagramPacket( 
					msg.getBytes("utf-8"), msg.getBytes().length, 
				ia, Integer.parseInt(port) );
			System.out.println("[sendMsg 发送的端口号：]"+port);
			
			ds = new DatagramSocket();
			System.out.println("[sendMsg]:"+msg);
			ds.send(dp);
			System.out.println("[Client sendMsg]:已发送");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void receiverMsg(){
		DatagramSocket dsSocket = null;
		byte[] buff = new byte[ 65507 ];     //65507  容器
		DatagramPacket dp = null;
		try {
			dsSocket = new DatagramSocket( this.getPort() );  //自身端口号接收聊天信息
			System.out.println("自身端口号接收聊天信息port:"+this.socket.getLocalPort());
			System.out.println( "[Client接收方] 正在等待接收聊天信息.." );
			while( true ){
				dp = new DatagramPacket( buff, buff.length );
				dsSocket.receive( dp );
				int size = dp.getLength();
				String line = new String( buff, 0, size ,"utf-8");
				System.out.println("line:"+line);
				if(line.substring(0, 10).contains("list:[")){
					ArrayList<String> arrayList = DealListData.deal(line);
					System.out.println("[updateList()]:"+arrayList);
					this.updateList(arrayList);
					continue;
				}
				String[] analy = analysisReceiverMsg(line);
				String msg = analy[1];
				String port = analy[0];
				System.out.println("[Client接收方]对方的端口号:"+port);
				//System.out.println("[Client接收方]已创建的端口号:"+this.nowChat.friendPort+","+port.equals(this.nowChat.friendPort));
				if((this.nowChat!=null&&this.nowChat.friendPort!=null)&&port.equals(this.nowChat.friendPort)){
					System.out.println("90行："+msg);
					JTextArea ta = this.nowChat.getTa();
					ta.append(this.nowChat.friendNickname+format.format(new Date())+"\n"+msg+"\n");
				}else{
					for(String s:this.arrayList){
						if(s.contains("udpport:"+port)){
							System.out.println("96行："+msg);
							String[] split = s.split(",");
							String[] friendNickname = split[1].split(":");
							System.out.println("new Chat:"+friendNickname[1]);
							Chat chat = new Chat(this, friendNickname[1], dp.getAddress().toString().replace("/", ""), port);
							chat.getTa().append(friendNickname[1]+format.format(new Date())+"\n"+msg+"\n");
						}
					}
					
				}
			}
			
		} catch ( SocketException e ) {
			e.printStackTrace();
		}catch ( IOException e ) {
			e.printStackTrace();
		}
	}
	public int getPort(){
		return this.selfPort;
	}
	public void updateList(ArrayList<String> arrayList){
		this.arrayList = arrayList;
		this.tp.initialize(arrayList);
	}
	public String[] analysisReceiverMsg(String msg){
		String str[] = new String[2];
		int end = msg.indexOf("}");
		str[0] = msg.substring(6, end);  //﻿port:{
		System.out.println("[analysisReceiverMsg]:"+str[0]);
		str[1] = msg.substring(end+1);
		return str;
		
	}
	public void exit(){
		try {
			PrintStream prt = new PrintStream(new Socket(Login.serverIP,2017).getOutputStream());
			prt.println("op:exit,user:"+this.name);  //op:login, user:andy, pass:123, udpport:5556
			System.out.println("[Client exit()]退出");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		receiverMsg(); //多线程启动实时接收聊天信息
	}
}
