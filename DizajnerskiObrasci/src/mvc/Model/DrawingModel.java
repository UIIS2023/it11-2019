package mvc.Model;

import java.io.Serializable;
import java.util.ArrayList;

import shapes.Shape;

public class DrawingModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Shape> shapes = new ArrayList<Shape>();
	private ArrayList<Shape> selectedShapes = new ArrayList<Shape>();

	public void add(Shape shape) {
		shapes.add(shape);
	}
	
	public void remove(Shape shape) {
		shapes.remove(shape);
	}
	
	public Shape get(int index) {
		return shapes.get(index);
	}
	
	public Shape getSelectedShape() {
		return selectedShapes.get(0);
	}

	public ArrayList<Shape> getShapes() {
		return shapes;
	}
	
	public ArrayList<Shape> getSelectedShapes() {
		return selectedShapes;
	}
	
	public void addSelectedShape(Shape selectedShape) {
		selectedShapes.add(selectedShape);
	}
	
	public int getIndex(Shape selectedShape) {
		return shapes.indexOf(selectedShape);
	}
	
	public void removeAll(ArrayList<Shape> shapeList) {
		shapes.removeAll(shapeList);
	}

}
