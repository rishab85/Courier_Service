package courierUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
import courierDM.DeliveryTicket;
import courierDM.Userprofile;
import javax.swing.border.BevelBorder;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import java.awt.SystemColor;



@Entity
public class deliveredTicket extends JPanel{

	/**
	 * 
	 */

	private JFrame frame;
	private JTable table;
	private JPanel pendingTicket;
	private JTextField textField;
	private ArrayList<Courier> driver;
	/**
	 * Launch the application.
	
	 * Create the application.
	 */
	public deliveredTicket(JFrame frame) {
		pendingTicket = new JPanel();
		pendingTicket.setLayout(null);
		frame.getContentPane().add(pendingTicket, "name_2138240631062768");
		
		JLabel lblClientInformation = new JLabel("Pending Orders");
		lblClientInformation.setForeground(Color.LIGHT_GRAY);
		lblClientInformation.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
		lblClientInformation.setBounds(48, 13, 209, 37);
		pendingTicket.add(lblClientInformation);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(48, 99, 902, 2);
		pendingTicket.add(separator_1);
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(48, 139, 902, 227);
		pendingTicket.add(scrollPane);
		
		
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setRowHeight(40);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ticket id","Package ID", "Sender", "Receiver", "Courier", "Pickedup Time", "Delivered Time", "Order Date"
			}
		));
		
		table.setFont(new Font("Arial", Font.PLAIN, 14));
		loadTable();
		
		JButton btnAddNew = new JButton("View Detail");
		btnAddNew.setBackground(SystemColor.controlHighlight);
		btnAddNew.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnAddNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String action = "detail";
				DeliveryTicket ticket = new DeliveryTicket();
				
				Configuration cfg = new Configuration();
				cfg.configure("hibernate.cfg.xml");
				SessionFactory sf = cfg.buildSessionFactory();
				Session s = sf.openSession();
				Transaction tx = s.beginTransaction();
				
				int row = table.getSelectedRow();
				if(row<0){
					   JOptionPane.showMessageDialog(frame, "Please select the row you want to view");
				   }else{
					   row = table.convertRowIndexToView(row);
						int id = ((Integer) table.getModel().getValueAt(row,0));
						ticket =(DeliveryTicket) s.get(DeliveryTicket.class, new Integer(id));
						s.flush();
						tx.commit();
						s.close();
						
						frame.getContentPane().removeAll();
						frame.getContentPane().add(new createTicket(frame, ticket, action));
						frame.getContentPane().repaint();
						frame.getContentPane().validate();
				   }
			}
		});
		btnAddNew.setBounds(814, 379, 136, 47);
		pendingTicket.add(btnAddNew);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(383, 379, 1, 53);
		pendingTicket.add(separator);
		
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
			Calendar cal = Calendar.getInstance();
			ArrayList<DeliveryTicket> ticket = new ArrayList<DeliveryTicket>();
			ticket  =  (ArrayList<DeliveryTicket>) s.createQuery("from DeliveryTicket where delivered =?")
					.setParameter(0, 1)
					.list();
					s.flush();
					tx.commit();
					s.close();
			Object[] rowData = new Object[8];
			for(int i=ticket.size()-1; i>=0; i--){
			rowData[0] = ticket.get(i).getTicketId();
			rowData[1] = ticket.get(i).getPackageId();
			Configuration cf = new Configuration();
			cf.configure("hibernate.cfg.xml");
			SessionFactory sfs = cf.buildSessionFactory();
			Session se = sf.openSession();
			Transaction txs = se.beginTransaction();
			
			rowData[2] = ticket.get(i).getSender().getClientName();
			
			rowData[3] = ticket.get(i).getReceiver().getClientName();
			rowData[4] = ticket.get(i).getCourier().getCourierName();
			rowData[5] = ticket.get(i).getActualPickup();	
			rowData[6] = ticket.get(i).getActualDelivery();
			rowData[7] = ticket.get(i).getTransactionDate();
//			if(driver.get(i).getCourierStatus()==1){
//				rowData[3] = "Active";
//				if(driver.get(i).getCourierBusy()==0){
//					rowData[4] = "Yes";
//				}else{
//					rowData[4] = "No";
//				}
//			}else{
//				rowData[3] = "Not Active";
//				rowData[4] = "No";
//			}
			se.flush();
			txs.commit();
			se.close();

			model.addRow(rowData);
			
			table.getColumnModel().getColumn(0).setMinWidth(0);
			   table.getColumnModel().getColumn(0).setMaxWidth(0);
			   table.getColumnModel().getColumn(0).setWidth(0);
			}	
	}
}
