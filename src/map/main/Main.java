package map.main;

import java.io.IOException;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;
import map.model.Model;
import map.view.View;

/**
 * The main class
 * @author Rafi Rosenbaum, Orly Sharon, Liz Sror
 */
public class Main{
	/**
	 * Static Members:
	 * static final Logger log
	 * static Model model
	 * static View 	view
	 */
	private static final Logger log =  Logger.getLogger(Main.class);
	public static Model model = null;
	public static View view =null;

	public static void main(String[] args) {    
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {                                           
				model = new Model();
				log.debug("The App Is running");
				try {
					view =new View();
					log.debug("The View Is Loaded");  
				} catch (IOException e) {
					e.printStackTrace();
					log.error("Error - The View Can Not Be Loaded");
				}
			}
		});  
	}
}