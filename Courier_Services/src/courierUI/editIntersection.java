package courierUI;

import java.awt.Color;
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


import courierDM.Client;
import courierDM.Intersection;
import javax.swing.SwingConstants;
@Entity
public class editIntersection extends JPanel {

	private JFrame frame = new JFrame();
	private JTable table;
	private JPanel IntersecEdit;
	private JTextField rate_permile;
	private JTextField bonus_ontime;
	private JLabel lbl_error;
	private JTextField avenue;
	private JTextField email;
	private JTextField street;
	private JTextField ave;
	private JTextField direction;
	private JTextField mapaddress;
	/**
	 * Create the panel.
	 */
	
	public editIntersection(JFrame frame, Intersection detail, String action) {
		this.frame = frame;
		JPanel IntersecEdit= new JPanel();
		frame.getContentPane().add(IntersecEdit, "name_2137049467741814");
		IntersecEdit.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Edit Intersection Information");
		lblNewLabel.setBounds(48, 13, 209, 37);
		lblNewLabel.setForeground(Color.LIGHT_GRAY);
		lblNewLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
		IntersecEdit.add(lblNewLabel);
		
		
		JSeparator separator = new JSeparator();
		separator.setBounds(48, 350, 578, 16);
		IntersecEdit.add(separator);
		
		
		
		JButton btnNewButton_1 = new JButton("Cancl");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new IntersectionsUI(frame));
				frame.getContentPane().repaint();
				frame.getContentPane().validate();
			
			}
		});
		btnNewButton_1.setBounds(139, 262, 97, 25);
		IntersecEdit.add(btnNewButton_1);
		
		JLabel Street_Num = new JLabel("StreetNum");
		Street_Num.setBounds(12, 116, 81, 16);
		IntersecEdit.add(Street_Num);
		
		street = new JTextField();
		street.setBounds(120, 79, 116, 22);
		IntersecEdit.add(street);
		street.setColumns(10);
		
		JLabel ave_num = new JLabel("Ave");
		Street_Num.setBounds(12, 82, 81, 16);
		IntersecEdit.add(ave_num);
		
		ave = new JTextField();
		ave.setBounds(120, 113, 116, 22);
		IntersecEdit.add(ave);
		ave.setColumns(10);
		
		direction = new JTextField();
		direction.setBounds(120, 148, 116, 22);
		IntersecEdit.add(direction);
		direction.setColumns(10);
		
		mapaddress = new JTextField();
		mapaddress.setBounds(120, 183, 116, 22);
		IntersecEdit.add(mapaddress);
		mapaddress.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("AVE Num");
		lblNewLabel_1.setBounds(12, 116, 56, 16);
		IntersecEdit.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Direction");
		lblNewLabel_2.setBounds(12, 151, 56, 16);
		IntersecEdit.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Map Address");
		lblNewLabel_3.setBounds(12, 186, 56, 16);
		IntersecEdit.add(lblNewLabel_3);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(92, 201, 1, 2);
		IntersecEdit.add(separator_1);
		
		if(action=="edit"){
			JButton btnNewButton = new JButton("Save");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
				try {
					updateintersection(detail.getId());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			});
			btnNewButton.setBounds(29, 262, 89, 25);
			IntersecEdit.add(btnNewButton);		}
		//else{
		/*JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		
				try {
					//addClient();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		});
		
		btnAdd.setBounds(529, 379, 97, 25);
		IntersecEdit.add(btnAdd);
		*/
		lbl_error = new JLabel("");
		lbl_error.setFont(new Font("Malgun Gothic", Font.BOLD | Font.ITALIC, 14));
		lbl_error.setForeground(Color.RED);
		lbl_error.setBounds(48, 63, 375, 16);
		IntersecEdit.add(lbl_error);
		
		
		if(action=="edit"){
			street.setText(detail.getstreets());
			ave.setText(detail.getave());
			direction.setText(detail.getdirection());
			mapaddress.setText(detail.getActual_Map_Address());
			
		}else{
			
			street.setText("");
			ave.setText("");
			direction.setText("");
			mapaddress.setText("");
		}
	}
	
	
	public JFrame getFrame() {
		return frame;
	}
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
	
	public void updateintersection(int Id) throws Exception{
		if(street.getText().isEmpty()){
			lbl_error.setText("Please enter street");
			street.requestFocus();
		}else if(ave.getText().isEmpty()){
			lbl_error.setText("Please enter avenue");
			ave.requestFocus();
		}else if(direction.getText().isEmpty()){
			lbl_error.setText("Please enter direction");
			direction.requestFocus();
		}else{
		lbl_error.setText("");
		Intersection editintersec = new Intersection();
		editintersec.setstreets(street.getText());
		editintersec.setave(ave.getText());
		editintersec.setdirection(direction.getText());
		editintersec.setActual_Map_Address(mapaddress.getText());
		editintersec.setid(Id);
		
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		SessionFactory sf = cfg.buildSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		s.update(editintersec);
		s.flush();
		tx.commit();
		s.close();
		
		frame.getContentPane().removeAll();
		frame.getContentPane().add(new IntersectionsUI(frame));
		frame.getContentPane().repaint();
		frame.getContentPane().validate();
		
		}
	}
	
	
}
