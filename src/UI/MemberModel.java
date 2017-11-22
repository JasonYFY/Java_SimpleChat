package UI;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Rectangle;
import javax.swing.JLabel;

import Clients.Client;

import java.awt.Dimension;
import java.awt.Font;
public class MemberModel{
	
	
	Client cli;
	private static final long serialVersionUID = 1L;
	public JButton jButton = null;
	//显示好友头像；
	public JPanel jPanel = new JPanel();
	//模板容器
    private JLabel lb_nickName = null;
    //显示昵称；
    private int pic; 
    private JLabel lb_mood = null;
    
    String friendData,friendNickname,friendIp,friendUdpport;
    public MemberModel(Client cli,String friendData) {   //freindData:user:sd,nickname:dsfdsf,img:1,ip:127.0.0.1,udpport:64261
    	super(); 
    	this.cli = cli;
    	if(friendData==""){
    		this.friendNickname = this.cli.nickname;
    		return;
    	}
    	this.friendData = friendData;
    	String[] split = friendData.split(",");
    	String[] nickname = split[1].split(":");
    	String[] ip = split[3].split(":");
    	String[] port = split[4].split(":");
    	this.friendNickname = nickname[1];
    	this.friendIp = ip[1];
    	this.friendUdpport = port[1];
    	initialize(); 
    	}    
    private void initialize() { 
    	lb_mood = new JLabel();  
    	lb_mood.setBounds(new Rectangle(51, 30, 131, 20)); 
    	lb_mood.setFont(new Font("Dialog", Font.PLAIN, 12));  
    	lb_mood.setText("IP:"+this.friendIp);
    	
    	lb_mood.addMouseListener(new java.awt.event.MouseAdapter() {   
    		public void mouseEntered(java.awt.event.MouseEvent e) {  
    			exchangeEnter(); 
    			lb_mood.setToolTipText(lb_mood.getText()); 
    			}   
    		public void mouseExited(java.awt.event.MouseEvent e) {  
    			exchangeExited(); 
    			}   
    		}); 
    	lb_nickName = new JLabel(); 
    	lb_nickName.setBounds(new Rectangle(52, 10, 80, 20)); 
    	lb_nickName.setFont(new Font("Dialog", Font.BOLD, 14));
    	lb_nickName.setText(this.friendNickname);         //--------------------
    	jPanel.setSize(new Dimension(285, 60)); 
    	jPanel.setLayout(null);  
    	jPanel.add(getJButton(), null); 
    	jPanel.add(lb_nickName, null); 
    	jPanel.add(lb_mood, null);
    	jPanel.addMouseListener(new java.awt.event.MouseAdapter() {    
    		public void mouseExited(java.awt.event.MouseEvent e) { 
    			exchangeExited();
    			//鼠标移出模板区，改变背景颜色；
    			}   
    		public void mouseEntered(java.awt.event.MouseEvent e) {  
    			exchangeEnter();
    			//鼠标移进模板区，改变背景颜色；
    			}
    		});  
    	}    
    private void exchangeEnter() { 
    	jPanel.setBackground(new Color(192,224,248)); 
    	}  
    private void exchangeExited() {  
    	jPanel.setBackground(null);
    	}     
    
    private JButton getJButton() { 
    	if (jButton == null) {  
    		jButton = new JButton();  
    		jButton.setBounds(new Rectangle(8, 10, 40, 40)); 
    		jButton.setBackground(new Color(236, 255, 236));
    		jButton.setIcon(new ImageIcon(pic + ".jpg"));
    		jButton.setName(this.friendNickname);

    		jButton.addMouseListener(new java.awt.event.MouseAdapter() {  
    			
    			public void mouseExited(java.awt.event.MouseEvent e) {  
    				exchangeExited();
    				//鼠标移出模板区，改变背景颜色；
    				}   
    			public void mouseEntered(java.awt.event.MouseEvent e) { 
    				exchangeEnter();
    				//鼠标移进模板区，改变背景颜色；
    				}   
    			public void mouseClicked(java.awt.event.MouseEvent e) {
    			    int clickTimes = e.getClickCount();
    			    if (clickTimes == 2) {
    			    //TODO Auto-generated method stub  根据传入的值的不同，选择与不同的人聊天 

    			      cli.nowChat = new Chat(cli, friendNickname,friendIp,friendUdpport);
    			    }
    			  }
    			});  
    		
    		}  
    	return jButton; 
    	} 
    } 
    
    		
    		
    

