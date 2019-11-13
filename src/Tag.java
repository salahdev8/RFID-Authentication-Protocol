import java.awt.Point;
import java.util.UUID;

public class Tag {
	
	EllipticParams ellipticParams;
	EccKeyPairValue tagKeyPairValue;
	Point serverPublicKey;
	Eccserver server;
	int r1 ;
	Point R2;
	
	public Tag()
	{
		
	}
	
	public void saveEllipticParams(	EllipticParams ellipticParams, 
									EccKeyPairValue tagKeyPairValue, 
									Point serverPublicKey,
									Eccserver server)
	{
		this.ellipticParams  = ellipticParams;
		this.tagKeyPairValue = tagKeyPairValue;
		this.serverPublicKey = serverPublicKey;
		this.server= server;
	}	
	
	public void setServerPublicKey(Point serverPublicKey) {
		this.serverPublicKey = serverPublicKey;
	}
	
	public void recieveFirstMessage(Point R2)
	{
		this.R2 = R2;
		r1 = EllipticParams.generateRandomNumber();
		Point R1 = ellipticParams.generateR(r1);
		byte[] auth = calcAuthPoint(R2, r1, R1);
		server.firstMessageResponse(auth, R1);
	}	
	

	public byte[] calcAuthPoint(Point R2)
	{
		int r1 =  EllipticParams.generateRandomNumber();
		Point R1 = ellipticParams.generateR(r1);
		return calcAuthPoint(R2, r1, R1);
	}
	
	public byte[] calcAuthPoint(Point R2, int r1, Point R1)
	{
		double TK1x= r1* R1.getX() * R2.getX();
		double TK1y= r1* R1.getX() * R2.getY();
		
		double TK2x= r1* R1.getY() * serverPublicKey.getX();
		double TK2y= r1* R1.getY() * serverPublicKey.getY();
				
		double TKx = TK1x +TK2x;
		double TKy = TK1y +TK2y;
		
		String TKstring= TKx + "," + TKy;
		
		byte [] TKbyte= TKstring.getBytes();
		
		String TKhash= HashXorFunction.getHash(TKbyte,"SHA-512");
		byte [] TKhashbyte= TKhash.getBytes();
		
		double publicX = tagKeyPairValue.getPublicKey().getX();
		double publicY = tagKeyPairValue.getPublicKey().getY();
		
		String publicstring= publicX+ "," +publicY;
		byte [] publicbyte= publicstring.getBytes();
		
		byte [] auth= HashXorFunction.xorWithKey(publicbyte,TKhashbyte) ;
		
		
		
		return auth;
	}

	public void completeAuth(Point serverAuth) {
		
		double checkAuthX= (r1 *  tagKeyPairValue.getPublicKey().getX()) +  (tagKeyPairValue.getPrivateKey() *  R2.getX());
		double checkAuthY= (r1 *  tagKeyPairValue.getPublicKey().getY()) +  (tagKeyPairValue.getPrivateKey() *  R2.getY());

			
		if(checkAuthX == serverAuth.getX() && checkAuthY == serverAuth.getY())
			System.out.println("tag approved, authentication success ");
		else
			System.out.println("FAILED");
		
	}
}
