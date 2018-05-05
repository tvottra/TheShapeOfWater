import java.io.*;
import java.util.*;

public class VanDerWaalsCalculator {
	Gas[] gases = new Gas[62];
	
	public VanDerWaalsCalculator() {
		try {
			Scanner in = new Scanner(new File("gas.txt"));
			for(int i = 0; i < gases.length && in.hasNext(); i++) {
				gases[i] = new Gas(in.nextLine(), in.nextDouble(), in.nextDouble());
				in.nextLine();
				in.nextLine();
			}
		}
		catch(Exception e) {
			System.out.print("There is an exception\n");
		}
		for(int i = 0; i < gases.length; i++) {
			if(gases[i] != null)
				System.out.println(gases[i]);
		}
	}
}
