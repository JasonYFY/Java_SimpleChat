package Clients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Login {
	Socket client = null;
	Client cli;
	//static String serverIP = "192.168.0.223";
	static String serverIP = "127.0.0.1";
	//连接
	boolean connect(){
		try {
			client = new Socket(serverIP,2017);   //服务器的ip
			return true;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	//发送
	boolean send(String data){
		int port = client.getLocalPort();
		data = data+"udpport:"+port;
		try {
			PrintStream prt = new PrintStream(client.getOutputStream());
			prt.println(data);  //op:login, user:andy, pass:123, udpport:5556
			return isLogin();    //判断是否登录成功
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	public Client doLogin(String user,String pass){
		connect();//连接
		int port = client.getLocalPort();
		String data = String.format("op:login,user:%s,pass:%s,udpport:%s", 
				new String[]{user,pass,String.valueOf(port)});
		if(send(data)){
			return this.cli;
		}
		return null;
	}
	//判断是否登录成功
	private boolean isLogin(){
		try (BufferedReader read = new BufferedReader(new InputStreamReader(this.client.getInputStream())))
		{
			String result = read.readLine();
			boolean b = result.contains("result:yes");
			if(b){ //登录成功取返回的用户资料
				String userData = read.readLine(); //userinfo:{user:andy, nickname:牛哥, mark:牛犊不怕虎,img:1}
				System.out.println(userData);//打印测试下
				userData = userData.replaceAll(".+\\{([^\\}]+)\\}", "$1");//user:andy, nickname:牛哥, mark:牛犊不怕虎,img:1
				String str[] = userData.split(",");
				String name[] = str[0].split(":");
				String nickname[] = str[1].split(":");
				String mark[] = str[2].split(":");
				String img[] = str[3].split(":");
				this.cli = new Client(this.client,name[1],nickname[1],mark[1],img[1]); //登录成功，启用Client
				new Thread(cli).start();//并开始接收udp的聊天信息
			}
			return b;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
		
	}
	//测试
	public static void main(String[] args) {
		String userData = "/192.168.0.223";
		//int start = userData.indexOf("port:{");
//		int end = userData.indexOf("}");
		System.out.println(userData.replace("/", ""));
//		System.out.println(userData.substring(end+1));
		//userData = userData.replaceAll("port:\\{([^\\}]+)\\}.+","$1");
//		String ss[] = userData.split(",");
//		System.out.println(ss[0]);
	}
}
