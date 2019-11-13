import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EllipticParamsTest {

	static EllipticParams params ;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		params = new EllipticParams(3, 23, 23, 11);
	}

	@BeforeEach
	void setUp() throws Exception {

	}
	
	@Test
	void test() {
		fail("Not yet implemented");
	}
	
	@Test
	 void generateR_test()
	{
		Point point = new Point(2, 3);
		params.setP(point);
		Point expectedPoint = params.generateR(5); 
		double expectedX = expectedPoint.getX();
		double expectedY = expectedPoint.getY();
		assertEquals(expectedX, 10);
		assertEquals(expectedY, 15);
	}

}
