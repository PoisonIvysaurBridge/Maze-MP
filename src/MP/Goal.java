package MP;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Goal extends Cell
{
	private Image img;
	public Goal(int x, int y)
	{
		super(x, y);
		img = new ImageIcon("res/images/Katana.png").getImage();
	}
	
	public Image getKatana()
	{
		return img;
	}
}
