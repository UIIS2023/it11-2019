package command;

import shapes.Donut;

public class UpdateDonutCmd implements Command {
	
	private Donut oldState;
	private Donut newState;
	private Donut original;
	
	public UpdateDonutCmd(Donut oldState, Donut newState) {
		this.oldState = oldState;
		this.newState = newState;
	}

	@Override
	public void execute() {
		original = oldState.clone();
		
		oldState.getCenter().setX(newState.getCenter().getX());
		oldState.getCenter().setY(newState.getCenter().getY());
		oldState.setRadius(newState.getRadius());
		oldState.setInnerRadius(newState.getInnerRadius());
		oldState.setInnerColor(newState.getInnerColor());
		oldState.setColor(newState.getColor());

	}

	@Override
	public void unexecute() {
		oldState.getCenter().setX(original.getCenter().getX());
		oldState.getCenter().setY(original.getCenter().getY());
		oldState.setRadius(original.getRadius());
		oldState.setInnerRadius(original.getInnerRadius());
		oldState.setInnerColor(original.getInnerColor());
		oldState.setColor(original.getColor());
	}

	@Override
	public String log() {
		return "Modified " + oldState.getClass().getSimpleName() + " " + oldState.toString() + "\n";
	}

}
