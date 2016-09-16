
public class Point2D
{
	public int x, y;
	public ColorType c;
	public Point2D(int _x, int _y, ColorType _c)
	{
		x = _x;
		y = _y;
		c = _c;
	}
	public Point2D()
	{
		c = new ColorType(1.0f, 1.0f, 1.0f);
	}
}