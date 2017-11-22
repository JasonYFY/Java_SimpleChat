package Server;

import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ServerTask implements Runnable{
	Socket socket;
	Server server;
	String userPath = "C:\\Users\\jason\\Desktop\\user.txt";
	UdpBroadcast sender;
	public ServerTask(Server server,Socket socket){
		this.server = server;
		this.socket = socket;
	}
	public void deal(){
		int port = this.socket.getPort();//得到连接客户端的端口
		String ClientIp = this.socket.getInetAddress().getHostAddress(); //获取客户端IP
		System.out.println("一个客户端已连接，端口号为："+port);
		try(BufferedReader in = new BufferedReader(
								new InputStreamReader(
									this.socket.getInputStream()) );){
			String msg = in.readLine();  
			if( msg != null ){
				String[] arr = msg.split( "," );//op:register,user:andy, pass:123, nickname:牛王, mark:牛不怕虎, img:1
				String op[] = arr[0].split(":");
				String name[] = arr[1].split(":");
				boolean ret;
				switch(op[1]){
				case "register":
					ret = isSameUser(name[1]);
					if(!ret){
						//服务写入以下数据到:  user.txt (服务器本地)
						//user:andy,pass:123,nickname:牛王,mark:牛不怕虎,img:1
						String userdata = msg.substring("op:register,".length());
						writeToUserFile(userdata);
						//注册成功返回：
						//op:register, result:yes, 
						//name:andy, pass:123, nickname:牛王, mark:牛不怕虎,img:1
						sendToClient("op:register, result:yes,\n"+userdata);
					}else{
						//失败返回：
						//op:register, result:no
						sendToClient("op:register, result:no");
					}
					break;
				case "login":    //op:login, user:andy, pass:123, udpport:5556
					ret = isSameUser(name[1]);
					if(ret){
						String userData = ReadUserData(name[1]); //取出用户信息
						String data[] = userData.split(",");
						String rightPass[] = data[1].split(":");
						String inPass[] = arr[2].split(":");
						
						if(rightPass[1].equals(inPass[1])){ //验证密码是否相等
							//验证是否重复登录
							if(!isRepeatLogin(name[1])){
								//可以登录,返回：
								//op:login, result:yes,
								//userinfo:{user:andy, nickname:牛哥, mark:牛犊不怕虎,img:1}
								userData = userData.replaceAll(",pass:[^,]+","" ); //除去密码返回给用户
								sendToClient("op:login, result:yes,\nuserinfo:{"+userData+"}");  //成功返回
								System.out.println("返回给用户的信息："+userData);
								//以下列的形式存入list在线列表
								//user:andy,nickName:牛王,img:1,ip:192.168.1.5,udpport:5558
								userData = userData.replaceAll(",mark:[^,]+", "");//除去mark
								userData = userData.replaceAll(",注册时间:[^,]+", "");//除去mark
								userData = userData+",ip:"+ClientIp+",udpport:"+port;
								System.out.println("添加到列表的信息："+userData);
								//添加到list
								Server.list.add(userData);
								//广播用户列表到 192.168.1.255(广播地址) 
								//群发列表到每个客户端, 客户端收到通知后, 立即更新列表;
							    //重绘界面.
								//需要处理下用户还没开始接收列表更新
								Thread.sleep(800);
								this.sender = new UdpBroadcast();
								sender.createUdpSender(Server.list);
								break;
							}
						}
					}
					//失败返回：
					sendToClient("op:login, result:no");
					break;
				case "exit":
					//退出登录，更新在线人员列表
					for(String str:Server.list){
						if(str.contains(name[1])){
							boolean b = Server.list.remove(str);
							System.out.println("[ServerTask exit]"+b);
							System.out.println(Server.list);
							if(Server.list!=null)
							this.sender = new UdpBroadcast();
							this.sender.createUdpSender(Server.list);
							break;
						}
					}
					
					break;
					
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	@Override
	public void run() {
		deal();
		
	}
	public boolean isRepeatLogin(String name){//Server.list的形式:user:andy,nickName:牛王,img:1,ip:192.168.1.5,udpport:5558
		if(Server.list!=null){
			for(String s : Server.list){
				String[] split = s.split(",");
				String[] listName = split[0].split(":");
				if(listName[1].equals(name)){
					return true;
				}
			}
		}
		return false;
	}
	public boolean isSameUser(String name){
		try (BufferedReader read = new BufferedReader(new FileReader(this.userPath))){
			String data = null;
			while((data = read.readLine())!=null){//name:andy,pass:123,nickname:牛王,mark:牛不怕虎,img:1
				String str[] = data.split(",");
				String uName[] = str[0].split(":");
				if(uName[1].equals(name)){
					return true;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	public String ReadUserData(String name){
		try (BufferedReader read = new BufferedReader(new FileReader(this.userPath))){
			String data = null;
			while((data = read.readLine())!=null){//name:andy,pass:123,nickname:牛王,mark:牛不怕虎,img:1
				String str[] = data.split(",");
				String uName[] = str[0].split(":");
				if(uName[1].equals(name)){
					return data;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public void writeToUserFile(String str){
		try (PrintStream prt = new PrintStream(new FileOutputStream(this.userPath,true))){
			prt.println(str);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void sendToClient(String str){
		try {
			PrintStream prt = new PrintStream(this.socket.getOutputStream());
			prt.println(str);  
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
