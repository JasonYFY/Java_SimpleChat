package UI;

import java.awt.event.*;  
import java.awt.*;  
  
import javax.swing.*;  

import Clients.*;
/* 
 * 注册界面。 
 */  
public class UserRegister extends JFrame implements ActionListener{  
  
    //定义组件  
    JFrame jf;  
    JPanel jp,jp2,jp0;  
    JLabel jl1,jl2,jl3,jl4,jl5,jlb;  
    JTextField jtf1,jtf2,jtf3,jtf4,jtf5;  
    JButton jb1,jb2;  
    ImageIcon image = new ImageIcon();
    public static void main(String[] args) {
		new UserRegister();
	}
    public UserRegister()  
    {  
        //初始化组件  
        jf=new JFrame();  
        jp=new JPanel();  
        jp2=new JPanel(); 
        jp0=new JPanel(); 
        jl1=new JLabel("用户账号：");  
        jtf1=new JTextField(10);  
        jtf1.setToolTipText("用户名必须为3-6位字母_或者数字");  
        jl2=new JLabel("密码：");  
        jtf2=new JTextField(10);  
        jtf2.setToolTipText("密码不能为空");  
        jl3=new JLabel("确认密码：");  
        jtf3=new JTextField(10);  
        jtf3.setToolTipText("密码不能为空");
        jl4=new JLabel("昵称：");  
        jtf4=new JTextField(10);  
        jtf4.setToolTipText("昵名不能为空");
        jl5=new JLabel("个人签名：");  
        jtf5=new JTextField(10);  
        jtf5.setToolTipText("个人签名不能为空");
        jlb=new JLabel();
        
        image = new ImageIcon("image/img2.jpg");
        Image img = image.getImage();  
        img = img.getScaledInstance(450, 100, Image.SCALE_DEFAULT);  
        image.setImage(img);  
        jlb.setIcon(image);  
        jlb.setSize(450, 100);
          
        jb1=new JButton("返回");  
        jb1.setToolTipText("点我返回登录界面哦");  
        jb2=new JButton("注册");  
        jb1.addActionListener(this);  
        jb2.addActionListener(this);  
          
        jp.setLayout(new GridLayout(5,2));
          
        jp0.add(jlb);
        
        jp.add(jl1);  
        jp.add(jtf1);  
          
        jp.add(jl2);  
        jp.add(jtf2);  
        
        jp.add(jl3);  
        jp.add(jtf3);  
        
        jp.add(jl4);  
        jp.add(jtf4); 
          
       
          
        jp.add(jl5);  
        jp.add(jtf5);  
          
        jp2.add(jb1);  
        jp2.add(jb2);  
        
        
        this.add(jp);  
        this.add(jp0,BorderLayout.NORTH);
        this.add(jp2,BorderLayout.SOUTH);
        this.setTitle("用户注册");  
        this.setBounds(300, 200, 450, 400);  
        this.setVisible(true);  
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        this.setResizable(false);  
          
          
          
    }  
      
      
      
      
  
    public void actionPerformed(ActionEvent e) {  
      
        if(e.getActionCommand()=="返回")  
        {  
            this.dispose();  
            new UserLogin();  
//          System.out.println("-------");  
              
        }else if(e.getActionCommand()=="注册")  
        {  
                //调用注册方法  
            this.zhuce();  
              
        }  
          
    }  
   public void zhuce()  
   {  
       String regex1="\\w{3,6}"; //用户名必须是3-6位  
        boolean flag1=jtf1.getText().matches(regex1);  
        
        
        boolean flag3=jtf3.getText().equals(jtf2.getText());
          
          
          
//      if(jtf1.getText()==null||jtf2.getText()==null||jtf5.getText()==null)  
        if(flag1==false)  
        {  
            JOptionPane.showMessageDialog(null, "用户名填写错误,必须为3-6位字母_或者数字", "提示信息", JOptionPane.WARNING_MESSAGE);  
            jtf1.setText("");  
        }else if(flag3==false)  
        {  
            JOptionPane.showMessageDialog(null, "两次密码必须一致", "提示信息", JOptionPane.WARNING_MESSAGE);  
            jtf3.setText("");
        }else if(jtf1.getText().equals("")||jtf2.getText().equals("")||jtf4.getText().equals("")||jtf5.getText().equals("")) 
        {  
            JOptionPane.showMessageDialog(null, "选项不能为空", "提示信息", JOptionPane.WARNING_MESSAGE);  
        }else  
        {             
        	 //调用注册方法  
        	Register re = new Register();
        	boolean ret = re.doRegister(jtf1.getText(), jtf2.getText(), jtf4.getText(), jtf5.getText(), "1");
        	if(ret){
        		JOptionPane.showMessageDialog(null, "注册成功", "提示信息", JOptionPane.WARNING_MESSAGE);
        		 this.dispose();  
                 new UserLogin();
        	}else{
        		JOptionPane.showMessageDialog(null, "注册失败", "提示信息", JOptionPane.WARNING_MESSAGE);
        	}
             
              
        }  
   }  
      
}  