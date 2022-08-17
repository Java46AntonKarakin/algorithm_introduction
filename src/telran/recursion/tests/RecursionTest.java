package telran.recursion.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import static telran.recursion.LineRecursion.*;

class RecursionTest {
	private static final int N_1ST = 10;
	private static final int N_2ND = 6;
	int count = 0;

	@Test
	void factorialTest() {
		assertEquals(24, factorial(4));
	}

	@Test
	void powTest() {
		int expected =  (int)Math.pow(N_1ST, N_2ND);
		int powResult = (int)pow(N_1ST, N_2ND);
		System.out.println(String.format("%d ^ %d = " + powResult, N_1ST, N_2ND));
		System.out.println("expected = " + expected);

		assertEquals(expected, powResult);
	}

	@Test
	void sumTest() {
		int[] ar = { 1, 2, 3, 4 };
		assertEquals(10, sum(ar));
	}

	@Test
	void squareTest() {
		assertEquals(0, square(0));
		assertEquals(1, square(1));
		assertEquals(4, square(2));
		assertEquals(100, square(10));
		assertEquals(15129, square(123));
	}

//	@Test
	void test() {
		f();
		System.out.println(count);
	}

	void f() {
		if (Math.random() < 0.9999) {
			count++;
			f();
		}
	}
}
