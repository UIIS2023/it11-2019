package command;

import java.util.Collections;
import mvc.Model.DrawingModel;

public class ToFrontCmd implements Command {
	
	private DrawingModel model;
	private int index;

	public ToFrontCmd(DrawingModel model, int index) {
		this.model = model;
		this.index = index;
	}

	@Override
	public void execute() {
		Collections.swap(model.getShapes(), index, index + 1);
	}

	@Override
	public void unexecute() {
		Collections.swap(model.getShapes(), index + 1, index);
	}

	@Override
	public String log() {
		return "To_front " + model.getSelectedShape().getClass().getSimpleName() + " " + model.getSelectedShape().toString() + "\n";
	}

}
