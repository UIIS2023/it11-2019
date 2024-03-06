package shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public abstract class Shape implements Moveable, Comparable<Shape>, Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean selected;
	private Color color;
	
	public Shape() {
		
	}
	
	public Shape(Color color) {
		this.color=color;
	}
	
	public Shape(Color color, boolean selected) {
		this(color);
		this.selected=selected;
	}
	
	public abstract boolean contains(Point p);
	public abstract void draw(Graphics g);
	
	public boolean isSelected() {
		return selected;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	
}
