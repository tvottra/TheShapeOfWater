import java.io.*;
import java.util.*;

/**
 * Victor's task to finish van der Waals calculator
 *
 * @author victor
 */
public class VanDerWaalsCalculator {
	// p + a(n/V)^2(V - nb) = n * R * T;
	private static double R = TheShapeOfWater.getR();

	public VanDerWaalsCalculator() {
	}

	public static double calculateP(double V, double n, double T, double a, double b) {
		try {
			return (R * T) / ((V / n) - b) - (a * Math.pow((n / V), 2));
		} catch (ArithmeticException e) {
			System.out.println("Division by 0!");
			return 0;
		}
	}

	public static double calculateV(double P, double n, double T, double a, double b) {
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

	public static double calculateN(double P, double V, double T, double a, double b) {
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

	public static double calculateT(double P, double V, double n, double a, double b) {
		return (1 / R) * (P + a * Math.pow((n / V), 2)) * ((V / n) - b);
	}

	public static double calculateCriticalV(double b) {
		return 3 * b;
	}

	public static double calculateCriticalT(double a, double b) {
		return (8 * a) / (27 * b * R);
	}

	public static double calculateCriticalP(double a, double b) {
		return a / (27 * Math.pow(b, 2));
	}
}
