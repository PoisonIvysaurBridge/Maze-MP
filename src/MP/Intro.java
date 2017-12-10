package MP;
import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Intro extends JFrame
{
	public Intro()
	{
		this.setTitle("");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(550, 268);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		
		ImageIcon picZoro = new ImageIcon("res/images/Zoro.gif");
	    JLabel imgLbl = new JLabel(picZoro);
	    imgLbl.setBounds(0, 0, 550, 268);
	    
		this.add(imgLbl);
		this.setVisible(true);
		try
		{
			TimeUnit.SECONDS.sleep(5);
		}
		catch(Exception e)
		{
			System.out.println("Interrupted Exception");
		}
		
		this.dispose();
	}
	
	public static void main(String[] args)
	{
		Intro app = new Intro();
	}
}
