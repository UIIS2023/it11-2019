package command;

import shapes.Circle;

public class UpdateCircleCmd implements Command {
	
	private Circle oldState;
	private Circle newState;
	private Circle original;
	
	public UpdateCircleCmd(Circle oldState, Circle newState) {
		this.oldState = oldState;
		this.newState = newState;
	}

	@Override
	public void execute() {
		original = oldState.clone();
		
		oldState.getCenter().setX(newState.getCenter().getX());
		oldState.getCenter().setY(newState.getCenter().getY());
		oldState.setRadius(newState.getRadius());
		oldState.setInnerColor(newState.getInnerColor());
		oldState.setColor(newState.getColor());
	}

	@Override
	public void unexecute() {
		oldState.getCenter().setX(original.getCenter().getX());
		oldState.getCenter().setY(original.getCenter().getY());
		oldState.setRadius(original.getRadius());
		oldState.setInnerColor(original.getInnerColor());
		oldState.setColor(original.getColor());
	}

	@Override
	public String log() {
		return "Modified " + oldState.getClass().getSimpleName() + " " + oldState.toString() + "\n";
	}

}
