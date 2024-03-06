package command;

import mvc.Model.DrawingModel;
import shapes.Shape;


public class RemoveShapeCmd implements Command {

	private DrawingModel model;
	private Shape shape;
	
	public RemoveShapeCmd(DrawingModel model, Shape shape) {
		this.model = model;
		this.shape = shape;
	}

	@Override
	public void execute() {
		model.remove(shape);
	}

	@Override
	public void unexecute() {
		model.add(shape);
	}

	@Override
	public String log() {
		return "Deleted " + shape.getClass().getSimpleName() + " " + shape.toString() + "\n";
	}

}
