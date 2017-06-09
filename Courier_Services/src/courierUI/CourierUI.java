package courierUI;

import java.awt.EventQueue;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.swing.JFrame;
import java.awt.CardLayout;

@Entity
public class CourierUI {

	private JFrame frame;
	@ManyToOne
	ScreenSize size = new ScreenSize();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CourierUI window = new CourierUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CourierUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(size.x, size.y, size.ScreenWidth, size.ScreenHeight);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		
	}

}
