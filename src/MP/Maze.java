package MP;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public abstract class Maze extends JPanel implements ActionListener
{
	protected int rows,
				  cols;
	protected static final String WALL = "#",
								PATH = " ",
								SPRITE = "S",
								GOAL = "G";
	protected String[][] map;
	protected ArrayList<Cell> cells;
	protected String path = "res/maps/Level ";
	static final int MIN_SIZE = 15;
	static final int MAX_SIZE = 30;
	protected int cellSizeX;
	protected int cellSizeY;
	protected Timer t;
	
	public Maze()
	{
		cells = new ArrayList<>();
		t = new Timer(30, this);
		t.start();
	}
	
	public String[][] getMap()
	{
		return map;
	}
	
	public abstract void generateMaze();
	
	public boolean isMapValid()
	{
		int ctrS = 0;
        int ctrG = 0;
        
        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < cols; j++)
            {
                if(map[i][j] == SPRITE)
                {
                    ctrS++;
                }
                else if(map[i][j] == GOAL)
                {
                    ctrG++;
                }
            }
        }
        return ctrS == 1 && ctrG == 1 && rows >= MIN_SIZE && cols >= MIN_SIZE && rows <= MAX_SIZE && cols <= MAX_SIZE;
	}

	public void paint(Graphics g)
	{
		super.paint(g);
		for(Cell c : cells)
		{
			if (c instanceof Sprite)
			{
				g.setColor(c.getColor());
				g.fillRect(c.getX(), c.getY(), cellSizeX,cellSizeY);	// image
				g.drawImage(((Sprite) c).getZoro(), c.getX(), c.getY(),cellSizeX,cellSizeY,null);
			}
			else if (c instanceof Goal)
			{
				g.setColor(c.getColor());
				g.fillRect(c.getX(), c.getY(), cellSizeX,cellSizeY);	// image
				g.drawImage(((Goal) c).getKatana(), c.getX(), c.getY(),cellSizeX,cellSizeY,null);
			}
			else if (c.isWall())
			{
				g.setColor(c.getColor());
				g.drawImage(c.getWallImage(), c.getX(), c.getY(),cellSizeX,cellSizeY,null);
			//	g.fillRect(c.getX(), c.getY(), c.getWidth(), c.getHeight());
			}
			else
			{
				g.setColor(c.getColor());
				g.fillRect(c.getX(), c.getY(),cellSizeX,cellSizeY);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		repaint();
	}
}
