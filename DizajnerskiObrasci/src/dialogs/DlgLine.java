package dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import mvc.View.DrawingFrame;
import shapes.Line;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DlgLine extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtStartX;
	private JTextField txtStartY;
	private JTextField txtEndX;
	private JTextField txtEndY;
	private Line l;
	private boolean isOK;
	protected boolean isClicked;
	protected Color color;



	/**
	 * Create the dialog.
	 */
	public DlgLine(DrawingFrame frame) {
		setBounds(100, 100, 340, 260);
		setTitle("New line");
		setResizable(false);
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		JLabel lblStartX = new JLabel("Start point X");
		JLabel lblStartY = new JLabel("Start point Y");
		JLabel lblEndX = new JLabel("End point X");
		JLabel lblEndY = new JLabel("End point Y");
		txtStartX = new JTextField();
		txtStartX.setColumns(10);
		txtStartY = new JTextField();
		txtStartY.setColumns(10);
		txtEndX = new JTextField();
		txtEndX.setColumns(10);
		txtEndY = new JTextField();
		txtEndY.setColumns(10);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(35)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(lblStartX, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblStartY, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblEndX, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblEndY, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(txtEndY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtEndX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtStartY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtStartX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(117, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(32)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblStartX)
						.addComponent(txtStartX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblStartY)
						.addComponent(txtStartY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(26)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblEndX)
						.addComponent(txtEndX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblEndY)
						.addComponent(txtEndY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(68, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnAdd = new JButton("OK");
				btnAdd.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(txtStartX.getText().trim().isEmpty() || txtStartY.getText().trim().isEmpty()
								|| txtEndX.getText().trim().isEmpty() || txtEndY.getText().trim().isEmpty()) {
							isOK = false;
							setVisible(true);
							getToolkit().beep();
							JOptionPane.showMessageDialog(null, "One or more text fields are empty!");
						} else {
							isOK = true;
							dispose();
						}
						
					}
				});
				btnAdd.setActionCommand("OK");
				buttonPane.add(btnAdd);
				getRootPane().setDefaultButton(btnAdd);
			}
			{
				JButton btnCancel = new JButton("Cancel");
				btnCancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				btnCancel.setActionCommand("Cancel");
				buttonPane.add(btnCancel);
			}
		}
	}
	
	public void setLine(Line l) {
		if(isClicked) {
			txtStartX.setText(String.valueOf(l.getStartPoint().getX()));
			txtStartY.setText(String.valueOf(l.getStartPoint().getY()));
			txtEndX.setText(String.valueOf(l.getEndPoint().getX()));
			txtEndY.setText(String.valueOf(l.getEndPoint().getY()));
			this.color=l.getColor();
		}
		else {
			txtStartX.setText("");
			txtStartY.setText("");
			txtEndX.setText("");
			txtEndY.setText("");
		}
		
	}

	public JTextField getTxtStartX() {
		return txtStartX;
	}

	public void setTxtStartX(JTextField txtStartX) {
		this.txtStartX = txtStartX;
	}

	public JTextField getTxtStartY() {
		return txtStartY;
	}

	public void setTxtStartY(JTextField txtStartY) {
		this.txtStartY = txtStartY;
	}

	public JTextField getTxtEndX() {
		return txtEndX;
	}

	public void setTxtEndX(JTextField txtEndX) {
		this.txtEndX = txtEndX;
	}

	public JTextField getTxtEndY() {
		return txtEndY;
	}

	public void setTxtEndY(JTextField txtEndY) {
		this.txtEndY = txtEndY;
	}

	public Line getLine() {
		return l;
	}

	public boolean isClicked() {
		return isClicked;
	}

	public void setClicked(boolean isClicked) {
		this.isClicked = isClicked;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean isOK() {
		return isOK;
	}

	public void setOK(boolean isOK) {
		this.isOK = isOK;
	}
	
	
	

}
