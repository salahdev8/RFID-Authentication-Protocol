import java.awt.Point;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

public class Eccserver {
	
	EllipticParams params;
	EccKeyPairValue _serverKeyPairValue;
	int r2;
	Tag currentTag;

	public void setR2(int r2) {
		this.r2 = r2;
	}

	private HashMap<Point, Integer> _publicPrivateKeysMapping;
	
	public Eccserver(EllipticParams params)
	{
		this.params = params;
		_publicPrivateKeysMapping = new HashMap<>();
	}
	
	
	public void generatePrivateKey()
	{
		_serverKeyPairValue = EccKeyPairValue.generateKeyPairValue(EllipticParams.maxLength, params.p);
	}
		
	public void registerTag(Tag tag)
	{
		EccKeyPairValue tagKeyPairValue = EccKeyPairValue.generateKeyPairValue(EllipticParams.maxLength, params.p);
		tag.saveEllipticParams(params, tagKeyPairValue, _serverKeyPairValue.publicKey, this);
		addPublicPrivateKeyMapping(tagKeyPairValue.publicKey,tagKeyPairValue.getPrivateKey());
	}
	
	public void addPublicPrivateKeyMapping(Point publicKey, int privateKey)
	{
		_publicPrivateKeysMapping.put(publicKey, privateKey);
	}
	
	public void clear()
	{
		_publicPrivateKeysMapping.clear();
	}
	
	public int count()
	{
		return _publicPrivateKeysMapping.size();
	}


	public void connect(Tag tag)
	{
		currentTag = tag;
		r2 = EllipticParams.generateRandomNumber();
		Point R2 = params.generateR(r2);
		tag.recieveFirstMessage(R2);		
	}
	
	
	public void firstMessageResponse(byte[] auth, Point R1) {
	
		Point tagPublicKey = getTagPublicKey(auth, R1);
		if(isPublicKeyExists(tagPublicKey) == false)
		{
			System.out.println("tagPublicKey not exists " + tagPublicKey.toString());
			return;
		}
			
		int tagPrivateKey = getTagPrivateKey(tagPublicKey);
		Point serverAuth = getAuth(tagPrivateKey, R1, tagPublicKey);
		currentTag.completeAuth(serverAuth);
	}	
	
	
	public Point getAuth(int tagPrivateKey, Point R1, Point tagPublicKey)
	{
		double authX= (tagPrivateKey * R1.getX()) + (r2 * tagPublicKey.getX());
		double authY= (tagPrivateKey * R1.getY()) + (r2 * tagPublicKey.getY());
		Point auth = new Point();
		auth.setLocation(authX, authY);
		return auth;
	}
	
	public Point getTagPublicKey(byte[] auth, Point R1)
	{
		double TK1x= r2* R1.getX() * R1.getX();
		double TK1y= r2* R1.getX() * R1.getY();
		

		double TK2x= _serverKeyPairValue.getPrivateKey()* R1.getY() * R1.getX();
		double TK2y=_serverKeyPairValue.getPrivateKey()* R1.getY() * R1.getY();
		
		double TKx= TK1x + TK2x;
		double TKy= TK1y + TK2y;
		
        String TKstring= TKx + "," + TKy;
		
		byte [] TKbyte= TKstring.getBytes();
		
		String TKhash= HashXorFunction.getHash(TKbyte,"SHA-512");
		byte [] TKhashbyte= TKhash.getBytes();
		
		byte [] tagPublicKeyByte= HashXorFunction.xorWithKey(auth,TKhashbyte) ;
		
		String tagPublicKeyString = new String(tagPublicKeyByte);
		
		
		//String tagPublicKeyString = Arrays.toString(tagPublicKeyByte);
		
		
		String [] coordXY = tagPublicKeyString.split(","); 
		
		Double[] doubleArray = new Double[coordXY.length];
		
		for(int i = 0; i < coordXY.length; i++) {
		    doubleArray[i] = Double.parseDouble(coordXY[i]);
		}
		
	    Double tagPublicKeyX =doubleArray[0];
	    Double tagPublicKeyY = doubleArray[1];
	   
		
		Point tagPublicKey = new Point();
		tagPublicKey.setLocation(tagPublicKeyX, tagPublicKeyY);
		
		return tagPublicKey;
		
		
	}
	
	
	public boolean isPublicKeyExists(Point publicKey)
	{
		for(Entry<Point, Integer> pair : _publicPrivateKeysMapping.entrySet())
		{
			Point point = pair.getKey();
			if(point.getX() == publicKey.getX() && point.getY() == publicKey.getY())
			{
				return true;
			}
		}		
	
		return false;
	}
	
	public int getTagPrivateKey(Point publicKey)
	{
		for(Entry<Point, Integer> pair : _publicPrivateKeysMapping.entrySet())
		{
			Point point = pair.getKey();
			if(point.getX() == publicKey.getX() && point.getY() == publicKey.getY())
			{
				return _publicPrivateKeysMapping.get(point);
			}
		}		
	
		return -1;
	}
	
	
}
