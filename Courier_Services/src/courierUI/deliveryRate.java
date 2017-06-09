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
import javax.swing.JOptionPane;
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

@Entity
public class deliveryRate extends JPanel{

	private JFrame frame;
	private JTable table;
	private JPanel deliveryRate;
	private JTextField rate_permile;
	private JTextField bonus_ontime;
	private JLabel lbl_error;
	
	public deliveryRate(JFrame frame) {
		deliveryRate = new JPanel();
		frame.getContentPane().add(deliveryRate, "name_2137049467741814");
		deliveryRate.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Delivery Rate");
		lblNewLabel.setForeground(Color.LIGHT_GRAY);
		lblNewLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
		lblNewLabel.setBounds(48, 13, 209, 37);
		deliveryRate.add(lblNewLabel);
		
		rate_permile = new JTextField();
		rate_permile.setBounds(48, 164, 220, 30);
		deliveryRate.add(rate_permile);
		rate_permile.setColumns(10);
		
		bonus_ontime = new JTextField();
		bonus_ontime.setBounds(48, 257, 220, 30);
		deliveryRate.add(bonus_ontime);
		bonus_ontime.setColumns(10);
		
		JLabel lblClientName = new JLabel("Rate per mile");
		lblClientName.setBounds(48, 135, 78, 16);
		deliveryRate.add(lblClientName);
		
		JLabel lblClientAddress = new JLabel("Bonus on time");
		lblClientAddress.setBounds(48, 228, 88, 16);
		deliveryRate.add(lblClientAddress);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(48, 367, 559, 9);
		deliveryRate.add(separator);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(510, 391, 97, 25);
		deliveryRate.add(btnSave);
		
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
		
		lbl_error = new JLabel("");
		lbl_error.setFont(new Font("Malgun Gothic", Font.BOLD | Font.ITALIC, 14));
		lbl_error.setForeground(Color.RED);
		lbl_error.setBounds(48, 63, 375, 16);
		deliveryRate.add(lbl_error);
		
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new clientUI(frame));
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
		
			JOptionPane.showMessageDialog(frame, "Information Updated Succefully");
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
