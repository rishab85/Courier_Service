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

@Entity
public class editClient extends JPanel{

	private JFrame frame = new JFrame();
	private JTable table;
	private JPanel userEdit;
	private JTextField rate_permile;
	private JTextField bonus_ontime;
	private JLabel lbl_error;
	private JTextField street;
	private JTextField avenue;
	private JTextField email;
	private JTextField clientName;
	private JTextField phone;
	
	public editClient(JFrame frame, Client client, String action) {
		this.frame = frame;
		userEdit = new JPanel();
		frame.getContentPane().add(userEdit, "name_2137049467741814");
		userEdit.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Edit Client Information");
		lblNewLabel.setForeground(Color.LIGHT_GRAY);
		lblNewLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
		lblNewLabel.setBounds(48, 13, 209, 37);
		userEdit.add(lblNewLabel);
		
		clientName = new JTextField();
		clientName.setBounds(48, 164, 220, 30);
		userEdit.add(clientName);
		clientName.setColumns(10);
		
		phone = new JTextField();
		phone.setBounds(48, 257, 220, 30);
		userEdit.add(phone);
		phone.setColumns(10);
		
		JLabel lblClientName = new JLabel("Client Name");
		lblClientName.setBounds(48, 135, 78, 16);
		userEdit.add(lblClientName);
		
		JLabel lblPhone = new JLabel("Phone");
		lblPhone.setBounds(48, 228, 88, 16);
		userEdit.add(lblPhone);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(48, 350, 578, 16);
		userEdit.add(separator);
		
		if(action=="edit"){
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					updateClient(client.getClientId());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnSave.setBounds(529, 379, 97, 25);
		userEdit.add(btnSave);
		}else{
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					addClient();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnAdd.setBounds(529, 379, 97, 25);
		userEdit.add(btnAdd);
		}
		lbl_error = new JLabel("");
		lbl_error.setFont(new Font("Malgun Gothic", Font.BOLD | Font.ITALIC, 14));
		lbl_error.setForeground(Color.RED);
		lbl_error.setBounds(48, 63, 375, 16);
		userEdit.add(lbl_error);
		
		JLabel lblStreet = new JLabel("Street");
		lblStreet.setBounds(349, 135, 88, 16);
		userEdit.add(lblStreet);
		
		
		JLabel lblAvenue = new JLabel("Avenue");
		lblAvenue.setBounds(510, 135, 56, 16);
		userEdit.add(lblAvenue);
		
		street = new JTextField();
		street.setBounds(349, 164, 116, 30);
		userEdit.add(street);
		street.setColumns(10);
		
		avenue = new JTextField();
		avenue.setBounds(510, 164, 116, 30);
		userEdit.add(avenue);
		avenue.setColumns(10);
		
		email = new JTextField();
		email.setBounds(349, 257, 277, 30);
		userEdit.add(email);
		email.setColumns(10);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(349, 228, 56, 16);
		userEdit.add(lblEmail);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new clientUI(frame));
				frame.getContentPane().repaint();
				frame.getContentPane().validate();
			}
		});
		btnCancel.setBounds(420, 379, 97, 25);
		userEdit.add(btnCancel);
		
		if(action=="edit"){
			clientName.setText(client.getClientName());
			street.setText(client.getClientStreet());
			avenue.setText(client.getClientAve());
			phone.setText(client.getClientPhone());
			email.setText(client.getClientEmail());
		}else{
			clientName.setText("");
			street.setText("");
			avenue.setText("");
			phone.setText("");
			email.setText("");
		}
	}
	public JFrame getFrame() {
		return frame;
	}
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
	
	public void addClient(){
		if(clientName.getText().isEmpty()){
			lbl_error.setText("Please enter the client name");
			clientName.requestFocus();
		}else if(street.getText().isEmpty()){
			lbl_error.setText("Please enter the Street Address");
			street.requestFocus();
		}else if(avenue.getText().isEmpty()){
			lbl_error.setText("Please enter the Avenue Address");
			avenue.requestFocus();
		}
		
		else if(!isNum(phone.getText())){
			lbl_error.setText("Entry not valid only numbers are allowed");
			phone.requestFocus();
		}
		
		else if(phone.getText().isEmpty()){
			lbl_error.setText("Please enter the phone number");
			phone.setText("");
			phone.requestFocus();
		}else if(email.getText().isEmpty()){
			lbl_error.setText("Field Cannot be empty");
			email.requestFocus();
		}else{
		lbl_error.setText("");
		Client addCl = new Client();
//		pass[0] = client_name.getText();
//		pass[1] = client_street.getText();
//		pass[2] = client_ave.getText();
//		pass[3] = client_phone.getText();
//		pass[4] = client_email.getText();
		
		addCl.setClientName(clientName.getText());
		addCl.setClientStreet(street.getText());
		addCl.setClientAve(avenue.getText());
		addCl.setClientPhone(phone.getText());
		addCl.setClientEmail(email.getText());
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		SessionFactory sf = cfg.buildSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		s.save(addCl);
		
		s.flush();
		tx.commit();
		s.close();
		
		frame.getContentPane().removeAll();
		frame.getContentPane().add(new clientUI(frame));
		frame.getContentPane().repaint();
		frame.getContentPane().validate();
		}
	}
	
	public void updateClient(int clientId) throws Exception{
		String[] pass = new String[10];
		if(clientName.getText().isEmpty()){
			lbl_error.setText("Please enter the client name");
			clientName.requestFocus();
		}else if(street.getText().isEmpty()){
			lbl_error.setText("Please enter the Street Address");
			street.requestFocus();
		}else if(avenue.getText().isEmpty()){
			lbl_error.setText("Please enter the Avenue Address");
			avenue.requestFocus();
		}
		
		else if(!isNum(phone.getText())){
			lbl_error.setText("Entry not valid only numbers are allowed");
			phone.requestFocus();
		}
		
		else if(phone.getText().isEmpty()){
			lbl_error.setText("Please enter the phone number");
			phone.setText("");
			phone.requestFocus();
		}else if(email.getText().isEmpty()){
			lbl_error.setText("Field Cannot be empty");
			email.requestFocus();
		}else{
		lbl_error.setText("");
		Client editCl = new Client();
//		pass[0] = client_name.getText();
//		pass[1] = client_street.getText();
//		pass[2] = client_ave.getText();
//		pass[3] = client_phone.getText();
//		pass[4] = client_email.getText();
		
		editCl.setClientId(clientId);
		editCl.setClientName(clientName.getText());
		editCl.setClientStreet(street.getText());
		editCl.setClientAve(avenue.getText());
		editCl.setClientPhone(phone.getText());
		editCl.setClientEmail(email.getText());
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		SessionFactory sf = cfg.buildSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		s.update(editCl);
		
		s.flush();
		tx.commit();
		s.close();
		
		frame.getContentPane().removeAll();
		frame.getContentPane().add(new clientUI(frame));
		frame.getContentPane().repaint();
		frame.getContentPane().validate();
		}
	}
	

	public Boolean isNum(String str){
		return str.matches("-?\\d+(\\.\\d+)?");
	}
}
