//****************************************************************************
//       Example Main Program for CS480 Lab 1
//****************************************************************************
// Description: 
//   
//   This is a template program for the sketching tool. 
//
//     LEFTMOUSE: add a vertex 
//
//     The following keys control the program:
//
//      Q,q: quit //      T,t: cycle through test cases
//      C,c: clear polygon (set vertex count=0)
//      R,r: randomly change the color
//
//****************************************************************************
// History :
//   Aug 2014 Created by Jianming Zhang based on the C
//   code by Stan Sclaroff
//


import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.*; 
import java.awt.image.*;
import java.nio.ByteBuffer;
import java.util.*;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.awt.GLCanvas;//for new version of gl
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import com.jogamp.opengl.util.FPSAnimator;//for new version of gl


public class lab1 extends JFrame
	implements GLEventListener, KeyListener, MouseListener, MouseMotionListener
{
	private final int DEFAULT_WINDOW_WIDTH=512;
	private final int DEFAULT_WINDOW_HEIGHT=512;
	private final float DEFAULT_LINE_WIDTH=1.0f;

	private GLCapabilities capabilities;
	private GLCanvas canvas;
	private FPSAnimator animator;

	private BufferedImage buff;
	private ColorType color;
	private Random rng;
	
	private ArrayList<Point2D> rectangles;
	private ArrayList<Point2D> triangles;


	public lab1()
	{
	    capabilities = new GLCapabilities(null);
	    capabilities.setDoubleBuffered(true);  // Enable Double buffering

	    canvas  = new GLCanvas(capabilities);
	    canvas.addGLEventListener(this);
	    canvas.addMouseListener(this);
	    canvas.addMouseMotionListener(this);
	    canvas.addKeyListener(this);
	    canvas.setAutoSwapBufferMode(true); // true by default. Just to be explicit
	    getContentPane().add(canvas);

	    animator = new FPSAnimator(canvas, 60); // drive the display loop @ 60 FPS

	    setTitle("CS480/680 Lab1");
	    setSize( DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setVisible(true);
	    setResizable(false);
	    
	    rng = new Random();
	    color = new ColorType(1.0f,0.0f,0.0f);
	    rectangles = new ArrayList<Point2D>();
	    triangles = new ArrayList<Point2D>();
	}

	public void run()
	{
		animator.start();
	}

	public static void main( String[] args )
	{
	    lab1 P = new lab1();
	    P.run();
	}

	//*********************************************** 
	//  GLEventListener Interfaces
	//*********************************************** 
	public void init( GLAutoDrawable drawable) 
	{
	    GL gl = drawable.getGL();
	    gl.glClearColor( 0.0f, 0.0f, 0.0f, 0.0f);
	    gl.glLineWidth( DEFAULT_LINE_WIDTH );
	    Dimension sz = this.getContentPane().getSize();
	    buff = new BufferedImage(sz.width,sz.height,BufferedImage.TYPE_3BYTE_BGR);
	    Graphics2D g = buff.createGraphics();
	    g.setColor(Color.BLACK);
	    g.fillRect(0, 0, buff.getWidth(), buff.getHeight());
	    g.dispose();
	}

	// Redisplaying graphics
	public void display(GLAutoDrawable drawable)
	{
	    GL2 gl = drawable.getGL().getGL2();
	    WritableRaster wr = buff.getRaster();
	    DataBufferByte dbb = (DataBufferByte) wr.getDataBuffer();
	    byte[] data = dbb.getData();

	    gl.glPixelStorei(GL2.GL_UNPACK_ALIGNMENT, 1);
	    gl.glDrawPixels (buff.getWidth(), buff.getHeight(),
                GL2.GL_BGR, GL2.GL_UNSIGNED_BYTE,
                ByteBuffer.wrap(data));
	}

	// Window size change
	public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h)
	{
		// deliberately left blank
	}
	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
	      boolean deviceChanged)
	{
		// deliberately left blank
	}


	//*********************************************** 
	//          KeyListener Interfaces
	//*********************************************** 
	public void keyTyped(KeyEvent key)
	{
	//      Q,q: quit
	//      T,t: cycle through test cases
	//      F,f: toggle polygon fill off/on
	//      C,c: clear polygon (set vertex count=0)
	//      A,a: toggle applying inside outside test on each pixel

	    switch ( key.getKeyChar() ) 
	    {
	    case 'Q' :
	    case 'q' : 
	    	new Thread()
	    	{
	          	public void run() { animator.stop(); }
	        }.start();
	        System.exit(0);
	        break;
	    case 'R' :
	    case 'r' :
	    	color = new ColorType(rng.nextFloat(),rng.nextFloat(),
	    			rng.nextFloat());
	    	break;
	    case 'C' :
	    case 'c' :
	    	rectangles.clear();
	    	triangles.clear();
	    	Graphics2D g = buff.createGraphics();
		    g.setColor(Color.BLACK);
		    g.fillRect(0, 0, buff.getWidth(), buff.getHeight());
		    g.dispose();
	    	break;
	    default :
	        break;
	    }
	}

	public void keyPressed(KeyEvent key)
	{
	    switch (key.getKeyCode()) 
	    {
	    case KeyEvent.VK_ESCAPE:
	    	new Thread()
	        {
	    		public void run()
	    		{
	    			animator.stop();
	    		}
	        }.start();
	        System.exit(0);
	        break;
	      default:
	        break;
	    }
	}

	public void keyReleased(KeyEvent key)
	{
		// deliberately left blank
	}

	//************************************************** 
	// MouseListener and MouseMotionListener Interfaces
	//************************************************** 
	public void mouseClicked(MouseEvent mouse)
	{
		// deliberately left blank
	}

	public void mousePressed(MouseEvent mouse)
	{
		int button = mouse.getButton();

	    // Add a new vertex using left mouse click
	    if ( button  == MouseEvent.BUTTON1 )
	    {
	    	int x = mouse.getX();
	    	int y = mouse.getY();
	    	rectangles.add(new Point2D(x,y,color));
	    	
	    	if (rectangles.size()%2 == 1)
	    		SketchBase.drawPoint(buff, rectangles.get(rectangles.size()-1));
	    	else if (rectangles.size() > 0)
	    		SketchBase.drawRect(buff, rectangles.get(rectangles.size()-2),
	    				rectangles.get(rectangles.size()-1));
	    }
	    // Pick the vertex closest to mouse down event using right button
	    else if ( button == MouseEvent.BUTTON3 )
	    {
	    	// Deliberately left blank
	    }

	}

	public void mouseReleased(MouseEvent mouse)
	{
	    // Deliberately left blank
	}

	public void mouseMoved( MouseEvent mouse)
	{
		// Deliberately left blank
	}

	public void mouseDragged( MouseEvent mouse )
	{
		// Deliberately left blank
	}

	public void mouseEntered( MouseEvent mouse)
	{
		// Deliberately left blank
	}

	public void mouseExited( MouseEvent mouse)
	{
		// Deliberately left blank
	} 


	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		
	}
}
