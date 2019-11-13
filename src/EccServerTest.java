
public class EccServerTest {

	public static void main(String[] args) {
		
		EllipticParams params = new EllipticParams(0, 1, 23, 11);
		Eccserver server = new Eccserver(params);
		server.generatePrivateKey();
		
		
		
		Tag tag = new Tag();
		server.registerTag(tag);
		server.connect(tag);
		
	}

}
