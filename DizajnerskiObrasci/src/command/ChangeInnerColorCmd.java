package command;

import java.awt.Color;

import shapes.Shape;
import shapes.SurfaceShape;

public class ChangeInnerColorCmd implements Command {
	
	private Color oldColor;
	private Color newColor;
	private Color originalColor;
	private Shape shape;
	
	public ChangeInnerColorCmd(Color oldColor, Color newColor, Shape shape) {
		this.oldColor = oldColor;
		this.newColor = newColor;
		this.shape = shape;
	}

	@Override
	public void execute() {
		originalColor = oldColor;
		oldColor = newColor;
		((SurfaceShape) shape).setInnerColor(newColor);
	}

	@Override
	public void unexecute() {
		oldColor = originalColor;
		((SurfaceShape) shape).setInnerColor(originalColor);
	}

	@Override
	public String log() {
		return "Changed_inner_color " + shape.getClass().getSimpleName() + " " + shape.toString() + "\n";
	}

}
