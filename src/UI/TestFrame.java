package UI;
import java.awt.BorderLayout; 
import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame; 
import javax.swing.JOptionPane;
import javax.swing.JPanel; 
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants; 
import javax.swing.SwingUtilities;  

import Clients.Client;
public class TestFrame {   
	private JFrame jFrame = null;   
	private JPanel jContentPane = null;  
	private JScrollPane scrollPane=null;  
	Client cli;
	TestFrame(){
		
	}
	public TestFrame(Client cli){
		this.cli = cli; 
		this.getJFrame().setVisible(true); 
	}
	public static int showDialog(Component c) {
		int dialogButton = JOptionPane.YES_NO_OPTION;
	    int dialogResult = JOptionPane.showConfirmDialog (null, "是否退出窗口?","退出",dialogButton);
	    return dialogResult;
	}
	
	public static void main(String[] args) { 
		SwingUtilities.invokeLater(new Runnable() { 
			public void run() { 
				TestFrame application = new TestFrame();  
				application.getJFrame().setVisible(true); 
				}
			}); 
		}  
	
	private JFrame getJFrame() {  
		if (jFrame == null) {  
			jFrame = new JFrame();  
			jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
			jFrame.setSize(230, 700);
			if(this.cli!=null)
			jFrame.setTitle(this.cli.nickname); 
			jFrame.setContentPane(getJContentPane()); 
			
			jFrame.setVisible(true);//窗口可见
			jFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			
			jFrame.addWindowListener( new WindowAdapter() {
	            @Override
	            public void windowClosing(WindowEvent we) {
	                if(showDialog(jFrame)==JOptionPane.YES_OPTION){
	                	//确定退出时 事件
	                	cli.exit();
	                	System.exit(0);
	                }else return;
	            }
	        } );
			}  
		return jFrame; 
		}      
	private JScrollPane getScrollPane(){
		//给添加好友的容器JPanel添加滚动条；
		if(scrollPane==null){   
			scrollPane=new JScrollPane(new TestPane(this.cli)); 
			//scrollPane.setBounds(20,5, -1, 600);  
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );
			//不显示水平滚动条；
			}  
		return scrollPane;  
		}      
	private JPanel getJContentPane() {
		//实例化底层的容器JPanel；
		if (jContentPane == null) { 
			jContentPane = new JPanel(); 
			jContentPane.setLayout(new BorderLayout()); 
			jContentPane.add(getScrollPane(), BorderLayout.CENTER); 
			} 
		return jContentPane;
		}
	} 
		
