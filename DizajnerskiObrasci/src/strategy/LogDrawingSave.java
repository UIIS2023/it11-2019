package strategy;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import mvc.Model.DrawingModel;
import mvc.View.DrawingFrame;


public class LogDrawingSave implements Save {
	

	@Override
	public void saveLog(DrawingFrame frame) {
		
		if(frame.getTxtArea().getText().isEmpty()) {
			 java.awt.Toolkit.getDefaultToolkit().beep();
	         JOptionPane.showMessageDialog(null, "Log is empty!");
	         return;
		 }
		
		JFileChooser choice = new JFileChooser();
		int option = choice.showSaveDialog(choice);
		
		 if (option == JFileChooser.APPROVE_OPTION) {
			 
			 try {   
		            FileOutputStream file = new FileOutputStream(choice.getSelectedFile().getAbsolutePath());
		            ObjectOutputStream out = new ObjectOutputStream(file);
		              
		            out.writeObject(frame.getTxtArea().getText());
		    
		            out.close();
		            file.close();
		            
		            java.awt.Toolkit.getDefaultToolkit().beep();
		            JOptionPane.showMessageDialog(null, "File saved!");
		  
		        } catch(IOException ex) {
		            System.out.println("Error during saving!");
		        }
		 }
	}

	@Override
	public void saveDrawing(DrawingModel model) {
		
		if(model.getShapes().size() == 0) {
			 java.awt.Toolkit.getDefaultToolkit().beep();
	         JOptionPane.showMessageDialog(null, "Drawing is empty!");
	         return;
		}
		
		JFileChooser choice = new JFileChooser();
		int option = choice.showSaveDialog(choice);
		
		 if (option == JFileChooser.APPROVE_OPTION) {
			 
			 try {   
		            FileOutputStream file = new FileOutputStream(choice.getSelectedFile().getAbsolutePath());
		            ObjectOutputStream out = new ObjectOutputStream(file);
		              
		            out.writeObject(model.getShapes());
		    
		            out.close();
		            file.close();
		            
		            java.awt.Toolkit.getDefaultToolkit().beep();
		            JOptionPane.showMessageDialog(null, "File saved!");
		  
		        } catch(IOException ex) {
		            System.out.println("Error during saving!");
		        }
		 }
		
	}

	
}


