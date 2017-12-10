
package MP;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import sun.audio.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainDriver extends JFrame implements ActionListener
{
	static JComboBox<String> lvlList;
	ArrayList<String> mapList;
	JPanel pnlBody;
	static boolean levelsExistAlready = false;
	public static int nxtLvl = 0;
	
	
	public MainDriver()
	{
		super("Rat in a Maze");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(485, 720);
		setLocationRelativeTo(null);
		setResizable(false);
		mainMenu();
		
		Intro intro = new Intro();
		music();
		
		setVisible(true);
	}
	
	public void mainMenu()
	{
		JPanel pnl = new JPanel();
		
		JButton btn = new JButton("Play!");
		btn.addActionListener(this);
		pnl.add(btn);
		
		btn = new JButton("Edit Maze");
		btn.addActionListener(this);
		pnl.add(btn);
		
		JLabel load = new JLabel("Load from: ");
		pnl.add(load);
		
		mapList = new ArrayList<String>();
		getLoadFromList();
		lvlList = new JComboBox<String>(mapList.toArray(new String[mapList.size()]));
	//	lvlList.addItemListener(this);
		pnl.add(lvlList);
		
		ImageIcon picZoro = new ImageIcon("res/images/Zoro Maze Runner.jpg");
	    JLabel imgLbl = new JLabel(picZoro);
	    imgLbl.setBounds(20, 20, 450, 500);
	    
	    pnlBody = new JPanel();
	    pnlBody.add(imgLbl);
		add(pnl, BorderLayout.NORTH);
		add(pnlBody, BorderLayout.CENTER);
	}
	
	public void getLoadFromList()
	{
    	for(int i = 0; i < 99; i++)
    	{
    		File map = new File("res/maps/Level "+i+".txt");
    		if(map.exists())
    		{
    		//	System.out.println("Level "+i+" exists");
    			mapList.add("Level "+i+".txt");
    			levelsExistAlready = true;
    			nxtLvl = i + 1;
    		}
    	}
    }
	
	public void editMaze()
	{
		// START coordinates
		JPanel pnlS = new JPanel();
		JLabel lbl = new JLabel("x coordinate:");
		pnlS.add(lbl);	
		JTextField tfSX = new JTextField(10);
		pnlS.add(tfSX);
		lbl = new JLabel("y coordinate:");
		pnlS.add(lbl);	
		JTextField tfSY = new JTextField(10);
		pnlS.add(tfSY);
		// GOAL coordinates
		JPanel pnlG = new JPanel();
		lbl = new JLabel("x coordinate:");
		pnlG.add(lbl);
		JTextField tfGX = new JTextField(10);
		pnlG.add(tfGX);
		lbl = new JLabel("y coordinate:");
		pnlG.add(lbl);	
		JTextField tfGY = new JTextField(10);
		pnlG.add(tfGY);
		
		
		try
		{
			// Set No. of Rows & Cols
			int c = 0, r = 0;
			while (c < Maze.MIN_SIZE || c > Maze.MAX_SIZE || r < Maze.MIN_SIZE || r > Maze.MAX_SIZE)
			{
				String rows = JOptionPane.showInputDialog("Enter no. of Rows (15-30): ");
				String cols = JOptionPane.showInputDialog("Enter no. of Columns (15-30): ");
				c = Integer.parseInt(cols);
				r = Integer.parseInt(rows);
			}/*
			// Set Start Coordinates
			Sprite s = null;
			int option = JOptionPane.showConfirmDialog(null, pnlS, "Enter Player's X & Y coordinates", JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION)
			{
				if (!tfSX.getText().equals("") && ! tfSY.getText().equals(""))
					s = new Sprite(Integer.parseInt(tfSX.getText())/32*32, Integer.parseInt(tfSY.getText())/32*32);
				else
					JOptionPane.showMessageDialog(null, "Incomplete inputs.");
			}
			// Set Goal Coordinates
			Goal g = null;
			option = JOptionPane.showConfirmDialog(null, pnlG, "Enter Goal's X & Y coordinates", JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION)
			{
				if (!tfGX.getText().equals("") && ! tfGY.getText().equals(""))
					g = new Goal(Integer.parseInt(tfGX.getText()), Integer.parseInt(tfGY.getText()));
				else
					JOptionPane.showMessageDialog(null, "Incomplete inputs.");
			}*/
			// Instantiate Edit Board
			EditBoard b = new EditBoard(r, c/*, s, g*/);
			JOptionPane.showMessageDialog(null, "Click to edit a cell");
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, "Incomplete inputs.");
			System.out.println("(MainDriver: editMaze())"+ ex.toString());
		}
					
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if (e.getActionCommand().equals("Play!"))
		{
			PlayBoard b = new PlayBoard("res/maps/"+lvlList.getSelectedItem().toString());
		}
		
		else if (e.getActionCommand().equals("Edit Maze"))
		{
			editMaze();
		}
	}
	
	public void music()
	{
        AudioPlayer BGP = AudioPlayer.player;
        AudioStream astream;
        AudioData AD;
        
        try{
            InputStream test = new FileInputStream("res/zorro.wav");
            astream = new AudioStream(test);
            AudioPlayer.player.start(astream);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
	
	public static void main(String[] args)
	{
		MainDriver app = new MainDriver();
	}
}
