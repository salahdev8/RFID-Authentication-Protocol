import java.awt.Point;
import java.security.PrivateKey;
import java.util.Random;

public class EccKeyPairValue {

	private int privateKey;
	Point publicKey;
	
	public static EccKeyPairValue generateKeyPairValue(int maxLength, Point point)
	{
		EccKeyPairValue keyPairValue = new EccKeyPairValue();
		Random random = new Random();
		int privateKey = random.nextInt(maxLength);
		keyPairValue.setPrivateKey(privateKey);
		
		int x = (int)(privateKey * point.getX());
		int y = (int)(privateKey * point.getY());
		
		Point publicKey = new Point(x, y);
		
		keyPairValue.setPublicKey(publicKey);
		
		return keyPairValue;
		
	}

	public int getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(int privateKey) {
		this.privateKey = privateKey;
	}

	public Point getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(Point publicKey) {
		this.publicKey = publicKey;
	}
	
	
}
