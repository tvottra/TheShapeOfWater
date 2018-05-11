import java.io.*;
import java.util.*;

/**
 * Victor's task to finish van der Waals calculator
 *
 * @author victor
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
				gases[i] =
						new Gas(
								in.nextLine(),
								in.nextDouble() * 0.986923,
								in.nextDouble()); // 0.986923 to convert the bar in gas.txt to atm
				in.nextLine();
				in.nextLine();
			}
		} catch (Exception e) {
			System.out.print("There is an exception\n");
		}
		for (int i = 0; i < gases.length; i++) {
			if (gases[i] != null) System.out.println(gases[i]);
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
		String resultUnit = "";
		switch (varToSolveFor) {
			case 'P':
				resultUnit = "atm";
				System.out.println("What is V (L)?");
				V = scan.nextDouble();
				System.out.println("What is n (mol)?");
				n = scan.nextDouble();
				System.out.println("What is T (K)?");
				T = scan.nextDouble();
				result = calculateP(V, n, T, a, b);
				break;
			case 'V':
				resultUnit = "L";
				System.out.println("What is P (atm)?");
				P = scan.nextDouble();
				System.out.println("What is n (mol)?");
				n = scan.nextDouble();
				System.out.println("What is T (K)?");
				T = scan.nextDouble();
				result = calculateV(P, n, T, a, b);
				break;
			case 'n':
				resultUnit = "mol";
				System.out.println("What is P (atm)?");
				P = scan.nextDouble();
				System.out.println("What is V (L)?");
				V = scan.nextDouble();
				System.out.println("What is T (K)?");
				T = scan.nextDouble();
				result = calculateN(P, V, T, a, b);
				break;
			case 'T':
				resultUnit = "K";
				System.out.println("What is P (atm)?");
				P = scan.nextDouble();
				System.out.println("What is V (L)?");
				V = scan.nextDouble();
				System.out.println("What is n (mol)?");
				n = scan.nextDouble();
				result = calculateT(P, V, n, a, b);
				break;
			default:
				throw new IllegalArgumentException("Invalid input");
		}
		System.out.println("Your " + varToSolveFor + " is " + result + " " + resultUnit);
	}

	public double calculateP(double V, double n, double T, double a, double b) {
		return (R * T) / ((V / n) - b) - (a * Math.pow((n / V), 2));
	}

	public double calculateV(double P, double n, double T, double a, double b) {
		// f(V) = PV^3 + (-Pnb - nRT)V^2 + an^2V - an^3b = 0
		// f'(V) = 3PV^2 + 2(-Pnb - nRT)V + an^2 = 0;
		double V =
				IdealGasCalculator.calculateV(P, n, T); // Use ideal gas law's value as initial estimate
		double diff = 1;
		while (diff > 0.0000001) { // 7th decimal accuracy
			double fOfV =
					P * Math.pow(V, 3)
							+ (-P * n * b - n * R * T) * Math.pow(V, 2)
							+ a * Math.pow(n, 2) * V
							- a * Math.pow(n, 3) * b;
			double fPrimeOfV =
					3 * P * Math.pow(V, 2) + 2 * (-P * n * b - n * R * T) * V + a * Math.pow(n, 2);
			double pastV = V;
			V = V - fOfV / fPrimeOfV;
			diff = Math.abs(V - pastV);
		}

		return V;
	}

	public double calculateN(double P, double V, double T, double a, double b) {
		// f(n) = abN^3 - aVn^2 + (V^2RT _ PbV^2)n - PV^3
		// f'(n) = 3abn^2 - 2aVn + V^2RT + PbV^2

		double n =
				IdealGasCalculator.calculateN(P, V, T); // Use ideal gas law's value as initial estimate
		double diff = 1;

		while (diff > 0.0000001) {
			double fOfN =
					a * b * Math.pow(n, 3)
							- a * V * Math.pow(n, 2)
							+ (Math.pow(V, 2) + (Math.pow(V, 2) * R * T + P * b * Math.pow(V, 2))) * n
							- P * Math.pow(V, 3);
			double fPrimeOfN =
					3 * a * b * Math.pow(n, 2)
							- 2 * a * V * n
							+ Math.pow(V, 2) * R * T
							+ P * b * Math.pow(V, 2);
			double pastN = n;
			n = n - fOfN / fPrimeOfN;
			diff = Math.abs(n - pastN);
		}

		return n;
	}

	public double calculateT(double P, double V, double n, double a, double b) {
		return (1 / R) * (P + a * Math.pow((n / V), 2)) * ((V / n) - b);
	}
}
