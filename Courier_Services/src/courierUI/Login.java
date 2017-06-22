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
import courierDM.Map;
import courierDM.Userprofile;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
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
	private ArrayList<Map> map;
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
		
		JButton btnNewButton = new JButton("New button");
		 btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Configuration cfg = new Configuration();
				cfg.configure("hibernate.cfg.xml");
				SessionFactory sf = cfg.buildSessionFactory();
				Session ss = sf.openSession();
				Transaction tx = ss.beginTransaction();
				ArrayList<Map> map = new ArrayList<Map>();
				map.clear();
				map = (ArrayList<Map>) ss.createQuery("From Map")
						.list();
				ss.flush();
				tx.commit();
				ss.close();
				char c = 'H';
				String[][] mapL = new String[8][c];
				int index = 0;
				for(int i=1; i<=7 ; i++){
					for (char j = 'A'; j<'H'; j++) {
						mapL[i][j] = map.get(index).getDirection();
						System.out.println(mapL[i][j]);
						index ++;
					}
					System.out.println(index);
				}
				
				
				int s = 3;
				int d = 5;
				char ns='F', nd = 'E', n='H';
				int p = 0, as = 0, np = 0, rev2 = 0, rev1 = 0;
				
				String[] seq = new String[4];
				
				//detection source node and destination node
				if(s>d){
					p = -1;
					as = s+1;
					seq[0] = "U";
					seq[3] = "D";
				}else{
					p = 1;
					as = d+1; 
					seq[0] = "D";
					seq[3] = "U";
				}
				
				if(ns>nd){
					n = ns;
					n+=1;
					np = -1;
					rev1 = 1;
					rev2 = 2;
					seq[1] = "L";
					seq[2] = "R";
				}else{
					n=nd;
					n+=1;
					np = 1;
					rev2 = -2;
					rev1 = -1;
 					seq[1] = "R";
					seq[2] = "L";
				}
				String[][] a = new String[as][n];
				CharSequence cs[] = (CharSequence[]) seq;
				CharSequence block = "B";
				System.out.println(mapL[s][ns]);
				ArrayList<String> visited = new ArrayList<String>();
				for(int i=s; s>d?i>=d:i<=d; i+=p){
					for (char cc = ns; ns > nd?cc>=nd:cc<=nd; cc+=np) {
						if(d==i){
							if(cc == nd){
								System.out.println(i + " " + cc);
								break;
							}else{
							System.out.println(i + " " + cc);
							}
						}else if(d == i+p){
							if(mapL[i+p][cc].contains(cs[1]) && !mapL[i][cc].contains(block)){
								System.out.println(i + " " + cc);
								break;
							}else{
								if(cc == nd){
									ns = nd;
									System.out.println(i + " " + cc);
									break;
								}else{
									System.out.println(i + " " + cc);
								}
							}
						}else{
						
							if(mapL[i][cc].contains(cs[0]) && !mapL[i][cc].contains(block)){
								ns = cc;
								System.out.println(i + " " + cc);
								break;
							}else if(mapL[i][cc].contains(cs[1])&& !mapL[i][cc].contains(block)){
								
								System.out.println(i + " " + cc);
							}else if(mapL[i][cc].contains(cs[2]) && !mapL[i][cc].contains(block)){
								
								System.out.println(i + " " + cc);
								cc+=rev2;
								ns+=rev1;
							}else if (mapL[i][cc].contains(cs[3]) && !mapL[i][cc].contains(block)){
								i+=2;

								break;
							}else{
								System.out.println("Sorry Destination cant be reached, Because all routes are blocked");
								return;
							}
						}
					}
				}
				
				
			}
		});
		btnNewButton.setBounds(130, 586, 97, 25);
		frame.getContentPane().add(btnNewButton);
		
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
