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
public class clientReport extends JPanel{

	private JFrame frame;
	private JTable table;
	private JPanel clientReport;
	private JLabel lbl_error;
	private ArrayList<Client> clientRe = new ArrayList<Client>();
	private List<DeliveryTicket> dT = new ArrayList<DeliveryTicket>();
	private Date dtFr =null;
	private Date dtTo = null;
	public clientReport(JFrame frame) {
		clientReport = new JPanel();
		frame.getContentPane().add(clientReport, "name_2137049467741814");
		clientReport.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Client Report");
		lblNewLabel.setForeground(Color.LIGHT_GRAY);
		lblNewLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
		lblNewLabel.setBounds(48, 29, 209, 37);
		clientReport.add(lblNewLabel);
		
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());
		datePicker.setLocation(94, 89);
		datePicker.setSize(182, 25);
		clientReport.add(datePicker);
		
		UtilDateModel model2 = new UtilDateModel();
		Properties p2 = new Properties();
		p2.put("text.today", "Today");
		p2.put("text.month", "Month");
		p2.put("text.year", "Year");
		JDatePanelImpl datePanel2 = new JDatePanelImpl(model2, p2);
		JDatePickerImpl datePicker2 = new JDatePickerImpl(datePanel2, new DateComponentFormatter());
		datePicker2.setLocation(431, 89);
		datePicker2.setSize(182, 25);
		clientReport.add(datePicker2);
		
		JLabel lblClientAddress = new JLabel("Till");
		lblClientAddress.setBounds(385, 89, 34, 16);
		clientReport.add(lblClientAddress);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(48, 127, 767, 9);
		clientReport.add(separator);
		
		JButton btnGenerate = new JButton("Generate");
		btnGenerate.setBounds(656, 79, 159, 32);
		clientReport.add(btnGenerate);
		
		
		
		lbl_error = new JLabel("");
		lbl_error.setFont(new Font("Malgun Gothic", Font.BOLD | Font.ITALIC, 14));
		lbl_error.setForeground(Color.RED);
		lbl_error.setBounds(48, 63, 375, 16);
		clientReport.add(lbl_error);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(48, 149, 767, 288);
		clientReport.add(scrollPane);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPane.setViewportView(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0};
		gbl_panel.rowHeights = new int[]{0, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblFrom = new JLabel("From");
		lblFrom.setBounds(48, 87, 34, 16);
		clientReport.add(lblFrom);
		
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

				int fromY = datePicker.getModel().getYear();
				int fromM = datePicker.getModel().getMonth()+1;
				int fromD = datePicker.getModel().getDay();
				String from = String.valueOf(fromY) + "-" + String.valueOf(fromM) + "-" + String.valueOf(fromD);
					
				String to = String.valueOf(datePicker2.getModel().getYear() + "-" + String.valueOf(datePicker2.getModel().getMonth()+1) + "-" + String.valueOf(datePicker2.getModel().getDay()));	
				try {
					dtFr = sdf.parse(from);
					dateFrom = sdf.format(dtFr);
					
					dtTo = sdf.parse(to);
					dateTo = sdf.format(dtTo);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
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
					
					dT = (ArrayList<DeliveryTicket>) s.createQuery("from DeliveryTicket where senderId = ? and transactionDate BETWEEN :start and :end")
							.setParameter(0, clientRe.get(i).getClientId())
							.setParameter("start", dtFr)
							.setParameter("end", dtTo)
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
							rowData[1] = "Receiver";
							rowData[2] = "Estimated Pickup";
							rowData[3] = "Actual Pickup";
							rowData[4] = "Estimated Delivery";
							rowData[5] = "Actual Delivery";
							}else{
								rowData[0] = dT.get(j).getPackageId();
								rowData[1] = dT.get(j).getReceiver().getClientName();
								rowData[2] = dT.get(j).getEstimatedDelivery();
								rowData[3] = dT.get(j).getActualDelivery();
								rowData[4] = dT.get(j).getEstimatedDelivery();
								rowData[5] = dT.get(j).getActualDelivery();
								System.out.println(rowData[5]);
							}
							model.addRow(rowData);
						}else{
							panel.remove(pan[i]);
							clientReport.remove(btnGeneratePdf);
						}
					}
					frame.getContentPane().validate();
					frame.getContentPane().repaint();
				}
				
				s.flush();
				tx.commit();
				s.close();
				if(genPDF==1){
					clientReport.add(btnGeneratePdf);
					genPDF=0;
				}else{
					clientReport.remove(btnGeneratePdf);
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
				"Package ID", "Receiver", "Estimated Pickup", "Actual Pickup", "Estimated Delivery", "Actual Delivery"
			}
		));
		
		DefaultTableModel model = new DefaultTableModel();
		model = (DefaultTableModel) table.getModel();
				model.setRowCount(0);
		
		table.setFont(new Font("Arial", Font.PLAIN, 14));
		ArrayList<Client> client = new ArrayList<Client>();
		
		Document doc = new Document();
		try {
			PdfWriter.getInstance(doc, new FileOutputStream("Report.pdf"));
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
		
		
		com.itextpdf.text.Font fontbold = FontFactory.getFont("Times-Roman", 18, Font.BOLD);
		com.itextpdf.text.Font regular = FontFactory.getFont("Arial", 10, Font.BOLD);
		for(int j=0; j<client.size(); j++){
			PdfPTable tbl = new PdfPTable(6);
			tbl.getDefaultCell().setPadding(4);;
			doc.add(new Paragraph("Client Name : " + client.get(j).getClientName(), fontbold));
			doc.add(new Paragraph("Client Address : " + client.get(j).getClientStreet() + " Street " + client.get(j).getClientAve()));
			doc.add(new Paragraph("Client Phone : " + client.get(j).getClientPhone()));
			doc.add(new Paragraph("Report From : " + format.format(dtFr) + "\t To : " + format.format(dtTo), regular));
			doc.add(new Paragraph(" "));
			doc.add(new Paragraph(" "));
			tbl.addCell("Package Id");
			tbl.setHeaderRows(0);
			
			tbl.addCell("Receiver");
			tbl.addCell("Estimated Pickup");
			tbl.addCell("Actual Pickup");
			tbl.addCell("Estimated Delivery");
			tbl.addCell("Actual Delivery");
			List<DeliveryTicket> list = new ArrayList<DeliveryTicket>();
			list = (ArrayList<DeliveryTicket>) s.createQuery("from DeliveryTicket where senderId = ? and transactionDate BETWEEN :start and :end")
					.setParameter(0, client.get(j).getClientId())
					.setParameter("start", dtFr)
					.setParameter("end", dtTo)
					.list();
			for(int i=0; i<list.size(); i++ ){
//				doc.add(new Paragraph(list.get(i).getPackageId() + "\t" + "\t | \t" + list.get(i).getReceiver().getClientName()
//						+ "\t" + "\t | \t" + list.get(i).getTransactionDate()
//						+ "\t" + "\t | \t" + list.get(i).getActualPickup()
//						+ "\t" + "\t | \t" + list.get(i).getActualDelivery()));
				PdfPCell myCell1 = new PdfPCell(new Phrase(String.valueOf(list.get(i).getPackageId()), regular)); 
				tbl.addCell(myCell1);
				PdfPCell myCell2 = new PdfPCell(new Phrase(list.get(i).getReceiver().getClientName(), regular)); 
				tbl.addCell(myCell2);
				PdfPCell myCell6 = new PdfPCell(new Phrase(String.valueOf(list.get(i).getEstimatedPickup()), regular)); 
				tbl.addCell(myCell6);
				PdfPCell myCell3 = new PdfPCell(new Phrase(String.valueOf(list.get(i).getActualPickup()), regular));
				tbl.addCell(myCell3);
				PdfPCell myCell4 = new PdfPCell(new Phrase(String.valueOf(list.get(i).getEstimatedDelivery()), regular));
				tbl.addCell(myCell4);
				PdfPCell myCell5 = new PdfPCell(new Phrase(String.valueOf(list.get(i).getActualDelivery()), regular));
				tbl.addCell(myCell5);
				
			}
			doc.add(tbl);
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
