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
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import courierDM.Client;
import courierDM.Courier;
import courierDM.Map;
import courierDM.Userprofile;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;



@Entity
public class intersectionUI extends JPanel{

	/**
	 * 
	 */

	private JFrame frame;
	private JTable table;
	private JPanel courier;
	private JTextField textField;
	private ArrayList<Map> map;
	private TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>();
	private JTextField search;
	/**
	 * Launch the application.
	
	 * Create the application.
	 */
	public intersectionUI(JFrame frame) {
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
				"id","Intersection Name", "Status"
			}
		));
		loadTable();
		
		JButton btnStatus = new JButton("Change Status");
		btnStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CharSequence cs = "B";
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
					   
					Map mapp =(Map) s.get(Map.class, new Integer(id));
					
//					if(cour.getCourierStatus()==0){
//						String active = "Not Active";
//					}else{
//						String active = "Active";
//					}
					String active = "Active";
					if(mapp.getDirection().contains(cs)){
						active = "Blocked";
					}
					Object[] possibilities = {"Active", "Blocked"};
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
						if(ans == "Blocked"){
							String temp = mapp.getDirection();
							mapp.setDirection(temp + "B");
						}else{
							String temp = mapp.getDirection();
							temp = temp.replace("B", " ");
							mapp.setDirection(temp);
						}
						s.update(mapp);
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
		
		search = new JTextField();
		search.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				filter(search.getText());
			}
		});
		search.setBounds(48, 77, 116, 22);
		courier.add(search);
		search.setColumns(10);
		
	}

	
	public void loadTable(){
	DefaultTableModel model = new DefaultTableModel();
	CharSequence cs = "B";
	model = (DefaultTableModel) table.getModel();
			model.setRowCount(0);
			Configuration cfg = new Configuration();
			cfg.configure("hibernate.cfg.xml");
			SessionFactory sf = cfg.buildSessionFactory();
			Session s = sf.openSession();
			Transaction tx = s.beginTransaction();
			Client cl = new Client();
			map  =  (ArrayList<Map>) s.createQuery("from Map")
					.list();
					System.out.println(map.size());
					s.flush();
					tx.commit();
					s.close();
			Object[] rowData = new Object[4];
			for(int i=0; i<map.size(); i++){
				rowData[0] = map.get(i).getId();
				rowData[1] = map.get(i).getEdgeName();
				if(map.get(i).getDirection().contains(cs)){
					rowData[2] = "Blocked";
				}else{
					rowData[2] = "Active";
				}
		
				model.addRow(rowData);
				table.getColumnModel().getColumn(0).setMinWidth(0);
				table.getColumnModel().getColumn(0).setMaxWidth(0);
				table.getColumnModel().getColumn(0).setWidth(0);
			   
			}	
			tr = new TableRowSorter<DefaultTableModel>(model);
	}
	
	public void filter(String query){
			table.setRowSorter(tr);
			tr.setRowFilter(RowFilter.regexFilter(query));
	}
}
