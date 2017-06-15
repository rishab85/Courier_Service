package courierDM;
// default package
// Generated Jun 9, 2017 12:10:45 AM by Hibernate Tools 4.3.1.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "delivery_rate", catalog = "courier")
public class DeliveryRate implements java.io.Serializable {

	private Integer id;
	private int ratePermile;
	private int bonusOntime;

	public DeliveryRate() {
	}

	public DeliveryRate(int ratePermile, int bonusOntime) {
		this.ratePermile = ratePermile;
		this.bonusOntime = bonusOntime;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "rate_permile", nullable = false)
	public int getRatePermile() {
		return this.ratePermile;
	}

	public void setRatePermile(int ratePermile) {
		this.ratePermile = ratePermile;
	}

	@Column(name = "bonus_ontime", nullable = false)
	public int getBonusOntime() {
		return this.bonusOntime;
	}

	public void setBonusOntime(int bonusOntime) {
		this.bonusOntime = bonusOntime;
	}

}
