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
import courierDM.Courier;
import courierDM.DeliveryRate;
import courierDM.Userprofile;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

@Entity
public class editCourier extends JPanel{

	private JFrame frame = new JFrame();
	private JTable table;
	private JPanel courierEdit;
	private JLabel lbl_error;
	private JTextField courierName;
	private JTextField phone;
	private JComboBox comboBox;
	private JLabel lblYes;
	
	public editCourier(JFrame frame, Courier profile, String action) {
		this.frame = frame;
		courierEdit = new JPanel();
		frame.getContentPane().add(courierEdit, "name_2137049467741814");
		courierEdit.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Edit User Information");
		lblNewLabel.setForeground(Color.LIGHT_GRAY);
		lblNewLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
		lblNewLabel.setBounds(48, 13, 209, 37);
		courierEdit.add(lblNewLabel);
		
		courierName = new JTextField();
		courierName.setBounds(48, 164, 145, 30);
		courierEdit.add(courierName);
		courierName.setColumns(10);
		
		JLabel lblCourierName = new JLabel("Full name");
		lblCourierName.setBounds(48, 135, 78, 16);
		courierEdit.add(lblCourierName);
		
		JLabel lblStatus = new JLabel("Status");
		lblStatus.setBounds(48, 228, 88, 16);
		courierEdit.add(lblStatus);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(48, 342, 559, 9);
		courierEdit.add(separator);
		
		if(action=="edit"){
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					updateUser(profile.getCourierId());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnSave.setBounds(510, 364, 97, 25);
		courierEdit.add(btnSave);
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
			courierEdit.add(btnAdd);
		}
		lbl_error = new JLabel("");
		lbl_error.setFont(new Font("Malgun Gothic", Font.BOLD | Font.ITALIC, 14));
		lbl_error.setForeground(Color.RED);
		lbl_error.setBounds(48, 63, 375, 16);
		courierEdit.add(lbl_error);
		
		JLabel lblPhone = new JLabel("Phone");
		lblPhone.setBounds(275, 134, 88, 16);
		courierEdit.add(lblPhone);
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new courierUI(frame));
				frame.getContentPane().repaint();
				frame.getContentPane().validate();
			}
		});
		cancel.setBounds(401, 364, 97, 25);
		courierEdit.add(cancel);
		
		phone = new JTextField();
		phone.setBounds(275, 163, 178, 30);
		courierEdit.add(phone);
		phone.setColumns(10);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Not Active", "Active"}));
		comboBox.setBounds(48, 257, 154, 30);
		courierEdit.add(comboBox);
		
		if(action=="edit"){
		JLabel lblAvailable = new JLabel("Available");
		lblAvailable.setBounds(275, 228, 56, 16);
		courierEdit.add(lblAvailable);
		}
		
		lblYes = new JLabel("");
		lblYes.setFont(new Font("Arial", Font.BOLD, 18));
		lblYes.setBounds(275, 257, 209, 30);
		courierEdit.add(lblYes);
	
		
		/* Set value to text field if edit action */
		
		if(action=="edit"){	
			courierName.setText(profile.getCourierName());
			phone.setText(profile.getCourierPhone());
			if(profile.getCourierStatus()==0){
				comboBox.setSelectedIndex(0);
				lblYes.setText("Not Available");
				lblYes.setForeground(Color.red);
			}else{
				comboBox.setSelectedIndex(1);
				if(profile.getCourierBusy()==0){
				lblYes.setText("Available");
				lblYes.setForeground(Color.blue);
				}else{
					lblYes.setText("Not Available");;
					lblYes.setForeground(Color.red);
				}
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
		if(courierName.getText().isEmpty()){
			lbl_error.setText("Please enter courier full name");
			courierName.requestFocus();
		}else if(phone.getText().isEmpty() || !(isNum(phone.getText()))){
			lbl_error.setText("Please enter valid number");
			phone.requestFocus();
		}else{
		lbl_error.setText("");
		Courier editCour = new Courier();
		
		editCour.setCourierName(courierName.getText());
		editCour.setCourierPhone(phone.getText());
		if(comboBox.getSelectedIndex()==0){
			editCour.setCourierStatus(0);
		}else{
			editCour.setCourierStatus(1);
		}
		
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		SessionFactory sf = cfg.buildSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		s.save(editCour);
		s.flush();
		tx.commit();
		s.close();
		
		frame.getContentPane().removeAll();
		frame.getContentPane().add(new courierUI(frame));
		frame.getContentPane().repaint();
		frame.getContentPane().validate();
		
		}
		
	}
	
	
	/* Edit user information */
	public void updateUser(int courierId) throws Exception{
		if(courierName.getText().isEmpty()){
			lbl_error.setText("Please enter courier full name");
			courierName.requestFocus();
		}else if(phone.getText().isEmpty() || !(isNum(phone.getText()))){
			lbl_error.setText("Please enter valid number");
			phone.requestFocus();
		}else{
		lbl_error.setText("");
		Courier editCour = new Courier();
		
		editCour.setCourierId(courierId);
		editCour.setCourierName(courierName.getText());
		editCour.setCourierPhone(phone.getText());
		if(comboBox.getSelectedIndex()==0){
			editCour.setCourierStatus(0);
		}else{
			editCour.setCourierStatus(1);
		}
		
		if(lblYes.getText()=="Yes"){
			editCour.setCourierBusy(1);
		}else{
			editCour.setCourierBusy(0);
		}
		
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		SessionFactory sf = cfg.buildSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		s.update(editCour);
		s.flush();
		tx.commit();
		s.close();
		
		frame.getContentPane().removeAll();
		frame.getContentPane().add(new courierUI(frame));
		frame.getContentPane().repaint();
		frame.getContentPane().validate();
		
		}
	}
	

	public Boolean isNum(String str){
		return str.matches("-?\\d+(\\.\\d+)?");
	}
}
