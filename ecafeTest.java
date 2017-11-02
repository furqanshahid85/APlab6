package lab6;

import static org.junit.Assert.*;

import org.junit.Test;

public class ecafeTest {

	@Test
	public void test() {
		persistentEcafe obj=new persistentEcafe();
		
		int menuSelection=1;
		int itemPrice=300;
		String itemSelected="fries";
		int itemTime=3;
		
		assertEquals(1,menuSelection);
		assertEquals(300,itemPrice);
		
		assertNotNull(itemPrice);
		assertNotNull(itemTime);
		assertNotNull(itemSelected);
	}

}
