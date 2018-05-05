import java.util.Scanner;

public class TheShapeOfWaterDriver {

  public static void main(String[] arg) {

    Scanner in = new Scanner(System.in);
    System.out.println("Welcome to the Shape of Water (and more)!");
    System.out.println("I can help you calculate the P, V, or T of a gas!");

    boolean validInput = false;
    while (!validInput) {
      System.out.println(
          "Would you like to use the (A) Ideal Gas Law or (B) van der Waals equation?");
      char chosenCalculator = in.nextLine().charAt(0);
      if (chosenCalculator == 'A') {
      	validInput = true;
        IdealGasCalculator myCalc = new IdealGasCalculator();

      } else if (chosenCalculator == 'B') {
      	validInput = true;
        //VanDerWaalsCalculator myCalc = new VanDerWaalsCalculator();

      } else {
        System.out.println("Invalid input. Try again.");
      }
    }
  }
}
