package mvc.Controller;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeSupport;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Stack;

import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import command.AddShapeCmd;
import command.BringToBackCmd;
import command.BringToFrontCmd;
import command.ChangeColorCmd;
import command.ChangeInnerColorCmd;
import command.Command;
import command.RemoveShapeCmd;
import command.SelectShapeCmd;
import command.ToBackCmd;
import command.ToFrontCmd;
import command.UpdateCircleCmd;
import command.UpdateDonutCmd;
import command.UpdateHexagonCmd;
import command.UpdateLineCmd;
import command.UpdatePointCmd;
import command.UpdateRectangleCmd;
import dialogs.DlgCircle;
import dialogs.DlgDonut;
import dialogs.DlgLine;
import dialogs.DlgPoint;
import dialogs.DlgRectangle;
import hexagon.Hexagon;
import dialogs.DlgHexagon;
import mvc.Model.DrawingModel;
import mvc.View.DrawingFrame;
import shapes.Circle;
import shapes.Donut;
import shapes.HexagonAdapter;
import shapes.Line;
import shapes.Point;
import shapes.Rectangle;
import shapes.Shape;
import shapes.SurfaceShape;
import strategy.SaveManager;
import strategy.LogDrawingSave;

public class DrawingController implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DrawingModel model;
	private DrawingFrame frame;
	private Point startPoint;
	private PropertyChangeSupport propertyChangeSupport;
	private LogDrawingSave logDrawingSave = new LogDrawingSave();
	private SaveManager saveManager = new SaveManager(logDrawingSave);
	private String text;
	private String lines[];
	private int counter = 0;
	private String action;
	private String shapeType;
	private String [] logLine;
	private String [] coordinates;
	private Stack<Command> redoStack;
	private Stack<Command> undoStack;
	private ArrayList<Shape> selectedToDelete;
	private boolean clickedNext;
	private int numberOfContainsShapes = 0;
	private int index;

	public DrawingController(DrawingModel model, DrawingFrame frame) {
		this.model = model;
		this.frame = frame;
		propertyChangeSupport = new PropertyChangeSupport(this);
		redoStack = new Stack<Command>();
		undoStack = new Stack<Command>();
		selectedToDelete = new ArrayList<Shape>();
	}
	
	public void addPropertyChangeListener(DrawingFrame drawingFrame) {
		propertyChangeSupport.addPropertyChangeListener(drawingFrame);
	}
	
	private void executeCmd(Command command) {
		command.execute();
		frame.getBtnRedo().setEnabled(false);
		undoStack.push(command);
		redoStack.clear();
		checkButtons();
	}
	
	public void undo() {
		if(undoStack.isEmpty()) {
			return;
		}
		
		Command cmd = undoStack.peek();
		undoStack.pop();
		redoStack.push(cmd);
		cmd.unexecute();
		checkButtons();
		
		if(!clickedNext)
			updateLog("Undo " + cmd.log());
		
		frame.getBtnRedo().setEnabled(true);

		frame.repaint();
	}
	
	public void redo() {
		if(redoStack.isEmpty())
			return;
		
		Command cmd = redoStack.peek();
		redoStack.pop();
		undoStack.push(cmd);
		cmd.execute();
		checkButtons();
		
		if(!clickedNext)
			updateLog("Redo " + cmd.log());
		
		frame.repaint();
	}
	
	public void chooseInnerColor() {
		Color confirmation = JColorChooser.showDialog(null, "Choose inner color", frame.getBtnInnerColor().getForeground());
		
		if(confirmation == null)
			return;
		else {
			frame.getBtnInnerColor().setBackground(confirmation);
			
			if(!model.getSelectedShapes().isEmpty()) {
				Iterator<Shape> it = model.getSelectedShapes().iterator();
				
				while(it.hasNext()) {
					Shape shape = it.next();
					if(shape instanceof SurfaceShape) {
						ChangeInnerColorCmd command = new ChangeInnerColorCmd(((SurfaceShape) shape).getInnerColor(), frame.getBtnInnerColor().getBackground(), shape);
						executeCmd(command);
						updateLog(command.log());
					}
				}
				frame.repaint();
			}
		}
	}
	
	public void chooseColor() {
		Color confirmation = JColorChooser.showDialog(null, "Choose border color", frame.getBtnColor().getForeground());
		
		if(confirmation == null)
			return;
		else {
			frame.getBtnColor().setBackground(confirmation);
			
			if(!model.getSelectedShapes().isEmpty()) {
				Iterator<Shape> it = model.getSelectedShapes().iterator();
				
				while(it.hasNext()) {
					Shape shape = it.next();
					ChangeColorCmd command = new ChangeColorCmd(shape.getColor(), frame.getBtnColor().getBackground(), shape);
					executeCmd(command);
					updateLog(command.log());
				}
				
				frame.repaint();
			}
		}
	}
	
	public void toFront() {
		index = model.getIndex(model.getSelectedShape());
		if(index == model.getShapes().size() - 1) {
			java.awt.Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "Shape is already in front!");
			return;
		} 
		
		Command toFrontCmd = new ToFrontCmd(model, index);
		executeCmd(toFrontCmd);
		
		if(!clickedNext)
			updateLog(toFrontCmd.log());
		
		frame.repaint();
	}
	
	public void toBack() {
		index = model.getIndex(model.getSelectedShape());
		if(index == 0) {
			java.awt.Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "Shape is already in back!");
			return;
		} 
		
		Command toBackCmd = new ToBackCmd(model, index);
		executeCmd(toBackCmd);
		
		if(!clickedNext)
			updateLog(toBackCmd.log());
		
		frame.repaint();
	}
	
	public void bringToFront() {
		index = model.getIndex(model.getSelectedShape());
		if(index == model.getShapes().size() - 1) {
			java.awt.Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "Shape is already in front!");
			return;
		} 
		
		Command bringToFrontCmd = new BringToFrontCmd(model, index);
		executeCmd(bringToFrontCmd);
		
		if(!clickedNext)
			updateLog(bringToFrontCmd.log());
		
		frame.repaint();
	}
	
	public void bringToBack() {
		index = model.getIndex(model.getSelectedShape());
		if(index == 0) {
			java.awt.Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "Shape is already in back!");
			return;
		} 
		
		Command bringToBackCmd = new BringToBackCmd(model, index);
		executeCmd(bringToBackCmd);
		
		if(!clickedNext)
			updateLog(bringToBackCmd.log());
		
		frame.repaint();
	}

	
	private void updateLog(String text) {
		frame.getTxtArea().append(text);
	}
	
	public void saveLog() {
		saveManager.saveLog(frame);
	}
	
	public void saveDrawing() {
		saveManager.saveDrawing(model);
	}
	
	public void loadLog() {
		
		if(!frame.getTxtArea().getText().isEmpty()) {
			int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to load new log, unsaved logs will be deleted?");
			
			if(confirmation == JOptionPane.YES_OPTION)
				frame.getTxtArea().setText("");
			else
				return;
		}
		
		JFileChooser choice = new JFileChooser();
		int option = choice.showOpenDialog(choice);
		
		if(option == JFileChooser.APPROVE_OPTION) {
			
			 try {   
		            FileInputStream file = new FileInputStream(choice.getSelectedFile().getAbsolutePath());
		            ObjectInputStream in = new ObjectInputStream(file);
		            
		            text = (String) in.readObject();
		            
		            in.close();
		            file.close();
		            
		            java.awt.Toolkit.getDefaultToolkit().beep();
		            JOptionPane.showMessageDialog(null, "File loaded, click on Next shape to draw!");
		            
		            frame.getBtnNext().setEnabled(true);
		            model.getShapes().clear();
		            model.getSelectedShapes().clear();
		            lines = text.split("\\r?\\n");
		            counter = 0;
		            frame.repaint();
		            
		        } catch(IOException ex) {
		        	JOptionPane.showMessageDialog(null, "Error during loading!");
		        } catch(ClassNotFoundException ex) {
		        	JOptionPane.showMessageDialog(null, "Error during loading!");
		        }
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public void loadDrawing() {
		
		if(model.getShapes().size() > 0) {
			int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to load new drawing, unsaved drawings will be deleted?");
			
			if(confirmation == JOptionPane.YES_OPTION)
				model.getShapes().clear();
			else
				return;
		}
		
			JFileChooser choice = new JFileChooser();
			int option = choice.showOpenDialog(choice);
			
			if(option == JFileChooser.APPROVE_OPTION) {
				
				 try {   
			            FileInputStream file = new FileInputStream(choice.getSelectedFile().getAbsolutePath());
			            ObjectInputStream in = new ObjectInputStream(file);
			            
			            model.getShapes().addAll((ArrayList<Shape>) in.readObject());
			            
			            in.close();
			            file.close();
			            
			            java.awt.Toolkit.getDefaultToolkit().beep();
			            JOptionPane.showMessageDialog(null, "File loaded!");
			            
			            frame.repaint();
			            
			        } catch(IOException ex) {
			        	JOptionPane.showMessageDialog(null, "Error during loading!");
			        } catch(ClassNotFoundException ex) {
			        	JOptionPane.showMessageDialog(null, "Error during loading!");
			        }
			}

	}
	
	public void next() {
		if(counter == lines.length) {
			java.awt.Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Drawing is finished!");
            return;
		}
		
		frame.getTxtArea().append(lines[counter] + "\n");
		
		logLine = lines[counter].split(" ");
		action = logLine[0];
		shapeType = logLine[1];
		coordinates = logLine[2].split(",");
		
		switch(action) {
			case "Drawn" : {
				switch(shapeType) {
					case "Point" : {
						AddShapeCmd command = new AddShapeCmd(new Point(Integer.parseInt(coordinates[0]),
								Integer.parseInt(coordinates[1]),
								Boolean.parseBoolean(coordinates[2]), new Color(Integer.parseInt(coordinates[3]), Integer.parseInt(coordinates[4]), Integer.parseInt(coordinates[5]))), model);
						executeCmd(command);
					} break;
					case "Line" : {
						AddShapeCmd command = new AddShapeCmd(new Line(new Point(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])),
								new Point(Integer.parseInt(coordinates[2]), Integer.parseInt(coordinates[3])), 
								Boolean.parseBoolean(coordinates[4]), new Color(Integer.parseInt(coordinates[5]), Integer.parseInt(coordinates[6]), Integer.parseInt(coordinates[7]))), model);
						executeCmd(command);
					} break;
					case "Rectangle" : {
						AddShapeCmd command = new AddShapeCmd(new Rectangle(new Point(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])),
								Integer.parseInt(coordinates[2]), Integer.parseInt(coordinates[3]),
								Boolean.parseBoolean(coordinates[4]), new Color(Integer.parseInt(coordinates[5]), Integer.parseInt(coordinates[6]), Integer.parseInt(coordinates[7])), 
								new Color(Integer.parseInt(coordinates[8]), Integer.parseInt(coordinates[9]), Integer.parseInt(coordinates[10]))), model);
						executeCmd(command);
					} break;
					case "Circle" : {
						AddShapeCmd command = new AddShapeCmd(new Circle(new Point(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])),
								Integer.parseInt(coordinates[2]), Boolean.parseBoolean(coordinates[3]), new Color(Integer.parseInt(coordinates[4]), Integer.parseInt(coordinates[5]), Integer.parseInt(coordinates[6])), 
								new Color(Integer.parseInt(coordinates[7]), Integer.parseInt(coordinates[8]), Integer.parseInt(coordinates[9]))), model);
						executeCmd(command);
					} break;
					case "Donut" : {
						AddShapeCmd command = new AddShapeCmd(new Donut(new Point(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])),
								Integer.parseInt(coordinates[2]), Integer.parseInt(coordinates[3]), Boolean.parseBoolean(coordinates[4]), new Color(Integer.parseInt(coordinates[5]), Integer.parseInt(coordinates[6]), Integer.parseInt(coordinates[7])), 
								new Color(Integer.parseInt(coordinates[8]), Integer.parseInt(coordinates[9]), Integer.parseInt(coordinates[10]))), model);
						executeCmd(command);
					} break;
					case "HexagonAdapter" : {
						AddShapeCmd command = new AddShapeCmd(new HexagonAdapter(new Hexagon(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]),
								Integer.parseInt(coordinates[2])), Boolean.parseBoolean(coordinates[3]), new Color(Integer.parseInt(coordinates[4]), Integer.parseInt(coordinates[5]), Integer.parseInt(coordinates[6])), new Color(Integer.parseInt(coordinates[7]), Integer.parseInt(coordinates[8]), Integer.parseInt(coordinates[9]))), model);
						executeCmd(command);
					} break;
				}
			} break;
			case "Selected" : {
				switch(shapeType) {
					case "Donut" : {
						ListIterator<Shape> shape = model.getShapes().listIterator(model.getShapes().size());
						while(shape.hasPrevious()) {
							Shape shapeToSelect = shape.previous();
							if(shapeToSelect instanceof Donut) {
								if(((Donut) shapeToSelect).getCenter().equals(new Point(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])))) {
									SelectShapeCmd command = new SelectShapeCmd(shapeToSelect, model, true);
									executeCmd(command);
									break;
								}
							}
						}
					} break;
					default : {
						ListIterator<Shape> shape = model.getShapes().listIterator(model.getShapes().size());
						while(shape.hasPrevious()) {
							Shape shapeToSelect = shape.previous();
								if(shapeToSelect.contains(new Point(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]))) && shapeToSelect.getClass().getSimpleName().equals(shapeType)) {
									SelectShapeCmd command = new SelectShapeCmd(shapeToSelect, model, true);
									executeCmd(command);
									break;
								}
						}
					} break;
				}
			} break;
			case "Deleted" : {
				switch(shapeType) {
					case "Donut" : {
						ListIterator<Shape> shape = model.getShapes().listIterator(model.getShapes().size());
						while(shape.hasPrevious()) {
							Shape shapeToDelete = shape.previous();
								if(((Donut) shapeToDelete).getCenter().equals(new Point(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])))) {
									RemoveShapeCmd command = new RemoveShapeCmd(model, shapeToDelete);
									executeCmd(command);
									break;
								}
						}
					} break;
					default : {
						ListIterator<Shape> shape = model.getShapes().listIterator(model.getShapes().size());
						while(shape.hasPrevious()) {
							Shape shapeToDelete = shape.previous();
							if(shapeToDelete.contains(new Point(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]))) && shapeToDelete.getClass().getSimpleName().equals(shapeType)) {
								RemoveShapeCmd command = new RemoveShapeCmd(model, shapeToDelete);
								executeCmd(command);
								break;
							}
						}
					} break;
				}
			} break;
			case "Modified" : {
				switch(shapeType) {
					case "Point" : {
						Point shapeToModify = (Point) model.getSelectedShape();
						UpdatePointCmd command = new UpdatePointCmd(shapeToModify, new Point(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]), Boolean.parseBoolean(coordinates[2]), new Color(Integer.parseInt(coordinates[3]), Integer.parseInt(coordinates[4]), Integer.parseInt(coordinates[5]))));
						executeCmd(command);
					} break;
					case "Line" : {
						Line shapeToModify = (Line) model.getSelectedShape();
						UpdateLineCmd command = new UpdateLineCmd(shapeToModify, new Line(new Point(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])), new Point(Integer.parseInt(coordinates[2]), Integer.parseInt(coordinates[3])), Boolean.parseBoolean(coordinates[4]), new Color(Integer.parseInt(coordinates[5]), Integer.parseInt(coordinates[6]), Integer.parseInt(coordinates[7]))));
						executeCmd(command);
					} break;
					case "Rectangle" : {
						Rectangle shapeToModify = (Rectangle) model.getSelectedShape();
						UpdateRectangleCmd command = new UpdateRectangleCmd(shapeToModify, new Rectangle(new Point(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])), Integer.parseInt(coordinates[2]), Integer.parseInt(coordinates[3]), Boolean.parseBoolean(coordinates[4]), new Color(Integer.parseInt(coordinates[5]), Integer.parseInt(coordinates[6]), Integer.parseInt(coordinates[7])), new Color(Integer.parseInt(coordinates[8]), Integer.parseInt(coordinates[9]), Integer.parseInt(coordinates[10]))));
						executeCmd(command);
					} break;
					case "Circle" : {
						Circle shapeToModify = (Circle) model.getSelectedShape();
						UpdateCircleCmd command = new UpdateCircleCmd(shapeToModify, new Circle(new Point(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])), Integer.parseInt(coordinates[2]), Boolean.parseBoolean(coordinates[3]), new Color(Integer.parseInt(coordinates[4]), Integer.parseInt(coordinates[5]), Integer.parseInt(coordinates[6])), new Color(Integer.parseInt(coordinates[7]), Integer.parseInt(coordinates[8]), Integer.parseInt(coordinates[9]))));
						executeCmd(command);
					} break;
					case "Donut" : {
						Donut shapeToModify = (Donut) model.getSelectedShape();
						UpdateDonutCmd command = new UpdateDonutCmd(shapeToModify, new Donut(new Point(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])), Integer.parseInt(coordinates[2]), Integer.parseInt(coordinates[3]), Boolean.parseBoolean(coordinates[4]), new Color(Integer.parseInt(coordinates[5]), Integer.parseInt(coordinates[6]), Integer.parseInt(coordinates[7])), new Color(Integer.parseInt(coordinates[8]), Integer.parseInt(coordinates[9]), Integer.parseInt(coordinates[10]))));
						executeCmd(command);
					} break;
					case "HexagonAdapter" : {
						HexagonAdapter shapeToModify = (HexagonAdapter) model.getSelectedShape();
						UpdateHexagonCmd command = new UpdateHexagonCmd(shapeToModify, new HexagonAdapter(new Hexagon(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]), Integer.parseInt(coordinates[2])), Boolean.parseBoolean(coordinates[3]), new Color(Integer.parseInt(coordinates[4]), Integer.parseInt(coordinates[5]), Integer.parseInt(coordinates[6])), new Color(Integer.parseInt(coordinates[7]), Integer.parseInt(coordinates[8]), Integer.parseInt(coordinates[9]))));
						executeCmd(command);
					} break;
				}
			} break;
			case "Deselected" : { 
				Iterator<Shape> shape = model.getShapes().iterator();
				while(shape.hasNext()) {
					Shape shapeToDeselect = shape.next();
					if(shapeToDeselect.isSelected()) {
						Command command = new SelectShapeCmd(shapeToDeselect, model, false);
						executeCmd(command);
					}
				}
				model.getSelectedShapes().clear();
			} break;
			case "Changed_color" : {
				switch(shapeType) {
					case "Point" :
					case "Line" : {
						ListIterator<Shape> shape = model.getShapes().listIterator(model.getShapes().size());
						while(shape.hasPrevious()) {
							Shape shapeToColor = shape.previous();
							if(shapeToColor.contains(new Point(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]))) && shapeToColor.getClass().getSimpleName().equals(shapeType)) {
								ChangeColorCmd command = new ChangeColorCmd(shapeToColor.getColor(), new Color(Integer.parseInt(coordinates[coordinates.length - 3]),Integer.parseInt(coordinates[coordinates.length - 2]),Integer.parseInt(coordinates[coordinates.length - 1])), shapeToColor);
								executeCmd(command);
								break;
							}
						}
					} break;
					case "Rectangle":
					case "Circle":
					case "HexagonAdapter": {
						ListIterator<Shape> shape = model.getShapes().listIterator(model.getShapes().size());
						while(shape.hasPrevious()) {
							Shape shapeToColor = shape.previous();
							if(shapeToColor.contains(new Point(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]))) && shapeToColor.getClass().getSimpleName().equals(shapeType)) {
								ChangeColorCmd command = new ChangeColorCmd(shapeToColor.getColor(), new Color(Integer.parseInt(coordinates[coordinates.length - 6]),Integer.parseInt(coordinates[coordinates.length - 5]),Integer.parseInt(coordinates[coordinates.length - 4])), shapeToColor);
								executeCmd(command);
								break;
							}
						}
					} break;
					case "Donut": {
						ListIterator<Shape> shape = model.getShapes().listIterator(model.getShapes().size());
						while(shape.hasPrevious()) {
							Shape shapeToColor = shape.previous();
							if(shapeToColor instanceof Donut) {
								if(((Donut) shapeToColor).getCenter().equals(new Point(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])))) {
									ChangeColorCmd command = new ChangeColorCmd(shapeToColor.getColor(), new Color(Integer.parseInt(coordinates[coordinates.length - 6]),Integer.parseInt(coordinates[coordinates.length - 5]),Integer.parseInt(coordinates[coordinates.length - 4])), shapeToColor);
									executeCmd(command);
									break;
								}
							}
						}
					} break;
				}
			} break;
			case "Changed_inner_color" : {
				switch(shapeType) {
					case "Donut" : {
						ListIterator<Shape> shape = model.getShapes().listIterator(model.getShapes().size());
						while(shape.hasPrevious()) {
							Shape shapeToColor = shape.previous();
							if(shapeToColor instanceof Donut) {
								if(((Donut) shapeToColor).getCenter().equals(new Point(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])))) {
									ChangeInnerColorCmd command = new ChangeInnerColorCmd(shapeToColor.getColor(), new Color(Integer.parseInt(coordinates[coordinates.length - 3]),Integer.parseInt(coordinates[coordinates.length - 2]),Integer.parseInt(coordinates[coordinates.length - 1])), shapeToColor);
									executeCmd(command);
									break;
								}
							}
						}
					} break;
					default : {
						ListIterator<Shape> shape = model.getShapes().listIterator(model.getShapes().size());
						while(shape.hasPrevious()) {
							Shape shapeToColor = shape.previous();
							if(shapeToColor.contains(new Point(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]))) && shapeToColor.getClass().getSimpleName().equals(shapeType)) {
								ChangeInnerColorCmd command = new ChangeInnerColorCmd(shapeToColor.getColor(), new Color(Integer.parseInt(coordinates[coordinates.length - 3]),Integer.parseInt(coordinates[coordinates.length - 2]),Integer.parseInt(coordinates[coordinates.length - 1])), shapeToColor);
								executeCmd(command);
								break;
							}
						}
					} break;
				}
			} break;
			case "To_front" : {
				toFront();
			} break;
			case "To_back" : {
				toBack();
			} break;
			case "Brought_to_front" : {
				bringToFront();
			} break;
			case "Brought_to_back" : {
				bringToBack();
			} break;
			case "Undo" : {
				undo();
			} break;
			case "Redo" : {
				redo();
			} break;
		}
		
		frame.repaint();
		clickedNext = true;
		counter++;
	}
	
	private void checkButtons() {
		if(model.getSelectedShapes().size() > 1 || model.getSelectedShapes().size() == 0) {
			frame.getBtnBringToBack().setEnabled(false);
			frame.getBtnBringToFront().setEnabled(false);
			frame.getBtnToBack().setEnabled(false);
			frame.getBtnToFront().setEnabled(false);
		} else {
			frame.getBtnBringToBack().setEnabled(true);
			frame.getBtnBringToFront().setEnabled(true);
			frame.getBtnToBack().setEnabled(true);
			frame.getBtnToFront().setEnabled(true);
		}
		
		if(undoStack.isEmpty())
			frame.getBtnUndo().setEnabled(false);
		else
			frame.getBtnUndo().setEnabled(true);
		
		if(model.getSelectedShapes().size() == 1) {
			propertyChangeSupport.firePropertyChange("modify", frame.getBtnModify().isEnabled(), true);
		} else {
			propertyChangeSupport.firePropertyChange("modify", frame.getBtnModify().isEnabled(), false);
		}
		
		if(model.getSelectedShapes().size() > 0) {
			propertyChangeSupport.firePropertyChange("delete", frame.getBtnDelete().isEnabled(), true);
		} else {
			propertyChangeSupport.firePropertyChange("delete", frame.getBtnDelete().isEnabled(), false);
		}
	}
	
	
	public void mouseClicked(MouseEvent e) {
		Shape newShape = null;
		
		if(frame.getTglbtnSelect().isSelected()) {
				ListIterator<Shape> it = model.getShapes().listIterator(model.getShapes().size());
				numberOfContainsShapes = 0;
				
				while(it.hasPrevious()) {
					Shape shape = it.previous();
					
					if(shape.contains(new Point(e.getX(), e.getY()))) {
						numberOfContainsShapes++;
						
						if(numberOfContainsShapes > 1)
							continue;
						
						Command command = new SelectShapeCmd(shape, model, true);
						executeCmd(command);
						updateLog(command.log());
					}
				}
				
				if(numberOfContainsShapes == 0) {
					it = model.getShapes().listIterator(model.getShapes().size());
					while(it.hasPrevious()) {
						Shape deselectedShape = it.previous();
						
						if(deselectedShape.isSelected()) {
							Command command = new SelectShapeCmd(deselectedShape, model, false);
							executeCmd(command);
						}
					}
					updateLog("Deselected all shapes \n");
				} 
				
				checkButtons();
				
		} else if(frame.getTglbtnPoint().isSelected()) {
			newShape = new Point(e.getX(), e.getY(), frame.getBtnColor().getBackground());
		} else if (frame.getTglbtnLine().isSelected()) {
			if(startPoint == null)
				startPoint = new Point(e.getX(), e.getY());
			else {
				newShape = new Line(startPoint, new Point(e.getX(), e.getY()), frame.getBtnColor().getBackground());
				startPoint = null;
			}
		} else if (frame.getTglbtnRectangle().isSelected()) {
			DlgRectangle dlgRect = new DlgRectangle(frame);
			dlgRect.setModal(true);
			dlgRect.setRectangle(new Rectangle(new Point(e.getX(), e.getY()), -1, -1, frame.getBtnColor().getBackground(), frame.getBtnInnerColor().getBackground()));
			dlgRect.getTxtX().setEditable(false);
			dlgRect.getTxtY().setEditable(false);
			dlgRect.setVisible(true);
			
			if(!dlgRect.isOK()) {
				return;
			}		
			
			try {
				newShape = dlgRect.getRectangle();
			}catch(NumberFormatException nfe) {
				java.awt.Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(null, nfe.getMessage());
			}catch(Exception ex) {
				java.awt.Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(frame, "Wrong data type", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else if(frame.getTglbtnCircle().isSelected()) {
			DlgCircle dlgCircle = new DlgCircle(frame);
			dlgCircle.setModal(true);
			dlgCircle.setCircle(new Circle(new Point(e.getX(), e.getY()), -1, frame.getBtnColor().getBackground(), frame.getBtnInnerColor().getBackground()));
			dlgCircle.getTxtX().setEditable(false);
			dlgCircle.getTxtY().setEditable(false);
			dlgCircle.setVisible(true);
			
			if(!dlgCircle.isOK())
				return;
			
			try {
				newShape = dlgCircle.getCircle();
			}catch(NumberFormatException nfe) {
				java.awt.Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(null, nfe.getMessage());
			}catch(Exception ex) {
				java.awt.Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(frame, "Wrong data type", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else if(frame.getTglbtnDonut().isSelected()) {
			DlgDonut dlgDonut = new DlgDonut(frame);
			dlgDonut.setModal(true);
			dlgDonut.setDonut(new Donut(new Point(e.getX(), e.getY()), -1, -1, frame.getBtnColor().getBackground(), frame.getBtnInnerColor().getBackground()));
			dlgDonut.getTxtX().setEditable(false);
			dlgDonut.getTxtY().setEditable(false);
			dlgDonut.setVisible(true);
			
			if(!dlgDonut.isOK())
				return;
			
			try {
				newShape = dlgDonut.getDonut();
			}catch(NumberFormatException nfe) {
				java.awt.Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(null, nfe.getMessage());
			}catch(Exception ex) {
				java.awt.Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(frame, "Wrong data type", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else if(frame.getTglbtnHexagon().isSelected()) {
			DlgHexagon dlgHexagon = new DlgHexagon(frame);
			dlgHexagon.setModal(true);
			dlgHexagon.setHexagonAdapter(new HexagonAdapter(new Hexagon(e.getX(), e.getY(), -1)));
			dlgHexagon.getTxtX().setEditable(false);
			dlgHexagon.getTxtY().setEditable(false);
			dlgHexagon.setVisible(true);
			
			if(!dlgHexagon.isOK())
				return;
			
			try {
				newShape = dlgHexagon.getHexagonAdapter();
			} catch(NumberFormatException nfe) {
				java.awt.Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(null, nfe.getMessage());
			} catch (Exception ex) {
				java.awt.Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(frame, "Wrong data type", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		else {
			JOptionPane.showMessageDialog(frame, "No shapes to select!");
		}
		
		 if(newShape != null) {
			 Command command = new AddShapeCmd(newShape, model);
			 executeCmd(command);
			 updateLog(command.log());
		 }
		 frame.repaint();
	}
	
	public void delete() {
		
		if(!model.getSelectedShapes().isEmpty()) {
			
			selectedToDelete.clear();
			selectedToDelete.addAll(model.getSelectedShapes());
			
			int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure?");
			
			if(confirmation == JOptionPane.YES_OPTION) {
				
				Iterator<Shape> it = selectedToDelete.iterator();
				
				while(it.hasNext()) {
					Shape shapeToDelete = it.next();
					
					Command command = new RemoveShapeCmd(model, shapeToDelete);
					executeCmd(command);
					updateLog(command.log());
				}
				
				model.getSelectedShapes().clear();
			}
		} else {
			JOptionPane.showMessageDialog(null, "You haven't selected shape!");
		}
		frame.repaint();
	}
	
	public void modify() {
		Shape selected;
		
		if(model.getSelectedShapes().isEmpty())
			selected = null;
		else
			selected = model.getSelectedShape();
		 
		if(selected != null) {
			if(selected instanceof Point) {
				Point point = (Point) selected;
				DlgPoint dlgPoint = new DlgPoint(frame);
				dlgPoint.setClicked(true);
				dlgPoint.setPoint(point);
				dlgPoint.setModal(true);
				dlgPoint.setVisible(true);
				if(dlgPoint.isOK()) {
					try {
						Point newPoint = new Point(Integer.parseInt(dlgPoint.getTxtX().getText()), Integer.parseInt(dlgPoint.getTxtY().getText()), point.getColor());
						Command command = new UpdatePointCmd(point, newPoint);
						executeCmd(command);
						updateLog(command.log());
					} catch (NumberFormatException nfe) {
						java.awt.Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(null, nfe.getMessage());;
					} catch (Exception ex) {
						java.awt.Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(null, ex.getMessage());
					} 
				}
			} else if(selected instanceof Line) {
				Line line = (Line) selected;
				DlgLine dlgLine = new DlgLine(frame);
				dlgLine.setClicked(true);
				dlgLine.setLine(line);
				dlgLine.setModal(true);
				dlgLine.setVisible(true);
				if(dlgLine.isOK()) {
					try {
						Line newLine = new Line(new Point(Integer.parseInt(dlgLine.getTxtStartX().getText()), Integer.parseInt(dlgLine.getTxtStartY().getText())), new Point(Integer.parseInt(dlgLine.getTxtEndX().getText()), Integer.parseInt(dlgLine.getTxtEndY().getText())), line.getColor());
						Command command = new UpdateLineCmd(line, newLine);
						executeCmd(command);
						updateLog(command.log());
					} catch (NumberFormatException nfe) {
						java.awt.Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(null, nfe.getMessage());
					} catch (Exception ex) {
						java.awt.Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(null, ex.getMessage());
					} 
				}
			} else if(selected instanceof Rectangle) {
				Rectangle rect = (Rectangle) selected;
				DlgRectangle dlgRect = new DlgRectangle(frame);
				dlgRect.setClicked(true);
				dlgRect.setRectangle(rect);
				dlgRect.setModal(true);
				dlgRect.setVisible(true);
				if(dlgRect.isOK())
				{
					try {
						Rectangle newRectangle = new Rectangle(new Point(Integer.parseInt(dlgRect.getTxtX().getText()), Integer.parseInt(dlgRect.getTxtY().getText())), Integer.parseInt(dlgRect.getTxtWidth().getText()), Integer.parseInt(dlgRect.getTxtHeight().getText()), rect.getColor(), rect.getInnerColor());
						Command command = new UpdateRectangleCmd(rect, newRectangle);
						executeCmd(command);
						updateLog(command.log());
					} catch (NumberFormatException nfe) {
						java.awt.Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(null, nfe.getMessage());
					} catch (Exception ex) {
						java.awt.Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(null, ex.getMessage());
					} 
				}
			} else if(selected instanceof Donut) {
				Donut donut = (Donut) selected;
				DlgDonut dlgDonut = new DlgDonut(frame);
				dlgDonut.setClicked(true);
				dlgDonut.setDonut(donut);
				dlgDonut.setModal(true);
				dlgDonut.setVisible(true);
				if(dlgDonut.isOK()) {
					try {
						Donut newDonut = new Donut(new Point(Integer.parseInt(dlgDonut.getTxtX().getText()), Integer.parseInt(dlgDonut.getTxtY().getText())), Integer.parseInt(dlgDonut.getTxtRadius().getText()), Integer.parseInt(dlgDonut.getTxtInnerRadius().getText()), donut.getColor(), donut.getInnerColor());
						Command command = new UpdateDonutCmd(donut, newDonut);
						executeCmd(command);
						updateLog(command.log());
					} catch(NumberFormatException nfe) {
						java.awt.Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(null, nfe.getMessage());
					} catch (Exception ex) {
						java.awt.Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(null, ex.getMessage());
					}
				}
			} else if(selected instanceof Circle) {
				Circle circle = (Circle) selected;
				DlgCircle dlgCircle = new DlgCircle(frame);
				dlgCircle.setClicked(true);
				dlgCircle.setCircle(circle);
				dlgCircle.setModal(true);
				dlgCircle.setVisible(true);
				if(dlgCircle.isOK()) {
					try {
						Circle newCircle = new Circle(new Point(Integer.parseInt(dlgCircle.getTxtX().getText()), Integer.parseInt(dlgCircle.getTxtY().getText())), Integer.parseInt(dlgCircle.getTxtRadius().getText()), circle.getColor(), circle.getInnerColor());
						Command command = new UpdateCircleCmd(circle, newCircle);
						executeCmd(command);
						updateLog(command.log());
					} catch(NumberFormatException nfe) {
						java.awt.Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(null, nfe.getMessage());
					} catch(Exception ex) {
						java.awt.Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(null, ex.getMessage());
					}
				}
			} else if(selected instanceof HexagonAdapter) {
				HexagonAdapter hexagonAdapter = (HexagonAdapter) selected;
				DlgHexagon dlgHexagon = new DlgHexagon(frame);
				dlgHexagon.setClicked(true);
				dlgHexagon.setHexagonAdapter(hexagonAdapter);
				dlgHexagon.setModal(true);
				dlgHexagon.setVisible(true);
				if(dlgHexagon.isOK()) {
					try {
						HexagonAdapter newHexagonAdapter = new HexagonAdapter(new Hexagon(Integer.parseInt(dlgHexagon.getTxtX().getText()), Integer.parseInt(dlgHexagon.getTxtY().getText()), Integer.parseInt(dlgHexagon.getTxtRadius().getText())), hexagonAdapter.getColor(), hexagonAdapter.getInnerColor());
						Command command = new UpdateHexagonCmd(hexagonAdapter, newHexagonAdapter);
						executeCmd(command);
						updateLog(command.log());
					} catch(NumberFormatException nfe) {
						java.awt.Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(null, nfe.getMessage());
					} catch(Exception ex) {
						java.awt.Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(null, ex.getMessage());
					}
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "You haven't selected shape!");
		}
		frame.repaint();
	}
}
