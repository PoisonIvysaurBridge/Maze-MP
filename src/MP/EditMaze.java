package MP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

public class EditMaze extends Maze implements ActionListener, MouseListener, KeyListener
{

	private Sprite sprite;
	private Goal goal;
	private int cellSizeX;
	private int cellSizeY;
	private Cell highlyt;
	
	public EditMaze(int rows, int cols, /*Sprite s, Goal g,*/ int width, int height)	// width & height is fixed based on the size of JFrame
	{
		this.rows = rows;
		this.cols = cols;
		cellSizeX = width / this.cols;
		cellSizeY = height / this.rows;
		map = new String[rows][cols];
	//	sprite = s;
	//	goal = g;
		generateMaze();
		addMouseListener(this);
		addKeyListener(this);
		setFocusable(true);
	}
	
	public void generateMaze()
	{
		int x = 0, y = 0;//, Sx = sprite.getX(), Sy = sprite.getY(), Gx = goal.getX(), Gy = goal.getY();
	//	Point S = new Point(Sx, Sy);
	//	Point G = new Point(Gx, Gy);
		for(int i = 0; i < rows; i++)
		{
			for(int j = 0; j < cols; j++)
			{
				Cell c = new Cell(x, y);
				c.setIsWall(true);
				cells.add(c);
				map[i][j] = "#";
				x += cellSizeX;
			}
			y += cellSizeY;
			x = 0;
		}
/*		for(int i = 0; i < cells.size(); i++)
		{
			Rectangle rect = new Rectangle(cells.get(i).getX(), cells.get(i).getY(), 30, 30);
			if (rect.contains(S))
			{
				sprite.setX(cells.get(i).getX());
				sprite.setY(cells.get(i).getY());
				cells.add(i, sprite);
				cells.get(i).setColor(Color.YELLOW);
				cells.remove(i+1);
			}
			else if(rect.contains(G))
			{
				goal.setX(cells.get(i).getX());
				goal.setY(cells.get(i).getY());
				cells.add(i, goal);
				cells.get(i).setColor(Color.CYAN);
				cells.remove(i+1);
			}
		}*/
	}
	
	public boolean saveMap()
	{
		if (isMapValid())
		{
			try
	        {
	            BufferedWriter bw = new BufferedWriter(new FileWriter(path + MainDriver.nxtLvl +".txt"));
	            int i;
	            String str = "";
	            
	            bw.write(rows + " " + cols);
	            bw.newLine();
	            
	            for (i = 0; i < cells.size(); i ++)
	            {
	                if (i % cols == 0 && i != 0)
	                    bw.newLine();
	                
	                if (cells.get(i).getColor().equals(Color.BLACK))
	                	str = "#";
	                else if (cells.get(i).getColor().equals(Color.LIGHT_GRAY))
	                	str = " ";
	                else if (cells.get(i).getColor().equals(Color.YELLOW))
	                	str = "S";
	                else if (cells.get(i).getColor().equals(Color.CYAN))
	                	str = "G";
	                	
	                bw.write(str);
	            }
	            bw.flush();
	            bw.close();
	        }
	        catch (Exception e) 
			{
	        	JOptionPane.showMessageDialog(null, "Unable to save file");
				System.out.println("(EditMaze:saveMap): " + e.toString());	
	        }
		return true;
		}
		else
		{
			JOptionPane.showMessageDialog(null, "The loaded map is invalid");
			return false;
		}
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
			//	g.drawImage(((Sprite) c).getZoro(), c.getX(), c.getY(),cellSizeX,cellSizeY,null);
			}
			else if (c instanceof Goal)
			{
				g.setColor(c.getColor());
				g.fillRect(c.getX(), c.getY(), cellSizeX,cellSizeY);	// image
			//	g.drawImage(((Goal) c).getKatana(), c.getX(), c.getY(),cellSizeX,cellSizeY,null);
			}
			else if (c.isWall())
			{
				g.setColor(c.getColor());
				g.fillRect(c.getX(), c.getY(), cellSizeX,cellSizeY);
			//	g.drawImage(c.getWallImage(), c.getX(), c.getY(),cellSizeX,cellSizeY,null);
			}
			else
			{
				g.setColor(c.getColor());
				g.fillRect(c.getX(), c.getY(), cellSizeX,cellSizeY);
			}
		}
		highlyt = new Cell(cells.get(0).getX(), cells.get(0).getY());
	//	g.setColor(Color.MAGENTA);
	// 	g.fillRect(highlyt.getX(), highlyt.getY(), cellSizeX,cellSizeY);
	}
	
	@Override
	public boolean isMapValid() 
	{
		int ctrS = 0;
        int ctrG = 0;
        
        for(Cell c : cells){
            if(c.getColor().equals(Color.YELLOW))
            	ctrS++;
            else if(c.getColor().equals(Color.CYAN))
            	ctrG++;
        }
        return ctrS == 1 && ctrG == 1;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		for (int i = 0; i < cells.size(); i ++)
        {
            Rectangle rect = new Rectangle(cells.get(i).getX(), cells.get(i).getY(), cellSizeX, cellSizeY);
			if (rect.contains(e.getPoint()))
            {
				int x = cells.get(i).getX();
            	int y = cells.get(i).getY();	
				if (cells.get(i).getColor().equals(Color.BLACK))
				{
					Cell c = new Cell(x, y);
					cells.get(i).setIsWall(false);
					cells.set(i, c);
					System.out.println("pressed black square");
					cells.get(i).setColor(Color.LIGHT_GRAY);
				}
                    
                else if (cells.get(i).getColor().equals(Color.LIGHT_GRAY))
                {	
                	Cell s = new Sprite(x, y);
                	cells.set(i, s);
                	cells.get(i).setColor(Color.YELLOW);
                	System.out.println("pressed path");
                }
                else if (cells.get(i).getColor().equals(Color.YELLOW))
                {	
                	Cell g = new Goal(x, y);
                	cells.set(i, g);
                	cells.get(i).setColor(Color.CYAN);
                	System.out.println("pressed sprite");
                }
                else if (cells.get(i).getColor().equals(Color.CYAN))
                {
                	Cell c = new Cell(x, y);
					cells.get(i).setIsWall(true);
					cells.set(i, c);
                	cells.get(i).setColor(Color.BLACK);
                	System.out.println("pressed goal");
                }

			}
        }
	}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		int i = 0;
		while(i < cells.size() && !cells.get(i).getColor().equals(Color.YELLOW))
		{
			i++;
		}
		int c = e.getKeyCode(); //gets the code of the key pressed
		if(c == KeyEvent.VK_LEFT)
		{
			if (i-1 > 0)
			{
				i--;
			}
			System.out.println("left");
		}
		
		else if(c == KeyEvent.VK_RIGHT)
		{
			if (i+1 < cells.size())
			{
				i++;
			}
		}
		
		else if(c == KeyEvent.VK_UP)
		{
			if(i - cols > 0)
			{
				i -= cols;
			}
		}
		
		else if(c == KeyEvent.VK_DOWN)
		{
			if(i + cols < cells.size())
			{
				i += cols;
			}
		}
		System.out.println(i);
		highlyt.setX(cells.get(i).getX());
		highlyt.setY(cells.get(i).getY());
		
		if(c == KeyEvent.VK_ENTER)
		{
			if (cells.get(i).getColor().equals(Color.BLACK))
                cells.get(i).setColor(Color.LIGHT_GRAY);
            else if (cells.get(i).getColor().equals(Color.LIGHT_GRAY))
            	cells.get(i).setColor(Color.YELLOW);
            else if (cells.get(i).getColor().equals(Color.YELLOW))
            	cells.get(i).setColor(Color.CYAN);
            else if (cells.get(i).getColor().equals(Color.CYAN))
            	cells.get(i).setColor(Color.BLACK);
		}
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {}
	
}
