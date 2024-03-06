package shapes;

import java.awt.Color;
import java.awt.Graphics;

public class Rectangle extends SurfaceShape{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Point upperLeftPoint;
	private int width;
	private int height;
	
	public Rectangle() {
		
	}

	public Rectangle(Point upperLeftPoint, int width, int height) {
		this.upperLeftPoint = upperLeftPoint;
		this.width = width;
		this.height = height;
	}
	
	public Rectangle(Point upperLeftPoint, int width, int height, Color color, Color innerColor) {
		this(upperLeftPoint, width, height);
		this.setColor(color);
		this.setInnerColor(innerColor);
	}
	
	public Rectangle(Point upperLeftPoint, int width, int height, boolean selected) {
		this(upperLeftPoint, width, height);
		this.setSelected(selected);
	}
	
	 public Rectangle(Point upperLeftPoint, int width, int height, boolean selected, Color color) {
		this(upperLeftPoint, width, height, selected);
		this.setColor(color);
	}
	 
	 public Rectangle(Point upperLeftPoint, int width, int height, boolean selected, Color color, Color innerColor) {
		 this(upperLeftPoint, width, height, selected, color);
		 this.setInnerColor(innerColor);
	 }
	 
	 public boolean contains(Point p) {
		 return (upperLeftPoint.getX() <= p.getX() &&
					upperLeftPoint.getY() <= p.getY() &&
					p.getX() <= upperLeftPoint.getX() + width &&
					p.getY() <= upperLeftPoint.getY() + height);
	}
		
	 public int area() {
		 return width * height;
	}
	 
	 public int compareTo(Shape o) {
			if (o instanceof Rectangle) {
				return this.area() - ((Rectangle) o).area();
			}
			return 0;
		}
	 
	 public String toString() {
		return (upperLeftPoint.getX() + "," + upperLeftPoint.getY()) + "," + width + "," + height + "," + isSelected() + "," + getColor().getRed() + "," + getColor().getGreen() + "," + getColor().getBlue() + "," + getInnerColor().getRed() + "," + getInnerColor().getGreen() + "," + getInnerColor().getBlue();	
	}
	 
	 public void draw(Graphics g) {	
		g.setColor(getColor());	
		g.drawRect(upperLeftPoint.getX(), upperLeftPoint.getY(), width, height);
		
		this.fill(g);
		
		if(isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(this.upperLeftPoint.getX()-3, this.upperLeftPoint.getY()-3, 6, 6);
			g.drawRect(this.upperLeftPoint.getX()+this.width-3, this.upperLeftPoint.getY()-3, 6, 6);
			g.drawRect(this.upperLeftPoint.getX()-3, this.upperLeftPoint.getY()+this.height-3, 6, 6);
			g.drawRect(this.upperLeftPoint.getX()+this.width-3, this.upperLeftPoint.getY()+this.height-3, 6, 6);
		}
	}
	 
	 public boolean equals(Object o) {
		 if(o instanceof Rectangle) {
			 return (this.upperLeftPoint.equals(((Rectangle) o).upperLeftPoint) &&
					 this.width == ((Rectangle)o).getWidth() &&
					 this.height == ((Rectangle)o).getHeight());
		 }
		 else {
			 return false;
		 }
	 }
	 
	 @Override
		public void moveBy(int byX, int byY) {
			this.upperLeftPoint.moveBy(byX, byY);
		}

	 @Override
		public void fill(Graphics g) {
			g.setColor(getInnerColor());
			g.fillRect(this.upperLeftPoint.getX()+1, this.upperLeftPoint.getY()+1, this.width-1, this.height-1);
			
		}

	public Point getUpperLeftPoint() {
		return upperLeftPoint;
	}

	public void setUpperLeftPoint(Point upperLeftPoint) throws NumberFormatException {
		if(upperLeftPoint.getX() >= 0 && upperLeftPoint.getY() >=0) {
			this.upperLeftPoint=upperLeftPoint;
		} else {
			throw new NumberFormatException("Upper left point coordinates must be positive!");
		}
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) throws NumberFormatException {
		if(width > 0) {
			this.width = width;
		}
		else {
			throw new NumberFormatException("Width can't be negative!");
		}

	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) throws NumberFormatException{
		if(height > 0) {
			this.height = height;
		}
		else {
			throw new NumberFormatException("Height can't be negative!");
		}
	}

    public Rectangle clone() {
    	return new Rectangle(upperLeftPoint.clone(), width, height, getColor(), getInnerColor());
    }
 
	 
}
