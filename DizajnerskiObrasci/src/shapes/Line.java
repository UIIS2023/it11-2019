package shapes;

import java.awt.Color;
import java.awt.Graphics;

public class Line extends Shape{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Point startPoint;
	private Point endPoint;
	
	public Line() {
		
	}
	
	public Line(Point startPoint, Point endPoint) {
		this.startPoint=startPoint;
		this.endPoint=endPoint;
	}
	
	public Line(Point startPoint, Point endPoint, Color color) {
		this(startPoint, endPoint);
		this.setColor(color);
	}
	
	public Line(Point startPoint, Point endPoint, boolean selected) {
		this(startPoint,endPoint);
		this.setSelected(selected);
	}
	
	public Line(Point startPoint, Point endPoint, boolean selected, Color color) {
		this(startPoint,endPoint, selected);
		this.setColor(color);
	}
	
	public double length() {
		return startPoint.getDistance(endPoint.getX(), endPoint.getY());
	}
	
	@Override
	public int compareTo(Shape o) {
		if(o instanceof Line) {
			return (int) (this.length() - ((Line) o).length());
		}
		return 0;
	}
	
	@Override
	public void moveBy(int byX, int byY) {
		this.startPoint.moveBy(byX, byY);
		this.endPoint.moveBy(byX, byY);
	}

	@Override
	public boolean contains(Point p) {
		return ((startPoint.getDistance(p.getX(), p.getY()) + endPoint.getDistance(p.getX(), p.getY())) - length()) <= 0.05;
	}
	
	public String toString() {
		return (startPoint.getX() + "," + startPoint.getY()) + "," + (endPoint.getX() + "," + endPoint.getY()) + "," + isSelected() + "," + getColor().getRed() + "," + getColor().getGreen() + "," + getColor().getBlue();
	}
	
	public Point middleOfLine() {
		int middleByX = (this.startPoint.getX() + this.endPoint.getX()) / 2;
		int middleByY = (this.startPoint.getY() + this.endPoint.getY()) / 2;
		return new Point(middleByX, middleByY);
	}
	
	public boolean equals(Object o) {
		if(o instanceof Line) {
			return (((Line)o).getStartPoint().equals(this.startPoint) && ((Line)o).getEndPoint().equals(this.endPoint));
		}
		else {
			return false;
		}
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(getColor());
		g.drawLine(this.startPoint.getX(), this.startPoint.getY(), this.endPoint.getX(), this.endPoint.getY());
		
		if(isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(this.startPoint.getX()-3, this.startPoint.getY()-3, 6, 6);
			g.drawRect(this.endPoint.getX()-3, this.endPoint.getY()-3, 6, 6);
			g.drawRect(this.middleOfLine().getX()-3, this.middleOfLine().getY()-3, 6, 6);
		}
		
	}


	public Point getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(Point startPoint) throws NumberFormatException {
		if(startPoint.getX() >= 0 && startPoint.getY() >=0) {
			this.startPoint=startPoint;
		} else {
			throw new NumberFormatException("Start point coordinates must be positive!");
		}
	}

	public Point getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(Point endPoint) throws NumberFormatException {
		if(endPoint.getX() >= 0 && endPoint.getY() >=0) {
			this.endPoint=endPoint;
		} else {
			throw new NumberFormatException("End point coordinates must be positive!");
		}
		
	}
	
	public Line clone() {
		return new Line(startPoint.clone(), endPoint.clone(), getColor());
	}

	




	
}
