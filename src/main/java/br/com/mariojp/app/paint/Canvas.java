package br.com.mariojp.app.paint;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.event.MouseInputAdapter;

public class Canvas extends JComponent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int X1, Y1, X2, Y2;
	private Graphics2D g;
	private Image img, background;
	ArrayList<Shape> shapes = new ArrayList<Shape>();
	private Shape shape;
	private MouseMotionListener motion;
	private MouseListener listener;
	MyMouseListener ml = new MyMouseListener();


	public void save(File file) {
		try {
			ImageIO.write((RenderedImage) img, "PNG", file);
		} catch (IOException ex) {
		}
	}

	public void load(File file) {
		try {
			img = ImageIO.read(file);
			g = (Graphics2D) img.getGraphics();
		}

		catch (IOException ex) {
		}
	}

	protected void paintComponent(Graphics g1) {
		if (img == null) {
			img = createImage(getSize().width, getSize().height);
			g = (Graphics2D) img.getGraphics();
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			clear();
		}
		
		g1.drawImage(img, 0, 0, null);
		
		if (shape != null) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.fill(shape);
		}
	}

	public Canvas() {
		setBackground(Color.WHITE);
		defaultListener();
	}

	public void defaultListener() {
		setDoubleBuffered(false);
		listener = new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				X2 = e.getX();
				Y2 = e.getY();
			}
		};

		motion = new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				X1 = e.getX();
				Y1 = e.getY();

				if (g != null) {
					g.drawLine(X2, Y2, X1, Y1);
					repaint();
					X2 = X1;
					Y2 = Y1;
				}
			}
		};
		addMouseListener(listener);
		addMouseMotionListener(motion);
	}

	public void addShape(Shape shape, Color color) {
		this.shape = shape;
		Graphics2D g2d = (Graphics2D) img.getGraphics();
		g2d.setColor(color);
		g2d.draw(shape);
		repaint();
	}

	public void red() {
		g.setPaint(Color.red);
	}

	public void black() {
		g.setPaint(Color.black);
	}

	public void magenta() {
		g.setPaint(Color.magenta);
	}

	public void green() {
		g.setPaint(Color.green);
	}

	public void blue() {
		g.setPaint(Color.blue);
	}

	public void gray() {
		g.setPaint(Color.GRAY);
	}

	public void orange() {
		g.setPaint(Color.ORANGE);
	}

	public void yellow() {
		g.setPaint(Color.YELLOW);
	}

	public void pink() {
		g.setPaint(Color.PINK);
	}

	public void cyan() {
		g.setPaint(Color.CYAN);
	}

	public void lightGray() {
		g.setPaint(Color.lightGray);
	}

	public void picker(Color color) {
		g.setPaint(color);
	}

	public void clear() {
		if (background != null) {
			setImage(copyImage(background));
		} else {
			g.setPaint(Color.white);
			g.fillRect(0, 0, getSize().width, getSize().height);
			g.setPaint(Color.black);
		}
		repaint();
	}

	public void pencil() {
		removeMouseListener(ml);
		removeMouseMotionListener(ml);
		defaultListener();
		
	}

	public void rect() {
		removeMouseListener(listener);
		removeMouseMotionListener(motion);
		addMouseListener(ml);
		addMouseMotionListener(ml);
	}

	private void setImage(Image img) {
		g = (Graphics2D) img.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setPaint(Color.black);
		this.img = img;
		repaint();
	}

	public void setBackground(Image img) {
		background = copyImage(img);
		setImage(copyImage(img));
	}

	private BufferedImage copyImage(Image img) {
		BufferedImage copyOfImage = new BufferedImage(getSize().width,
				getSize().height, BufferedImage.TYPE_INT_RGB);
		Graphics g = copyOfImage.createGraphics();
		g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
		return copyOfImage;
	}

	public void setThickness(int thick) {
		g.setStroke(new BasicStroke(thick, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
	}

	class MyMouseListener extends MouseInputAdapter
	{
		private Point startPoint;
		Rectangle retangulo;

		public void mousePressed(MouseEvent e)
		{
			startPoint = e.getPoint();
			retangulo = new Rectangle();
		}

		public void mouseDragged(MouseEvent e)
		{
			int x = Math.min(startPoint.x, e.getX());
			int y = Math.min(startPoint.y, e.getY());
			int width = Math.abs(startPoint.x - e.getX());
			int height = Math.abs(startPoint.y - e.getY());

			retangulo.setBounds(x, y, width, height);
			repaint();
		}

		public void mouseReleased(MouseEvent e)
		{
			if (retangulo.width != 0 || retangulo.height != 0)
			{
				addShape(retangulo, e.getComponent().getForeground());
			}

			shape = null;
		}
	}

}

	
