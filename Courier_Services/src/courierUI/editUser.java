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
public class editUser extends JPanel{

	private JFrame frame;
	private JTable table;
	private JPanel userEdit;
	private JTextField rate_permile;
	private JTextField bonus_ontime;
	private JLabel lbl_error;
	
	public editUser(JFrame frame, Userprofile profile, String action) {
		userEdit = new JPanel();
		frame.getContentPane().add(userEdit, "name_2137049467741814");
		userEdit.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Edit User Information");
		lblNewLabel.setForeground(Color.LIGHT_GRAY);
		lblNewLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
		lblNewLabel.setBounds(48, 13, 209, 37);
		userEdit.add(lblNewLabel);
		
		JTextField first_name = new JTextField();
		first_name.setBounds(48, 164, 220, 30);
		userEdit.add(first_name);
		first_name.setColumns(10);
		
		JTextField username = new JTextField();
		username.setBounds(48, 257, 220, 30);
		userEdit.add(username);
		username.setColumns(10);
		
		JLabel lblClientName = new JLabel("First Name");
		lblClientName.setBounds(48, 135, 78, 16);
		userEdit.add(lblClientName);
		
		JLabel lblClientAddress = new JLabel("User Name");
		lblClientAddress.setBounds(48, 228, 88, 16);
		userEdit.add(lblClientAddress);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(48, 463, 559, 9);
		userEdit.add(separator);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(510, 487, 97, 25);
		userEdit.add(btnSave);
		
		lbl_error = new JLabel("");
		lbl_error.setFont(new Font("Malgun Gothic", Font.BOLD | Font.ITALIC, 14));
		lbl_error.setForeground(Color.RED);
		lbl_error.setBounds(48, 63, 375, 16);
		userEdit.add(lbl_error);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setBounds(387, 135, 88, 16);
		userEdit.add(lblLastName);
		
		last_name = new JTextField();
		last_name.setBounds(387, 164, 220, 30);
		userEdit.add(last_name);
		last_name.setColumns(10);
		
		user_password = new JTextField();
		user_password.setBounds(387, 257, 220, 30);
		userEdit.add(user_password);
		user_password.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(387, 228, 56, 16);
		editUser.add(lblPassword);
		
		user_email = new JTextField();
		user_email.setBounds(48, 356, 220, 30);
		userEdit.add(user_email);
		user_email.setColumns(10);
		
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					updateRate();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new ClientUI(frame));
				frame.getContentPane().repaint();
				frame.getContentPane().validate();
			}
		});
		
		loadRate();
	}
	public JFrame getFrame() {
		return frame;
	}
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
	
	public void updateRate() throws Exception{
		String[] pass = new String[10];
		if(rate_permile.getText().isEmpty()){
			lbl_error.setText("Field cannot be empty");
			rate_permile.requestFocus();
		}else if(!isNum(rate_permile.getText())){
			lbl_error.setText("Entry not valid only numbers are allowed");
			rate_permile.setText("");
			rate_permile.requestFocus();
		}else if(bonus_ontime.getText().isEmpty()){
			lbl_error.setText("Field Cannot be empty");
			bonus_ontime.requestFocus();
		}else if(!isNum(bonus_ontime.getText())){
			lbl_error.setText("Entry not valid only numbers are allowed");
			bonus_ontime.setText("");
			bonus_ontime.requestFocus();
		}else{
		lbl_error.setText("");
		DeliveryRate rate = new DeliveryRate();
//		pass[0] = client_name.getText();
//		pass[1] = client_street.getText();
//		pass[2] = client_ave.getText();
//		pass[3] = client_phone.getText();
//		pass[4] = client_email.getText();
		
		rate.setId(1);
		rate.setRatePermile(Integer.parseInt(rate_permile.getText()));		
		rate.setBonusOntime(Integer.parseInt(bonus_ontime.getText()));

		
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		SessionFactory sf = cfg.buildSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		s.update(rate);
		
		s.flush();
		tx.commit();
		s.close();
		}
	}
	
	
	public void loadRate(){
		
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		SessionFactory sf = cfg.buildSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		DeliveryRate rate = (DeliveryRate) s.get(DeliveryRate.class, new Integer(1));
		System.out.println(rate.getBonusOntime());
		
		rate_permile.setText(String.valueOf(rate.getRatePermile()));
		bonus_ontime.setText(String.valueOf(rate.getBonusOntime()));
		
		s.flush();
		tx.commit();
		s.close();
	}
	

	public Boolean isNum(String str){
		try {
		     Integer.parseInt(str);
		     return true;
		}
		catch (NumberFormatException e) {
		     return false;
		}
	}
	/**
	 * Initialize the contents of the frame.
	 */
	
}
