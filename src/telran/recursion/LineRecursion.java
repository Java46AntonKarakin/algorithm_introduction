package telran.recursion;

import java.util.Arrays;

public class LineRecursion {

	public static long factorial(int n) {

		if (n == 0) {
			return 1;
		}
		return n * factorial(n - 1);
	}

	/**
	 * 
	 * @param a - negative value is possible
	 * @param b - positive only
	 * @return a ^ b
	 * 
	 * idea - sum of the two squares of the previous value
	 * 
	 */
	public static long pow(int a, int b) {
		if (b < 0) {
			return 0;
		} else if (b == 0){
			return 1;
		} else if (b % 2 == 0) {
			return pow(square(a), b / 2);
		} else {
			return a + a + pow(a, b - 1);
		}
	}

	public static Integer square(int x) {
		if (x == 0) {
			return 0;
		}
		if (x == 1) {
			return 1;
		}
		
		return x + x - 1 + square(x - 1);
	}

	/**
	 * 
	 * @param ar
	 * @return sum of numbers from the given array
	 */
	public static int sum(int ar[]) {
		return sum(0, ar);
	}

	private static int sum(int firstIndex, int[] ar) {
		if (firstIndex == ar.length) {
			return 0;
		}
		return ar[firstIndex] + sum(firstIndex + 1, ar);

	}

}
