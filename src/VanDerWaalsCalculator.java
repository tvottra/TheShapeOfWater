import java.io.*;
import java.util.*;

/**
 * Victor's task to finish van der Waals calculator
 * 
 * @author victor
 *
 */
public class VanDerWaalsCalculator {
	// p + a(n/V)^2(V - nb) = n * R * T;
	Gas[] gases = new Gas[62];
	private final double R = 0.082057;
	private double a, b;

	public VanDerWaalsCalculator() {
		double P, V, n, T;
		Scanner scan = new Scanner(System.in);
		try {
			Scanner in = new Scanner(new File("gas.txt"));
			for (int i = 0; i < gases.length && in.hasNext(); i++) {
				gases[i] = new Gas(in.nextLine(), in.nextDouble(), in.nextDouble());
				in.nextLine();
				in.nextLine();
			}
		} catch (Exception e) {
			System.out.print("There is an exception\n");
		}
		for (int i = 0; i < gases.length; i++) {
			if (gases[i] != null)
				System.out.println(gases[i]);
		}

		System.out.println("What gas would you like to use?");
		String s = scan.nextLine();
		for (Gas g : gases) {
			if (s.equals(g.getName())) {
				a = g.getA();
				b = g.getB();
			}
		}
		System.out.println("What variable would you like to solve for? P, V, n, or T?");
		char varToSolveFor = scan.nextLine().charAt(0);
		double result = -1;
		switch (varToSolveFor) {

		case 'P':
			System.out.println("What is V?");
			V = scan.nextDouble();
			System.out.println("What is n?");
			n = scan.nextDouble();
			System.out.println("What is T?");
			T = scan.nextDouble();
			result = calculateP(V, n, T, a, b);
			break;
		case 'V':
			System.out.println("What is P?");
			P = scan.nextDouble();
			System.out.println("What is n?");
			n = scan.nextDouble();
			System.out.println("What is T?");
			T = scan.nextDouble();
			result = calculateV(P, n, T, a, b);
			break;
		case 'n':
			System.out.println("What is P?");
			P = scan.nextDouble();
			System.out.println("What is V?");
			V = scan.nextDouble();
			System.out.println("What is T?");
			T = scan.nextDouble();
			result = calculateN(P, V, T, a, b);
			break;
		case 'T':
			System.out.println("What is P?");
			P = scan.nextDouble();
			System.out.println("What is V?");
			V = scan.nextDouble();
			System.out.println("What is n?");
			n = scan.nextDouble();
			result = calculateT(P, V, n, a, b);
			break;
		default:
			throw new IllegalArgumentException("Invalid input");
		}
		System.out.println("Your " + varToSolveFor + " is " + result);

	}

	public double calculateP(double V, double n, double T, double a, double b) {
		return (R * T) / ((V / n) - b) - (a * Math.pow((n / V), 2));
	}

	public double calculateV(double P, double n, double T, double a, double b) {
		double v = IdealGasCalculator.calculateV(P, n, T);
		for (int i = 0; i < 10; i++) {
			double numerator = (P + a * Math.pow(n, 2) / (Math.pow(v, 2))) * (v - (n * b)) - (n * R * T);
			double denominator = P - (a * Math.pow(n, 2) / (Math.pow(v, 2)))
					+ (2 * a * b * Math.pow(n, 3)) / (Math.pow(v, 3));
			v = v - (numerator / denominator);
		}
		return v;
	}

	public double calculateN(double P, double V, double T, double a, double b) {
		double n = IdealGasCalculator.calculateN(P, V, T);
		for (int i = 0; i < 10; i++) {
			double numerator = (P + (a * Math.pow(n, 2)) / (Math.pow(V, 2))) * (V - (n * b)) - (n * R * T);
			double denominator = ((2 * a * n) / V) - ((3 * a * b * Math.pow(n, 2)) / (Math.pow(V, 2))) - (b * P)
					- (R * T);
			n = n - (numerator / denominator);
		}
		return n;
	}

	public double calculateT(double P, double V, double n, double a, double b) {
		return (1 / R) * (P + a * Math.pow((n / V), 2)) * ((V / n) - b);

	}

}
