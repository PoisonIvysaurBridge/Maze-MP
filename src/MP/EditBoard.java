package MP;

import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

public class EditBoard extends JFrame implements ActionListener
{
	private JButton btnSave,
					btnCancel;
	EditMaze em;
	
	public EditBoard(int rows, int cols/*, Sprite s, Goal g*/)
	{
		super("Edit Maze");
	    
	    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    setSize(650, 720);
	    
	    em = new EditMaze(rows, cols, /*s, g,*/650,650);
	    add(em,BorderLayout.CENTER);
	    
	    btnSave = new JButton("Save Maze");
	    btnSave.addActionListener(this);
	    
	    btnCancel = new JButton("Cancel");
	    btnCancel.addActionListener(this);
	    
	    JPanel pnl = new JPanel();
	    pnl.add(btnSave);
	    pnl.add(btnCancel);
	    
	    add(pnl, BorderLayout.SOUTH);
	    setLocationRelativeTo(null);
	    setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getActionCommand().equals("Save Maze"))
		{
			if(em.saveMap())
			{
				MainDriver.lvlList.addItem("Level "+ MainDriver.nxtLvl+".txt");
				MainDriver.nxtLvl++;
			}
			this.dispose();
		}
		else if (e.getActionCommand().equals("Cancel"))
		{
			this.dispose();
		}
		
	}
}
