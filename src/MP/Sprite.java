package MP;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Sprite extends Cell
{
//	private BufferedImage img;
	private Image img;
	public Sprite(int x, int y)
	{
		super(x, y);
		img = new ImageIcon("res/images/Zoro Right.png").getImage();
		/*
		try
		{
			img = ImageIO.read(new File("res/Images/Zoro right.png"));
		} 
		catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Image loading error");
		}*/
	}
	
	public Image getZoro()
	{
		return img;
	}
	
	public void setImage(Image img)
	{
		this.img = img; 
	}
	
	// nov. 7 edit
	public void moveUp(){
		
	}
	public void moveDown(){
		
	}
	public void moveRight(){
		
	}
	public void moveLeft(){
		
	}
}
