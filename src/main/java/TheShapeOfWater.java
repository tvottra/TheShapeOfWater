import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class TheShapeOfWater {
	private final int numOfGases = 62;
	Gas[] gases = new Gas[numOfGases];
	private final double barToATMMultiplier = 0.986923;
	private final static double R = 0.082057;
	private static double currentA = 0;
	private static double currentB = 0;
	private static String currentGas = "";

	public TheShapeOfWater() {
		Scanner userInput = new Scanner(System.in);
		System.out.println("Welcome to the Shape of Water (and more)!");

		//Set up the table of gases
		try {
			Scanner gasesInput = new Scanner(new File("src/main/resources/gases.txt"));
			for (int i = 0; i < gases.length && gasesInput.hasNext(); i++) {
				gases[i] =
						new Gas(
								gasesInput.nextLine(),
								gasesInput.nextDouble() * barToATMMultiplier,
								gasesInput.nextDouble());
				gasesInput.nextLine();
				gasesInput.nextLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		boolean userIsFinished = false;
		int numOfRuns = 0;
		do {
			Gas myGas = null;
			double a = -1;
			double b = -1;
			double P, V, n, T;
			System.out.println("I can help you calculate the P, V, n, or T of a gas!");
			for (int i = 0; i < gases.length; i++) {
				if (gases[i] != null) System.out.println(gases[i]);
			}
			System.out.println("Which gas are we using?");
			String s = userInput.nextLine();
			for (Gas g : gases) {
				if (s.equals(g.getName())) {
					myGas = g;
				}
			}
			if(myGas == null) {
				throw new NoSuchElementException("Sorry. I don't have any information on that gas.");
			}
			a = myGas.getA();
			b = myGas.getB();

			currentGas = myGas.getName();
			currentA = a;
			currentB = b;

			System.out.println("What variable would you like to solve for? P, V, n, or T?");
			char varToSolveFor = userInput.nextLine().charAt(0);
			double idealGasResult = -1;
			double vanDerWaalResult = -1;
			String resultUnit = "";
			switch (varToSolveFor) {
				case 'P':
					resultUnit = "atm";
					System.out.println("What is V (L)?");
					V = userInput.nextDouble();
					System.out.println("What is n (mol)?");
					n = userInput.nextDouble();
					System.out.println("What is T (K)?");
					T = userInput.nextDouble();
					idealGasResult = IdealGasCalculator.calculateP(V, n, T);
					vanDerWaalResult = VanDerWaalsCalculator.calculateP(V, n, T, a, b);
					break;
				case 'V':
					resultUnit = "L";
					System.out.println("What is P (atm)?");
					P = userInput.nextDouble();
					System.out.println("What is n (mol)?");
					n = userInput.nextDouble();
					System.out.println("What is T (K)?");
					T = userInput.nextDouble();
					idealGasResult = IdealGasCalculator.calculateV(P, n, T);
					vanDerWaalResult = VanDerWaalsCalculator.calculateV(P, n, T, a, b);
					break;
				case 'n':
					resultUnit = "mol";
					System.out.println("What is P (atm)?");
					P = userInput.nextDouble();
					System.out.println("What is V (L)?");
					V = userInput.nextDouble();
					System.out.println("What is T (K)?");
					T = userInput.nextDouble();
					idealGasResult = IdealGasCalculator.calculateN(P, V, T);
					vanDerWaalResult = VanDerWaalsCalculator.calculateN(P, V, T, a, b);
					break;
				case 'T':
					resultUnit = "K";
					System.out.println("What is P (atm)?");
					P = userInput.nextDouble();
					System.out.println("What is V (L)?");
					V = userInput.nextDouble();
					System.out.println("What is n (mol)?");
					n = userInput.nextDouble();
					idealGasResult = IdealGasCalculator.calculateT(P, V, n);
					vanDerWaalResult = VanDerWaalsCalculator.calculateT(P, V, n, a, b);
					break;
				default:
					throw new IllegalArgumentException("Invalid input");
			}
			System.out.println("Ideal gas result: " + idealGasResult + " " + resultUnit);
			System.out.println("Van der Waal result: " + vanDerWaalResult + " " + resultUnit);
			System.out.println("Van der Waal's critical constants for the given gas.");
			System.out.println("---------------------------------------------------");
			System.out.println("Critical volume for the given gas is " + VanDerWaalsCalculator.calculateCriticalV(b));
			System.out.println("Critical temperature for the given gas is " + VanDerWaalsCalculator.calculateCriticalT(a, b));
			System.out.println("Critical pressure for the given gas is " + VanDerWaalsCalculator.calculateCriticalP(a, b));
			System.out.println("---------------------------------------------------");

			numOfRuns++;
			if (numOfRuns == 1) {
				System.out.println("Displaying PVT surface plot...");
				Grapher.main(null);
			}

			System.out.println("\nWould you like to solve for a different gas (Y/N)?");
			String choice = userInput.next();
			userInput.nextLine();
			if (choice.charAt(0) == 'N') {
				userIsFinished = true;
			}

		} while (!userIsFinished);
	}

	public static double getR() {
		return R;
	}

	public static double getCurrentA() {
		return currentA;
	}

	public static double getCurrentB() {
		return currentB;
	}

	public static String getCurrentGas() {
		return currentGas;
	}
}
