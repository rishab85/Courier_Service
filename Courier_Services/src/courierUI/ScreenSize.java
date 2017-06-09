package courierUI;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ScreenSize {
	Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
	@Id
	@GeneratedValue
	int ScreenWidth = 1200;
	int ScreenHeight = 768;
	int x = ((size.width - ScreenWidth)/2);
	int y = ((size.height - ScreenHeight)/2);
}
