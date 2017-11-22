package UI;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BorderFactory; 
import javax.swing.JPanel;
import javax.swing.JLabel; 
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;  

import Clients.Client;
public class TestPane extends JPanel { 
	private static final long serialVersionUID = 1L;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;  
	private JLabel jLabel11 = null; 
	private JLabel jLabel12 = null; 
	private int clickF=0; 
	private int clickB=0;   
	Client cli;
	public TestPane(Client cli) {  
		super();  
		this.cli = cli;
		this.cli.tp = this; //复制给Client的TestPane（tp）
		initialize();
		
		//initialize(null);
	//	initialize("Jason");
		}   
	public void initialize(ArrayList<String> list) {  
		this.removeAll();
		init();
		ImageIcon image = new ImageIcon("image/tou.jpg");
		Image img = image.getImage();
		img = img.getScaledInstance(180, 58, Image.SCALE_DEFAULT);
		image.setImage(img);
		for(String s : list){   //s:  user:sd,nickname:dsfdsf,img:1,ip:127.0.0.1,udpport:64261
			System.out.println("[initialize(ArrayList<String> list)]:"+s);
			JLabel a = new JLabel();
			a.setIcon(image); 
			a.add(new MemberModel(this.cli,s).jPanel); 
			a.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
			this.add(a, null);
		
		}
		update();
		
	
		}
	public void initialize(){  //显示自己
		init();
		ImageIcon image = new ImageIcon("image/tou.jpg");
		Image img = image.getImage();
		img = img.getScaledInstance(180, 58, Image.SCALE_DEFAULT);
		image.setImage(img);
		
		JLabel a = new JLabel();
		a.setIcon(image);
		a.add(new MemberModel(this.cli,"").jPanel); 
		a.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		this.add(a, null);
	}
	public void init(){
		jLabel = new JLabel(); 
		jLabel.setText("我的好友");
		jLabel.setIcon(new ImageIcon("image/icon2.jpg")); 
		jLabel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));  
//		jLabel.addMouseListener(new java.awt.event.MouseAdapter() { 
//			public void mouseClicked(java.awt.event.MouseEvent e) {   
//				clickF+=1;  
//				if(clickF%2==1){   
//					jLabel1.setVisible(false);  
//					jLabel11.setVisible(false);  
//					jLabel12.setVisible(false);  
//					jLabel.setIcon(new ImageIcon("image/icon.jpg"));  
//					update();  
//					}else{   
//						jLabel1.setVisible(true); 
//						jLabel11.setVisible(true);   
//						jLabel12.setVisible(true);   
//						jLabel.setIcon(new ImageIcon("image/icon2.jpg"));  
//						update();  
//						}    
//				} 
//			});  
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setSize(200, 408); 
		this.setLocation(20, 5); 
		this.add(jLabel, null);
	}
	private void update(){
		//更新UI界面；
		this.updateUI(); 
		}     
	private void clickBlack2(JLabel []jb){
		//点击标签，将后面的标签全部设为不可视；
		for(int i=1;i<jb.length;i++){ 
			try{ 
				jb[i].setVisible(false); 
				}catch(Exception e){ 
					e.printStackTrace(); 
					} 
			}  
		update(); 
		}  
	private void clickBlack(JLabel []jb){
		//点击标签，将后面的标签全部设为可视；
		for(int i=1;i<jb.length;i++){ 
			try{  
				jb[i].setVisible(true); 
				}catch(Exception e){ 
					e.printStackTrace();  
					}   
			}   
		update();
		}   
		} 
	
