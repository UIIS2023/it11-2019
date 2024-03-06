package command;

import mvc.Model.DrawingModel;

public class BringToBackCmd implements Command {
	
	private DrawingModel model;
	private int index;
	
	public BringToBackCmd(DrawingModel model, int index) {
		this.model = model;
		this.index = index;
	}

	@Override
	public void execute() {
		model.getShapes().add(0, model.getSelectedShape());
		model.getShapes().remove(index + 1);
	}

	@Override
	public void unexecute() {
		model.getShapes().remove(0);
		model.getShapes().add(index, model.getSelectedShape());
	}

	@Override
	public String log() {
		return "Brought_to_back " + model.getSelectedShape().getClass().getSimpleName() + " " + model.getSelectedShape().toString() + "\n";
	}

}
