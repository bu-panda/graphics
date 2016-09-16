import java.awt.MultipleGradientPaint.ColorSpaceType;
import java.awt.image.BufferedImage;

public class LineRender {
	
	public void renderLine(BufferedImage img, Point2D begin, Point2D end){
		
		// Is the same point
		if(begin.x == end.x && begin.y == end.y){
			img.setRGB(begin.x, begin.y, begin.c.getBRGUint8());
			return;
		}
		
		// Always keep begin points x coor is smaller. 
		if(begin.x > end.x){
			renderLine(img, end, begin);
			return;
		}
		
		ColorType beginColor = null;
		ColorType endColor = null;
		
		int dx = end.x - begin.x;
		int dy = end.y - begin.y;
		int steps = dx > dy ? dy : dx;
		int x, y, endx, endy;
		
		if(begin.y > end.y){
			x = -end.x;
			y = end.y;
			endx = -begin.x;
			endy = begin.y;
			beginColor = new ColorType(end.c);
			endColor = new ColorType(begin.c);
		}
		else{
			x = begin.x;
			y = begin.y;
			endx = end.x;
			endy = end.y;
			beginColor = new ColorType(begin.c);
			endColor = new ColorType(end.c); 
		}
		
		// Draw begin and end first
		img.setRGB(begin.x, img.getHeight() - begin.y - 1, begin.c.getBRGUint8());
		img.setRGB(end.x, img.getHeight() - end.y - 1, end.c.getBRGUint8());
		
		int[] arrayColorBegin = {0,0,0}, arrayColorEnd = {0,0,0};
		int[] colorGradient = {0,0,0}, accuError = {0,0,0}, currentColor = {0,0,0};
		
		this.setupColor(y, currentColor, colorGradient, arrayColorBegin, arrayColorEnd, beginColor, endColor);
		
		// Increase x per step
		if(dy < dx){
			
			
			
			for(int e = 2*dy - dx; x < endx; x++){
				
				
			}
		}
		
		
		
	}
	



	private calculateColor(int[] currentColor, int[] colorGradient, int[])

	// Extract rgb component separately. Store them in a array.
	private void setupColor(int steps, int[] cur, int[] gradient, int[] arraybegin, int[] arrayend, ColorType beginColor, ColorType endColor){
		
		for(int i = 0; i < 3; i++){
			arraybegin[i] = (beginColor.getBRGUint8() >> (16 - 8*i)) & 0xff;
			arrayend[i] = (endColor.getBRGUint8() >> (16 - 8*i)) & 0xff;
			gradient[i] = (arrayend[i] - arraybegin[i]) / steps;
			
			// there may have some color value remains
			cur[i] = (arrayend[i] - arraybegin[i]) - gradient[i] * steps;
		}
		
	}
	
	
}
