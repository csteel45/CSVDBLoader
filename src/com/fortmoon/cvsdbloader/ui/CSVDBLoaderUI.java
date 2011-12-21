/*
 * @(#)CSVDBLoaderUI.java $Date: Dec 20, 2011 4:34:24 PM $
 * 
 * Copyright © 2011 FortMoon Consulting, Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of FortMoon
 * Consulting, Inc. ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the license agreement you entered into with FortMoon Consulting.
 * 
 * FORTMOON MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 * NON-INFRINGEMENT. FORTMOON SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */
package com.fortmoon.cvsdbloader.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.border.EtchedBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Dimension;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JMenuItem;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import javax.swing.border.TitledBorder;
import javax.swing.BoxLayout;
import javax.swing.UIManager;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JSpinner;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractListModel;
import javax.swing.JComboBox;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JFileChooser;
import javax.swing.JCheckBox;
import javax.swing.SpinnerNumberModel;
import javax.swing.JToggleButton;
import java.awt.Rectangle;

/**
 * @author Christopher Steel - FortMoon Consulting, Inc.
 * 
 * @since Dec 20, 2011 4:34:24 PM
 */
public class CSVDBLoaderUI {

	private JFrame frame;
	private final JLabel progressLabel = new JLabel("Progress");
	private final JProgressBar progressBar = new JProgressBar();
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Create the application.
	 */
	public CSVDBLoaderUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(CSVDBLoaderUI.class.getResource("/images/new_database.png")));
		frame.setBackground(new Color(105, 105, 105));
		frame.setMinimumSize(new Dimension(800, 600));
		frame.setTitle("CSV DB Loader");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel headerPanel = new JPanel();
		frame.getContentPane().add(headerPanel, BorderLayout.NORTH);
		headerPanel.setLayout(new BorderLayout(0, 0));
		
		JButton iconButton = new JButton("");
		iconButton.setFocusPainted(false);
		iconButton.setRequestFocusEnabled(false);
		iconButton.setRolloverEnabled(false);
		iconButton.setContentAreaFilled(false);
		iconButton.setBorderPainted(false);
		iconButton.setMaximumSize(new Dimension(11, 9));
		iconButton.setIcon(new ImageIcon(CSVDBLoaderUI.class.getResource("/images/new_database.png")));
		iconButton.setOpaque(false);
		headerPanel.add(iconButton, BorderLayout.WEST);
		
		JLabel titleLabel = new JLabel("CSV Database Loader               ");
		titleLabel.setForeground(new Color(255, 20, 147));
		titleLabel.setFont(new Font("Modern No. 20", Font.BOLD, 30));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		headerPanel.add(titleLabel);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(new Color(220, 220, 220));
		mainPanel.setOpaque(false);
		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel bodyPanel = new JPanel();
		mainPanel.add(bodyPanel, BorderLayout.CENTER);
		bodyPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel configPanel = new JPanel();
		bodyPanel.add(configPanel);
		configPanel.setName("configPanel");
		configPanel.setLayout(new GridLayout(0, 2, 5, 10));
		
		JPanel filePanel = new JPanel();
		filePanel.setForeground(new Color(70, 130, 180));
		filePanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "File", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(70, 130, 180)));
		configPanel.add(filePanel);
		filePanel.setLayout(new BoxLayout(filePanel, BoxLayout.Y_AXIS));
		
		JPanel panel_5 = new JPanel();
		panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.Y_AXIS));
		
		JPanel filenamePanel = new JPanel();
		filenamePanel.setPreferredSize(new Dimension(10, 40));
		filenamePanel.setMaximumSize(new Dimension(32767, 40));
		FlowLayout fl_filenamePanel = (FlowLayout) filenamePanel.getLayout();
		fl_filenamePanel.setAlignment(FlowLayout.LEADING);
		panel_5.add(filenamePanel);
		
		JLabel lblNewLabel = new JLabel("File name:");
		lblNewLabel.setPreferredSize(new Dimension(80, 14));
		lblNewLabel.setMinimumSize(new Dimension(60, 14));
		filenamePanel.add(lblNewLabel);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setMinimumSize(new Dimension(23, 25));
		filenamePanel.add(comboBox);
		comboBox.setEditable(true);
		comboBox.setPreferredSize(new Dimension(225, 25));
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setPreferredSize(new Dimension(35, 25));
		btnNewButton.setIcon(new ImageIcon(CSVDBLoaderUI.class.getResource("/images/filebluesm.jpg")));
		filenamePanel.add(btnNewButton);
		filePanel.add(panel_5);
		
		JPanel tablenamePanel = new JPanel();
		tablenamePanel.setMaximumSize(new Dimension(32767, 40));
		FlowLayout fl_tablenamePanel = (FlowLayout) tablenamePanel.getLayout();
		fl_tablenamePanel.setAlignment(FlowLayout.LEADING);
		tablenamePanel.setPreferredSize(new Dimension(120, 40));
		panel_5.add(tablenamePanel);
		
		JLabel lblNewLabel_1 = new JLabel("Table name:");
		lblNewLabel_1.setPreferredSize(new Dimension(80, 14));
		tablenamePanel.add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setMinimumSize(new Dimension(250, 25));
		textField.setPreferredSize(new Dimension(250, 25));
		tablenamePanel.add(textField);
		textField.setColumns(10);
		
		JPanel delimiterPanel = new JPanel();
		delimiterPanel.setMaximumSize(new Dimension(32767, 40));
		FlowLayout fl_delimiterPanel = (FlowLayout) delimiterPanel.getLayout();
		fl_delimiterPanel.setAlignment(FlowLayout.LEADING);
		panel_5.add(delimiterPanel);
		
		JLabel lblNewLabel_3 = new JLabel("Delimiter(s):");
		lblNewLabel_3.setPreferredSize(new Dimension(80, 16));
		delimiterPanel.add(lblNewLabel_3);
		
		textField_1 = new JTextField();
		textField_1.setHorizontalAlignment(SwingConstants.TRAILING);
		textField_1.setPreferredSize(new Dimension(6, 25));
		textField_1.setText(",");
		delimiterPanel.add(textField_1);
		textField_1.setColumns(10);
		
		JPanel batchPanel = new JPanel();
		batchPanel.setMaximumSize(new Dimension(32767, 40));
		FlowLayout fl_batchPanel = (FlowLayout) batchPanel.getLayout();
		fl_batchPanel.setAlignment(FlowLayout.LEADING);
		panel_5.add(batchPanel);
		
		JLabel lblNewLabel_2 = new JLabel("Batch size:");
		lblNewLabel_2.setPreferredSize(new Dimension(80, 14));
		batchPanel.add(lblNewLabel_2);
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(500, 100, 1000, 100));
		spinner.setPreferredSize(new Dimension(60, 25));
		batchPanel.add(spinner);
		
		JPanel blobsPanel = new JPanel();
		FlowLayout fl_blobsPanel = (FlowLayout) blobsPanel.getLayout();
		blobsPanel.setMaximumSize(new Dimension(32767, 40));
		panel_5.add(blobsPanel);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("Truncate blobs to varchar");
		chckbxNewCheckBox.setHorizontalAlignment(SwingConstants.LEFT);
		chckbxNewCheckBox.setHorizontalTextPosition(SwingConstants.LEFT);
		blobsPanel.add(chckbxNewCheckBox);
		
		JPanel databasePanel = new JPanel();
		databasePanel.setForeground(new Color(70, 130, 180));
		configPanel.add(databasePanel);
		databasePanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Database", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(70, 130, 180)));
		databasePanel.setLayout(new BoxLayout(databasePanel, BoxLayout.Y_AXIS));
		
		JPanel panel_9 = new JPanel();
		FlowLayout flowLayout_5 = (FlowLayout) panel_9.getLayout();
		flowLayout_5.setAlignment(FlowLayout.LEADING);
		panel_9.setPreferredSize(new Dimension(120, 40));
		panel_9.setMaximumSize(new Dimension(32767, 40));
		databasePanel.add(panel_9);
		
		JLabel lblNewLabel_4 = new JLabel("JDBC URL:");
		lblNewLabel_4.setPreferredSize(new Dimension(80, 16));
		panel_9.add(lblNewLabel_4);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setEditable(true);
		comboBox_1.setPreferredSize(new Dimension(250, 25));
		panel_9.add(comboBox_1);
		
		JPanel panel_10 = new JPanel();
		FlowLayout flowLayout_6 = (FlowLayout) panel_10.getLayout();
		flowLayout_6.setAlignment(FlowLayout.LEADING);
		panel_10.setMaximumSize(new Dimension(32767, 40));
		panel_10.setPreferredSize(new Dimension(10, 40));
		databasePanel.add(panel_10);
		
		JLabel lblNewLabel_5 = new JLabel("Username:");
		lblNewLabel_5.setPreferredSize(new Dimension(80, 16));
		panel_10.add(lblNewLabel_5);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setEditable(true);
		comboBox_2.setPreferredSize(new Dimension(250, 25));
		panel_10.add(comboBox_2);
		
		JPanel panel_11 = new JPanel();
		FlowLayout flowLayout_7 = (FlowLayout) panel_11.getLayout();
		flowLayout_7.setAlignment(FlowLayout.LEADING);
		panel_11.setPreferredSize(new Dimension(10, 40));
		panel_11.setMaximumSize(new Dimension(32767, 40));
		databasePanel.add(panel_11);
		
		JLabel lblNewLabel_6 = new JLabel("Password:");
		lblNewLabel_6.setPreferredSize(new Dimension(80, 16));
		lblNewLabel_6.setMinimumSize(new Dimension(80, 16));
		panel_11.add(lblNewLabel_6);
		
		JComboBox comboBox_3 = new JComboBox();
		comboBox_3.setEditable(true);
		comboBox_3.setPreferredSize(new Dimension(250, 25));
		panel_11.add(comboBox_3);
		
		JPanel panel_15 = new JPanel();
		FlowLayout flowLayout_8 = (FlowLayout) panel_15.getLayout();
		flowLayout_8.setAlignment(FlowLayout.LEADING);
		panel_15.setPreferredSize(new Dimension(10, 40));
		panel_15.setMinimumSize(new Dimension(10, 40));
		panel_15.setMaximumSize(new Dimension(32767, 40));
		databasePanel.add(panel_15);
		
		JLabel lblNewLabel_7 = new JLabel("Database:");
		lblNewLabel_7.setMaximumSize(new Dimension(80, 16));
		lblNewLabel_7.setPreferredSize(new Dimension(80, 16));
		lblNewLabel_7.setMinimumSize(new Dimension(80, 16));
		panel_15.add(lblNewLabel_7);
		
		JComboBox comboBox_4 = new JComboBox();
		comboBox_4.setPreferredSize(new Dimension(250, 25));
		panel_15.add(comboBox_4);
		
		JPanel panel_14 = new JPanel();
		databasePanel.add(panel_14);
		
		JButton btnNewButton_1 = new JButton("Test");
		panel_14.add(btnNewButton_1);
		
		JPanel runPanel = new JPanel();
		runPanel.setPreferredSize(new Dimension(10, 60));
		bodyPanel.add(runPanel, BorderLayout.SOUTH);
		runPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		runPanel.add(panel_2, BorderLayout.SOUTH);
		
		JButton btnNewButton_3 = new JButton("Run");
		panel_2.add(btnNewButton_3);
		btnNewButton_3.setPreferredSize(new Dimension(63, 25));
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		panel_2.add(horizontalStrut_2);
		horizontalStrut_2.setPreferredSize(new Dimension(10, 0));
		
		JToggleButton tglbtnPause = new JToggleButton("Pause");
		panel_2.add(tglbtnPause);
		
		JPanel statsPanel = new JPanel();
		statsPanel.setPreferredSize(new Dimension(10, 120));
		mainPanel.add(statsPanel, BorderLayout.SOUTH);
		statsPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Statistics", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(70, 130, 180)));
		
		JPanel progressPanel = new JPanel();
		progressPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		progressPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		frame.getContentPane().add(progressPanel, BorderLayout.SOUTH);
		progressPanel.setLayout(new BorderLayout(0, 5));
		progressLabel.setHorizontalAlignment(SwingConstants.CENTER);
		progressLabel.setVerticalAlignment(SwingConstants.TOP);
		progressPanel.add(progressLabel, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		progressPanel.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setPreferredSize(new Dimension(5, 0));
		panel.add(horizontalStrut, BorderLayout.WEST);
		progressBar.setValue(80);
		progressBar.setMinimumSize(new Dimension(10, 20));
		progressBar.setPreferredSize(new Dimension(146, 20));
		panel.add(progressBar, BorderLayout.CENTER);
		progressBar.setStringPainted(true);
		progressBar.setForeground(Color.GREEN);
		progressBar.setBackground(Color.BLACK);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		horizontalStrut_1.setPreferredSize(new Dimension(5, 0));
		panel.add(horizontalStrut_1, BorderLayout.EAST);
		
		Component verticalStrut = Box.createVerticalStrut(-179);
		verticalStrut.setPreferredSize(new Dimension(0, 2));
		verticalStrut.setMinimumSize(new Dimension(0, 1));
		progressPanel.add(verticalStrut, BorderLayout.SOUTH);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic('F');
		menuBar.add(fileMenu);
		
		LookAndFeelMenu lookAndFeelMenu = new LookAndFeelMenu(frame, "LooknFeel");
		lookAndFeelMenu.setMnemonic('L');
		menuBar.add(lookAndFeelMenu);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		menuBar.add(horizontalGlue);
		
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic('H');
		menuBar.add(helpMenu);
		
		JMenuItem aboutMenuItem = new JMenuItem("About");
		aboutMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AboutDialog dialog = new AboutDialog(frame, "CSV DataBase Loader", "Christopher Steel", "1.0", "12/20/2011", "\u00a9 Copyright JackBe 2011", "beta");
				dialog.setImage("images/jackbelogo.gif");
				dialog.setVisible(true);
			}
		});
		
		aboutMenuItem.setMnemonic('A');
		helpMenu.add(aboutMenuItem);
		
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CSVDBLoaderUI window = new CSVDBLoaderUI();
					window.frame.setVisible(true);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
