package alloy;

import java.util.concurrent.ForkJoinPool;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Control {
	
	static ForkJoinPool pool;
	
	static ForkHeater heater;
	
	static Alloy alloy;
	
	static int s, t, width, height, threshold;
	
	static float c1, c2, c3;
	
	static final int LIMIT_ITERACTIONS = 1000;
	
	static int iteractions = 0;
	
	static AlloyPanel panel;
	
	public static void main(String[] args) throws InterruptedException {
		if(args.length!=8)
			throw new IllegalArgumentException("Args Required: S, T, C_1, C_2, C_3, width, height, threshold");
		
		s = Integer.parseInt(args[0]);
		t = Integer.parseInt(args[1]);
		c1 = Float.parseFloat(args[2]);
		c2 = Float.parseFloat(args[3]);
		c3 = Float.parseFloat(args[4]);
		width = Integer.parseInt(args[5]);
		height = Integer.parseInt(args[6]);
		threshold = Integer.parseInt(args[7]);
		
		alloy = new Alloy(width, height, s, t, c1, c2, c3);
		heater = new ForkHeater(alloy, threshold);
        ForkJoinPool pool = new ForkJoinPool();
        long startTime = System.currentTimeMillis();
        //System.out.println(alloy.converged());
        //alloy.print();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }

        JFrame frame = new JFrame("Alloy");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new AlloyPanel(alloy);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        long endTime = System.currentTimeMillis();
        
        //Thread.sleep(100);
        pool.invoke(heater);
        //System.out.println(alloy.converged());
        //alloy.print();
        //System.out.println(Long.MAX_VALUE);
       // System.out.println(Long.MIN_VALUE);
        while(!alloy.converged() && iteractions<LIMIT_ITERACTIONS){
        	iteractions++;
        	//alloy.print();
        	alloy.flipIndex();
        	heater = new ForkHeater(alloy, threshold);
        	//Thread.sleep(100);
        	pool.invoke(heater);
        	panel.repaint();
        }
        
       
	}
	
	

}
