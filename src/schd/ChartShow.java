package schd;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ChartShow extends JFrame
{
	private MyPanel panel = new MyPanel();
	GraphDraw gr = null;
	
	class MyPanel extends JPanel
	{
		public void paintComponent(Graphics g) 
		{
			Font font = new Font("고딕체", Font.BOLD, 10);
			super.paintComponent(g);
			g.setFont(font);
			g.setColor(Color.black);
			for(int i = 0; i < gr.count; i++) {
				g.drawString("" + gr.StartPList.get(i), 11 * gr.StartPList.get(i), 120);
				g.drawString("p" + gr.PIDList.get(i).toString(), 11 * gr.StartPList.get(i), 40);
				g.setColor(Color.blue);
				g.drawRect(11 * gr.StartPList.get(i), 50, 11 * (gr.EndPList.get(i) - gr.StartPList.get(i)), 50);
				g.setColor(Color.black);
				if(i < gr.count-1)
				{
					if(gr.EndPList.get(i) != gr.StartPList.get(i+1))
					{
						g.drawString("" + gr.EndPList.get(i), 11 * gr.EndPList.get(i), 120);
					}
				}
			}
			g.drawString("" + gr.EndPList.get(gr.count-1), 11 * gr.EndPList.get(gr.count-1), 120);
			System.out.println("");
		}
	}
	public ChartShow()
	{
		setTitle("간트 차트");
		setContentPane(panel);
		
		setSize(1500, 200);
		setVisible(true);
	}

}
