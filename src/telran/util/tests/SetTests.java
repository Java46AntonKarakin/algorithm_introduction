package telran.util.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public abstract class SetTests extends CollectionTests {
	
	@Test
	@Override
	void addTest() {
		assertTrue(collection.add(100));
		assertFalse(collection.add(10));
		int size = collection.size();
		for (int i = 0; i < N_NUMBERS; i++) {
			collection.add(101 + i);
		}
		assertEquals(size + N_NUMBERS, collection.size());
	}
	
}
