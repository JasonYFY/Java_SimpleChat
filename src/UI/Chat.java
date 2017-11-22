package UI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.ScrollPane;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.CRC32;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Clients.Client;

public class Chat{
	
	JFrame jf = new JFrame();
	JTextArea ta = new JTextArea(10,20);
	JTextArea tf = new JTextArea(5,20);
	JScrollPane jScrollPane = new JScrollPane(ta);
	JScrollPane jScrollPane2 = new JScrollPane(tf);
	SimpleDateFormat format = new SimpleDateFormat(" yyyy.MM.dd   HH:mm:ss");
	public String friendNickname,friendIp,friendPort;
	public Client cli;
	
	public static int showDialog(Component c) {
		int dialogButton = JOptionPane.YES_NO_OPTION;
	    int dialogResult = JOptionPane.showConfirmDialog (null, "是否退出窗口?","退出",dialogButton);
	    return dialogResult;
	}
	
	public Chat(Client cli,String friendNickname,String friendIp,String friendPort){
		this.cli = cli;
		this.cli.nowChat = this;
		jf.setSize(500,700);//设计主窗口大小
		this.friendNickname = friendNickname;
		jf.setTitle(this.friendNickname);
		this.friendIp = friendIp;
		this.friendPort = friendPort;
		System.out.println("[Chat]"+this.friendNickname+","+this.friendIp+","+this.friendPort);
		//文本域
		ta.setEditable(false);//文本域不可编辑
	
		tf.requestFocus();//默认得到焦点
		
		JButton send = new JButton("发送");
		JButton clear = new JButton("清除");
		
		Box HBox = Box.createHorizontalBox();
		Box HBox2 = Box.createHorizontalBox();
		Box VBox = Box.createVerticalBox();
		
		HBox.add(send);
		HBox.add(Box.createHorizontalStrut(8));//水平间隔
		HBox.add(clear);
		
		VBox.add(Box.createVerticalStrut(8));
		VBox.add(jScrollPane2);
		VBox.add(Box.createVerticalStrut(8));//垂直间隔
		VBox.add(HBox);
		
		
		jScrollPane.setHorizontalScrollBarPolicy(
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);//隐藏水平滚动条
		jf.add(jScrollPane);
		jf.add(VBox,BorderLayout.SOUTH);
//		jf.add(,BorderLayout.WEST);
		jf.getRootPane().setDefaultButton(send);//回车按钮
		
		//事件
		send.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ta.append("我"+format.format(new Date())+"\n");
				String sendMsg = tf.getText();
				ta.append(sendMsg+"\n");
				sendMsg(sendMsg);
				tf.setText("");
				
//				ta.append("他"+format.format(new Date())+"\n");
//				ta.append("你好"+"\n");
			}
		});
		
		jf.setVisible(true);//窗口可见
		
		jf.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		jf.addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                if(showDialog(jf)==JOptionPane.YES_OPTION){
                	//确定退出时 事件
                	exit();
                	jf.dispose();
                }else return;
            }
        } );
	}
	public void exit(){
		this.cli.nowChat = null;
	}
	public JTextArea getTa(){
		return this.ta;
	}
	public void sendMsg(String msg){
		this.cli.sendMsg(this.friendIp, this.friendPort, msg);
	}
	 
	
//	public static void main(String[] args) {
//		new Chat("");
//	}
}
