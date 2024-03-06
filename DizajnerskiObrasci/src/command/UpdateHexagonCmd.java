package command;

import shapes.HexagonAdapter;

public class UpdateHexagonCmd implements Command {
	
	private HexagonAdapter oldState;
	private HexagonAdapter newState;
	private HexagonAdapter original;
	
	public UpdateHexagonCmd(HexagonAdapter oldState, HexagonAdapter newState) {
		this.oldState = oldState;
		this.newState = newState;
	}

	@Override
	public void execute() {
		original = oldState.clone();
		
		oldState.setX(newState.getX());
		oldState.setY(newState.getY());
		oldState.setR(newState.getR());
		oldState.setInnerColor(newState.getInnerColor());
		oldState.setColor(newState.getColor());
	}

	@Override
	public void unexecute() {
		oldState.setX(original.getX());
		oldState.setY(original.getY());
		oldState.setR(original.getR());
		oldState.setInnerColor(original.getInnerColor());
		oldState.setColor(original.getColor());
	}

	@Override
	public String log() {
		return "Modified " + oldState.getClass().getSimpleName() + " " + oldState.toString() + "\n";
	}

}
