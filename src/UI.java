package src;
/* This class controls the manner in which the User Interface looks, which includes the panels we user, their sizes and positionning */
import java.awt.BorderLayout;
import javax.swing.JFrame;

import java.util.ArrayList;

public class UI {

	private static final int FRAME_WIDTH = 1200; // Here we are initializing the size of our window
	private static final int FRAME_HEIGHT = 800;
	
	JFrame frame = new JFrame(); // This is the part of the code that we decalare all the sections of our window
	private BoardPanel boardPanel;	
	private InfoPanel infoPanel = new InfoPanel();
	private CommandPanel commandPanel = new CommandPanel();
	
	UI (ArrayList<Player> players) 
	{
		boardPanel = new BoardPanel(players);
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setTitle("Monopoly"); // Title of out frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(boardPanel, BorderLayout.LINE_START); // Here we are assigning our components to different parts of out window
		frame.add(infoPanel, BorderLayout.LINE_END);
		frame.add(commandPanel,BorderLayout.PAGE_END);
		frame.setResizable(false);
		frame.setVisible(true);
		return;
	}
	
	public String getCommand () {
		return commandPanel.getCommand(); // This is the function to get the command the user inputs into the command panel 
	}
	
	public void display () {
		boardPanel.refresh();
		return;
	}
	
	public void displayString (String string) {
		infoPanel.addText(string); // This line adds the string the user has input into the infopanel from the command panel
		
		return;
	}
	
}
