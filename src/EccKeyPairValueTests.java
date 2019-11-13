import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EccKeyPairValueTests {

	static Point point;
	static int n ;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		point = new Point(6, 16);
		n = 11;
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void generateKeyPairValue_test() {
		EccKeyPairValue keyPairValue = EccKeyPairValue.generateKeyPairValue(n, point);
		assertNotEquals(0, keyPairValue.getPrivateKey());
		assertNotNull(keyPairValue.getPublicKey());
		System.out.println(keyPairValue.getPrivateKey());
		System.out.println(keyPairValue.getPublicKey());
	}

}
