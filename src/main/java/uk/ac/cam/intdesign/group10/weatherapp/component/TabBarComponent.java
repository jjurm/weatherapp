package uk.ac.cam.intdesign.group10.weatherapp.component;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TabBarComponent extends JPanel implements MouseListener
{
	private String title;
	private Color background, activeBackground;
	private TabBarImpl parent;
	private int tabID;
	
	public void deactivate()
	{
		setBackground(background);
	}
	
	public void activate()
	{
		setBackground(activeBackground);
	}
	
	TabBarComponent(String text, Color bgc, Color activebgc, TabBarImpl p, int id)
	{
		add(new JLabel(text));
        setBackground(bgc);
        
        title = text;
        background = bgc;
        activeBackground = activebgc;
        
        parent = p;
        tabID = id;
        
        addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent arg0)
	{
		parent.tabBarChange(tabID);
	}

	@Override
	public void mouseEntered(MouseEvent arg0)
	{
		
	}

	@Override
	public void mouseExited(MouseEvent arg0)
	{
		
	}

	@Override
	public void mousePressed(MouseEvent arg0)
	{
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		
	}
}
