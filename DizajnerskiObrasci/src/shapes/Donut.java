package shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;


public class Donut extends Circle{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int innerRadius;
	
	public Donut() {
		
	}
	
	public Donut(Point center, int radius, int innerRadius) {
		super(center, radius); 
		this.innerRadius = innerRadius;
	}
	
	public Donut(Point center, int radius, int innerRadius, Color color, Color innerColor) {
		this(center, radius, innerRadius);
		this.setColor(color);
		this.setInnerColor(innerColor);
	}
	
	public Donut(Point center, int radius, int innerRadius, boolean selected) {
		this(center, radius, innerRadius);
		this.setSelected(selected);
	}
	
	public Donut(Point center, int radius, int innerRadius, boolean selected, Color color) {
		this(center, radius, innerRadius, selected);
		this.setColor(color);
	}
	
	public Donut(Point center, int radius, int innerRadius, boolean selected, Color color, Color innerColor) {
		this(center, radius, innerRadius, selected, color);
		this.setInnerColor(innerColor);
	}
	
	public double area() {
		return super.area() - innerRadius * innerRadius * Math.PI;
	}
	
	public int compareTo(Shape o) {
		if (o instanceof Donut) {
			return (int) (this.area() - ((Donut) o).area());
		}
		return 0;
	}

	public void draw(Graphics g) {
		Shape donut = transparentDonut();
		g.setColor(getInnerColor());
		((Graphics2D) g).fill(donut);
		g.setColor(getColor());
        ((Graphics2D) g).draw(donut);
        
        if (isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(this.getCenter().getX()-3, this.getCenter().getY()-3, 6, 6);
			g.drawRect(this.getCenter().getX()-super.getRadius()-3, this.getCenter().getY()-3, 6, 6);
			g.drawRect(this.getCenter().getX()+super.getRadius()-3, this.getCenter().getY()-3, 6, 6);
			g.drawRect(this.getCenter().getX()-3, this.getCenter().getY()-super.getRadius()-3, 6, 6);
			g.drawRect(this.getCenter().getX()-3, this.getCenter().getY()+super.getRadius()-3, 6, 6);
		}
        
	}
	
	public boolean equals(Object o) {
		if (o instanceof Donut) {
			
			if (this.getCenter().equals(((Donut)o).getCenter()) 
					&& this.getRadius() == ((Donut)o).getRadius()
					&& this.innerRadius == ((Donut)o).innerRadius) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public boolean contains(Point p) {
		double dFromCenter = this.getCenter().getDistance(p.getX(), p.getY());
		return super.contains(p) && dFromCenter > innerRadius;
	}
	
	public String toString() {
		return (super.getCenter().getX() + "," + super.getCenter().getY()) + "," + super.getRadius() + "," + innerRadius + "," + isSelected() + "," + getColor().getRed() + "," + getColor().getGreen() + "," + getColor().getBlue() + "," + getInnerColor().getRed() + "," + getInnerColor().getGreen() + "," + getInnerColor().getBlue();
	}	
	
	public int getInnerRadius() {
		return innerRadius;
	}

	public void setInnerRadius(int innerRadius) throws NumberFormatException {
		if(innerRadius > 0 && innerRadius < this.getRadius()) {
			this.innerRadius = innerRadius;
		}
		else {
			throw new NumberFormatException("Inner radius must be positive and less than radius!");
		}
	}
	
	private Shape transparentDonut() {
		Ellipse2D inner = new Ellipse2D.Double(this.getCenter().getX() - this.innerRadius, this.getCenter().getY() - this.innerRadius,
				this.innerRadius * 2, this.innerRadius * 2);
		Ellipse2D outter = new Ellipse2D.Double(super.getCenter().getX() - super.getRadius(),
				super.getCenter().getY() - super.getRadius(), super.getRadius() * 2, super.getRadius() * 2);
		
		Area area = new Area(outter);
        area.subtract(new Area(inner));
        
        return area;
	}
	
	public Donut clone() {
		return new Donut(super.getCenter().clone(), super.getRadius(), innerRadius, getColor(), getInnerColor());
	}
	
	
	
}
