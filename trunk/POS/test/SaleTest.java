package ee.ut.math.tvt.salessystem.domain.data;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class SaleTest {
	 @Before
	  public void setUp() {
	    item1 = new Solditem("Lauaviin", 3); 
	  }
	 
	 @Test
	  public void testRowSumWithZeroQuantity() {
	    BillRow r = new BillRow(item1, 0);
	    
	    assertEquals(r.getRowPrice(), 0.0, 0.0001);
	  }
}
