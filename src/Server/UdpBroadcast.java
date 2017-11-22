package Server;


import java.io.IOException;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class UdpBroadcast {   //使用upd广播更新列表
	ArrayList<String> list;
	DatagramSocket socket = null;
	InetAddress ia = null;
	//String boardCastIp = "192.168.0.255";
	String boardCastIp = "127.0.0.1";
	
	/*
	 * {
	 * list:[
	      {user:andy,nickName:牛王,img:1,ip:192.168.1.5,udpport:5558},
	      {user:cat,nickName:通哥,img:2,ip:192.168.1.6,udpport:5559},
	      {user:dd,nickName:火箭,img:5,ip:192.168.1.4,udpport:5561}
	      ]
	      }*/
	public synchronized void createUdpSender(ArrayList list){ //ArrayList:user:andy,nickName:牛王,img:1,ip:192.168.1.5,udpport:5558
		this.list = list;                                      //user:cat,nickName:通哥,img:2,ip:192.168.1.6,udpport:5559
																//user:dd,nickName:火箭,img:5,ip:192.168.1.4,udpport:5561
		StringBuffer sb = new StringBuffer();
		sb.append("{\nlist:[\n");
		try {
			socket = new DatagramSocket();
			for(String s : this.list){
				sb.append("{"+s+"},\n");
			}
			sb.append("]\n}");
			//System.out.println("组成的列表数据："+sb.toString());
			boardCast(sb.toString());
		} catch ( SocketException e ) {
			e.printStackTrace();
		}
	}
	
	private void boardCast( String msg ) {
		InetAddress ia = null;
		if(this.list!=null)
		for(String s : this.list){ //s:user:andy,nickName:牛王,img:1,ip:192.168.1.5,udpport:5558
			String str[]= s.split(",");
			String port[] = str[4].split(":");
			try {
				ia = InetAddress.getByName( boardCastIp );
				DatagramPacket pack = new DatagramPacket(
						msg.getBytes(), msg.getBytes().length,
						ia, Integer.parseInt(port[1]) );   //广播使用的端口号：2345
				socket.send( pack );
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}

	
}
