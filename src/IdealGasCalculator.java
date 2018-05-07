import java.util.Scanner;

public class IdealGasCalculator {
	//PV = nRT;
	private final static double R = 0.082057;
	
	
	public IdealGasCalculator() {
		double P, V, n, T;
		Scanner in = new Scanner(System.in);
		System.out.println("What variable would you like to solve for? P, V, n, or T?");
		char varToSolveFor = in.nextLine().charAt(0);
		double result = -1;
		switch(varToSolveFor) {

			case 'P':
				System.out.println("What is V?");
				V = in.nextDouble();
				System.out.println("What is n?");
				n = in.nextDouble();
				System.out.println("What is T?");
				T = in.nextDouble();
				result = calculateP(V, n, T);
				break;
			case 'V':
				System.out.println("What is P?");
				P = in.nextDouble();
				System.out.println("What is n?");
				n = in.nextDouble();
				System.out.println("What is T?");
				T = in.nextDouble();
				result = calculateV(P, n, T);
				break;
			case 'n':
				System.out.println("What is P?");
				P = in.nextDouble();
				System.out.println("What is V?");
				V = in.nextDouble();
				System.out.println("What is T?");
				T = in.nextDouble();
				result = calculateN(P, V, T);
				break;
			case 'T':
				System.out.println("What is P?");
				P = in.nextDouble();
				System.out.println("What is V?");
				V = in.nextDouble();
				System.out.println("What is n?");
				n = in.nextDouble();
				result = calculateT(P, V, n);
				break;
			default:
				throw new IllegalArgumentException("Invalid input");
		}
		System.out.println("Your " + varToSolveFor + " is " + result);

	}

	public double calculateP(double V, double n, double T) {
		return n * R * T / V;
	}

	public static double calculateV(double P, double n, double T) {
		return n * R * T / P;
	}

	public double calculateN(double P, double V, double T) {
		return P * V / (R * T);
	}

	public double calculateT(double P, double V, double n) {
		return P * V / (n * R);

	}

}