package telran.util.tests;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import telran.util.Collection;
import telran.util.HashSet;

public class HashSetTests extends SetTests {

	@Override
	protected Collection<Integer> createCollection() {

		return new HashSet<>();
	}

	@Override
	@Test
	void toArrayTest() {
		Integer[] expected1 = { 10, -5, 13, 20, 40, 15 };
		Integer[] target = collection.toArray(new Integer[0]);
		assertTrue(expected1.length == target.length);

		Arrays.sort(expected1);
		Arrays.sort(target);

		assertArrayEquals(expected1, target);
		assertTrue(expected1 == collection.toArray(expected1));
		Integer expected2[] = new Integer[100];
		assertTrue(expected2 == collection.toArray(expected2));
		assertArrayEquals(expected1, Arrays.copyOf(expected2, collection.size()));
		for (int i = collection.size(); i < expected2.length; i++) {
			assertNull(expected2[i]);
		}
	}
}
