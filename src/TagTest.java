import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TagTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

		
	@Test 
	void calcAuthPoint_test()
	{
		Point R2 = new Point(2, 5);
		Point serverPublicKey = new Point(4, 3);
		
		Tag tag = new Tag();
		EllipticParams ellipticParams = new EllipticParams(0, 0, 0, 0);
		ellipticParams.setP(new Point(3, 3));
		int r1 = 7;
		Point R1 = ellipticParams.generateR(r1);
		
		Eccserver server = new Eccserver(ellipticParams);
		
		EccKeyPairValue tagKeyPairValue = new EccKeyPairValue();
		tagKeyPairValue.setPublicKey(new Point(1, 2));
		
		tag.saveEllipticParams(ellipticParams, tagKeyPairValue, serverPublicKey, server);
		double expectedAuthX = 883;
		double expectedAuthY = 1178;
		//Point expectedAuthPoint  = tag.calcAuthPoint(R2, r1, R1);
		//assertEquals(expectedAuthX, expectedAuthPoint.getX());
		//assertEquals(expectedAuthY, expectedAuthPoint.getY());
	}
}
