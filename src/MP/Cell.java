package MP;

import java.awt.*;
import javax.swing.*;

public class Cell 
{
	private int x,
				y;
	public int gCost, // the distance from the start to the current cell
    		   hCost, // the distance from the current cell to the end
    		   fCost; // the sum of gCost and hCost
	private boolean isWall,
					isVisited;
	private Image wallImg;
	private Color color;
	private Cell parentCell;
	
	public Cell(int x, int y)
	{
		this.x = x;
		this.y = y;
		isWall = false;
		isVisited = false;
		color = Color.BLACK;
		try
		{
		//	wallImg = new ImageIcon("res/images/grass wall.png").getImage();
			wallImg = new ImageIcon("res/images/cement vines wall 3.jpg").getImage();
		} 
		catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Cell: Image loading error");
		}
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public boolean isVisited()
	{
		return isVisited;
	}
	
	public void setIsVisited(boolean b)
	{
		isVisited = b;
	}
	
	public int getGCost()
	{
		return gCost;
	}
	
	public int getHCost()
	{
		return hCost;
	}
	
	public int getFCost()
	{
		return fCost;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public Image getWallImage()
	{
		return wallImg;
	}
	
	public Cell getParent()
	{
        return parentCell;
    }
	
    public void setParent(Cell p)
    {
        parentCell = p;
    }
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	public void setY(int y)
	{
		this.y = y;
	}
	
	public void setGCost(int n)
	{
		this.gCost = n;
	}
	
	public void setHCost(int n)
	{
		this.hCost = n;
	}
	
	public void setColor(Color c)
	{
		color = c;
	}
	
	public void setIsWall(boolean b)
	{
		isWall = b;
	}
	
	public boolean isWall()
	{
		return isWall;
	}
	
	public void setCosts(Cell start, Cell end)
	{
        gCost = (Math.abs(start.getX() - this.getX()) + Math.abs(start.getY() - this.getY()))*10;
        hCost = (Math.abs(end.getX() - this.getX()) + Math.abs(end.getY() - this.getY()))*10;
        fCost = gCost + hCost;
    }
	public boolean Equals(Cell c)
	{
		return this.getX() == c.getX() && this.getY() == c.getY();
	}
}
