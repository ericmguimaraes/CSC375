package Domain;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class AlloyPanel extends JPanel {

	protected int ROWS;
	protected int COLS;
	protected int BOX_SIZE = 30;
	Alloy alloy;

	public AlloyPanel(Alloy alloy) {
		this.alloy = alloy;
		COLS = alloy.h;
		ROWS = alloy.w;
		BOX_SIZE = 600/COLS;
                if(BOX_SIZE==0) BOX_SIZE=1;
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(COLS * BOX_SIZE, ROWS * BOX_SIZE);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();

		int xOffset = (getWidth() - (COLS * BOX_SIZE)) / 2;
		int yOffset = (getHeight() - (ROWS * BOX_SIZE)) / 2;
		
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				int R=0;
				int G=0;
				int B=0;

				double temp = alloy.getOrigin(row, col).getTemperature();
				if(temp==0){ //zero
					R=220;
					G=220;
					B=220;
				}else if(temp<-0.01){ //blue
					R=50;
					G=50;
					B=255;
				}else if (temp>0.01) { //red
					R=255;
					G=50;
					B=50;
				} else if(temp<-0.00000000000000000000001){ //light blue
					R=150;
					G=150;
					B=255;
				}else if (temp>0.00000000000000000000001) { //light red
					R=255;
					G=215;
					B=30;
				} else if(temp<0){ //lighter blue
					R=200;
					G=200;
					B=255;
				}else{ //lighter red
					R=255;
					G=240;
					B=130;
				}
				g2d.setColor(new Color(R,G,B));
				g2d.fillRect(xOffset + (col * BOX_SIZE), yOffset + (row * BOX_SIZE), BOX_SIZE, BOX_SIZE);
			}
		}
		g2d.dispose();
	}
	
	public void setAlloy(Alloy alloy) {
		this.alloy = alloy;
	}
}