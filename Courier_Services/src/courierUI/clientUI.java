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
import courierDM.Userprofile;



@Entity
public class clientUI extends JPanel{

	/**
	 * 
	 */

	private JFrame frame;
	private JTable table;
	private JPanel client;
	private JTextField txt_search;
	private JTextField textField;
	private ArrayList<Client> cl;
	/**
	 * Launch the application.
	
	 * Create the application.
	 */
	public clientUI(JFrame frame) {
		client = new JPanel();
		client.setLayout(null);
		frame.getContentPane().add(client, "name_2138240631062768");
		
		JLabel lblClientInformation = new JLabel("Client Information");
		lblClientInformation.setForeground(Color.LIGHT_GRAY);
		lblClientInformation.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
		lblClientInformation.setBounds(48, 13, 209, 37);
		client.add(lblClientInformation);
		
		txt_search = new JTextField();
		txt_search.setColumns(10);
		txt_search.setBounds(131, 63, 220, 30);
		client.add(txt_search);
		
		JLabel lblClientId = new JLabel("Client Id");
		lblClientId.setBounds(48, 70, 78, 16);
		client.add(lblClientId);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(48, 110, 772, 2);
		client.add(separator_1);
		
		JButton btnSearch = new JButton("Search");
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
				"Client Id", "Client Name", "Street", "Avenue", "Phone", "Email"
			}
		));
		loadTable();
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					Client detail = new Client();
					String action = "edit";
				   int row = table.getSelectedRow();
				   if(row<0){
					   JOptionPane.showMessageDialog(frame, "Please selecte the row to Edit");
				   }else{  
				   
				   row = table.convertRowIndexToView(row);
				   detail.setClientId(cl.get(row).getClientId());
				   detail.setClientName(cl.get(row).getClientName());
				   detail.setClientStreet(cl.get(row).getClientStreet());
				   detail.setClientAve(cl.get(row).getClientAve());
				   detail.setClientPhone(cl.get(row).getClientPhone());
				   detail.setClientEmail(cl.get(row).getClientEmail());	   
				   
				   frame.getContentPane().removeAll();
				   frame.getContentPane().add(new editClient(frame, detail, action));
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
				String action = "add";
				frame.getContentPane().removeAll();
				Client client = new Client();
				frame.getContentPane().add(new editClient(frame, client, action));
				frame.getContentPane().repaint();
				frame.getContentPane().validate();
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
				
				if(row<0)
				{
					JOptionPane.showMessageDialog(frame, "Please selecte the row to delete");
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
				   
				Client clDel =(Client) s.get(Client.class, new Integer(id));
				s.delete(clDel);
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
			cl  =  (ArrayList<Client>) s.createQuery("from Client")
					.list();
					System.out.println(cl.size());
					s.flush();
					tx.commit();
					s.close();
			Object[] rowData = new Object[6];
			for(int i=0; i<cl.size(); i++){
			rowData[0] = cl.get(i).getClientId();
			rowData[1] = cl.get(i).getClientName();
			rowData[2] = cl.get(i).getClientStreet();
			rowData[3] = cl.get(i).getClientAve();
			rowData[4] = cl.get(i).getClientPhone();
			rowData[5] = cl.get(i).getClientEmail();
			model.addRow(rowData);
			}	
	}

}
