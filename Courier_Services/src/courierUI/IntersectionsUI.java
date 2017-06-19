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
import courierDM.Intersection;
import courierDM.Userprofile;



@Entity
public class IntersectionsUI extends JPanel {

	
	private JFrame frame;
	private JTable table;
	private JPanel client;
	private JTextField txt_search;
	private JTextField textField;
	private ArrayList<Intersection> intersec;
	/**
	 * Create the panel.
	 */
	public IntersectionsUI(JFrame frame) {


		client = new JPanel();
		client.setLayout(null);
		frame.getContentPane().add(client, "name_2138240631062768");
		JLabel lblClientInformation = new JLabel("Intersection Information");
		lblClientInformation.setForeground(Color.LIGHT_GRAY);
		lblClientInformation.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
		lblClientInformation.setBounds(48, 13, 209, 37);
		client.add(lblClientInformation);
		
		txt_search = new JTextField();
		txt_search.setColumns(10);
		txt_search.setBounds(131, 63, 220, 30);
		client.add(txt_search);
		
		JLabel lblClientId = new JLabel("Id");
		lblClientId.setBounds(48, 70, 78, 16);
		client.add(lblClientId);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(48, 110, 772, 2);
		client.add(separator_1);
		
		JButton btnSearch = new JButton("Streets");
		btnSearch.setBounds(363, 66, 97, 25);
		client.add(btnSearch);
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(48, 139, 772, 227);
		client.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setRowHeight(40);
		
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"id", "streets", "ave", "address","actual_Map_Address"
			}
		));
		loadTable();
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
					Intersection detail = new Intersection();
					String action = "edit";
				   int row = table.getSelectedRow();
				   
				   if(row<0){
					   JOptionPane.showMessageDialog(frame, "Please select the row you want to edit");
				   }else{
					 
				   row = table.convertRowIndexToView(row);
				   detail.setid(intersec.get(row).getId());
				   detail.setstreets(intersec.get(row).getstreets());
				   detail.setave(intersec.get(row).getave());
				   detail.setdirection(intersec.get(row).getdirection());
				   detail.setActual_Map_Address(intersec.get(row).getActual_Map_Address());
		           
				   
				   frame.getContentPane().removeAll();
					frame.getContentPane().add(new editIntersection(frame, detail, action));
					frame.getContentPane().repaint();
					frame.getContentPane().validate();
				   } 
			}
		});
		btnEdit.setBounds(505, 379, 97, 25);
		client.add(btnEdit);
		
		JButton btnAddNew = new JButton("Add New");
		btnAddNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*String action = "add";
				frame.getContentPane().removeAll();
				Userprofile profile = new Userprofile();
				frame.getContentPane().add(new editUser(frame, profile, action));
				frame.getContentPane().repaint();
				frame.getContentPane().validate();*/
			}
		});
		btnAddNew.setBounds(614, 379, 97, 25);
		client.add(btnAddNew);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
				
				Configuration cfg = new Configuration();
				cfg.configure("hibernate.cfg.xml");
				SessionFactory sf = cfg.buildSessionFactory();
				Session s = sf.openSession();
				Transaction tx = s.beginTransaction();
				
				int row = table.getSelectedRow();
				
				if(row<0){
					JOptionPane.showMessageDialog(frame, "Please Select the row you want to delete");
				}else{
				Object[] options = {"Yes",
                "No"};
				int n = JOptionPane.showOptionDialog(frame,
			    "Would you like to Delete the record? ",
			    "Delete Client",
			    JOptionPane.YES_NO_CANCEL_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    null,
			    options,
			    options[1]);
		
				if(n==0){
					row = table.convertRowIndexToView(row);
					int id = ((Integer) table.getModel().getValueAt(row,0));
					   
					Intersection usDel =(Intersection) s.get(Intersection.class, new Integer(id));
					s.delete(usDel);
					s.flush();
					tx.commit();
					s.close();
					loadTable();
					}
				}
			}
		});
		btnDelete.setBounds(723, 379, 97, 25);
		client.add(btnDelete);	

		
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
			intersec  =  (ArrayList<Intersection>) s.createQuery("from Intersection")
					.list();
					System.out.println(intersec.size());
					s.flush();
					tx.commit();
					s.close();
			Object[] rowData = new Object[6];
			for(int i=0; i<intersec.size(); i++){
			rowData[0] = intersec.get(i).getId();
			rowData[1] = intersec.get(i).getstreets();
			rowData[2] = intersec.get(i).getave();
			rowData[3] = intersec.get(i).getdirection();
			rowData[4] = intersec.get(i).getActual_Map_Address();
			
			model.addRow(rowData);
			}	
	}
}
