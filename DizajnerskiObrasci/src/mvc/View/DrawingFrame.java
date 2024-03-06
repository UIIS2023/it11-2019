package mvc.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;
import mvc.Controller.DrawingController;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class DrawingFrame extends JFrame implements PropertyChangeListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DrawingView view = new DrawingView();
	private DrawingController controller;
	
	private ButtonGroup btnGroup = new ButtonGroup();
	private JToggleButton tglbtnPoint = new JToggleButton();
	private JToggleButton tglbtnLine = new JToggleButton();
	private JToggleButton tglbtnRectangle = new JToggleButton();
	private JToggleButton tglbtnCircle = new JToggleButton();
	private JToggleButton tglbtnDonut = new JToggleButton("Donut");
	private JToggleButton tglbtnHexagon = new JToggleButton("Hexagon");
	private JToggleButton tglbtnSelect = new JToggleButton("Select");
	private JButton btnDelete = new JButton("Delete");
	private JButton btnModify = new JButton("Modify");
	private final JButton btnColor = new JButton("");
	private final JButton btnInnerColor = new JButton("");
	private JButton btnToFront = new JButton("To front");
	private JButton btnToBack = new JButton("To back");
	private JButton btnBringToFront = new JButton("Bring to front");
	private JButton btnBringToBack = new JButton("Bring to back");
	private JButton btnSave = new JButton("Save log");
	private JButton btnLoad = new JButton("Load log");
	private JButton btnNext = new JButton("Next shape");
	private JButton btnUndo = new JButton("Undo");
	private JButton btnRedo = new JButton("Redo");
	private final JPanel pnlEast = new JPanel();
	private final JPanel pnlWest = new JPanel();
	private final JPanel pnlNorth = new JPanel();
	private final JPanel pnlSouth = new JPanel();
	private final JTextArea txtArea = new JTextArea();
	private final JScrollPane scrollPane = new JScrollPane(txtArea);
	
	
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals("delete")) {
			this.btnDelete.setEnabled((boolean) evt.getNewValue());
		} else if(evt.getPropertyName().equals("modify")) {
			this.btnModify.setEnabled((boolean) evt.getNewValue());
		}
	}

	public DrawingFrame() {
		setResizable(false);
		txtArea.setEditable(false);
		setTitle("Piptorosevic Filip IT11/2019");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 700);
		((JComponent) getContentPane()).setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		
		pnlNorth.setBackground(Color.DARK_GRAY);
		getContentPane().add(pnlNorth, BorderLayout.NORTH);
		pnlNorth.add(tglbtnSelect);
		
		btnGroup.add(tglbtnSelect);
		btnModify.setEnabled(false);
		pnlNorth.add(btnModify);
		btnDelete.setEnabled(false);
		pnlNorth.add(btnDelete);
		btnToFront.setEnabled(false);
		
		
		btnToFront.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.toFront();
			}
		});
		pnlNorth.add(btnToFront);
		btnToBack.setEnabled(false);
		
		
		btnToBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.toBack();
			}
		});
		pnlNorth.add(btnToBack);
		btnBringToFront.setEnabled(false);
		
		
		btnBringToFront.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.bringToFront();
			}
		});
		pnlNorth.add(btnBringToFront);
		btnBringToBack.setEnabled(false);
		
		
		btnBringToBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.bringToBack();
			}
		});
		pnlNorth.add(btnBringToBack);
		btnUndo.setEnabled(false);
		
		
		btnUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.undo();
			}
		});
		pnlNorth.add(btnUndo);
		btnRedo.setEnabled(false);
		
		
		btnRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.redo();
			}
		});
		pnlNorth.add(btnRedo);
		
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.delete();
			}
		});
		
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.modify();
			}
		});
		
		getContentPane().add(view, BorderLayout.CENTER);
		
		
		pnlSouth.setBackground(Color.DARK_GRAY);
		getContentPane().add(pnlSouth, BorderLayout.SOUTH);
		
		
		
		
		GroupLayout gl_pnlSouth = new GroupLayout(pnlSouth);
		gl_pnlSouth.setHorizontalGroup(
			gl_pnlSouth.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 826, Short.MAX_VALUE)
		);
		gl_pnlSouth.setVerticalGroup(
			gl_pnlSouth.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
		);
		
		pnlSouth.setLayout(gl_pnlSouth);
		
		getContentPane().add(pnlEast, BorderLayout.EAST);
		pnlWest.setBackground(Color.DARK_GRAY);
		
		getContentPane().add(pnlWest, BorderLayout.WEST);
		btnGroup.add(tglbtnPoint);
		btnGroup.add(tglbtnLine);
		btnGroup.add(tglbtnRectangle);
		btnGroup.add(tglbtnCircle);
		btnGroup.add(tglbtnDonut);
		
		JLabel lblNewLabel = new JLabel("Inner color");
		lblNewLabel.setForeground(Color.WHITE);
		btnInnerColor.setBackground(Color.WHITE);
		btnInnerColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.chooseInnerColor();
			}
		});
		
		JLabel lblNewLabel_1 = new JLabel("Border color");
		lblNewLabel_1.setForeground(Color.WHITE);
		btnColor.setBackground(Color.BLACK);
		btnColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.chooseColor();
			}
		});
		btnNext.setEnabled(false);
		
		
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.next();
			}
		});
		
		JButton btnSaveDrawing = new JButton("Save drawing");
		btnSaveDrawing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.saveDrawing();
			}
		});
		
		JButton btnLoadDrawing = new JButton("Load drawing");
		btnLoadDrawing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.loadDrawing();
			}
		});
		
		GroupLayout gl_pnlWest = new GroupLayout(pnlWest);
		gl_pnlWest.setHorizontalGroup(
			gl_pnlWest.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnlWest.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlWest.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_pnlWest.createSequentialGroup()
							.addGroup(gl_pnlWest.createParallelGroup(Alignment.TRAILING)
								.addComponent(tglbtnLine, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
								.addComponent(tglbtnPoint, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE))
							.addGap(38))
						.addGroup(Alignment.TRAILING, gl_pnlWest.createSequentialGroup()
							.addComponent(tglbtnRectangle, GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
							.addGap(38))
						.addGroup(Alignment.TRAILING, gl_pnlWest.createSequentialGroup()
							.addComponent(tglbtnCircle, GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
							.addGap(38))
						.addGroup(Alignment.TRAILING, gl_pnlWest.createSequentialGroup()
							.addComponent(tglbtnDonut, GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
							.addGap(38))
						.addGroup(Alignment.TRAILING, gl_pnlWest.createSequentialGroup()
							.addComponent(tglbtnHexagon, GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
							.addGap(38))
						.addGroup(Alignment.TRAILING, gl_pnlWest.createSequentialGroup()
							.addGroup(gl_pnlWest.createParallelGroup(Alignment.LEADING)
								.addComponent(btnColor, GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
								.addComponent(btnInnerColor, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_pnlWest.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel)
								.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE))
							.addGap(67))
						.addGroup(Alignment.TRAILING, gl_pnlWest.createSequentialGroup()
							.addComponent(btnSave)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnLoad, GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
							.addGap(17))
						.addGroup(Alignment.TRAILING, gl_pnlWest.createSequentialGroup()
							.addComponent(btnNext, GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
							.addGap(17))
						.addGroup(gl_pnlWest.createSequentialGroup()
							.addGroup(gl_pnlWest.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(btnLoadDrawing, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnSaveDrawing, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addContainerGap())))
		);
		gl_pnlWest.setVerticalGroup(
			gl_pnlWest.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlWest.createSequentialGroup()
					.addContainerGap()
					.addComponent(tglbtnPoint, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tglbtnLine, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tglbtnRectangle, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tglbtnCircle, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tglbtnDonut, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tglbtnHexagon, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addGap(17)
					.addGroup(gl_pnlWest.createParallelGroup(Alignment.LEADING)
						.addComponent(btnInnerColor, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pnlWest.createParallelGroup(Alignment.LEADING)
						.addComponent(btnColor, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pnlWest.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSave)
						.addComponent(btnLoad))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnNext)
					.addGap(18)
					.addComponent(btnSaveDrawing)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnLoadDrawing)
					.addContainerGap(68, Short.MAX_VALUE))
		);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.saveLog();
			}
		});
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.loadLog();
			}
		});
		tglbtnRectangle.setText("Rectangle");
		tglbtnCircle.setText("Circle");
		tglbtnLine.setText("Line");
		tglbtnPoint.setText("Point");
		pnlWest.setLayout(gl_pnlWest);
		
		btnGroup.add(tglbtnHexagon);
		
		view.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.mouseClicked(e);
			}
		});
	}

	public DrawingView getView() {
		return view;
	}

	public void setController(DrawingController controller) {
		this.controller = controller;
	}
	
	public ButtonGroup getBtnGroup() {
		return btnGroup;
	}

	public void setBtnGroup(ButtonGroup btnGroup) {
		this.btnGroup = btnGroup;
	}

	public JToggleButton getTglbtnPoint() {
		return tglbtnPoint;
	}

	public void setTglbtnPoint(JToggleButton tglbtnPoint) {
		this.tglbtnPoint = tglbtnPoint;
	}

	public JToggleButton getTglbtnLine() {
		return tglbtnLine;
	}

	public void setTglbtnLine(JToggleButton tglbtnLine) {
		this.tglbtnLine = tglbtnLine;
	}

	public JToggleButton getTglbtnRectangle() {
		return tglbtnRectangle;
	}

	public void setTglbtnRectangle(JToggleButton tglbtnRectangle) {
		this.tglbtnRectangle = tglbtnRectangle;
	}

	public JToggleButton getTglbtnCircle() {
		return tglbtnCircle;
	}

	public void setTglbtnCircle(JToggleButton tglbtnCircle) {
		this.tglbtnCircle = tglbtnCircle;
	}

	public JToggleButton getTglbtnDonut() {
		return tglbtnDonut;
	}

	public void setTglbtnDonut(JToggleButton tglbtnDonut) {
		this.tglbtnDonut = tglbtnDonut;
	}

	public JToggleButton getTglbtnSelect() {
		return tglbtnSelect;
	}

	public void setTglbtnSelect(JToggleButton tglbtnSelect) {
		this.tglbtnSelect = tglbtnSelect;
	}

	public JButton getBtnModify() {
		return btnModify;
	}

	public void setBtnModify(JButton btnModify) {
		this.btnModify = btnModify;
	}

	public JButton getBtnDelete() {
		return btnDelete;
	}

	public void setBtnDelete(JButton btnDelete) {
		this.btnDelete = btnDelete;
	}

	public JButton getBtnColor() {
		return btnColor;
	}

	public JButton getBtnInnerColor() {
		return btnInnerColor;
	}

	public JPanel getPnlEast() {
		return pnlEast;
	}

	public JPanel getPnlWest() {
		return pnlWest;
	}

	public JPanel getPnlNorth() {
		return pnlNorth;
	}

	public JPanel getPnlSouth() {
		return pnlSouth;
	}

	public JToggleButton getTglbtnHexagon() {
		return tglbtnHexagon;
	}

	public void setTglbtnHexagon(JToggleButton tglbtnHexagon) {
		this.tglbtnHexagon = tglbtnHexagon;
	}

	public DrawingController getController() {
		return controller;
	}

	public JButton getBtnToFront() {
		return btnToFront;
	}

	public void setBtnToFront(JButton btnToFront) {
		this.btnToFront = btnToFront;
	}

	public JButton getBtnToBack() {
		return btnToBack;
	}

	public void setBtnToBack(JButton btnToBack) {
		this.btnToBack = btnToBack;
	}

	public JButton getBtnBringToFront() {
		return btnBringToFront;
	}

	public void setBtnBringToFront(JButton btnBringToFront) {
		this.btnBringToFront = btnBringToFront;
	}

	public JButton getBtnBringToBack() {
		return btnBringToBack;
	}

	public void setBtnBringToBack(JButton btnBringToBack) {
		this.btnBringToBack = btnBringToBack;
	}

	public JTextArea getTxtArea() {
		return txtArea;
	}

	public JButton getBtnNext() {
		return btnNext;
	}

	public void setBtnNext(JButton btnNext) {
		this.btnNext = btnNext;
	}

	public JButton getBtnUndo() {
		return btnUndo;
	}

	public JButton getBtnRedo() {
		return btnRedo;
	}
}