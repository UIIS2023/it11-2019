package strategy;

import mvc.Model.DrawingModel;
import mvc.View.DrawingFrame;

public interface Save {
	void saveLog(DrawingFrame frame);
	void saveDrawing(DrawingModel model);
}
