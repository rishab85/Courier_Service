package courierDM;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "intersection", catalog = "courier")

public class Intersection implements java.io.Serializable{
	private Integer id;
	private String streets;
	private String ave;
	private String direction;
	private String Actual_Map_Address;
	
	
public Intersection ()
{}

public Intersection(int id, String streets, String ave, String direction, String Actual_Map_Address){
this.id=id;
this.streets=streets;
this.ave=ave;
this.direction=direction;
this.Actual_Map_Address=Actual_Map_Address;
}

@Id
@GeneratedValue(strategy = IDENTITY)

@Column(name = "id", unique = true, nullable = false)
public Integer getId() {
	return this.id;
}

public void setid(Integer id) {
	this.id = id;
}



@Column(name = "streets", nullable = false, length = 100)
public String getstreets() {
	return this.streets;
}

public void setstreets(String streets) {
	this.streets = streets;
}



@Column(name = "ave", nullable = false, length = 100)
public String getave() {
	return this.ave;
}

public void setave(String ave) {
	this.ave = ave;
}



@Column(name = "direction", nullable = false, length = 100)
public String getdirection() {
	return this.direction;
}

public void setdirection(String direction) {
	this.direction = direction;
}


@Column(name = "Actual_Map_Address", nullable = false, length = 100)
public String getActual_Map_Address() {
	return this.Actual_Map_Address;
}

public void setActual_Map_Address(String Actual_Map_Address) {
	this.Actual_Map_Address = Actual_Map_Address;
}



}
