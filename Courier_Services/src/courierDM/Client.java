package courierDM;
// default package
// Generated Jun 9, 2017 12:10:45 AM by Hibernate Tools 4.3.1.Final

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "client", catalog = "courier")
public class Client implements java.io.Serializable {

	private Integer clientId;
	private String clientName;
	private String clientStreet;
	private String clientAve;
	private String clientPhone;
	private String clientEmail;

	
	private Collection<DeliveryTicket> ticket;
	
	@OneToMany(mappedBy = "sender")
	public Collection<DeliveryTicket> getTicket() {
		return ticket;
	}

	public void setTicket(Collection<DeliveryTicket> ticket) {
		this.ticket = ticket;
	}

	public Client() {
	}

	public Client(String clientName, String clientStreet, String clientAve, String clientPhone, String clientEmail) {
		this.clientName = clientName;
		this.clientStreet = clientStreet;
		this.clientAve = clientAve;
		this.clientPhone = clientPhone;
		this.clientEmail = clientEmail;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "client_id", unique = true, nullable = false)
	public Integer getClientId() {
		return this.clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	@Column(name = "client_name", nullable = false, length = 100)
	public String getClientName() {
		return this.clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	@Column(name = "client_street", nullable = false, length = 10)
	public String getClientStreet() {
		return this.clientStreet;
	}

	public void setClientStreet(String clientStreet) {
		this.clientStreet = clientStreet;
	}

	@Column(name = "client_ave", nullable = false, length = 10)
	public String getClientAve() {
		return this.clientAve;
	}

	public void setClientAve(String clientAve) {
		this.clientAve = clientAve;
	}

	@Column(name = "client_phone", nullable = false, length = 20)
	public String getClientPhone() {
		return this.clientPhone;
	}

	public void setClientPhone(String clientPhone) {
		this.clientPhone = clientPhone;
	}

	@Column(name = "client_email", nullable = false, length = 20)
	public String getClientEmail() {
		return this.clientEmail;
	}

	public void setClientEmail(String clientEmail) {
		this.clientEmail = clientEmail;
	}

	

}
