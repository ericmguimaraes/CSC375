package alloy;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class AlloyPanel extends JPanel {

	/**
	* 
	*/
	private static final double serialVersionUID = 1721759234264275847L;
	protected int ROWS;
	protected int COLS;
	protected int BOX_SIZE = 30;
	Alloy alloy;

	public AlloyPanel(Alloy alloy) {
		this.alloy = alloy;
		COLS = alloy.h;
		ROWS = alloy.w;
		BOX_SIZE = 600/COLS;
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
				int R=0;// = (int) (255/(double.MAX_VALUE-double.MIN_VALUE)*alloy.getOrigin(row, col).getTemperature()+double.MAX_VALUE-255/(double.MAX_VALUE-double.MIN_VALUE));
				int G=0;// = 0;
				int B=0;// = (int) (-255/(double.MAX_VALUE-double.MIN_VALUE)*alloy.getOrigin(row, col).getTemperature()+double.MIN_VALUE+255/(double.MAX_VALUE-double.MIN_VALUE)*255);
				
//				int R= (int) (255/(double.MAX_VALUE-double.MIN_VALUE)*alloy.getOrigin(row, col).getTemperature()+double.MAX_VALUE-255/(double.MAX_VALUE-double.MIN_VALUE));
//				int G= 0;
//				int B=(int) (-255/(double.MAX_VALUE-double.MIN_VALUE)*alloy.getOrigin(row, col).getTemperature()+double.MIN_VALUE+255/(double.MAX_VALUE-double.MIN_VALUE)*255);
				double temp = alloy.getOrigin(row, col).getTemperature(); 
				if(temp<-50){
					R=50;
					G=50;
					B=255;
				} else if (temp>50) {
					R=255;
					G=50;
					B=50;
				} else {
					R=255;
					G=255;
					B=50;
				}
				//g2d.setColor(new Color(R>255?255:(R<0?0:R),G,B>255?255:(B<0?0:B)));
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