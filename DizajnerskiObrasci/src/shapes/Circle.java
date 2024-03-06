package shapes;

import java.awt.Color;
import java.awt.Graphics;

public class Circle extends SurfaceShape{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int radius;
	private Point center;
	
	public Circle() {
		
	}
	
	public Circle(Point center, int radius) {
		this.radius = radius;
		this.center = center;
	}
	
	public Circle(Point center, int radius, Color color, Color innerColor) {
		this(center, radius);
		this.setColor(color);
		this.setInnerColor(innerColor);
	}
	
	public Circle(Point center, int radius, boolean selected) {
		this(center, radius);
		this.setSelected(selected);
	}
	
	public Circle(Point center, int radius, boolean selected, Color color) {
		this(center, radius, selected);
		this.setColor(color);
	}
	
	public Circle(Point center, int radius, boolean selected, Color color, Color innerColor) {
		this(center, radius, selected, color);
		this.setInnerColor(innerColor);
	}
	
	public double area() {
		return radius * radius * Math.PI;
	}
	
	@Override
	public void moveBy(int byX, int byY) {
		this.center.moveBy(byX, byY);
	}

	@Override
	public int compareTo(Shape o) {
		if(o instanceof Circle) {
			return this.radius - ((Circle)o).radius; 
		}
		return 0;
	}

	@Override
	public boolean contains(Point p) {
		return this.center.getDistance(p.getX(), p.getY()) <= radius;
	}

	@Override
	public void fill(Graphics g) {
		g.setColor(getInnerColor());
		g.fillOval(this.center.getX() - this.radius + 1, this.center.getY() - this.radius + 1,
				this.radius*2 - 2, this.radius*2 - 2);
		
	}
	
	public String toString() {
		return (center.getX() + "," + center.getY()) + "," + radius + "," + isSelected() + "," + getColor().getRed() + "," + getColor().getGreen() + "," + getColor().getBlue() + "," + getInnerColor().getRed() + "," + getInnerColor().getGreen() + "," + getInnerColor().getBlue();
	}
	
	public boolean equals(Object o) {
		if(o instanceof Circle) {
			return (this.radius == ((Circle)o).radius && this.center.equals(((Circle) o).center));
		}
		return false;
	}
	
	public void draw(Graphics g) {
		g.setColor(getColor());
		g.drawOval(center.getX() - radius, center.getY() - radius, radius * 2, radius * 2);
		
		this.fill(g);
		
		if (isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(this.center.getX()-3, this.center.getY()-3, 6, 6);
			g.drawRect(this.center.getX()-this.radius-3, this.center.getY()-3, 6, 6);
			g.drawRect(this.center.getX()+this.radius-3, this.center.getY()-3, 6, 6);
			g.drawRect(this.center.getX()-3, this.center.getY()-this.radius-3, 6, 6);
			g.drawRect(this.center.getX()-3, this.center.getY()+this.radius-3, 6, 6);
		}
		
		
	}
	

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) throws NumberFormatException{
		if(radius > 0) {
			this.radius = radius;
		}
		else {
			throw new NumberFormatException("Radius must be positive!");
		}
	}

	public Point getCenter() {
		return center;
	}

	public void setCenter(Point center) throws NumberFormatException {
		if(center.getX() >= 0 && center.getY() >= 0)
		{
			this.center = center;
		} else {
			throw new NumberFormatException("Center coordinates must be positive!");
		}
		
	}
	
	public Circle clone() {
		return new Circle(center.clone(), radius, getColor(), getInnerColor());
	}
	
	
}
