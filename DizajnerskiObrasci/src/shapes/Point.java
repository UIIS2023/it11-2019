package shapes;

import java.awt.Color;
import java.awt.Graphics;

public class Point extends Shape{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int x;
	private int y;
	
	public Point() {
		
	}
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Point(int x, int y, Color color) {
		this(x,y);
		this.setColor(color);
	}
	
	public Point(int x, int y, boolean selected) {
		this(x,y);
		this.setSelected(selected);
	}
	
	public Point(int x, int y, boolean selected, Color color) {
		this(x,y, selected);
		this.setColor(color);
	}
	
	public double getDistance(int x, int y) {
		return Math.sqrt(Math.pow(this.x-x, 2) + Math.pow(this.y-y, 2));
	}
	
	public String toString()
	{
		return x + "," + y + "," + isSelected() + "," + getColor().getRed() + "," + getColor().getGreen() + "," + getColor().getBlue();
	}

	public void draw(Graphics g) {
		
		g.setColor(getColor());
		
		g.drawLine(x - 2, y , x + 2 , y);
		g.drawLine(x, y - 2, x, y + 2);
		
		if(isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(this.x-3, this.y-3, 6, 6);
		}
	}
	
	@Override
	public void moveBy(int byX, int byY) {
		this.x += byX;
		this.y += byY;
	}

	@Override
	public int compareTo(Shape o) {
		if(o instanceof Point) {
			return (int) (this.getDistance(((Point)o).getX(), ((Point)o).getY()));
		}
		return 0;
		
	}

	@Override
	public boolean contains(Point p) {
		return this.getDistance(p.getX(), p.getY()) <= 3;
	}
	
	public boolean equals(Object o) {
		if(o instanceof Point) {
			return ((Point)o).getX() == this.x && ((Point)o).getY() == this.y;
		}
		else {
			return false;
		}
	}

	public int getX() {
		return x;
	}

	public void setX(int x) throws NumberFormatException{
		if(x >= 0) {
			this.x = x;
		}
		else {
			throw new NumberFormatException("X can't be negative!");
		}
		
	}

	public int getY() {
		return y;
	}

	public void setY(int y) throws NumberFormatException {
		if(y >= 0) {
			this.y = y;
		}
		else {
			throw new NumberFormatException("Y can't be negative!");
		}
	}
	
    public Point clone() {
    	return new Point(x, y, getColor());
    }

	
}
