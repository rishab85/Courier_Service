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
@Table(name = "userprofile", catalog = "courier")
public class Userprofile implements java.io.Serializable {

	private Integer userId;
	private String firstName;
	private String userName;
	private String userPassword;
	private String userPhone;
	private String userEmail;
	private int userRole;

	public Userprofile() {
	}

	public Userprofile(String firstName, String userName, String userPassword, String userPhone, String userEmail,
			int userRole) {
		this.firstName = firstName;
		this.userName = userName;
		this.userPassword = userPassword;
		this.userPhone = userPhone;
		this.userEmail = userEmail;
		this.userRole = userRole;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "user_id", unique = true, nullable = false)
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "first_name", nullable = false, length = 100)
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "user_name", nullable = false, length = 100)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "user_password", nullable = false, length = 100)
	public String getUserPassword() {
		return this.userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	@Column(name = "user_phone", nullable = false, length = 100)
	public String getUserPhone() {
		return this.userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	@Column(name = "user_email", nullable = false, length = 100)
	public String getUserEmail() {
		return this.userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	@Column(name = "user_role", nullable = false)
	public int getUserRole() {
		return this.userRole;
	}

	public void setUserRole(int userRole) {
		this.userRole = userRole;
	}

}
