package courierUI;

import java.awt.EventQueue;
import static java.awt.event.InputEvent.CTRL_DOWN_MASK;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;


import java.awt.Font;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SpinnerDateModel;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import courierDM.Userprofile;
import courierDM.Client;
import courierDM.DeliveryTicket;

import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.SystemColor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@Entity
public class MainScreen extends JFrame{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JFrame frame;
	@ManyToOne
	Userprofile profile = new Userprofile();
	
	/** Welcome Screen **/
	
	private JPanel welcome;
	
	/** client screen **/
	JPanel client;
	JPanel addClient;
	
	private JTextField client_name;
	private JTextField client_phone;
	private JTextField client_street;
	private JTextField client_email;
	private JTextField txt_search;
	private JTable table;
	private JTextField textField;
	JLabel lbl_error;
	private JTable table_1;
	private JTextField client_ave;
	private JFrame currentFrame;
	private JTextField username;
	private JTextField phone;
	private JTextField email;
	private JTextField test;
	private String comb;
	/**
	 * Launch the application.

	/**
	 * Create the application.
	 */
	public MainScreen(Userprofile profile) {
		this.profile = profile;
//		JFrame currentFrame = this;
//		set();
		
		System.out.println(profile.getUserRole());
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		ScreenSize size = new ScreenSize();
		frame = new JFrame();
		frame.setBounds(size.x, size.y, size.ScreenWidth, size.ScreenHeight);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnOrder = new JMenu("Order");
		menuBar.add(mnOrder);
		
		JMenuItem mntmCreateNew = new JMenuItem("Create New");
		mntmCreateNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.getContentPane().removeAll();
				DeliveryTicket ticket = new DeliveryTicket();
				frame.getContentPane().add(new createTicket(frame, ticket, "add"));
				frame.getContentPane().repaint();
				frame.getContentPane().validate();
			}
		});
		mnOrder.add(mntmCreateNew);
		mntmCreateNew.setAccelerator(KeyStroke.getKeyStroke('N', CTRL_DOWN_MASK));
		
		JMenuItem mntmTodaysOrder = new JMenuItem("Pending Orders");
		mntmTodaysOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new markTicket(frame));
				frame.getContentPane().repaint();
				frame.getContentPane().validate();
			}
		});
		mnOrder.add(mntmTodaysOrder);
		
		JMenuItem mntmDeliveredOrders = new JMenuItem("Delivered Orders");
		mntmDeliveredOrders.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new deliveredTicket(frame));
				frame.getContentPane().repaint();
				frame.getContentPane().validate();
			}
		});
		mnOrder.add(mntmDeliveredOrders);
		
		JMenu mnMaintian = new JMenu("Maintian");
		menuBar.add(mnMaintian);
		
		JMenuItem mntmClientInformation = new JMenuItem("Client Information");
		mntmClientInformation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new clientUI(frame));
				frame.getContentPane().repaint();
				frame.getContentPane().validate();
				
			}
		});
		mnMaintian.add(mntmClientInformation);
		
		mntmClientInformation.setAccelerator(KeyStroke.getKeyStroke('C', CTRL_DOWN_MASK));
		
	if(profile.getUserRole()==1){
		JMenuItem mntmUserInformation = new JMenuItem("User Information");
		mntmUserInformation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new userUI(frame));
				frame.getContentPane().repaint();
				frame.getContentPane().validate();
			}
		});
		mnMaintian.add(mntmUserInformation);
		mntmUserInformation.setAccelerator(KeyStroke.getKeyStroke('U', CTRL_DOWN_MASK));
		
		JMenuItem mntmDeliveryRate = new JMenuItem("Delivery Rate");
		mntmDeliveryRate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new deliveryRate(frame));
				frame.getContentPane().repaint();
				frame.getContentPane().validate();
			}
		});
		mnMaintian.add(mntmDeliveryRate);
		mntmDeliveryRate.setAccelerator(KeyStroke.getKeyStroke('R', CTRL_DOWN_MASK));
	}
		JMenuItem mntmIntersectionInformation = new JMenuItem("Intersection Information");
		mntmIntersectionInformation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new intersectionUI(frame));
				frame.getContentPane().repaint();
				frame.getContentPane().validate();
			}
		});
		mnMaintian.add(mntmIntersectionInformation);
		
		JMenuItem mntmCourierInformation = new JMenuItem("Courier Information");
		mntmCourierInformation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new courierUI(frame));
				frame.getContentPane().repaint();
				frame.getContentPane().validate();
			}
		});
		mnMaintian.add(mntmCourierInformation);
		mntmCourierInformation.setAccelerator(KeyStroke.getKeyStroke('D', CTRL_DOWN_MASK));
		
		JMenu mnReport = new JMenu("Report");
		menuBar.add(mnReport);
		
		JMenuItem mntmClientReport = new JMenuItem("Client Report");
		mntmClientReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new clientReport(frame));
				frame.getContentPane().repaint();
				frame.getContentPane().validate();
			}
		});
		mnReport.add(mntmClientReport);
		
		JMenuItem mntmDriverReport = new JMenuItem("Driver Report");
		mntmDriverReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new courierReport(frame));
				frame.getContentPane().repaint();
				frame.getContentPane().validate();
			}
		});
		mnReport.add(mntmDriverReport);
		
		JMenuItem mntmGenerateBill = new JMenuItem("Generate Bill");
		mntmGenerateBill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new clientBill(frame));
				frame.getContentPane().repaint();
				frame.getContentPane().validate();
			}
		});
		mnReport.add(mntmGenerateBill);
		
		JMenu mnLogout = new JMenu("Logout");
		mnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		menuBar.add(mnLogout);
		
		JMenuItem mntmLogout = new JMenuItem("Logout");
		mntmLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				profile =null;
				frame.dispose();
				Login login = new Login();
				login.getFrame().setVisible(true);
			}
		});
		mnLogout.add(mntmLogout);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		JLabel user_name = new JLabel("New label");
		user_name.setFont(new Font("Tahoma", Font.BOLD, 15));
		user_name.setBounds(999, 13, 141, 16);
		user_name.setText(profile.getFirstName());
		
		JLabel lblLoggedInAs = new JLabel("Logged in as : ");
		lblLoggedInAs.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblLoggedInAs.setBounds(899, 13, 97, 16);
		
		
		

		
		/****** Welcome screen *********/
		
		welcome = new JPanel();
		frame.getContentPane().add(welcome, "name_2135251003229631");
		welcome.setLayout(null);
		
		welcome.add(user_name);
		
		welcome.add(lblLoggedInAs);
		
		JLabel lblWelcomeTo = new JLabel("Welcome to");
		lblWelcomeTo.setFont(new Font("Segoe UI Semilight", Font.PLAIN, 26));
		lblWelcomeTo.setBounds(476, 248, 141, 53);
		welcome.add(lblWelcomeTo);
		
		JLabel lblAcmeCourierService = new JLabel("Acme Courier Service");
		lblAcmeCourierService.setFont(new Font("Segoe UI Black", Font.PLAIN, 34));
		lblAcmeCourierService.setBounds(385, 308, 366, 53);
		welcome.add(lblAcmeCourierService);
		
		
		loadScreen(welcome);
		
		JLabel lblOrPressCtrl = new JLabel("OR PRESS Ctrl + N");
		lblOrPressCtrl.setForeground(SystemColor.textInactiveText);
		lblOrPressCtrl.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblOrPressCtrl.setBounds(476, 444, 163, 34);
		welcome.add(lblOrPressCtrl);
		
//		Calendar cal = Calendar.getInstance();
//		Date date = (Date) cal.getTime();
//		SpinnerDateModel sm = new SpinnerDateModel(date, null, null, Calendar.MINUTE);

//		JSpinner spinner = new JSpinner(sm);
//		JSpinner.DateEditor de = new JSpinner.DateEditor(spinner, "hh:mm a");
//		de.getTextField().setEditable( true );
//		spinner.setBounds(32, 548, 163, 34);
//		spinner.setEditor(de);
//		welcome.add(spinner);
		
		JButton btnStartOrder = new JButton("Start Order");
		btnStartOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.getContentPane().removeAll();
				DeliveryTicket ticket = new DeliveryTicket();
				frame.getContentPane().add(new createTicket(frame, ticket, "add"));
				frame.getContentPane().repaint();
				frame.getContentPane().validate();
			}
		});
		btnStartOrder.setBounds(503, 389, 114, 42);
		welcome.add(btnStartOrder);
		
		test = new JTextField();
		test.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				if(!isNum(String.valueOf(e.getKeyChar()))){
					e.consume();
				}else{
				if(comb == null){
					comb = String.valueOf(e.getKeyChar());
				}else{
					if(!test.getText().isEmpty()){
						comb = test.getText() + e.getKeyChar();
					}else{
						comb = String.valueOf(e.getKeyChar());
								
					}
				}
				int con = Integer.parseInt(comb);
					if(comb.length()>2){
						comb = comb.substring(0, 2);
						e.consume();
						
					}else if(con>60){
						JOptionPane.showMessageDialog(frame, "Number more than 60 is not allowed");
						test.setText("");
						comb = null;
						e.consume();
					}
				}
				System.out.println(comb);
			}
			
		});
		test.setBounds(193, 551, 116, 22);
//		welcome.add(test);
		test.setColumns(10);
		
		
		JPanel editUser = new JPanel();
		frame.getContentPane().add(editUser, "name_2137049467741814");
		editUser.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Edit User Information");
		lblNewLabel.setForeground(Color.LIGHT_GRAY);
		lblNewLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
		lblNewLabel.setBounds(48, 13, 209, 37);
		editUser.add(lblNewLabel);
		
		JTextField name = new JTextField();
		name.setBounds(48, 164, 220, 30);
		editUser.add(name);
		name.setColumns(10);
		
		JTextField password = new JTextField();
		password.setBounds(48, 257, 220, 30);
		editUser.add(password);
		password.setColumns(10);
		
		JLabel lblClientName = new JLabel("Name");
		lblClientName.setBounds(48, 135, 78, 16);
		editUser.add(lblClientName);
		
		JLabel lblClientAddress = new JLabel("Password");
		lblClientAddress.setBounds(48, 228, 88, 16);
		editUser.add(lblClientAddress);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(48, 463, 559, 9);
		editUser.add(separator);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(510, 487, 97, 25);
		editUser.add(btnSave);
		
		lbl_error = new JLabel("");
		lbl_error.setFont(new Font("Malgun Gothic", Font.BOLD | Font.ITALIC, 14));
		lbl_error.setForeground(Color.RED);
		lbl_error.setBounds(48, 63, 375, 16);
		editUser.add(lbl_error);
		
		JLabel lblLastName = new JLabel("User Name");
		lblLastName.setBounds(387, 135, 88, 16);
		editUser.add(lblLastName);
		
		username = new JTextField();
		username.setBounds(387, 164, 220, 30);
		editUser.add(username);
		username.setColumns(10);
		
		phone = new JTextField();
		phone.setBounds(387, 257, 220, 30);
		editUser.add(phone);
		phone.setColumns(10);
		
		JLabel lblPassword = new JLabel("Phone");
		lblPassword.setBounds(387, 228, 56, 16);
		editUser.add(lblPassword);
		
		email = new JTextField();
		email.setBounds(48, 356, 220, 30);
		editUser.add(email);
		email.setColumns(10);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(48, 327, 56, 16);
		editUser.add(lblEmail);
		
		JComboBox userrole = new JComboBox();
		userrole.setModel(new DefaultComboBoxModel(new String[] {"Superuser", "Order Taker", "Not Active"}));
		userrole.setBounds(387, 356, 220, 26);
		editUser.add(userrole);
	}

	public void loadScreen(Component name){
		frame.getContentPane().removeAll();
		frame.getContentPane().add(name);
		frame.getContentPane().repaint();
		frame.getContentPane().validate();
		
	}
	public void set(){
//		ArrayList<UserProfile> list = new ArrayList<>();
//		for(int i=0; i<3; i++){
//		UserProfile profile = new UserProfile();
//		profile.setFirst_name("rishab" + i);
//		list.add(profile);
//		}
//		
//		for(int i=0; i<3; i++){
//			System.out.println(list.get(i).getFirst_name());
//		}
		System.out.println(profile.getUserId());
	}
	
	public void addClient() throws Exception{
		String[] pass = new String[10];
		if(client_name.getText().isEmpty()){
			lbl_error.setText("*Please Enter the client Name");
			client_name.requestFocus();
		}else if(client_street.getText().isEmpty()){
			lbl_error.setText("*Please Enter the Street Address");
			client_street.requestFocus();
		}else if(client_ave.getText().isEmpty()){
			lbl_error.setText("*Please Enter the Avenue Address");
			client_ave.requestFocus();
		}else if(client_phone.getText().isEmpty()){
			lbl_error.setText("*Please Enter the Phone Number");
			client_phone.requestFocus();
		}else if(client_email.getText().isEmpty()){
			lbl_error.setText("*Please Enter the Email Address");
			client_email.requestFocus();
		}else{
		lbl_error.setText("");
		Client addCl = new Client();
//		pass[0] = client_name.getText();
//		pass[1] = client_street.getText();
//		pass[2] = client_ave.getText();
//		pass[3] = client_phone.getText();
//		pass[4] = client_email.getText();
		
		addCl.setClientName(client_name.getText());
		addCl.setClientStreet(client_street.getText());
		addCl.setClientAve(client_ave.getText());
		addCl.setClientPhone(client_phone.getText());
		addCl.setClientEmail(client_email.getText());
		
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		SessionFactory sf = cfg.buildSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		
		
		if(s.save(addCl) != null){;
				
				client_name.setText("");
				client_street.setText("");
				client_ave.setText("");
				client_phone.setText("");
				client_email.setText("");
				
				
			}
		s.flush();
		tx.commit();
		s.close();
		}
	}
	
	public Boolean isNum(String str){
		return str.matches("-?\\d+(\\.\\d+)?");
	}
}
