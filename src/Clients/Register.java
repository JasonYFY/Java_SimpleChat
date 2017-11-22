package Clients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Register {
	Socket client = null;
	//连接
	boolean connect(){
		try {
			client = new Socket(Login.serverIP,2017);
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
		connect();//连接
		try {
			PrintStream prt = new PrintStream(client.getOutputStream());
			prt.println(data);  //op:register,user:andy, pass:123, nickname:牛王, mark:牛不怕虎, img:1
			
			//服务器返回一个是否成功的判断
			return isRegister();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	private boolean isRegister(){
		try (BufferedReader read = new BufferedReader(new InputStreamReader(this.client.getInputStream())))
		{
			String result = read.readLine();
			boolean b = result.contains("result:yes");
			return b;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	//mark: 个人签名,  img: 头像的序号
//	提交如下数据到服务端
//	{op:register,user:andy, pass:123, nickname:牛王, mark:牛不怕虎, img:1}
	public boolean doRegister(String user,String pass,String nickname,String mark,String img){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		String time = sdf.format(new Date());
		String data = String.format("op:register,user:%s,pass:%s,nickname:%s,mark:%s,img:1,注册时间:%s", 
				new String[]{user,pass,nickname,mark,time});
		boolean ret = send(data);
		return ret;
	}
	
	//测试：
	public static void main(String[] args) {
		String str = "user:sd,pass:212,nickname:dsfdsf,mark:fsdf,img:1";
		str = str.replaceAll(",pass:[^,]+","" );
    	System.out.println(str);
	}
	
}
