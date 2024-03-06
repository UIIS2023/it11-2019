package command;

import mvc.Model.DrawingModel;

public class BringToFrontCmd implements Command {
	
	private DrawingModel model;
	private int index;
	
	public BringToFrontCmd(DrawingModel model, int index) {
		this.model = model;
		this.index = index;
	}

	@Override
	public void execute() {
		model.getShapes().add(model.getShapes().size(), model.getSelectedShape());
		model.getShapes().remove(index);
	}

	@Override
	public void unexecute() {
		model.getShapes().remove(model.getShapes().size() - 1);
		model.getShapes().add(index, model.getSelectedShape());
	}

	@Override
	public String log() {
		return "Brought_to_front " + model.getSelectedShape().getClass().getSimpleName() + " " + model.getSelectedShape().toString() + "\n";
	}

}
