package UI;

/* 
 * 功能：登录界面带着注册功能，弹出注册界面。 
 *    将注册的信息保存在数据库中，并且可以进行登录操作。 
 * author：ywq 
 */  
import javax.swing.*;  


import Clients.*;

import java.awt.*;  
import java.awt.event.*;  
import java.sql.*;  
  
public class UserLogin extends JFrame implements ActionListener{  
      
    //定义登录界面的组件  
		
        JButton jb1,jb2,jb3=null;  
        JRadioButton jrb1,jrb2=null;  
        JPanel jp1,jp2,jp3,jp4,jp5=null;  
        JTextField jtf=null;  
        JLabel jlb1,jlb2,jlbi1,jlbi2=null;  
        JPasswordField jpf=null;  
        ImageIcon image,image2;
              
      
    public static void main(String[] args)  
    {  
        UserLogin ur=new UserLogin();  
        
    }  
      
    public UserLogin()  
    {  
        //创建组件  
         //创建组件  
        jb1=new JButton("登录");  
        jb2=new JButton("注册");  
        jb3=new JButton("退出");  
        
        //设置监听  
        jb1.addActionListener(this);  
        jb2.addActionListener(this);  
        jb3.addActionListener(this);  
          
        jlb1=new JLabel("用户名：");  
        jlb2=new JLabel("密    码：");  
        jlbi1=new JLabel();
        jlbi2=new JLabel();
        image = new ImageIcon("image/tou.jpg");
        image2 = new ImageIcon("image/img.jpg");
        Image img = image.getImage();  
        img = img.getScaledInstance(100, 100, Image.SCALE_DEFAULT);  
        image.setImage(img);  
        jlbi1.setIcon(image);  
        jlbi1.setSize(100, 100);
        
        Image img2 = image2.getImage();
        img2 = img2.getScaledInstance(450, 200, Image.SCALE_DEFAULT); 
        image2.setImage(img2); 
        jlbi2.setIcon(image2);  
        jlbi2.setSize(450, 200);
        
        jtf=new JTextField(15);  
        jpf=new JPasswordField(15);  
          
        jp1=new JPanel();  
        jp2=new JPanel();  
        jp3=new JPanel();  
        jp4=new JPanel();
        jp5=new JPanel();
        
        jp1.add(jlb1);  
        jp1.add(jtf);  
          
        jp2.add(jlb2);  
        jp2.add(jpf);  
          
        jp3.add(jb1);  
        jp3.add(jb2);  
        jp3.add(jb3);  
        
        jp4.add(jp1);  
        jp4.add(jp2);  
        jp4.add(jp3);  
        jp4.setLayout(new GridLayout(3,1));
        
        
        
        jp5.add(jlbi1);
        jp5.add(jp4);
        this.add(jlbi2);
        this.add(jp5);
        this.setLayout(new GridLayout(2,1));
        
        
          
        this.setVisible(true);  
        this.setResizable(false);  
        this.setTitle("用户登录");  
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        this.setBounds(300, 200, 450, 400);  
        this.setBackground(Color.blue);
          
          
    }  
  
    @Override  
    public void actionPerformed(ActionEvent e) {  
          
        //监听各个按钮  
        if(e.getActionCommand()=="退出")  
        {  
            System.exit(0);  
        }else if(e.getActionCommand()=="登录")  
        {
             //调用登录方法  
        	if(this.jtf.getText().equals("")||this.jpf.getText().equals("")){
        		return;
        	}
            Login login = new Login();
            Client cli = login.doLogin(this.jtf.getText(), this.jpf.getText());
            if(cli!=null){
            	 this.dispose();  //关闭当前界面  
                 new TestFrame(cli);   //打开新界面  
            }else{
            	JOptionPane.showMessageDialog(null, "登录失败，请检查你的用户名及密码是否有误或是否重复登录", "提示信息", JOptionPane.WARNING_MESSAGE);
            }
        }else if(e.getActionCommand()=="注册")  
        {  
            //调用注册方法  
            this.Regis();  
        }  
          
    }  
      
    //注册方法  
     public void Regis() {  
           
           
         this.dispose();  //关闭当前界面  
         new UserRegister();   //打开新界面  
           
          
          
    }  
  
    //登录方法  
    public void RetrunLoginUI() {  
          
          
    }  
  
}  