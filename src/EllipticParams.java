import java.awt.Point;
import java.util.Random;
import java.math.*;

public class EllipticParams {

double acoef,bcoef;
int q;
int n;
Point p;

public static int maxLength = 10;

public EllipticParams (double a, double b, int q, int n) {	
	acoef=a;
	bcoef=b;
	this.q=q;
	this.n=n;
	this.p=generatePoint();
}

public void setP(Point p) {
	this.p = p;
}

private Point generatePoint() {
	 int x = generateRandomNumber();
	int y = (int)((Math.sqrt(Math.pow(x,3) + (getAcoef() * x) + getBcoef())) % getQ()) ;
	
	return new Point(x, y);
}



public double getAcoef() {
	return acoef;
}

public double getBcoef() {
	return bcoef;
}

public int getQ() {
	return q;
}

public int getN() {
	return n;
}

public Point getP() {
	return p;
}


public static  int generateRandomNumber() {
	Random random = new Random();
	return random.nextInt(maxLength);
}

public Point generateR()
{
	return generateR( generateRandomNumber());
}

public Point generateR(int r)
{
	double x = r * getP().getX();
	double y = r * getP().getY();
	Point point = new Point();
	point.setLocation(x, y);
	return point;
}
}
