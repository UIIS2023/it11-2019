package command;

import java.awt.Color;
import shapes.Shape;

public class ChangeColorCmd implements Command {
	
	private Color oldColor;
	private Color newColor;
	private Color originalColor;
	private Shape shape;
	
	public ChangeColorCmd(Color oldColor, Color newColor, Shape shape) {
		this.oldColor = oldColor;
		this.newColor = newColor;
		this.shape = shape;
	}
	@Override
	public void execute() {
		originalColor = oldColor;
		oldColor = newColor;
		shape.setColor(newColor);
	}

	@Override
	public void unexecute() {
		oldColor = originalColor;
		shape.setColor(originalColor);
	}

	@Override
	public String log() {
		return "Changed_color " + shape.getClass().getSimpleName() + " " + shape.toString() + "\n";
	}

}
