package courierDM;
// default package
// Generated Jun 9, 2017 2:02:39 PM by Hibernate Tools 4.3.1.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.Collection;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "courier", catalog = "courier")
public class Courier implements java.io.Serializable {

	

	private Integer courierId;
	private String courierName;
	private String courierPhone;
	private int courierStatus;
	private int courierBusy;

	private Collection<DeliveryTicket> ticket;
	
	@OneToMany(mappedBy = "courier")
	public Collection<DeliveryTicket> getTicket() {
		return ticket;
	}
	
	public void setTicket(Collection<DeliveryTicket> ticket) {
		this.ticket = ticket;
	}
	
	public Courier() {
	}

	public Courier(String courierName, String courierPhone, int courierStatus, int courierBusy) {
		this.courierName = courierName;
		this.courierPhone = courierPhone;
		this.courierStatus = courierStatus;
		this.courierBusy = courierBusy;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "courier_id", unique = true, nullable = false)
	public Integer getCourierId() {
		return this.courierId;
	}

	public void setCourierId(Integer courierId) {
		this.courierId = courierId;
	}

	@Column(name = "courier_name", nullable = false, length = 20)
	public String getCourierName() {
		return this.courierName;
	}

	public void setCourierName(String courierName) {
		this.courierName = courierName;
	}

	@Column(name = "courier_phone", nullable = false, length = 20)
	public String getCourierPhone() {
		return this.courierPhone;
	}

	public void setCourierPhone(String courierPhone) {
		this.courierPhone = courierPhone;
	}

	@Column(name = "courier_status", nullable = false)
	public int getCourierStatus() {
		return this.courierStatus;
	}

	public void setCourierStatus(int courierStatus) {
		this.courierStatus = courierStatus;
	}

	@Column(name = "courier_busy", nullable = false)
	public int getCourierBusy() {
		return this.courierBusy;
	}

	public void setCourierBusy(int courierBusy) {
		this.courierBusy = courierBusy;
	}

}
