package mvc.View;

import java.util.Iterator;
import java.awt.Graphics;
import javax.swing.JPanel;

import mvc.Model.DrawingModel;
import shapes.Shape;

public class DrawingView extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DrawingModel model = new DrawingModel();

	public void setModel(DrawingModel model) {
		this.model = model;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Iterator<Shape> it = model.getShapes().iterator();
		while (it.hasNext()) {
			it.next().draw(g);
		}
	}
	
	
	
}
