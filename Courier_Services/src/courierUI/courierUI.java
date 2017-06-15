package courierUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
import courierDM.Courier;
import courierDM.Userprofile;



@Entity
public class courierUI extends JPanel{

	/**
	 * 
	 */

	private JFrame frame;
	private JTable table;
	private JPanel courier;
	private JTextField textField;
	private ArrayList<Courier> driver;
	/**
	 * Launch the application.
	
	 * Create the application.
	 */
	public courierUI(JFrame frame) {
		courier = new JPanel();
		courier.setLayout(null);
		frame.getContentPane().add(courier, "name_2138240631062768");
		
		JLabel lblClientInformation = new JLabel("Courier Information");
		lblClientInformation.setForeground(Color.LIGHT_GRAY);
		lblClientInformation.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
		lblClientInformation.setBounds(48, 13, 209, 37);
		courier.add(lblClientInformation);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(48, 110, 772, 2);
		courier.add(separator_1);
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(48, 139, 772, 227);
		courier.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setRowHeight(40);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Courier ID", "Courier Name", "Phone", "Status", "Available"
			}
		));
		loadTable();
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					Courier detail = new Courier();
					String action = "edit";
				   int row = table.getSelectedRow();
				   
				   if(row<0){
					   JOptionPane.showMessageDialog(frame, "Please select the row you want to edit");
				   }else{
					 
				   row = table.convertRowIndexToView(row);

		           
				   detail.setCourierId(driver.get(row).getCourierId());
				   detail.setCourierName(driver.get(row).getCourierName());
				   detail.setCourierPhone(driver.get(row).getCourierPhone());
				   detail.setCourierStatus(driver.get(row).getCourierStatus());
				   detail.setCourierBusy(driver.get(row).getCourierBusy());
				   
				   frame.getContentPane().removeAll();
					frame.getContentPane().add(new editCourier(frame, detail, action));
					frame.getContentPane().repaint();
					frame.getContentPane().validate();
				   } 
			}
		});
		btnEdit.setBounds(478, 379, 97, 25);
		courier.add(btnEdit);
		
		JButton btnAddNew = new JButton("Add New");
		btnAddNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String action = "add";
				frame.getContentPane().removeAll();
				Courier courier = new Courier();
				frame.getContentPane().add(new editCourier(frame, courier, action));
				frame.getContentPane().repaint();
				frame.getContentPane().validate();
			}
		});
		btnAddNew.setBounds(587, 379, 97, 25);
		courier.add(btnAddNew);
		
		JButton btnStatus = new JButton("Change Status");
		btnStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				Configuration cfg = new Configuration();
				cfg.configure("hibernate.cfg.xml");
				SessionFactory sf = cfg.buildSessionFactory();
				Session s = sf.openSession();
				Transaction tx = s.beginTransaction();
				
				int row = table.getSelectedRow();
				
				if(row<0){
					JOptionPane.showMessageDialog(frame, "Please Select the row you want to edit.");
				}else{
		
				
					row = table.convertRowIndexToView(row);
					int id = ((Integer) table.getModel().getValueAt(row,0));
					   
					Courier cour =(Courier) s.get(Courier.class, new Integer(id));
					
//					if(cour.getCourierStatus()==0){
//						String active = "Not Active";
//					}else{
//						String active = "Active";
//					}
					String active = "Active";
					if(cour.getCourierStatus()==0){
						active = "Not Active";
					}
					Object[] possibilities = {"Not Active", "Active"};
					String ans = (String)JOptionPane.showInputDialog(
					                    frame,
					                    "Select courier Status:\n",
					                    "Courier Status",
					                    JOptionPane.PLAIN_MESSAGE,
					                    null,
					                    possibilities,
					                    active);
					
					if(ans.isEmpty()|| ans==null){
						System.out.println("process end");
					}else{
						if(ans == "Not Active"){
							cour.setCourierStatus(0);
						}else{
							cour.setCourierStatus(1);
						}
						s.update(cour);
						s.flush();
						tx.commit();
						s.close();
						loadTable();
						}
					}
				}
		});
		btnStatus.setBounds(696, 379, 124, 25);
		courier.add(btnStatus);	
		
	}

	
	public void loadTable(){
	DefaultTableModel model = new DefaultTableModel();
	model = (DefaultTableModel) table.getModel();
			model.setRowCount(0);
			Configuration cfg = new Configuration();
			cfg.configure("hibernate.cfg.xml");
			SessionFactory sf = cfg.buildSessionFactory();
			Session s = sf.openSession();
			Transaction tx = s.beginTransaction();
			Client cl = new Client();
			driver  =  (ArrayList<Courier>) s.createQuery("from Courier")
					.list();
					System.out.println(driver.size());
					s.flush();
					tx.commit();
					s.close();
			Object[] rowData = new Object[6];
			for(int i=0; i<driver.size(); i++){
			rowData[0] = driver.get(i).getCourierId();
			rowData[1] = driver.get(i).getCourierName();
			rowData[2] = driver.get(i).getCourierPhone();
			if(driver.get(i).getCourierStatus()==1){
				rowData[3] = "Active";
				if(driver.get(i).getCourierBusy()==0){
					rowData[4] = "Yes";
				}else{
					rowData[4] = "No";
				}
			}else{
				rowData[3] = "Not Active";
				rowData[4] = "No";
			}
			

			model.addRow(rowData);
			}	
	}

}
