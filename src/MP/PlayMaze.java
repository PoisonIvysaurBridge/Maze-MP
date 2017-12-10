package MP;
 
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;
 
public class PlayMaze extends Maze implements ActionListener, KeyListener
{
     
    int x = 0, velX = 0;
    int y = 0, velY = 0;
    private ArrayList<Cell> path;
    private Cell[][] arrCells;
    private Timer t2;
    int nRects;
    private Image img;
    Cell start,
    	end;
    Stack<Cell> stack;
    boolean isPathFound;
    Cell curCell;
    ArrayList<Cell> neighbors;
    int neighIndex;
     
    public PlayMaze(String filename, int width, int height)
    {
        loadMap(filename);
        cellSizeX = width / this.cols;
        cellSizeY = height / this.rows;
        arrCells = new Cell[rows][cols];
        
        if (isMapValid())
            generateMaze();
        else
        {
        	JOptionPane.showMessageDialog(null, "The loaded map is invalid");
        }
        
        addKeyListener(this);
        setFocusable(true);
        path = new ArrayList<>();
        t2 = new Timer(100, this);
        nRects = 0;
		img = new ImageIcon("res/images/Zoro Right.png").getImage();
		
		
		start = null;
		end = null;
		stack = new Stack<>();
		isPathFound = false;
	    neighIndex = 0;
	    neighbors = new ArrayList<>();
    }
    
    public void paint(Graphics g)
	{
		super.paint(g);
		if(PlayBoard.btnHint.isEnabled() == true)
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
		
		if(start!=null)
			g.drawImage(((Sprite) start).getZoro(), x, y,cellSizeX,cellSizeY,null);
		
		if(PlayBoard.btnHint.isEnabled() == false)
		{
			if(!isPathFound && !stack.isEmpty())
			{
				curCell = stack.pop();
	            neighbors = getNeighborCells(curCell);
	        }
		}
		
		
		g.setColor(Color.MAGENTA);
		for(int i = 0; i < nRects; i++)
		{
			int x1 = path.get(i).getX();//p.getVertexAt(nLines).getX();
			int y1 = path.get(i).getY();//p.getVertexAt(nLines).getY();
			int x2 = path.get(i+1).getX();//p.getVertexAt(nLines+1).getX();
			int y2 = path.get(i+1).getY();//p.getVertexAt(nLines+1).getY();
		//	g.fillRect(x1+cellSizeX/2, y1+cellSizeY/2, 5, 5);
			g.drawLine(x1+cellSizeX/2, y1+cellSizeY/2, x2+cellSizeX/2, y2+cellSizeY/2);
		//	g.drawImage(img,x1,x2,y1,y2, null);	
		//	g.drawImage(img,x1, y1, x2+cellSizeX/2, y2+cellSizeY/2, null);	// EPIC
		}
	}
     
    public void BFS()	// NOT USED IN PROGRAM
    {
        Cell start = null;
        Cell end = null;
        for(int i = 0; i < arrCells.length; i++)
        {
            for(int j = 0; j < arrCells[i].length; j++)
            {
                if (arrCells[i][j] instanceof Sprite)
                {
                    start = arrCells[i][j];
                    System.out.println("found start");
                }
                else if (arrCells[i][j] instanceof Goal)
                {
                    end = arrCells[i][j];
                    System.out.println("found end");
                }
            }
        }
        Queue<Cell> openSet = new LinkedList();
        ArrayList<Cell> closedSet = new ArrayList<>();
        openSet.add(start);
        closedSet.add(start);
         
        boolean isPathFound = false;
        while(!isPathFound && !openSet.isEmpty())
        {
            Cell curCell = (Cell)openSet.remove();
            System.out.println(curCell); if(curCell instanceof Sprite) System.out.println("ZORO start ");
        /*  if (curCell instanceof Goal || curCell.equals(end) || curCell.getColor().equals(Color.CYAN))
            {
                isPathFound = true;
                backTrack(start, end);
                break;
            }
        */ 
            ArrayList<Cell> neighbors = getNeighborCells(curCell);
            if(neighbors.size() > 0)
            {
                System.out.println("neighbors -- "+neighbors.size());
                for(Cell neighbor: neighbors)
                {
                    neighbor.setParent(curCell);
                    if(neighbor.isWall() == false || closedSet.contains(neighbor))
                    {
                        if (curCell instanceof Goal || curCell.equals(end) || curCell.getColor().equals(Color.CYAN))
                        {
                            isPathFound = true;
                            backTrack(start, end);
                            break;
                        }
                        openSet.add(neighbor);
                        closedSet.add(curCell);
                    }
                     
                }
            }
            /*
            for(Cell neighbor: neighbors)
            {
                neighbor.setParent(curCell);
                if(neighbor.isWall() || !closedSet.contains(neighbor))
                {
                    if(neighbor.equals(end))
                    {
                        backTrack(start, end);
                        isPathFound = true;
                    }
                    openSet.add(neighbor);
                    closedSet.add(neighbor);
                }
                else
                {
                    openSet.remove();
                }
            }
            */
 
        }
        if(path.size() > 0)
            for(int i = 0; i < path.size(); i++)
            {
                System.out.println("X: "+path.get(i).getX()+", Y: "+path.get(i).getY());
            }
         
        if(!isPathFound)
        {
            JOptionPane.showMessageDialog(null, "No possible paths to traverse:<");
        }
         
    }
     
    public void generateMaze()
    {
        int x = 0, y = 0;
        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < cols; j++)
            {
                if (map[i][j].equals(PATH))
                {
                    Cell c = new Cell(x, y);
                    c.setColor(Color.LIGHT_GRAY);
                    cells.add(c);
                    arrCells[i][j] = c;
                }
                else if (map[i][j].equals(WALL))
                {
                    Cell c = new Cell(x, y);
                    c.setIsWall(true);
                    cells.add(c);
                    arrCells[i][j] = c;
                }
                else if (map[i][j].equals(SPRITE))
                {
                    Sprite s = new Sprite(x, y);
                    s.setColor(Color.LIGHT_GRAY);
                    cells.add(s);
                    arrCells[i][j] = s;
                }
                else if (map[i][j].equals(GOAL))
                {
                    Goal g = new Goal(x, y);
                    g.setColor(Color.CYAN);
                    cells.add(g);
                    arrCells[i][j] = g;
                }
                x += cellSizeX;
            }
            y += cellSizeY;
            x = 0;
        }
    }
     
    public void loadMap(String filename)
    {
        String mapCode = "";
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            while ((line  = br.readLine())!= null) 
            {
                mapCode += line+"N";
            }
            br.close();
        }
         
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Unable to load file");
            System.out.println("(PlayMaze:loadMap): " + e.toString());  
        }
        try{
        rows = Integer.parseInt(mapCode.substring(0, mapCode.indexOf(" ")));    // first 2 digits
        cols = Integer.parseInt(mapCode.substring(mapCode.indexOf(" ")+1, mapCode.indexOf("N")));   // next 2 digits after space
        mapCode = mapCode.substring(mapCode.indexOf("N")+1);    
         
        map = new String[rows][cols];
        for(int y = 0; y < rows; y++)
            for(int x = 0; x < cols; x++)
                map[y][x] = " ";    // initialize everything to an empty cell
         
        int x = 0, y = 0;
        boolean isNewLine = false;
        int ctr = 0;
         
        for (int i = 0; i < mapCode.length(); i++) {
            if(mapCode.charAt(i) == 'N'){
                x = -1;
                y++;
            }
            if (mapCode.charAt(i) == '#') 
            {
                map[y][x] = WALL;
            } 
            else if (mapCode.charAt(i) == ' ') 
            {
                map[y][x] = PATH;
            } 
            else if (mapCode.charAt(i) == 'S') 
            {
                map[y][x] = SPRITE;
            } 
            else if (mapCode.charAt(i) == 'G') 
            {
                map[y][x] = GOAL;
             }
            x++;
        }
        }
        catch(Exception ex)
        {
        	JOptionPane.showMessageDialog(null, "Invalid map");
        	
        }
    }
     
    public void findPath()  // A* Algo	NOT USED IN PROGRAM
    {
        System.out.println("finding path...");
        Cell start = null;
        Cell end = null;
        for(int i = 0; i < arrCells.length; i++)
        {
            for(int j = 0; j < arrCells[i].length; j++)
            {
                if (arrCells[i][j] instanceof Sprite)
                {
                    start = arrCells[i][j];
                    System.out.println("found start");
                }
                else if (arrCells[i][j] instanceof Goal)
                {
                    end = arrCells[i][j];
                    System.out.println("found end");
                }
            }
        }
         
        for(int i = 0; i < arrCells.length; i++)
        {
            for(int j = 0; j < arrCells[i].length; j++)
            {
                arrCells[i][j].setCosts(start, end);
            }
        }
 
        System.out.println("done setting costs");
         
        ArrayList<Cell> openSet = new ArrayList<>();
        ArrayList<Cell> closedSet = new ArrayList<>();
        openSet.add(start);
         
        boolean isPathFound = false;
        while(!isPathFound && openSet.size() > 0)
        {
            Cell curCell = openSet.get(0);
            for(int i = 1; i < openSet.size(); i++)
            {
                if(openSet.get(i).getFCost() </*=*/ curCell.getFCost() || openSet.get(i).getFCost() == curCell.getFCost())
                {
                    if(openSet.get(i).getHCost() < curCell.getHCost())
                    {
                        curCell = openSet.get(i);
                         
                    }
                }
            }
            System.out.println("OYAH");
            openSet.remove(curCell);
            closedSet.add(curCell);
             
            if(curCell.equals(end))
            {
                backTrack(start, end);
                isPathFound = true;
            }
             
            ArrayList<Cell> neighbors = getNeighborCells(curCell);
            System.out.println("got neighbors -- "+neighbors.size());
             
            for(Cell neighbor: neighbors)
            {
                System.out.println("entered for loop");
                if(neighbor.isWall() || closedSet.contains(neighbor))
                    continue;
                 
                // check if there is a better (shorter) way
                int newCost = curCell.getGCost() + getDistance(curCell, neighbor);
                if(newCost < neighbor.getGCost() || !openSet.contains(neighbor))
                {
                    neighbor.setGCost(newCost);
                    neighbor.setHCost(getDistance(neighbor, end));
                    neighbor.setParent(curCell);
                     
                    if(!openSet.contains(neighbor))
                        openSet.add(neighbor);
                }
            }
        }
        if(path.size() > 0)
            for(int i = 0; i < path.size(); i++)
            {
                System.out.println("X: "+path.get(i).getX()+", Y: "+path.get(i).getY());
            }
         
        if(!isPathFound)
        {
            JOptionPane.showMessageDialog(null, "No possible paths to traverse:<");
        }
         
 
    }
 
    public void DFS()	// THIS ALGO IS THE ONE USED IN THE MAZE PROGRAM (implemented in the acionPerformed() method as well)
    {
    //    t.stop();
    //    t2.start();
    //	Cell start = null;
    //  Cell end = null;
        for(int i = 0; i < arrCells.length; i++)
        {
            for(int j = 0; j < arrCells[i].length; j++)
            {
                if (arrCells[i][j] instanceof Sprite)
                {
                    start = arrCells[i][j];
                    stack.push(start);
                    this.x = start.getX();
                    this.y = start.getY();
            	    System.out.println("found start");
                }
                else if (arrCells[i][j] instanceof Goal)
                {
                    end = arrCells[i][j];
                    System.out.println("found end");
                }
            }
        }
         
        Stack<Cell> stack = new Stack<>();
        ArrayList<Cell> visitedCells = new ArrayList<>();// might use this
        stack.push(start);
        start.setIsVisited(true);
    //  visitedCells.add(start);
         
        boolean isPathFound = false;
        while(!isPathFound && !stack.isEmpty())
        {
            
        	Cell curCell = stack.pop();
            ArrayList<Cell> neighbors = getNeighborCells(curCell);
             
            for(Cell neighbor : neighbors)
            {
                
                if(neighbor != null && neighbor.isVisited() == false)
                {
                	neighbor.setParent(curCell);
                	neighbor.setIsVisited(true);
                	stack.push(neighbor);
                }
                
                if (neighbor instanceof Goal || neighbor.equals(end) || neighbor.getColor().equals(Color.CYAN))
                {
                    isPathFound = true;
                    System.out.println("FOUND KATANA!!!");
                    backTrack(start, end);
                //    t2.start();
                    break;
                }
            }
        }
        if(path.size() > 0)	// prints the coordinates of the path
            for(int i = 0; i < path.size(); i++)
            {
                System.out.println("X: "+path.get(i).getX()+", Y: "+path.get(i).getY());
            }
         
        if(!isPathFound)
        {
            JOptionPane.showMessageDialog(null, "No possible paths to traverse:<");
            t.stop();
        }
        System.out.println("------------------ end of DFS() ----------------------");
    }
     
    public void backTrack(Cell start, Cell end)
    {
        Cell cur = end;
        ArrayList<Cell> path = new ArrayList<>();
        System.out.println("backtracking...");
        while(cur != (start))
        {
            path.add(cur);
            
        //    System.out.println(path.size());
            	cur = cur.getParent();
        //    System.out.println("X: "+cur.getX()+" Y: "+cur.getY());
        //    System.out.println("still in while loop");
        }
        for(int i = path.size()-1; i > 0; i--) // reverse
        {
            this.path.add(path.get(i));
        }
        System.out.println("reversed path!!! path size: "+this.path.size());
        System.out.println("end of backtrack");
        resetVisitedCells();
    }
    
    public void resetVisitedCells()	// NOT USED
    {
    	for(int i = 0; i < arrCells.length; i++)	// sets everything to unvisited
        {
        	for(int j = 0; j < arrCells[i].length; j++)
        	{
        		arrCells[i][j].setIsVisited(false);
        	}
        }
    }
     
    public int getDistance(Cell a, Cell b)	// NOT USED
    {
        int dX = Math.abs(a.getX() - b.getX());
        int dY = Math.abs(a.getY() - b.getY());
         
        if (dX > dY)
        {
            return 14 * dY + 10 * (dX - dY);
        }
        return 14*dX + 10 * (dY - dX);
    }
     
    public ArrayList<Cell> getNeighborCells(Cell c)
    {
        ArrayList<Cell> neighbors = new ArrayList<>();
        int originX = 0;
        int originY = 0;
        for(int i = 0; i < arrCells.length; i++)
        {
            for(int j = 0; j < arrCells[i].length; j++)
            {
                if(arrCells[i][j].equals(c))
                {
                    originY= j;
                    originX = i;
                    System.out.println("found cell in arrCells");
                    break;
                }
            }
        }
        Cell up = null, down = null, left = null, right = null;
        if (originX -1 >= 0) up = arrCells[originX-1][originY];
        if (originX + 1 < arrCells.length) down = arrCells[originX+1][originY];
        if (originY -1 >= 0) left = arrCells[originX][originY-1];
        if (originY + 1 < arrCells[0].length) right = arrCells[originX][originY+1];
         
        if(originX > 0 && up != null && up.isWall()==false /*&& up.isVisited() == false*/) // UP
        {
        //  up.setIsVisited(true);
            neighbors.add(up);
            System.out.println("added UP");
        }
        if(originX < arrCells.length && down != null && down.isWall()==false /*&& up.isVisited() == false*/) // DOWN
        {
        //  up.setIsVisited(true);
            neighbors.add(down);
            System.out.println("added DOWN");
        }
        if(originY > 0 && left != null && left.isWall()==false /*&& up.isVisited() == false*/) // LEFT
        {
        //  up.setIsVisited(true);
            neighbors.add(left);
            System.out.println("added LEFT");
        }
        if(originY < arrCells[0].length && right != null && right.isWall()==false /*&& up.isVisited() == false*/) // RIGHT
        {
        //  up.setIsVisited(true);
            neighbors.add(right);
            System.out.println("added RIGHT");
        }
         
        return neighbors;
    }
 
    @Override
    public void keyTyped(KeyEvent e) {}
    public void keyPressed(KeyEvent e) 
    {
         
        int i = 0;
        int tempIndx = 0;
        boolean isBorder = false;
        while(i < cells.size() && !(cells.get(i) instanceof Sprite))
        {
            i++;
        }
        switch(e.getKeyCode())
        {
            case KeyEvent.VK_LEFT: 
                if(i - 1 > 0 && i % cols != 0)
                {
                    tempIndx = i;
                    i--;
                }
                else if((i) %cols == 0)
            	{
                	isBorder = true;
                }
                break;
            case KeyEvent.VK_RIGHT: 
                if(i+1 < cells.size() && (i + 1) % cols !=0)
                {
                    tempIndx = i;
                    i++;
                }
                else if((i + 1) % cols ==0)
            	{
                	isBorder = true;
                }
                break;
            case KeyEvent.VK_UP: 
                if(i - cols > 0)
                {
                    tempIndx = i;
                    i-= cols;
                }
                else 
                {
                	isBorder = true;
                }
                break;
            case KeyEvent.VK_DOWN: 
                if(i + cols < cells.size())
                {
                    tempIndx = i;
                    i += cols;
                }
                else
                {
                	isBorder = true;
                }
                break;
        }
         
        if (cells.get(i).getColor().equals(Color.CYAN) /*instanceof Goal*/)
        {
            cells.get(i).setColor(Color.YELLOW);
             
            ((Sprite)cells.get(tempIndx)).setImage(new ImageIcon("res/images/winner Zoro.gif").getImage());
            JOptionPane.showMessageDialog(null, "Congratulations! You Won!!!");
            PlayBoard.btnHint.setEnabled(false);
        }
         
        if (cells.get(i) instanceof Cell && !cells.get(i).isWall() && !isBorder)
        {
            // swap
            Cell temp = cells.get(tempIndx);
            Cell replace = cells.get(i);
             
            System.out.println("former x: "+temp.getX());
            System.out.println("former y: "+temp.getY());
             
            cells.set(tempIndx, replace);
             
            int x = cells.get(tempIndx).getX();
            int y = cells.get(tempIndx).getY();
             
            cells.get(tempIndx).setX(temp.getX());
            cells.get(tempIndx).setY(temp.getY());
             
            cells.set(i, temp);
            cells.get(i).setX(x);
            cells.get(i).setY(y);
             
            System.out.println("new x: "+temp.getX());
            System.out.println("new y: "+temp.getY());
        }
    }
    public void keyReleased(KeyEvent e) {}
     
    @Override
    public void actionPerformed(ActionEvent e) 
    {
    	try{
    	if(PlayBoard.btnHint.isEnabled() == false)
    	{
    		for(neighIndex = 0; neighIndex < neighbors.size(); neighIndex++)
            {
               
                if(neighbors.get(neighIndex) != null && neighbors.get(neighIndex).isVisited() == false)
                {
                	neighbors.get(neighIndex).setParent(curCell);
                	neighbors.get(neighIndex).setIsVisited(true);
                	stack.push(neighbors.get(neighIndex));
                	
                	 // swap
                    Cell temp = curCell;//cells.get(tempIndx);
                    Cell replace = neighbors.get(neighIndex);//cells.get(i);
                    x = replace.getX();
                    y = replace.getY();
                    replace.setColor(Color.GREEN);

                    PlayBoard.lblStatus.setText("finding path...");
                }
                
                if (neighbors.get(neighIndex) instanceof Goal || neighbors.get(neighIndex).equals(end) || neighbors.get(neighIndex).getColor().equals(Color.CYAN))
                {
                    isPathFound = true;
                   // ((Sprite)neighbors.get(neighIndex)).setImage(new ImageIcon("res/images/winner Zoro.gif").getImage());
//                    System.out.println("FOUND KATANA!!!");
                    //backTrack(start, end);
                    t.stop();
                    PlayBoard.lblStatus.setText("FOUND PATH!!!");
                    t2.start();
                   // break;
                }
            }
    	}
    	
    	if(e.getSource() == t2)
    	{
    		if (nRects < path.size()-1)
			{
				nRects++;
			}
    	//	else
    	//		PlayBoard.lblStatus.setText("");
    	}
    	repaint();
    	}	// try
    	catch(Exception ex){}
    }
     
 
/*  public static void main (String[] args)
    {
        PlayMaze p = new PlayMaze("res/maps/Level 6.txt",720,720);
        String[][]test = p.getMap();
        for(int i = 0; i < p.rows; i++)
        {
            for(int j = 0; j < p.cols; j++)
            {
                System.out.print(test[i][j]);
            }
            System.out.println();
        }
    }
/*  public static void main (String[] args)
    {
        JFrame jf = new JFrame();
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.setSize(720, 720);
        PlayMaze p = new PlayMaze("res/maps/Level 6.txt",720,720);
        if (p.isMapValid())
        {
            jf.add(p,BorderLayout.CENTER);
            JButton btnHint = new JButton("Hint!");
            //btnHint.addActionListener(this);
            JPanel pnl = new JPanel();
            pnl.add(btnHint);
            jf.add(pnl, BorderLayout.SOUTH);
            jf.setLocationRelativeTo(null);
            jf.setVisible(true);
        }
        else
            jf.dispose();
    }*/
}