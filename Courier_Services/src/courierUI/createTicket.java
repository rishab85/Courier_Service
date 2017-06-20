package courierUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;
import java.util.Random;

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
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import courierDM.Client;
import courierDM.DeliveryRate;
import courierDM.DeliveryTicket;
import courierDM.Userprofile;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.SystemColor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
public class createTicket extends JPanel{

	private JFrame frame = new JFrame();
	private JTable table;
	private JPanel createTicket;
	private JTextField rate_permile;
	private JTextField bonus_ontime;
	private JLabel lbl_error;
	private JTextField senderID;
	private JTextField senderName;
	private JTextField senderAddress;
	private JTextField receiverID;
	private JTextField receiverName;
	private JTextField receiverAddress;
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	private JTextField packageID;
	private JTextField pickup_time;
	private JTextField delivery_time;
	private JTextField miles;
	private JTextField cost;
	private Boolean ready = false;
	private JButton findSender;
	private JButton findReceiver;
	private JButton btnCalculate;
	
	private DeliveryTicket dTicket = new DeliveryTicket();
	 private static final DateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	 private static final DateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
	 private int check = 0;
	 private JTextField actualPickup;
	 private JTextField actualDelivery;
	 private JTextField courier;
	public createTicket(JFrame frame, DeliveryTicket dTicket, String action) {
		this.frame = frame;
		this.dTicket = dTicket;
		createTicket = new JPanel();
		frame.getContentPane().add(createTicket, "name_2137049467741814");
		createTicket.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Delivery Ticket");
		lblNewLabel.setForeground(Color.LIGHT_GRAY);
		lblNewLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
		lblNewLabel.setBounds(48, 13, 209, 37);
		createTicket.add(lblNewLabel);
		
		senderID = new JTextField();
		senderID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				lbl_error.setText("");
				if(!isNum(String.valueOf(e.getKeyChar()))){
					lbl_error.setText("Only numbers allowed in this field");
					e.consume();
				}
			}
		});
		senderID.setBounds(163, 151, 86, 30);
		createTicket.add(senderID);
		senderID.setColumns(10);
		
		JLabel lblClientName = new JLabel("Sender");
		lblClientName.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblClientName.setBounds(54, 112, 78, 16);
		createTicket.add(lblClientName);
		
		JLabel lblPhone = new JLabel("Sender ID");
		lblPhone.setBounds(54, 158, 88, 16);
		createTicket.add(lblPhone);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(54, 130, 290, 16);
		createTicket.add(separator);
		
		UtilDateModel model = new UtilDateModel();
		//model.setDate(20,04,2014);
		// Need this...
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		
		lbl_error = new JLabel("");
		lbl_error.setFont(new Font("Malgun Gothic", Font.BOLD | Font.ITALIC, 14));
		lbl_error.setForeground(Color.RED);
		lbl_error.setBounds(48, 63, 375, 16);
		createTicket.add(lbl_error);
		
		if(action!="detail"){
		findSender = new JButton("Find");
		findSender.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(senderID.getText().isEmpty() || !isNum(senderID.getText())){
					lbl_error.setText("Not allowed");
				}else{
					lbl_error.setText("");
					find(Integer.parseInt(senderID.getText()),"Sender");
				}
			}
		});
		findSender.setBounds(247, 151, 97, 30);
		createTicket.add(findSender);
		}
		JLabel lblCustomerName = new JLabel("Sender Name");
		lblCustomerName.setBounds(54, 207, 97, 16);
		createTicket.add(lblCustomerName);
		
		senderName = new JTextField();
		senderName.setEditable(false);
		senderName.setBounds(163, 199, 183, 32);
		createTicket.add(senderName);
		senderName.setColumns(10);
		
		JLabel lblPickupAddress = new JLabel("Pickup Address");
		lblPickupAddress.setBounds(54, 254, 105, 16);
		createTicket.add(lblPickupAddress);
		
		senderAddress = new JTextField();
		senderAddress.setEditable(false);
		senderAddress.setBounds(163, 247, 181, 30);
		createTicket.add(senderAddress);
		senderAddress.setColumns(10);
		
		JLabel lblReceiver = new JLabel("Receiver");
		lblReceiver.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblReceiver.setBounds(54, 314, 78, 16);
		createTicket.add(lblReceiver);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(54, 332, 290, 16);
		createTicket.add(separator_1);
		
		JLabel lblReceiverId = new JLabel("Receiver ID");
		lblReceiverId.setBounds(52, 368, 88, 16);
		createTicket.add(lblReceiverId);
		
		receiverID = new JTextField();
		receiverID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				lbl_error.setText("");
				if(!isNum(String.valueOf(e.getKeyChar()))){
					lbl_error.setText("Only numbers allowed in this field");
					e.consume();
				}
			}
		});
		receiverID.setColumns(10);
		receiverID.setBounds(161, 361, 86, 30);
		createTicket.add(receiverID);
		
		if(action!="detail"){
		findReceiver = new JButton("Find");
		findReceiver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(receiverID.getText().isEmpty() || !isNum(receiverID.getText())){
					lbl_error.setText("Not allowed");
				}else if(receiverID.getText().equals(senderID.getText())){
					JOptionPane.showMessageDialog(frame, "Sender and receiver can't be the same");
				}else{
					lbl_error.setText("");
					find(Integer.parseInt(receiverID.getText()),"Receiver");
				}
			}
		});
		findReceiver.setBounds(245, 361, 97, 30);
		createTicket.add(findReceiver);
		}
		receiverName = new JTextField();
		receiverName.setEditable(false);
		receiverName.setColumns(10);
		receiverName.setBounds(161, 409, 183, 32);
		createTicket.add(receiverName);
		
		JLabel lblReceiverName = new JLabel("Receiver Name");
		lblReceiverName.setBounds(52, 417, 97, 16);
		createTicket.add(lblReceiverName);
		
		JLabel lblDeliveryAddress = new JLabel("Delivery Address");
		lblDeliveryAddress.setBounds(52, 464, 99, 16);
		createTicket.add(lblDeliveryAddress);
		
		receiverAddress = new JTextField();
		receiverAddress.setEditable(false);
		receiverAddress.setColumns(10);
		receiverAddress.setBounds(161, 457, 181, 30);
		createTicket.add(receiverAddress);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(54, 557, 724, 8);
		createTicket.add(separator_2);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.info);
		panel.setBounds(417, 96, 361, 448);
		createTicket.add(panel);
		panel.setLayout(null);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(157, 12, 0, 2);
		panel.add(separator_3);
		
		JLabel lblTicketInfo = new JLabel("Ticket Info");
		lblTicketInfo.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTicketInfo.setBounds(14, 7, 78, 16);
		panel.add(lblTicketInfo);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(0, 30, 361, 16);
		panel.add(separator_4);
		
		JLabel lblPackageId = new JLabel("Package ID");
		lblPackageId.setBounds(14, 56, 97, 16);
		panel.add(lblPackageId);
		
		packageID = new JTextField();
		packageID.setEditable(false);
		packageID.setColumns(10);
		packageID.setBounds(143, 48, 183, 32);
		panel.add(packageID);
		
		JLabel lblEstimatedPickup = new JLabel("Estimated Pickup");
		lblEstimatedPickup.setBounds(14, 103, 117, 16);
		panel.add(lblEstimatedPickup);
		
		pickup_time = new JTextField();
		pickup_time.setEditable(false);
		pickup_time.setColumns(10);
		pickup_time.setBounds(143, 95, 183, 32);
		panel.add(pickup_time);
		
		JLabel lblEstimatedDelivery = new JLabel("Estimated Delivery");
		lblEstimatedDelivery.setBounds(14, 148, 117, 16);
		panel.add(lblEstimatedDelivery);
		
		delivery_time = new JTextField();
		delivery_time.setEditable(false);
		delivery_time.setColumns(10);
		delivery_time.setBounds(143, 140, 183, 32);
		panel.add(delivery_time);
		
		JLabel lblEstimatedMiles = new JLabel("Estimated Miles");
		lblEstimatedMiles.setBounds(14, 193, 97, 16);
		panel.add(lblEstimatedMiles);
		
		miles = new JTextField();
		miles.setEditable(false);
		miles.setColumns(10);
		miles.setBounds(143, 185, 183, 32);
		panel.add(miles);
		
		JLabel lblEstimatedMiles_1 = new JLabel("Estimated Cost");
		lblEstimatedMiles_1.setBounds(14, 238, 97, 16);
		panel.add(lblEstimatedMiles_1);
		
		cost = new JTextField();
		cost.setEditable(false);
		cost.setColumns(10);
		cost.setBounds(143, 230, 183, 32);
		panel.add(cost);
		
		if(action == "detail"){
		JLabel lblActualPickup = new JLabel("Actual Pickup");
		lblActualPickup.setBounds(14, 283, 97, 16);
		panel.add(lblActualPickup);
		
		actualPickup = new JTextField();
		actualPickup.setEditable(false);
		actualPickup.setColumns(10);
		actualPickup.setBounds(143, 275, 183, 32);
		panel.add(actualPickup);
		
		JLabel lblActualDelivery = new JLabel("Actual Delivery");
		lblActualDelivery.setBounds(14, 328, 97, 16);
		panel.add(lblActualDelivery);
		
		actualDelivery = new JTextField();
		actualDelivery.setEditable(false);
		actualDelivery.setColumns(10);
		actualDelivery.setBounds(143, 320, 183, 32);
		panel.add(actualDelivery);
		
		JLabel lblCourier = new JLabel("Courier");
		lblCourier.setBounds(14, 375, 97, 16);
		panel.add(lblCourier);
		
		courier = new JTextField();
		courier.setEditable(false);
		courier.setColumns(10);
		courier.setBounds(143, 367, 183, 32);
		panel.add(courier);
		}
		
		
		if(action!="detail"){
		btnCalculate = new JButton("Calculate");
		btnCalculate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				calculate();
			}
		});
		btnCalculate.setBounds(229, 282, 97, 25);
		panel.add(btnCalculate);
		}
//		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());
//		datePicker.setBounds(123, 137, 183, 25);
//		panel.add(datePicker);
		
		if(action == "add"){
		JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if((buttonGroup_1.getSelection()==null)){
					lbl_error.setText("Select Bill to sender or receiver");
				}else{
					lbl_error.setText("");
					try {
						create();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		});
		btnCreate.setBounds(681, 568, 97, 25);
		createTicket.add(btnCreate);
		}else if(action=="update"){
		JButton btnUpdate = new JButton("update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if((buttonGroup_1.getSelection()==null)){
					lbl_error.setText("Select Bill to sender or receiver");
				}else{
					lbl_error.setText("");
					try {
						update();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		});
		btnUpdate.setBounds(681, 568, 97, 25);
		createTicket.add(btnUpdate);
		}
		
		if(action!="detail"){
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				frame.getContentPane().add(new markTicket(frame));
				frame.getContentPane().repaint();
				frame.getContentPane().validate();
			}
		});
		btnCancel.setBounds(572, 568, 97, 25);
		createTicket.add(btnCancel);
		}
		
		JRadioButton billSender = new JRadioButton("Bill to Sender");
		buttonGroup_1.add(billSender);
		billSender.setBounds(48, 523, 127, 25);
		createTicket.add(billSender);
		billSender.setActionCommand("sender");
		
		JRadioButton billReceiver = new JRadioButton("Bill to Reciever");
		buttonGroup_1.add(billReceiver);
		billReceiver.setBounds(176, 523, 127, 25);
		createTicket.add(billReceiver);
		billReceiver.setActionCommand("receiver");
		
		
		
		
		if(action=="update"){
			senderID.setText(String.valueOf(dTicket.getSenderId()));
			senderName.setText(dTicket.getSender().getClientName());
			senderAddress.setText(dTicket.getSender().getClientStreet() + " Street" + dTicket.getSender().getClientAve());
			receiverID.setText(String.valueOf(dTicket.getReceiverId()));
			receiverName.setText(dTicket.getReceiver().getClientName());
			receiverAddress.setText(dTicket.getReceiver().getClientStreet() + " Street" + dTicket.getSender().getClientAve());;
			
			if(dTicket.getBilllTo()==dTicket.getSenderId()){
				billSender.setSelected(true);
			}else{
				billReceiver.setSelected(true);
			}
			packageID.setText(String.valueOf(dTicket.getPackageId()));
			pickup_time.setText(String.valueOf(dTicket.getEstimatedPickup()));
			delivery_time.setText(String.valueOf(dTicket.getEstimatedDelivery()));
			miles.setText(String.valueOf(dTicket.getEstimatedMiles()));
			cost.setText(String.valueOf(dTicket.getEstimatedCost()));
			
			findSender.doClick();
			findReceiver.doClick();
			btnCalculate.doClick();
		}
		
		if(action=="detail"){
			JButton btnBack = new JButton("BACK");
			btnBack.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.getContentPane().removeAll();
					frame.getContentPane().add(new deliveredTicket(frame));
					frame.getContentPane().repaint();
					frame.getContentPane().validate();
				}
			});
			btnBack.setFont(new Font("Tahoma", Font.PLAIN, 14));
			btnBack.setBounds(681, 570, 97, 40);
			createTicket.add(btnBack);
			
			senderID.setText(String.valueOf(dTicket.getSenderId()));
			senderName.setText(dTicket.getSender().getClientName());
			senderAddress.setText(dTicket.getSender().getClientStreet() + " Street" + dTicket.getSender().getClientAve());
			receiverID.setText(String.valueOf(dTicket.getReceiverId()));
			receiverName.setText(dTicket.getReceiver().getClientName());
			receiverAddress.setText(dTicket.getReceiver().getClientStreet() + " Street" + dTicket.getSender().getClientAve());;
			
			if(dTicket.getBilllTo()==dTicket.getSenderId()){
				billSender.setSelected(true);
			}else{
				billReceiver.setSelected(true);
			}
			packageID.setText(String.valueOf(dTicket.getPackageId()));
			pickup_time.setText(String.valueOf(dTicket.getEstimatedPickup()));
			delivery_time.setText(String.valueOf(dTicket.getEstimatedDelivery()));
			miles.setText(String.valueOf(dTicket.getEstimatedMiles()));
			cost.setText(String.valueOf(dTicket.getEstimatedCost()));
			actualPickup.setText(String.valueOf(dTicket.getActualDelivery()));
			actualDelivery.setText(String.valueOf(dTicket.getActualDelivery()));
			courier.setText(dTicket.getCourier().getCourierName());
		}
		
		
	}
	public JFrame getFrame() {
		return frame;
	}
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
	
	
	public void updateClient(int clientId) throws Exception{
		/* 
		String[] pass = new String[10];
		if(senderID.getText().isEmpty()){
			lbl_error.setText("Please enter the client name");
			senderID.requestFocus();
		}else if(street.getText().isEmpty()){
			lbl_error.setText("Please enter the Street Address");
			street.requestFocus();
		}else if(avenue.getText().isEmpty()){
			lbl_error.setText("Please enter the Avenue Address");
			avenue.requestFocus();
		}
		
		else if(!isNum(phone.getText())){
			lbl_error.setText("Entry not valid only numbers are allowed");
			phone.requestFocus();
		}
		
		else if(phone.getText().isEmpty()){
			lbl_error.setText("Please enter the phone number");
			phone.setText("");
			phone.requestFocus();
		}else if(email.getText().isEmpty()){
			lbl_error.setText("Field Cannot be empty");
			email.requestFocus();
		}else{
		lbl_error.setText("");
		Client editCl = new Client();

		editCl.setClientId(clientId);
		editCl.setClientName(senderID.getText());
		editCl.setClientStreet(street.getText());
		editCl.setClientAve(avenue.getText());
		editCl.setClientPhone(phone.getText());
		editCl.setClientEmail(email.getText());
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		SessionFactory sf = cfg.buildSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		s.update(editCl);
		
		s.flush();
		tx.commit();
		s.close();
		
		frame.getContentPane().removeAll();
		frame.getContentPane().add(new clientUI(frame));
		frame.getContentPane().repaint();
		frame.getContentPane().validate();
		}
		*/
	}
	
	public void find(int clientID, String role){
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		SessionFactory sf = cfg.buildSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		ArrayList<Client> sender = new ArrayList<Client>();
		ArrayList<Client> receiver = new ArrayList<Client>();
		sender.clear();
		receiver.clear();
		if(role=="Sender"){
		sender = (ArrayList<Client>) s.createQuery("From Client where clientId = ?")
				.setParameter(0, clientID)
				.list();
		}else{
			receiver = (ArrayList<Client>) s.createQuery("From Client where clientId = ?")
					.setParameter(0, clientID)
					.list();
		}
		s.flush();
		tx.commit();
		s.close();
		if(role == "Sender"){
			senderName.setText("");
			senderAddress.setText("");
			if(sender.size()>0){
				senderName.setText(sender.get(0).getClientName());
				senderAddress.setText(sender.get(0).getClientStreet() + " Street " + " " +sender.get(0).getClientAve());
				
				if(check<2){
					check = check+1;
				}
			}else{
				JOptionPane.showMessageDialog(frame, "No record found with this ID");
			}
		}else{
			receiverName.setText("");
			receiverAddress.setText("");
			if(receiver.size()>0){
				receiverName.setText(receiver.get(0).getClientName());
				receiverAddress.setText(receiver.get(0).getClientStreet() + " Street " + " " +receiver.get(0).getClientAve());
				
				if(check<2){
					check = check+1;
				}
			}else{
				JOptionPane.showMessageDialog(frame, "No record found with this ID");
			}
		}
	}
	
	public void calculate(){
		if(check==2){
			Random rand = new Random();

			int  n = rand.nextInt(900) + 100;
			
			System.out.println(n);
			
			LocalDate localDate = LocalDate.now();
	        String date = (String)DateTimeFormatter.ofPattern("yyy/MM/dd").format(localDate);
	        date = date.replaceAll("/", "");
	        date = date.substring(3, 6);
			String pkgID = date = date + String.valueOf(n);
			packageID.setText(pkgID);
			
			 Calendar cal = Calendar.getInstance();
			pickup_time.setText(sdf.format(cal.getTime()));
			delivery_time.setText(sdf.format(cal.getTime()));
			miles.setText("5");
			cost.setText("20");
			
			ready = true;
			}else{
				JOptionPane.showMessageDialog(frame, "Please provide the sender and receiver information");
			}
	}

	
	public Boolean isNum(String str){
		return str.matches("-?\\d+(\\.\\d+)?");
	}
	
	public void create() throws ParseException{
		if(!ready){
			JOptionPane.showMessageDialog(frame, "Please complete all fields on the form.");
		}else{
			DeliveryTicket ticket = new DeliveryTicket();
			
			ticket.setSenderId(Integer.parseInt(senderID.getText()));
			ticket.setReceiverId(Integer.parseInt(receiverID.getText()));
			ticket.setCourierId(4);
			ticket.setEstimatedPickup(sdf.parse(pickup_time.getText()));
			ticket.setEstimatedDelivery(sdf.parse(delivery_time.getText()));
			ticket.setActualDelivery(sdf.parse("00:00:00"));
			ticket.setActualPickup(sdf.parse("00:00:00"));
			ticket.setActualDelivery(sdf.parse("00:00:00"));
			ticket.setEstimatedMiles(Integer.parseInt(miles.getText()));
			ticket.setEstimatedCost(Integer.parseInt(cost.getText()));
			if(buttonGroup_1.getSelection().getActionCommand()=="sender"){
				ticket.setBilllTo(Integer.parseInt(senderID.getText()));
			}else{
				ticket.setBilllTo(Integer.parseInt(receiverID.getText()));
			}
			
			Calendar cal = Calendar.getInstance();
			ticket.setTransactionDate(cal.getTime());
			ticket.setPackageId(Integer.parseInt(packageID.getText()));
			ticket.setDriverBonus(0);
			
			Configuration cfg = new Configuration();
			cfg.configure("hibernate.cfg.xml");
			SessionFactory sf = cfg.buildSessionFactory();
			Session s = sf.openSession();
			Transaction tx = s.beginTransaction();
			s.save(ticket);
			
			s.flush();
			tx.commit();
			s.close();
			
			frame.getContentPane().removeAll();
			frame.getContentPane().add(new markTicket(frame));
			frame.getContentPane().repaint();
			frame.getContentPane().validate();
		}
	}
	
	public void update() throws ParseException{

		if(!ready){
			JOptionPane.showMessageDialog(frame, "Please complete all fields on the form.");
		}else{

			
			dTicket.setSenderId(Integer.parseInt(senderID.getText()));
			dTicket.setReceiverId(Integer.parseInt(receiverID.getText()));
			dTicket.setEstimatedPickup(sdf.parse(pickup_time.getText()));
			dTicket.setEstimatedDelivery(sdf.parse(delivery_time.getText()));
			dTicket.setEstimatedMiles(Integer.parseInt(miles.getText()));
			dTicket.setEstimatedCost(Integer.parseInt(cost.getText()));
			if(buttonGroup_1.getSelection().getActionCommand()=="sender"){
				dTicket.setBilllTo(Integer.parseInt(senderID.getText()));
			}else{
				dTicket.setBilllTo(Integer.parseInt(receiverID.getText()));
			}
			
			
			Configuration cfg = new Configuration();
			cfg.configure("hibernate.cfg.xml");
			SessionFactory sf = cfg.buildSessionFactory();
			Session s = sf.openSession();
			Transaction tx = s.beginTransaction();
			s.update(dTicket);
			
			s.flush();
			tx.commit();
			s.close();
			
			frame.getContentPane().removeAll();
			frame.getContentPane().add(new markTicket(frame));
			frame.getContentPane().repaint();
			frame.getContentPane().validate();
		}
	}
}
