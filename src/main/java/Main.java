import java.util.concurrent.ThreadLocalRandom;

public class Main {
	public static void main(String[] args) {
		int[] arr = new int[1_000_000];
		populateArrayWithRandomIntegers(arr);

		double initialTime = System.currentTimeMillis();
		sum(arr);
		double finalTime = System.currentTimeMillis();

		showFinalTime(finalTime - initialTime);
	}

	private static void populateArrayWithRandomIntegers(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			arr[i] = ThreadLocalRandom.current().nextInt(1, 10_000);
		}
	}

	private static void sum(int[] arr) {
		int totalSum = 0;
		for (int number : arr) {
			totalSum += number;
		}
	}

	private static void showFinalTime(double finalTime) {
		String ANSI_RESET = "\u001B[0m";
		String ANSI_GREEN = "\u001B[32m";
		String ANSI_BLUE = "\u001B[34m";

		System.out.printf("""
						%sFinal Time: %s%.2f ms%s
						""", ANSI_BLUE, ANSI_GREEN, finalTime, ANSI_RESET);
	}
}
