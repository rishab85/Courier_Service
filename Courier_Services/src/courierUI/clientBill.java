package courierUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.swing.JFormattedTextField.AbstractFormatter;

import javax.persistence.Entity;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DateFormatter;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfDate;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import courierDM.Client;
import courierDM.DeliveryRate;
import courierDM.DeliveryTicket;

import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerDateModel;

import java.awt.SystemColor;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.BoxLayout;
import javax.swing.border.LineBorder;
import org.jdatepicker.DateModel;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jdatepicker.impl.SqlDateModel;
import org.jdatepicker.impl.UtilCalendarModel;

import java.util.Calendar;

@Entity
public class clientBill extends JPanel{

	private JFrame frame;
	private JTable table;
	private JLabel cost;
	private JPanel clientBill;
	private JLabel lbl_error;
	private ArrayList<Client> clientRe = new ArrayList<Client>();
	private List<DeliveryTicket> dT = new ArrayList<DeliveryTicket>();
	private Date dtFr =null;
	private Date dtTo = null;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private int totalCost = 0, totalService = 0;
	public clientBill(JFrame frame) {
		clientBill = new JPanel();
		frame.getContentPane().add(clientBill, "name_2137049467741814");
		clientBill.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Generate Bill");
		lblNewLabel.setForeground(Color.LIGHT_GRAY);
		lblNewLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
		lblNewLabel.setBounds(48, 29, 209, 37);
		clientBill.add(lblNewLabel);
		
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		
		UtilDateModel model2 = new UtilDateModel();
		Properties p2 = new Properties();
		p2.put("text.today", "Today");
		p2.put("text.month", "Month");
		p2.put("text.year", "Year");
		JDatePanelImpl datePanel2 = new JDatePanelImpl(model2, p2);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(48, 127, 767, 9);
		clientBill.add(separator);
		
		JButton btnGenerate = new JButton("Generate");
		btnGenerate.setBounds(656, 79, 159, 32);
		clientBill.add(btnGenerate);
		
		
		
		lbl_error = new JLabel("");
		lbl_error.setFont(new Font("Malgun Gothic", Font.BOLD | Font.ITALIC, 14));
		lbl_error.setForeground(Color.RED);
		lbl_error.setBounds(48, 63, 375, 16);
		clientBill.add(lbl_error);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(48, 149, 767, 288);
		clientBill.add(scrollPane);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPane.setViewportView(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0};
		gbl_panel.rowHeights = new int[]{0, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblFrom = new JLabel("All due bills will be generated till : ");
		lblFrom.setForeground(Color.DARK_GRAY);
		lblFrom.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFrom.setBounds(48, 87, 209, 27);
		clientBill.add(lblFrom);
		
		JLabel lblTill = new JLabel("Till");
		lblTill.setForeground(Color.DARK_GRAY);
		lblTill.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTill.setBounds(246, 87, 209, 27);
		clientBill.add(lblTill);
		
		Calendar calen = Calendar.getInstance();
		lblTill.setText(sdf.format(calen.getTime()));
		JButton btnGeneratePdf = new JButton("Generate PDF");
		btnGeneratePdf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					createPdf();
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnGeneratePdf.setBounds(673, 450, 142, 37);
		
		
		
		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.removeAll();
				int genPDF = 0;
				String dateFrom = null;
				String dateTo = null;
				Configuration cfg = new Configuration();
				cfg.configure("hibernate.cfg.xml");
				SessionFactory sf = cfg.buildSessionFactory();
				Session s = sf.openSession();
				Transaction tx = s.beginTransaction();
				
				Calendar c = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

						
				clientRe = (ArrayList<Client>) s.createQuery("from Client").list();
			
				
				JPanel[] pan = new JPanel[clientRe.size()];
				GridBagConstraints[] gbc = new GridBagConstraints[clientRe.size()];
				
				
				
				for (int i=0; i<clientRe.size(); i++){
					
					pan[i] = new JPanel();
					pan[i].setBorder(new LineBorder(new Color(0, 0, 0)));
					gbc[i] = new GridBagConstraints();
					Dimension dim = new Dimension();
					dim.width=panel.getWidth();
					dim.height=panel.getHeight();
					pan[i].setPreferredSize(new Dimension(dim.width-30,500));
					pan[i].setLayout(null);
					gbc[i].gridx = 0;
					gbc[i].gridy = i;
					panel.add(pan[i], gbc[i]);
					
					JLabel name = new JLabel();
					name.setBounds(10, 10, 300, 30);
					name.setText("Client Name : " + clientRe.get(i).getClientName());
					pan[i].add(name);
					
					cost = new JLabel();
					cost.setBounds(10, 230, 300, 30);
					pan[i].add(cost);
					
					JTable tab = new JTable();
					tab.setBackground(new Color(240,240,240));
					tab.setRowHeight(30);
					tab.setBounds(5, 80, dim.width-40, 400);
					tab.setModel(new DefaultTableModel(
						new Object[][] {
						},
						new String[] {
								"Package ID", "Receiver", "Estimated Pickup", "Actual Pickup", "Estimated Delivery", "Actual Delivery"
						}
					));
					
					tab.setFont(new Font("Arial", Font.PLAIN, 14));
					
					dT = (ArrayList<DeliveryTicket>) s.createQuery("from DeliveryTicket where billlTo = :id and billed = :bil and delivered = :del")
							.setParameter("id", clientRe.get(i).getClientId())
							.setParameter("bil", 0)
							.setParameter("del", 1)
							.list();
					pan[i].add(tab);
					DefaultTableModel model = new DefaultTableModel();
					model = (DefaultTableModel) tab.getModel();
					model.setRowCount(0);
					Object[] rowData = new Object[7];
					for(int j=dT.size(); j>=0; j--){
						if(dT.size()>0){
							genPDF = 1;
							
							if(j==dT.size()){
							rowData[0] = "Package ID";
							rowData[1] = "Pickup Time";
							rowData[2] = "Delivered Time";
							rowData[3] = "Service Date";
							rowData[4] = "Miles";
							rowData[5] = "Cost";
							}else{
								totalCost = totalCost + dT.get(j).getEstimatedCost();
								totalService++;
								rowData[0] = dT.get(j).getPackageId();
								rowData[1] = dT.get(j).getActualPickup();
								rowData[2] = dT.get(j).getActualDelivery();
								rowData[3] = dT.get(j).getTransactionDate();
								rowData[4] = dT.get(j).getEstimatedMiles();
								rowData[5] = dT.get(j).getEstimatedCost();
								System.out.println(rowData[5]);
							}
							model.addRow(rowData);
						}else{
							panel.remove(pan[i]);
							clientBill.remove(btnGeneratePdf);
							clientRe.remove(clientRe.get(i));
							i = i-1;
						}
					}
					cost.setText("Total Cost : " + String.valueOf(totalCost));
					frame.getContentPane().validate();
					frame.getContentPane().repaint();
				}
				
				s.flush();
				tx.commit();
				s.close();
				if(genPDF==1){
					clientBill.add(btnGeneratePdf);
					genPDF=0;
				}else{
					clientBill.remove(btnGeneratePdf);
				}
				
			}
		});
		

		
		
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new clientUI(frame));
				frame.getContentPane().repaint();
				frame.getContentPane().validate();
			}
		});	
	}
	
	
	public JFrame getFrame() {
		return frame;
	}
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	
	
	public void createPdf() throws DocumentException, ParseException{
		
		JTable table = new JTable();
		
		table.setRowHeight(40);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Package ID", "Pickup Time", "Delivered Time", "Service Date", "Miles", "Cost"
			}
		));
		
		DefaultTableModel model = new DefaultTableModel();
		model = (DefaultTableModel) table.getModel();
				model.setRowCount(0);
		
		table.setFont(new Font("Arial", Font.PLAIN, 14));
		ArrayList<Client> client = new ArrayList<Client>();
		
		Document doc = new Document();
		try {
			PdfWriter.getInstance(doc, new FileOutputStream("clientBill.pdf"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		doc.open();
		
		

		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		SessionFactory sf = cfg.buildSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String from = "2017-06-12", to ="2017-06-15";
		

		
			Date frmDate = format.parse(from);
			Date enDate = format.parse(to);
		
		
		client = (ArrayList<Client>) s.createQuery("from Client").list();
		
		Calendar calen = Calendar.getInstance();
		com.itextpdf.text.Font fontbold = FontFactory.getFont("Times-Roman", 18, Font.BOLD);
		com.itextpdf.text.Font regular = FontFactory.getFont("Arial", 10, Font.BOLD);
		for(int j=0; j<clientRe.size(); j++){
			List<DeliveryTicket> list = new ArrayList<DeliveryTicket>();
			list = (ArrayList<DeliveryTicket>) s.createQuery("from DeliveryTicket where billlTo = :id and billed = :bil and delivered = :del")
					.setParameter("id", clientRe.get(j).getClientId())
					.setParameter("bil", 0)
					.setParameter("del", 1)
					.list();
			
			PdfPTable tbl = new PdfPTable(6);
			tbl.getDefaultCell().setPadding(4);;
			doc.add(new Paragraph("Client Name : " + client.get(j).getClientName(), fontbold));
			doc.add(new Paragraph("Client Address : " + client.get(j).getClientStreet() + " Street " + client.get(j).getClientAve()));
			doc.add(new Paragraph("Client Phone : " + client.get(j).getClientPhone()));
			doc.add(new Paragraph("Bill From : " + format.format(list.get(list.size()-1).getTransactionDate()) + "\t To : " + format.format(calen.getTime()), regular));
			doc.add(new Paragraph(" "));
			doc.add(new Paragraph(" "));
			tbl.addCell("Package Id");
			tbl.addCell("Pickup Time");
			tbl.addCell("Delivered Time");
			tbl.addCell("Service Date");
			tbl.addCell("Miles");
			tbl.addCell("Cost");
			
			for(int i=0; i<list.size(); i++ ){
//				doc.add(new Paragraph(list.get(i).getPackageId() + "\t" + "\t | \t" + list.get(i).getReceiver().getClientName()
//						+ "\t" + "\t | \t" + list.get(i).getTransactionDate()
//						+ "\t" + "\t | \t" + list.get(i).getActualPickup()
//						+ "\t" + "\t | \t" + list.get(i).getActualDelivery()));
				PdfPCell myCell1 = new PdfPCell(new Phrase(String.valueOf(list.get(i).getPackageId()), regular)); 
				tbl.addCell(myCell1);
				PdfPCell myCell2 = new PdfPCell(new Phrase(String.valueOf(list.get(i).getActualPickup()), regular)); 
				tbl.addCell(myCell2);
				PdfPCell myCell6 = new PdfPCell(new Phrase(String.valueOf(list.get(i).getActualDelivery()), regular)); 
				tbl.addCell(myCell6);
				PdfPCell myCell3 = new PdfPCell(new Phrase(sdf.format(list.get(i).getTransactionDate()), regular));
				tbl.addCell(myCell3);
				PdfPCell myCell4 = new PdfPCell(new Phrase(String.valueOf(list.get(i).getEstimatedMiles()), regular));
				tbl.addCell(myCell4);
				PdfPCell myCell5 = new PdfPCell(new Phrase(String.valueOf(list.get(i).getEstimatedCost()), regular));
				tbl.addCell(myCell5);
				
			}
			doc.add(tbl);
			doc.add(new Paragraph(""));
			doc.add(new Paragraph("------------------------------------------------------------------------"));
			doc.add(new Paragraph(""));
			doc.add(new Paragraph("Total cost :" + String.valueOf(totalCost)));
			doc.add(new Paragraph("Total Service : " + String.valueOf(totalService)));
			doc.newPage();
		}
		s.flush();
		tx.commit();
		s.close();
		
		doc.close();
		
		JOptionPane.showMessageDialog(frame, "PDF file created successfully");
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
	
	public void getData() throws ParseException{
		
		
		
		
	}
}
