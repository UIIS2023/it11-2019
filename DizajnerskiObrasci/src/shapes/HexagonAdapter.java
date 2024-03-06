package shapes;

import java.awt.Color;
import java.awt.Graphics;
import hexagon.Hexagon;

public class HexagonAdapter extends SurfaceShape {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Hexagon hexagon;
	
	public HexagonAdapter(Hexagon hexagon) {
		this.hexagon = hexagon;
	}
	
	public HexagonAdapter(Hexagon hexagon, Color color, Color innerColor) {
		this.hexagon = hexagon;
		hexagon.setBorderColor(color);
		hexagon.setAreaColor(innerColor);
	}
	
	public HexagonAdapter(Hexagon hexagon, boolean selected, Color color, Color innerColor) {
		this.hexagon = hexagon;
		hexagon.setBorderColor(color);
		hexagon.setAreaColor(innerColor);
		hexagon.setSelected(selected);
	}

	@Override
	public void moveBy(int byX, int byY) {
		this.hexagon.setX(byX);
		this.hexagon.setY(byY);
	}

	@Override
	public int compareTo(Shape o) {
		if(o instanceof HexagonAdapter)
			return this.hexagon.getR() - ((HexagonAdapter) o).getR();
		
		return 0;
	}

	@Override
	public boolean contains(Point p) {
		return hexagon.doesContain(p.getX(), p.getY());
	}
	
	@Override
	public void fill(Graphics g) {
		hexagon.paint(g);
	}

	@Override
	public void draw(Graphics g) {
		if(isSelected()) {
			hexagon.setSelected(true);
		} else {
			hexagon.setSelected(false);
		}
		
		this.fill(g);
	}
	
	public String toString() {
		return (hexagon.getX() + "," + hexagon.getY() + "," + hexagon.getR()) + "," + hexagon.isSelected() + "," + hexagon.getBorderColor().getRed() + "," + hexagon.getBorderColor().getGreen() + "," + hexagon.getBorderColor().getBlue() + "," + hexagon.getAreaColor().getRed() + "," + hexagon.getAreaColor().getGreen() + "," + hexagon.getAreaColor().getBlue();
	}

	public int getX() {
		return hexagon.getX();
	}
	
	public int getY() {
		return hexagon.getY();
	}
	
	public int getR() {
		return hexagon.getR();
	}
	
	public void setX(int x) {
		hexagon.setX(x);
	}
	
	public void setY(int y) {
		hexagon.setY(y);
	}
	
	public void setR(int r) {
		hexagon.setR(r);
	}
	
	public void setColor(Color color) {
		hexagon.setBorderColor(color);
	}
	
	public void setInnerColor(Color innerColor) {
		hexagon.setAreaColor(innerColor);
	}
	
	public Color getColor() {
		return hexagon.getBorderColor();
	}
	
	public Color getInnerColor() {
		return hexagon.getAreaColor();
	}
	
	public HexagonAdapter clone() {
		Hexagon hexagon = new Hexagon(getX(), getY(), getR());
		hexagon.setBorderColor(getColor());
		hexagon.setAreaColor(getInnerColor());
		
		return new HexagonAdapter(hexagon);
	}

}
