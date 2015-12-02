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

	static final int LIMIT_ITERACTIONS = 9000;

	static int iteractions = 0;

	static AlloyPanel panel;
	
	public static double INITIAL_TEMPERATURE = 0;

	public static void main(String[] args) throws InterruptedException {
		if (args.length != 8){
                    System.out.println("Args Required: S, T, C_1, C_2, C_3, width, height, threshold");
                    System.out.println("Using default args");
                    String[] newargs = new String[8];
                    newargs[0] = "999999999";
                    newargs[1] = "-999999999";
                    newargs[2] = "1.2";
                    newargs[3] = "1.0";
                    newargs[4] = "0.75";
                    newargs[5] = "600";
                    newargs[6] = "600";
                    newargs[7] = "1000";
                    for (int i = 0; i < newargs.length; i++) {
                        String newarg = newargs[i];
                        System.out.print(newarg+", ");
                    }
                    System.out.println("");
                    args = newargs;              
                }
		if(args.length>8) INITIAL_TEMPERATURE = Double.parseDouble(args[8]);

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

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JFrame frame = new JFrame("Alloy");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new AlloyPanel(alloy);
		frame.add(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		//alloy.print();
		pool.invoke(heater);
//		System.out.println(alloy.getDestination(1,0).metals[0]);
//		System.out.println(alloy.getDestination(1,0).metals[1]);
//		System.out.println(alloy.getDestination(1,0).metals[2]);
		while (!alloy.converged() && iteractions < LIMIT_ITERACTIONS) {
			iteractions++;
			alloy.flipIndex();
			heater = new ForkHeater(alloy, threshold);
			pool.invoke(heater);
			//alloy.print();
			panel.repaint();
		}
//        alloy.print();
		System.out.println("iteractions: " + iteractions);

	}

}
