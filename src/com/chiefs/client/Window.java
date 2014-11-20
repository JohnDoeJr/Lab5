package com.chiefs.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;

import com.chiefs.server.Forme;
public class Window extends JFrame implements ActionListener, ChangeListener {

	private static final int GRAPHIC_WIDTH = 600;
	private static final int GRAPHIC_HEIGHT = 400;
	private static final int HORIZONTAL_CENTER = 300;
	private static final int VERTICAL_CENTER = 200;
	private static final int ROWS_NUMBER = 1; 
	private static final int COLS_NUMBER = 5;

	private Forme forme;
	private Server server;
	private ResourceBundle labelsBundle;
	
	private GraphicPanel graphicArea;
	private JList axisXList;
	
	private ButtonGroup axisYButtonGroup;
	private JRadioButton axisYRadioButton1;
	private JRadioButton axisYRadioButton2;
	private JRadioButton axisYRadioButton3;
	private JRadioButton axisYRadioButton4;
	
	private JButton addPointButton;
	
	private JSpinner radiusSpinner;
	private JLabel pointLabel;
	
	private List<Nokta> points;
	
	public Window(Forme forme, Server server, Locale locale) {
		this.forme = forme;
		this.server = server;
		labelsBundle = ResourceBundle.getBundle("FrameLabelsBundle", locale);
		points = new ArrayList<Nokta>();
		initUI();
	}

	private void initUI() {
		setTitle(labelsBundle.getString("frameName"));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setLayout(new BorderLayout());
		
		graphicArea = new GraphicPanel(forme, points);		
		graphicArea.setPreferredSize(new Dimension(GRAPHIC_WIDTH, GRAPHIC_HEIGHT));
		
		graphicArea.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Nokta point = new Nokta(e.getX() - HORIZONTAL_CENTER, VERTICAL_CENTER - e.getY());
				addPoint(point);
			}
		});
		
		add(graphicArea, BorderLayout.CENTER);
		
		JPanel userControls = new JPanel(new GridLayout(ROWS_NUMBER, COLS_NUMBER));
		userControls.setPreferredSize(new Dimension(600, 80));
		//Setting x
		Object[] data = {0f, -5f, 3f, 20f};
		axisXList = new JList(data);
		axisXList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);		
		axisXList.setLayout(new FlowLayout());
		userControls.add(axisXList);
		
		//Setting y
		axisYRadioButton1 = new JRadioButton("0");		
		
		axisYRadioButton2 = new JRadioButton("20");		
		
		axisYRadioButton3 = new JRadioButton("-40");		
		
		axisYRadioButton4 = new JRadioButton("30");		
		
		axisYButtonGroup = new ButtonGroup();
		axisYButtonGroup.add(axisYRadioButton1);
		axisYButtonGroup.add(axisYRadioButton2);
		axisYButtonGroup.add(axisYRadioButton3);
		axisYButtonGroup.add(axisYRadioButton4);
		
		JPanel axisYSetterPanel = new JPanel(new FlowLayout());
		
		axisYSetterPanel.add(axisYRadioButton1);
		axisYSetterPanel.add(axisYRadioButton2);
		axisYSetterPanel.add(axisYRadioButton3);
		axisYSetterPanel.add(axisYRadioButton4);
		userControls.add(axisYSetterPanel);
		
		//Setting add point button
		addPointButton = new JButton(labelsBundle.getString("addButtonLabel"));
		addPointButton.setBorder(new EmptyBorder(15, 20, 20, 20));
		addPointButton.addActionListener(this);
		JPanel kostylPanel = new JPanel(new FlowLayout());
		kostylPanel.add(addPointButton);
		userControls.add(kostylPanel);
		
		//Setting radius
		SpinnerModel model = new SpinnerNumberModel(forme.getRadius(), 50f, 200f, 1f);
		radiusSpinner = new JSpinner(model);
		radiusSpinner.addChangeListener(this);
		userControls.add(radiusSpinner);
		
		pointLabel = new JLabel(labelsBundle.getString("pointLabelLabel"));
		userControls.add(pointLabel);
		
		add(userControls, BorderLayout.SOUTH);	
		
		pack();
	}
	
	private void addPoint(Nokta point) {		
		points.add(point);
		pointLabel.setText(point.toString());
		try {
			if (server.checkNokta(point.getAxisX(), point.getAxisY(), forme.getRadius())) {
				graphicArea.setGray(false);
				graphicArea.repaint();
			} else {
				graphicArea.setGray(false);
				DrawingThread drawingThread = new DrawingThread(graphicArea);
				drawingThread.start();
			}
		} catch (IOException e) {
			graphicArea.setGray(true);
			graphicArea.repaint();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		
		float tmpAxisX;
		float tmpAxisY = 0;
		boolean radioIsSelected = false;
		
		if (axisXList.getSelectedIndex() == -1) {
			return;
		} else {
			tmpAxisX = (Float) axisXList.getSelectedValue();
		}
		
		for (Enumeration<AbstractButton> buttons = axisYButtonGroup.getElements(); buttons.hasMoreElements();) {
			AbstractButton button = buttons.nextElement();
			
			if (button.isSelected()) {
				tmpAxisY = Float.valueOf(button.getText());
				radioIsSelected = true;
			}
		}
		
		if (radioIsSelected) {
			Nokta point = new Nokta(tmpAxisX, tmpAxisY);
			addPoint(point);
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		forme.setRadius((float) (double) (Double) radiusSpinner.getValue());
		graphicArea.repaint();
	}	
}

class DrawingThread extends Thread {
	
	private GraphicPanel panel;
	
	public DrawingThread(GraphicPanel panel) {
		this.panel = panel;
	}
	
	@Override
	public void run() {
		panel.redraw();
	}
}