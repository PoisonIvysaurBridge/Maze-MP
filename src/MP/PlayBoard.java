package MP;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PlayBoard extends JFrame implements ActionListener
{
	public static JButton btnHint;
	public static JLabel lblStatus;
	PlayMaze p;
	public PlayBoard(String file)
	{
		super("Play Rate in a Maze");
	    
	    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    setSize(650, 720);
	    p = new PlayMaze(file,650,650);
	    lblStatus = new JLabel("");
	    //btnHint = new JButton("Hint!");
	    if (p.isMapValid())
	    {
	    	add(p,BorderLayout.CENTER);
	 	    btnHint = new JButton("Hint!");
	 	    btnHint.addActionListener(this);
	 	    JPanel pnl = new JPanel();
	 	    pnl.add(lblStatus);
	 	    pnl.add(btnHint);
	 	    add(pnl, BorderLayout.SOUTH);
	 	    setLocationRelativeTo(null);
	 	    setVisible(true);
	    }
	    else
	    	this.dispose();
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getActionCommand().equals("Hint!"))
		{
			// THE ALGO MEHTOD
			System.out.println("pressed hint");
			lblStatus.setText("finding path...");
		//	p.findPath();
		//	p.BFS();
			p.DFS();
			btnHint.setEnabled(false);
			btnHint.setVisible(false);
		}
		
	}
}
