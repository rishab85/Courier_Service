package courierUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.persistence.Entity;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import courierDM.Client;
import courierDM.DeliveryRate;
import courierDM.Userprofile;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

@Entity
public class editUser extends JPanel{

	private JFrame frame = new JFrame();
	private JTable table;
	private JPanel userEdit;
	private JLabel lbl_error;
	private JTextField userName;
	private JTextField password;
	private JTextField email;
	private JTextField fullName;
	private JTextField phone;
	private JComboBox comboBox;
	public editUser(JFrame frame, Userprofile profile, String action) {
		this.frame = frame;
		userEdit = new JPanel();
		frame.getContentPane().add(userEdit, "name_2137049467741814");
		userEdit.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Edit User Information");
		lblNewLabel.setForeground(Color.LIGHT_GRAY);
		lblNewLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
		lblNewLabel.setBounds(48, 13, 209, 37);
		userEdit.add(lblNewLabel);
		
		fullName = new JTextField();
		fullName.setBounds(48, 164, 145, 30);
		userEdit.add(fullName);
		fullName.setColumns(10);
		
		phone = new JTextField();
		phone.setBounds(48, 257, 145, 30);
		userEdit.add(phone);
		phone.setColumns(10);
		
		JLabel lblFirstname = new JLabel("Full name");
		lblFirstname.setBounds(48, 135, 78, 16);
		userEdit.add(lblFirstname);
		
		JLabel lblClientAddress = new JLabel("Phone");
		lblClientAddress.setBounds(48, 228, 88, 16);
		userEdit.add(lblClientAddress);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(48, 342, 559, 9);
		userEdit.add(separator);
		
		if(action=="edit"){
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					updateUser(profile.getUserId());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnSave.setBounds(510, 364, 97, 25);
		userEdit.add(btnSave);
		}else{
			JButton btnAdd = new JButton("Add");
			btnAdd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						addUser();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			btnAdd.setBounds(510, 364, 97, 25);
			userEdit.add(btnAdd);
		}
		lbl_error = new JLabel("");
		lbl_error.setFont(new Font("Malgun Gothic", Font.BOLD | Font.ITALIC, 14));
		lbl_error.setForeground(Color.RED);
		lbl_error.setBounds(48, 63, 375, 16);
		userEdit.add(lbl_error);
		
		JLabel lblUsername = new JLabel("User name");
		lblUsername.setBounds(231, 135, 88, 16);
		userEdit.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(438, 135, 154, 16);
		userEdit.add(lblPassword);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(231, 228, 178, 16);
		userEdit.add(lblEmail);
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new userUI(frame));
				frame.getContentPane().repaint();
				frame.getContentPane().validate();
			}
		});
		cancel.setBounds(401, 364, 97, 25);
		userEdit.add(cancel);
		
		userName = new JTextField();
		userName.setBounds(231, 164, 178, 30);
		userEdit.add(userName);
		userName.setColumns(10);
		
		password = new JTextField();
		password.setBounds(438, 164, 169, 30);
		userEdit.add(password);
		password.setColumns(10);
		
		email = new JTextField();
		email.setBounds(231, 257, 178, 30);
		userEdit.add(email);
		email.setColumns(10);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Ordertaker","Not Active","Superuser"}));
		comboBox.setBounds(453, 253, 154, 30);
		userEdit.add(comboBox);
		
		
		if(action=="edit"){
			fullName.setText(profile.getFirstName());
			userName.setText(profile.getUserName());
			password.setText(profile.getUserPassword());
			phone.setText(profile.getUserPhone());
			email.setText(profile.getUserEmail());
			if(profile.getUserRole()==1){
			comboBox.setSelectedIndex(2);
			}else if(profile.getUserRole()==0){
				comboBox.setSelectedIndex(1);
			}else if(profile.getUserRole()==2){
				comboBox.setSelectedIndex(0);
			}
		}
		
		
		
//		loadRate();
	}
	public JFrame getFrame() {
		return frame;
	}
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
	
	/* Add user in system*/
	public void addUser(){
		if(fullName.getText().isEmpty()){
			lbl_error.setText("Please enter your full name");
			fullName.requestFocus();
		}else if(userName.getText().isEmpty()){
			lbl_error.setText("Please enter the username");
			userName.requestFocus();
		}else if(password.getText().isEmpty()){
			lbl_error.setText("Please enter the password");
			password.requestFocus();
		}else if(phone.getText().isEmpty() || !(isNum(phone.getText()))){
			lbl_error.setText("Entry not valid only numbers are allowed");
			phone.requestFocus();
		}else{
		lbl_error.setText("");
		Userprofile editProf = new Userprofile();

		editProf.setFirstName(fullName.getText());
		editProf.setUserName(userName.getText());
		editProf.setUserPassword(password.getText());
		editProf.setUserPhone(phone.getText());
		editProf.setUserEmail(email.getText());
		if(comboBox.getSelectedItem()=="Not Active"){
			editProf.setUserRole(0);
		}else if(comboBox.getSelectedItem()=="Superuser"){
			editProf.setUserRole(1);
		}else if(comboBox.getSelectedItem()=="Ordertaker"){
			editProf.setUserRole(2);
		}else{
			editProf.setUserRole(0);
		}
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		SessionFactory sf = cfg.buildSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		s.save(editProf);
		s.flush();
		tx.commit();
		s.close();
		
		frame.getContentPane().removeAll();
		frame.getContentPane().add(new userUI(frame));
		frame.getContentPane().repaint();
		frame.getContentPane().validate();
		
		}
		
	}
	
	
	/* Edit user information */
	public void updateUser(int userId) throws Exception{
		if(fullName.getText().isEmpty()){
			lbl_error.setText("Please enter your full name");
			fullName.requestFocus();
		}else if(userName.getText().isEmpty()){
			lbl_error.setText("Please enter the username");
			userName.requestFocus();
		}else if(password.getText().isEmpty()){
			lbl_error.setText("Please enter the password");
			password.requestFocus();
		}else if(phone.getText().isEmpty() || !(isNum(phone.getText()))){
			lbl_error.setText("Entry not valid only numbers are allowed");
			phone.requestFocus();
		}else{
		lbl_error.setText("");
		Userprofile editProf = new Userprofile();

		editProf.setUserId(userId);
		editProf.setFirstName(fullName.getText());
		editProf.setUserName(userName.getText());
		editProf.setUserPassword(password.getText());
		editProf.setUserPhone(phone.getText());
		editProf.setUserEmail(email.getText());
		if(comboBox.getSelectedItem()=="Not Active"){
			editProf.setUserRole(0);
		}else if(comboBox.getSelectedItem()=="Superuser"){
			editProf.setUserRole(1);
		}else if(comboBox.getSelectedItem()=="Ordertaker"){
			editProf.setUserRole(2);
		}else{
			editProf.setUserRole(0);
		}
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		SessionFactory sf = cfg.buildSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		s.update(editProf);
		s.flush();
		tx.commit();
		s.close();
		
		frame.getContentPane().removeAll();
		frame.getContentPane().add(new userUI(frame));
		frame.getContentPane().repaint();
		frame.getContentPane().validate();
		
		}
	}
	

	public Boolean isNum(String str){
		return str.matches("-?\\d+(\\.\\d+)?");
	}
}
