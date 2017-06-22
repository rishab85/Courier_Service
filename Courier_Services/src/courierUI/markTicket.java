package courierUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import courierDM.BestRoute;
import courierDM.Client;
import courierDM.Courier;
import courierDM.DeliveryTicket;
import courierDM.Map;
import courierDM.Userprofile;
import javax.swing.border.BevelBorder;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import java.awt.SystemColor;



@Entity
public class markTicket extends JPanel{

	/**
	 * 
	 */

	private JFrame frame;
	private JTable table;
	private JPanel pendingTicket;
	private JTextField textField;
	private ArrayList<Courier> driver;
	private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	/**
	 * Launch the application.
	
	 * Create the application.
	 */
	public markTicket(JFrame frame) {
		pendingTicket = new JPanel();
		pendingTicket.setLayout(null);
		frame.getContentPane().add(pendingTicket, "name_2138240631062768");
		
		JLabel lblClientInformation = new JLabel("Pending Orders");
		lblClientInformation.setForeground(Color.LIGHT_GRAY);
		lblClientInformation.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
		lblClientInformation.setBounds(48, 13, 209, 37);
		pendingTicket.add(lblClientInformation);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(48, 99, 772, 2);
		pendingTicket.add(separator_1);
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(48, 139, 772, 227);
		pendingTicket.add(scrollPane);
		
		
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setRowHeight(40);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ticket id","Package ID", "Sender", "Receiver", "Courier", "Pickedup Time", "Delivered Time"
			}
		));
		
		table.setFont(new Font("Arial", Font.PLAIN, 14));
		loadTable();
		
		
		//------Mark picked up----------------//
		JButton btnPicked = new JButton("PICKED");
		btnPicked.setBackground(SystemColor.controlHighlight);
		btnPicked.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnPicked.setForeground(new Color(255, 0, 0));
		btnPicked.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					Courier detail = new Courier();
					DeliveryTicket dTicket = new DeliveryTicket();
					String action = "edit";
				   int row = table.getSelectedRow();
				   
				   if(row<0){
					   JOptionPane.showMessageDialog(frame, "Please select the row you want to edit");
				   }else{
					 
				   row = table.convertRowIndexToView(row);

		           
				   String cr = (String) table.getModel().getValueAt(row,4);
				   if("Not Assigned".equalsIgnoreCase(cr.trim())){
					   System.out.println(cr);
					   JOptionPane.showMessageDialog(frame, "You must assign Courier first.");
				   }else if(table.getModel().getValueAt(row,5)=="Pending"){
					   
					   Object[] options = {"Yes",
	                    "No"};
					   int n = JOptionPane.showOptionDialog(frame,
					   "Do you want to mark ticket as Pickedup ",
					    "Pickedup",
					    JOptionPane.YES_NO_CANCEL_OPTION,
					    JOptionPane.QUESTION_MESSAGE,
					    null,
					    options,
					    options[1]);
				
					   if(n==0){
						Configuration cfg = new Configuration();
						cfg.configure("hibernate.cfg.xml");
						SessionFactory sf = cfg.buildSessionFactory();
						Session s = sf.openSession();
						Transaction tx = s.beginTransaction();
						
						Calendar cal = Calendar.getInstance();
						int id = ((Integer) table.getModel().getValueAt(row,0));
						dTicket =(DeliveryTicket) s.get(DeliveryTicket.class, new Integer(id));
						dTicket.setActualPickup(cal.getTime());
						dTicket.setPicked(1);
						s.save(dTicket);
						s.flush();
						tx.commit();
						s.close();
						loadTable();
					   }
				   }
				   else{
					   JOptionPane.showMessageDialog(frame, "Ticket is Already Marked !");
				   }
				   
//				   frame.getContentPane().removeAll();
//					frame.getContentPane().add(new editCourier(frame, detail, action));
//					frame.getContentPane().repaint();
//					frame.getContentPane().validate();
				   } 
				  
			}
		});
		btnPicked.setBounds(683, 379, 137, 47);
		pendingTicket.add(btnPicked);
		
		JButton btnAddNew = new JButton("EDIT");
		btnAddNew.setBackground(SystemColor.controlHighlight);
		btnAddNew.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnAddNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String action = "update";
				
				DeliveryTicket ticket = new DeliveryTicket();
				Client sender = new Client();
				Client receiver = new Client();
				Courier cr = new Courier();
				
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
				if(table.getModel().getValueAt(row, 5)=="Pending"){
					int id = ((Integer) table.getModel().getValueAt(row,0));
					ticket =(DeliveryTicket) s.get(DeliveryTicket.class, new Integer(id));
					s.flush();
					tx.commit();
					s.close();
					frame.getContentPane().removeAll();
					frame.getContentPane().add(new createTicket(frame, ticket, action));
					frame.getContentPane().repaint();
					frame.getContentPane().validate();
				}else{
					JOptionPane.showMessageDialog(frame, "Courier is already out for delivery, cannot Edit ticket !!");
				}
				}
			}
		});
		btnAddNew.setBounds(562, 379, 97, 47);
		pendingTicket.add(btnAddNew);
		
		
		//---------------Assign Courier---------------------//
		JButton btnCourier = new JButton("ASSSIGN COURIER");
		btnCourier.setBackground(SystemColor.controlHighlight);
		btnCourier.setForeground(new Color(128, 0, 0));
		btnCourier.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCourier.setToolTipText("Assign courier to the ticket");
		btnCourier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeliveryTicket dTicket = new DeliveryTicket();
				Courier c = new Courier();
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
					if(table.getModel().getValueAt(row, 5)=="Pending"){
					dTicket =(DeliveryTicket) s.get(DeliveryTicket.class, new Integer(id));
					
					System.out.println(id);
//					if(cour.getCourierStatus()==0){
//						String active = "Not Active";
//					}else{
//						String active = "Active";
//					}
					ArrayList<Courier> courier = new ArrayList<Courier>();
					courier  =  (ArrayList<Courier>) s.createQuery("from Courier where courierStatus =? and courierBusy = ?")
							.setParameter(0, 1)
							.setParameter(1, 0)
							.list();
						List<String> optionList = new ArrayList<String>();
							for(int i=0; i<courier.size(); i++){
								optionList.add(courier.get(i).getCourierName());
							}
							
						
							
							
							
					Object[] possibilities = optionList.toArray();
					
					String ans = (String)JOptionPane.showInputDialog(
					                    frame,
					                    "Select courier Status:\n",
					                    "Courier Status",
					                    JOptionPane.PLAIN_MESSAGE,
					                    null,
					                    possibilities,
					                    possibilities[0]);
					
					int index = optionList.indexOf(ans);
					System.out.println(courier.get(index).getCourierName());
					if(ans.isEmpty()|| ans==null){
						System.out.println("process end");
					}else{
						System.out.println(index);
						courier.get(index).setCourierBusy(1);
						Courier oldCour = new Courier();
						
						oldCour  = (Courier) s.get(Courier.class, new Integer(dTicket.getCourierId()));
						oldCour.setCourierBusy(0);
						dTicket.setCourierId(courier.get(index).getCourierId());
						c =(Courier) s.get(Courier.class, new Integer(courier.get(index).getCourierId()));
						
						s.save(c);
						s.save(oldCour);
						s.save(dTicket);
						s.flush();
						tx.commit();
						s.close();
						loadTable();
						}
					}else{
						 JOptionPane.showMessageDialog(frame, "Courier can't be changed at this moment!");
					}
					}
				}
		});
		btnCourier.setBounds(48, 379, 209, 47);
		pendingTicket.add(btnCourier);	
		
		
		//-------- mark delivered-------------- ///
		
		JButton btnMarkDelivered = new JButton("DELIVERED");
		btnMarkDelivered.setBackground(SystemColor.controlHighlight);
		btnMarkDelivered.setForeground(new Color(0, 100, 0));
		btnMarkDelivered.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnMarkDelivered.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Courier cour = new Courier();
				DeliveryTicket dTicket = new DeliveryTicket();
				String action = "edit";
			   int row = table.getSelectedRow();
			   
			   if(row<0){
				   JOptionPane.showMessageDialog(frame, "Please select the row you want to edit");
			   }else{
				 
			   row = table.convertRowIndexToView(row);

	           
//			   String cr = (String) table.getModel().getValueAt(row,5);
			   if(table.getModel().getValueAt(row,5)=="Pending"){

				   JOptionPane.showMessageDialog(frame, "Ticket is not pickedup yet.");
			   }else if(table.getModel().getValueAt(row,6)=="Pending"){
				   
				   Object[] options = {"Yes",
                    "No"};
				   int n = JOptionPane.showOptionDialog(frame,
				   "Do you want to mark ticket as Delivered ",
				    "Delivered",
				    JOptionPane.YES_NO_CANCEL_OPTION,
				    JOptionPane.QUESTION_MESSAGE,
				    null,
				    options,
				    options[1]);
			
				   if(n==0){
					Configuration cfg = new Configuration();
					cfg.configure("hibernate.cfg.xml");
					SessionFactory sf = cfg.buildSessionFactory();
					Session s = sf.openSession();
					Transaction tx = s.beginTransaction();
					
					Calendar cal = Calendar.getInstance();
					int id = ((Integer) table.getModel().getValueAt(row,0));
					dTicket =(DeliveryTicket) s.get(DeliveryTicket.class, new Integer(id));
					cour =(Courier) s.get(Courier.class, new Integer(dTicket.getCourierId()));
					cour.setCourierBusy(0);
					dTicket.setActualDelivery(cal.getTime());
					dTicket.setDelivered(1);
					String est = sdf.format(dTicket.getEstimatedDelivery());
					est = est.replaceAll(":", "");
					
					long t = cal.getTimeInMillis() - (5*60000);
					Date now = new Date(t);
					String nowD = sdf.format(now);
					nowD = nowD.replaceAll(":", "");
					System.out.println(est +" " + nowD);
					if(Integer.parseInt(nowD)<Integer.parseInt(est)){
						dTicket.setDriverBonus(2);
					}else{
						System.out.println("sorry");
					}
					
					s.save(dTicket);
					s.save(cour);
					
					s.flush();
					tx.commit();
					s.close();
					loadTable();
				   }
			   }
			   else{
				   JOptionPane.showMessageDialog(frame, "Ticket is Already Marked !");
			   }
			 }
			}
		});
		btnMarkDelivered.setBounds(683, 439, 137, 47);
		pendingTicket.add(btnMarkDelivered);
		
		
		// generate route //////////
		JButton btnGenereateRoute = new JButton("COURIER INSTRUCTION");
		btnGenereateRoute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();
				   if(row<0){
					   JOptionPane.showMessageDialog(frame, "Please select the row you want to edit");
				   }else{
					 
				   row = table.convertRowIndexToView(row);
					int id = ((Integer) table.getModel().getValueAt(row,0));
		           
//				   String cr = (String) table.getModel().getValueAt(row,5);
				   					   
					   Object[] options = {"Yes",
	                    "No"};
					   int n = JOptionPane.showOptionDialog(frame,
					   "Do you want to create the Driver Instruction",
					    "Delivered",
					    JOptionPane.YES_NO_CANCEL_OPTION,
					    JOptionPane.QUESTION_MESSAGE,
					    null,
					    options,
					    options[1]);
				
					   if(n==0){
						Configuration cfg = new Configuration();
						cfg.configure("hibernate.cfg.xml");
						SessionFactory sf = cfg.buildSessionFactory();
						Session s = sf.openSession();
						Transaction tx = s.beginTransaction();
						DeliveryTicket dTicket = new DeliveryTicket();
						ArrayList<BestRoute> best = new ArrayList<BestRoute>();
						dTicket =(DeliveryTicket) s.get(DeliveryTicket.class, new Integer(id));
						best =  (ArrayList<BestRoute>) s.createQuery("from BestRoute where packageId = :id")
								.setParameter("id", dTicket.getPackageId())
								.list();
						
						
						ArrayList<Map> map = new ArrayList<Map>();
						map.clear();
						map = (ArrayList<Map>) s.createQuery("From Map")
								.list();
						
						
						s.flush();
						tx.commit();
						s.close();
						
						char c = 'H';
						String[][] mapL = new String[8][c];
						int index = 0;
						for(int i=1; i<=7 ; i++){
							for (char j = 'A'; j<'H'; j++) {
								mapL[i][j] = map.get(index).getEdgeName();
								index ++;
							}
						}
						
						Document doc = new Document();
						try {
							PdfWriter.getInstance(doc, new FileOutputStream("instruction.pdf"));
						} catch (FileNotFoundException | DocumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						doc.open();
						com.itextpdf.text.Font fontbold = FontFactory.getFont("Times-Roman", 18, Font.BOLD);
						com.itextpdf.text.Font regular = FontFactory.getFont("Arial", 10, Font.PLAIN);
						com.itextpdf.text.Font header = FontFactory.getFont("Arial", 10, Font.BOLD);
						try {
							doc.add(new Paragraph("Delivery Information", fontbold));
							doc.add(new Paragraph("Package ID : " + dTicket.getPackageId(),header));
							
							doc.add(new Paragraph(" "));
							doc.add(new Paragraph(" "));
							
							doc.add(new Paragraph("Pickup Information", header));
							doc.add(new Paragraph("Pickup Time : " + dTicket.getEstimatedPickup(), regular));
							doc.add(new Paragraph("Pickup Destination : " + dTicket.getSender().getClientStreet() + " Street " + dTicket.getSender().getClientAve()));
							doc.add(new Paragraph(" "));
							doc.add(new Paragraph("Direction", header));
							int count = 0;
							String direction = null;
							for(int i=0; i<best.get(0).getRouteId().SIZE/3; i+=3){
								if(best.get(0).getRoute().charAt(count) == 'U'){
								 direction  = "North";	
								}else if(best.get(0).getRoute().charAt(count) == 'D'){
									direction = "South";
								}else if(best.get(0).getRoute().charAt(count) == 'L'){
									direction = "West";
								}else{
									direction = "East";
								}
								
								doc.add(new Paragraph("- Go " + direction + " from " + mapL[Integer.parseInt(String.valueOf(best.get(0).getRoute().charAt(count+1)))][best.get(0).getRoute().charAt(count+2)]));
								count += 3;
							}
							
							doc.add(new Paragraph(" "));
							doc.add(new Paragraph("------------------------------------------------------------------------------------"));
							doc.add(new Paragraph(" "));
							doc.add(new Paragraph("Delivery Information", header));
							doc.add(new Paragraph("Deliver Time : " + dTicket.getEstimatedDelivery(), regular));
							doc.add(new Paragraph("Deliver Destination : " + dTicket.getReceiver().getClientStreet() + " Street " + dTicket.getReceiver().getClientAve()));
							doc.add(new Paragraph(" "));
							doc.add(new Paragraph("Direction", header));
							int countD = 0;
							String directionD = null;
							for(int i=0; i<=best.get(1).getRouteId().SIZE/3; i+=3){
								if(best.get(1).getRoute().charAt(countD) == 'U'){
								 directionD  = "North";	
								}else if(best.get(1).getRoute().charAt(countD) == 'D'){
									directionD = "South";
								}else if(best.get(1).getRoute().charAt(countD) == 'L'){
									directionD = "West";
								}else{
									directionD = "East";
								}
								
								doc.add(new Paragraph("- Go " + directionD + " from " + mapL[Integer.parseInt(String.valueOf(best.get(1).getRoute().charAt(countD+1)))][best.get(1).getRoute().charAt(countD+2)]));
								countD += 3;
							}
							
							doc.close();
							JOptionPane.showMessageDialog(frame, "PDF sucessfully created");
						} catch (DocumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					   }
				   else{
					   JOptionPane.showMessageDialog(frame, "Ticket is Already Marked !");
				   }
				 }
			}
		});
		btnGenereateRoute.setBackground(SystemColor.controlHighlight);
		btnGenereateRoute.setForeground(new Color(128, 0, 0));
		btnGenereateRoute.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnGenereateRoute.setToolTipText("Generate the direction for the driver");
		btnGenereateRoute.setBounds(48, 439, 209, 47);
		pendingTicket.add(btnGenereateRoute);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(383, 379, 1, 53);
		pendingTicket.add(separator);
		
		JButton btnDelete = new JButton("DELETE");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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
					if(table.getModel().getValueAt(row, 5)=="Pending"){
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
				 
					
						DeliveryTicket clDel =(DeliveryTicket) s.get(DeliveryTicket.class, new Integer(id));
						s.delete(clDel);
						s.flush();
						tx.commit();
						s.close();
						loadTable();
					}
				}else{
					JOptionPane.showMessageDialog(frame, "Sorry cannot delete ticket at this moment.");
				}
			}
			}
		});
		btnDelete.setBackground(SystemColor.controlHighlight);
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnDelete.setBounds(562, 439, 97, 47);
		pendingTicket.add(btnDelete);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setOrientation(SwingConstants.VERTICAL);
		separator_3.setBounds(671, 379, 7, 107);
		pendingTicket.add(separator_3);
		
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
					.setParameter(0, 0)
					.list();
					s.flush();
					tx.commit();
					s.close();
			Object[] rowData = new Object[7];
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
			if(ticket.get(i).getPicked()==0){
			rowData[5] = "Pending";
			}else{
				rowData[5] = ticket.get(i).getActualPickup();
			}
			
			if(ticket.get(i).getDelivered()==0){
				rowData[6] = "Pending";
				}else{
					rowData[6] = ticket.get(i).getActualDelivery();
				}
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
