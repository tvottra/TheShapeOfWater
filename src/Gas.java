/**
 * @author Jonathan Lim
 * <p>
 * Gas class - class to keep track of gas specific constants
 */
public class Gas {
	private String name;
	private final double a;
	private final double b;

	/**
	 * Gas constructor - initializes name, a, and b
	 *
	 * @param n - name of gas
	 * @param a - a constant
	 * @param b - b constant
	 */
	public Gas(String n, double a, double b) {
		name = n;
		this.a = a;
		this.b = b;
	}

	/**
	 * Method to get name of gas
	 *
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Method to get a constant of gas
	 *
	 * @return a
	 */
	public double getA() {
		return a;
	}

	/**
	 * Method to get b constant of gas
	 *
	 * @return b
	 */
	public double getB() {
		return b;
	}

	public String toString() {
		String s = "";
		s += "Name: " + name + "\n";
		s += "a constant: " + a + "\n";
		s += "b constant: " + b + "\n";
		return s;
	}
}
