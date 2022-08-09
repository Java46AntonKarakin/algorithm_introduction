package telran.util.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.util.Collection;

abstract class CollectionTests {
	protected static final int N_NUMBERS = 100;
	protected Collection<Integer> collection;

	protected abstract Collection<Integer> createCollection();

	Integer expected[] = { 10, -5, 13, 20, 40, 15 };

	@BeforeEach
	void setUp() throws Exception {
		collection = createCollection();
		fillCollection();

	}

	private void fillCollection() {
		for (Integer num : expected) {
			collection.add(num);
		}

	}

	@Test
	void addTest() {
		assertTrue(collection.add(100));
		assertTrue(collection.add(10));
		int size = collection.size();
		for (int i = 0; i < N_NUMBERS; i++) {
			collection.add(100);
		}
		assertEquals(size + N_NUMBERS, collection.size());
	}

	@Test
	void removeTest() {
		int size = collection.size();
		assertTrue(collection.remove(expected[0]));
		assertEquals(--size, collection.size());
		assertFalse(collection.remove(expected[0]));
		assertEquals(size, collection.size());
	}

	@Test
	void removeIfTest() {
		Predicate<Integer> allFalsePredicate = new AllFalsePredicate();
		assertFalse(collection.removeIf(allFalsePredicate));
		assertEquals(expected.length, collection.size());
		assertTrue(collection.removeIf(allFalsePredicate.negate()));
		assertEquals(0, collection.size());
	}

	@Test
	void containsTest() {
		assertTrue(collection.contains(10));
		assertFalse(collection.contains(1000));
	}

	@Test
	void toArrayTest() {
		Integer expected1[] = { 10, -5, 13, 20, 40, 15 };
		assertArrayEquals(expected1, collection.toArray(new Integer[0]));
		assertTrue(expected1 == collection.toArray(expected1));
		Integer expected2[] = new Integer[100];
		assertTrue(expected2 == collection.toArray(expected2));
		assertArrayEquals(expected1, Arrays.copyOf(expected2, collection.size()));
		for (int i = collection.size(); i < expected2.length; i++) {
			assertNull(expected2[i]);
		}

	}

	@Test
	void sizeTest() {
		assertEquals(expected.length, collection.size());
	}

}
