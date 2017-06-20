package courierUI;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.FlowLayout;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import courierDM.ConnectDB;
import courierDM.Userprofile;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Login{

	private JFrame frame;
	private JTextField user_name;
	private JPasswordField password;
	JLabel lblUserName;
	private JLabel lblAcmeCourier;
	private JLabel lblerror;
	private Userprofile profile = new Userprofile();
	private ArrayList<Userprofile> list;
	private Boolean userBlocked = false;
	/**
	 * Launch the application.
	 * @throws Exception 
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException, Exception {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.getFrame().setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
		
		
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();	
		
	}
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		ScreenSize size = new ScreenSize();
		setFrame(new JFrame());
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screenSize.getWidth();
		int height = (int) screenSize.getHeight();
		getFrame().setBounds(size.x, size.y, size.ScreenWidth, size.ScreenHeight);
		getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getFrame().getContentPane().setLayout(null);
		
		user_name = new JTextField();
		user_name.setBounds(288, 161, 296, 35);
		getFrame().getContentPane().add(user_name);
		user_name.setColumns(10);
		
		password = new JPasswordField();
		password.setBounds(288, 270, 296, 35);
		getFrame().getContentPane().add(password);
		password.setColumns(10);
		
		JLabel lblUserName = new JLabel("Password");
		lblUserName.setBounds(288, 241, 91, 16);
		getFrame().getContentPane().add(lblUserName);
		
		JLabel label = new JLabel("User Name");
		label.setBounds(288, 134, 91, 16);
		getFrame().getContentPane().add(label);
		
		JButton btn_login = new JButton("Login");
		
		
		btn_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					
					validation();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btn_login.setBounds(471, 360, 109, 35);
		getFrame().getContentPane().add(btn_login);
		
		lblAcmeCourier = new JLabel("ACME COURIER SERVICE");
		lblAcmeCourier.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 22));
		lblAcmeCourier.setBounds(330, 37, 195, 35);
		getFrame().getContentPane().add(lblAcmeCourier);
		
		lblerror = new JLabel("");
		lblerror.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		lblerror.setForeground(Color.RED);
		lblerror.setBounds(288, 90, 399, 16);
		getFrame().getContentPane().add(lblerror);
		
	}
	
	public void validation() throws SQLException, Exception {
		String user = user_name.getText();
		String pass = (String)password.getText();
		System.out.println(user);
		
		if(user.isEmpty() || user == null){
			lblerror.setText("*Please enter your username");
			user_name.requestFocus();
		}else if(pass.isEmpty() || pass == null){
			lblerror.setText("*Please enter your password");
			password.requestFocus();
		}
		else if(check(user,pass)){
		lblerror.setText("");
		getFrame().dispose();
		MainScreen main = new MainScreen(profile);
		main.frame.setVisible(true);
		}else if(!check(user,pass)&&userBlocked==true){
			lblerror.setText("Your account has been blocked by Administrator");
			userBlocked = false;
		}
		else{
			lblerror.setText("You entered the wrong username or password");
		}
	}
	
	public Boolean check(String user, String pass){

		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		SessionFactory sf = cfg.buildSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		ArrayList<Userprofile> list = new ArrayList<Userprofile>();
		list.clear();
		list = (ArrayList<Userprofile>) s.createQuery("From Userprofile where userName = ? and userPassword = ?")
				.setParameter(0, user)
				.setParameter(1, pass)
				.list();
		s.flush();
		tx.commit();
		s.close();
		System.out.println(list.size());
		if(list.size()>0){
			if(list.get(0).getUserRole()==0){
				userBlocked = true;
				return false;
			}else{
				userBlocked = false;
				profile.setUserId(list.get(0).getUserId());
				profile.setFirstName(list.get(0).getFirstName());
				profile.setUserName(list.get(0).getUserName());
				profile.setUserRole(list.get(0).getUserRole());
				profile.setUserEmail(list.get(0).getUserEmail());
				profile.setUserPassword(list.get(0).getUserPassword());
				profile.setUserPhone(list.get(0).getUserPhone());
				return true;
			}
		}else{
			return false;
		}
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
}
