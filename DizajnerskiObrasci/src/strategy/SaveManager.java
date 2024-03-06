package strategy;

import mvc.Model.DrawingModel;
import mvc.View.DrawingFrame;

public class SaveManager implements Save {
	
	private LogDrawingSave logDrawingSave;
	
	public SaveManager(LogDrawingSave textSaveLoad) {
		this.logDrawingSave = textSaveLoad;
	}

	@Override
	public void saveLog(DrawingFrame frame) {
		logDrawingSave.saveLog(frame);
	}

	@Override
	public void saveDrawing(DrawingModel model) {
		logDrawingSave.saveDrawing(model);
	}


}
