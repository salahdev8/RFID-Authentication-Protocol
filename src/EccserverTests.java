

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EccserverTests {
	static Eccserver server ;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		EllipticParams params = new EllipticParams(3, 23, 23, 11);
		server = new Eccserver(params);
		server.generatePrivateKey();
	}

	@BeforeEach
	void setUp() throws Exception {
		server.clear();
	}

	@Test
	void generatePrivateKey_test() {
		System.out.println(server.params.getP().toString());
	}
	
	@Test
	 void registerTag_test()
	{
		assertEquals(0, server.count());
		Tag tag = new Tag();
		server.registerTag(tag);
		assertEquals(1, server.count());
		Tag tag2 = new Tag();
		server.registerTag(tag2);
		assertEquals(2, server.count());
	}
	
	@Test
	 void getAuth_test()
	{
		int r2 = 4;
		server.setR2(r2);
		int tagPrivateKey = 4;
		Point R1 = new Point(2, 3);
		Point tagPublicKey = new Point(8, 4);
		double expectedAuthX = 40;
		double expectedAuthY = 28;
		assertEquals(expectedAuthX, server.getAuth(tagPrivateKey, R1, tagPublicKey).getX());
		assertEquals(expectedAuthY, server.getAuth(tagPrivateKey, R1, tagPublicKey).getY());
	}
	
	@Test
	void isPublicKeyExists_publicNotExists_return_false()
	{
		server.addPublicPrivateKeyMapping(new Point(10, 1), 3);
		assertFalse(server.isPublicKeyExists(new Point(1,3)));
	}
	
	@Test
	void isPublicKeyExists_publicExists_return_true()
	{
		server.addPublicPrivateKeyMapping(new Point(1, 3), 3);
		assertTrue(server.isPublicKeyExists(new Point(1,3)));
	}
	
	@Test
	void getTagPrivateKey_publicKeyNotExists_return_minus_one()
	{
		server.addPublicPrivateKeyMapping(new Point(10, 1), 3);
		assertEquals(-1,server.getTagPrivateKey(new Point(1,3)));
	}
	
	@Test
	void getTagPrivateKey_publicKeyNotExists_return_privateKeyValue()
	{
		server.addPublicPrivateKeyMapping(new Point(1, 3), 3);
		assertEquals(3, server.getTagPrivateKey(new Point(1,3)));
	}
}
