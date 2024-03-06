package mvc;

import java.awt.EventQueue;

import mvc.Controller.DrawingController;
import mvc.Model.DrawingModel;
import mvc.View.DrawingFrame;

public class DrawingApp {

	public static void main(String[] args) {
		
		DrawingModel model = new DrawingModel();
		DrawingFrame frame = new DrawingFrame();
		DrawingController controller = new DrawingController(model, frame);
		frame.getView().setModel(model);
		frame.setController(controller);
		frame.getController().addPropertyChangeListener(frame);
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
	}

}
