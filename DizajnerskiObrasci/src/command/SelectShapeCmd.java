package command;

import mvc.Model.DrawingModel;
import shapes.Shape;

public class SelectShapeCmd implements Command {

	private Shape shape;
	private boolean selected;
	private DrawingModel model;
	
	public SelectShapeCmd(Shape shape, DrawingModel model, boolean selected) {
		this.shape = shape;
		this.model = model;
		this.selected = selected;
	}
	
	@Override
	public void execute() {
		shape.setSelected(selected);
		if(selected == true)
			model.getSelectedShapes().add(shape);
		else
			model.getSelectedShapes().remove(shape);
	}
	
	@Override
	public void unexecute() {	
		shape.setSelected(!selected);
		if(selected == true)
			model.getSelectedShapes().remove(shape);
		else
			model.getSelectedShapes().add(shape);
	}


	@Override
	public String log() {
		return "Selected " + shape.getClass().getSimpleName() + " " + shape.toString() + "\n";
	}

}
