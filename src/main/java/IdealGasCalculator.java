import java.util.Scanner;

public class IdealGasCalculator {
	// PV = nRT;
	private static double R = TheShapeOfWater.getR();

	public IdealGasCalculator() {
	}

	public static double calculateP(double V, double n, double T) {
		return n * R * T / V;
	}

	public static double calculateV(double P, double n, double T) {
		return n * R * T / P;
	}

	public static double calculateN(double P, double V, double T) {
		return P * V / (R * T);
	}

	public static double calculateT(double P, double V, double n) {
		return P * V / (n * R);
	}
}
