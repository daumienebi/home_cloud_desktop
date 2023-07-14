package com.devdaumienebi.homecloud.client.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import com.devdaumienebi.homecloud.HomeCloudApplication;
import com.devdaumienebi.homecloud.client.utils.*;

import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.OutputStream;
import java.io.PrintStream;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;

import java.awt.FlowLayout;
import javax.swing.JTextArea;
import javax.swing.border.SoftBevelBorder;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;

public class HomeUI {

	private JFrame frmHomeCloud;
	private JTextField txtStoragePath;
	private ApplicationContext applicationContext;
	private JTextArea consoleOutputTxtArea;
	private JScrollPane consoleOutputScrollPane;
	private boolean hideConsoleOutput = false;
	private boolean serverIsRunning = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomeUI window = new HomeUI();
					window.frmHomeCloud.setVisible(true);
					window.frmHomeCloud.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public HomeUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	*/
	
	private void initialize() {
		frmHomeCloud = new JFrame();
		frmHomeCloud.setIconImage(Toolkit.getDefaultToolkit().getImage(HomeUI.class.getResource("/com/devdaumienebi/homecloud/resources/icon_128_128.png")));
		frmHomeCloud.setTitle("Home Cloud");
		frmHomeCloud.setBounds(100, 100, 800, 550);
		//450,300
		frmHomeCloud.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frmHomeCloud.getContentPane().add(panel, BorderLayout.CENTER);
		
		JPanel serverControlPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) serverControlPanel.getLayout();
		flowLayout.setAlignOnBaseline(true);
		
		JButton startBtn = new JButton("Start Server ");
		startBtn.setForeground(new Color(46, 139, 87));
		startBtn.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		startBtn.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		startBtn.setIcon(new ImageIcon(HomeUI.class.getResource("/com/devdaumienebi/homecloud/resources/start_server_128.png")));
		startBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(applicationContext == null) {
					if(!txtStoragePath.getText().isEmpty()) {
						redirectConsoleOutput(consoleOutputTxtArea);
						applicationContext = SpringApplication.run(HomeCloudApplication.class);
						serverIsRunning = true; // polish this part in case of any error
						redirectConsoleOutput(consoleOutputTxtArea);
					}else {
						JOptionPane.showMessageDialog(null, "A storage location must be selected before running the server", "Select a storage location", JOptionPane.INFORMATION_MESSAGE);
					}
					
				}else {
					JOptionPane.showMessageDialog(null, "The server is already running", "Server running", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		serverControlPanel.add(startBtn);
		
		JButton stopBtn = new JButton("Stop Server ");
		stopBtn.setForeground(Color.RED);
		stopBtn.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		stopBtn.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		stopBtn.setIcon(new ImageIcon(HomeUI.class.getResource("/com/devdaumienebi/homecloud/resources/shutdown_server_128.png")));
		stopBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(applicationContext != null) {
					//()-> Lambda expression to return 0 for the ExitGenerator
					SpringApplication.exit(applicationContext,()-> 0);
					applicationContext = null;
					serverIsRunning = false;
					redirectConsoleOutput(consoleOutputTxtArea);
				}else {
					JOptionPane.showMessageDialog(null, "The server is already shutdown", "Server shutdown", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		JLabel spaceLbl = new JLabel("                     ");
		serverControlPanel.add(spaceLbl);
		serverControlPanel.add(stopBtn);
		
		JPanel panel_2 = new JPanel();
		
		JPanel consoleOutputPanel = new JPanel();
		
		JLabel consoleLbl = new JLabel("");
		consoleLbl.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(serverControlPanel, GroupLayout.DEFAULT_SIZE, 763, Short.MAX_VALUE)
				.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 763, Short.MAX_VALUE)
				.addComponent(consoleOutputPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(321)
					.addComponent(consoleLbl)
					.addContainerGap(335, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(serverControlPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(consoleLbl)
					.addGap(13)
					.addComponent(consoleOutputPanel, GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE))
		);
		consoleOutputPanel.setLayout(new BorderLayout(0, 0));
		
		consoleOutputTxtArea = new JTextArea();
		consoleOutputTxtArea.setBackground(new Color(248, 248, 255));
		consoleOutputTxtArea.setForeground(new Color(34, 139, 34));
		consoleOutputTxtArea.setLineWrap(true);
		consoleOutputTxtArea.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		consoleOutputTxtArea.setEditable(false);
		consoleOutputScrollPane = new JScrollPane(consoleOutputTxtArea);
		consoleOutputPanel.add(consoleOutputScrollPane);
		
		JLabel lblNewLabel = new JLabel("CONSOLE OUTPUT :");
		lblNewLabel.setIcon(new ImageIcon(HomeUI.class.getResource("/com/devdaumienebi/homecloud/resources/console_output_32.png")));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
		consoleOutputPanel.add(lblNewLabel, BorderLayout.NORTH);
		
		JCheckBox consoleVisibilityChkBox = new JCheckBox("Hide Console output");
		consoleOutputPanel.add(consoleVisibilityChkBox, BorderLayout.SOUTH);
		
		JLabel storageLocationLbl = new JLabel("Storage Location");
		storageLocationLbl.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		storageLocationLbl.setIcon(new ImageIcon(HomeUI.class.getResource("/com/devdaumienebi/homecloud/resources/storage.png")));
		panel_2.add(storageLocationLbl);
		
		JButton selectBtn = new JButton("Select");
		selectBtn.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		selectBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!serverIsRunning) {
					StorageChooser sc = new StorageChooser();
					String storagePath = sc.setStoragePath();
					txtStoragePath.setText(storagePath);
				}else {
					JOptionPane.showMessageDialog(null, "The storage location cannot be modified while the server is running", "Info", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		panel_2.add(selectBtn);
		
		txtStoragePath = new JTextField();
		txtStoragePath.setBackground(new Color(240, 248, 255));
		txtStoragePath.setForeground(new Color(46, 139, 87));
		txtStoragePath.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		txtStoragePath.setEditable(false);
		panel_2.add(txtStoragePath);
		txtStoragePath.setColumns(35);
		panel.setLayout(gl_panel);
		
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		menuBar.setForeground(Color.WHITE);
		menuBar.setBackground(Color.DARK_GRAY);
		frmHomeCloud.setJMenuBar(menuBar);
		
		JMenu homeMenu = new JMenu("Home");
		homeMenu.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		homeMenu.setForeground(Color.WHITE);
		homeMenu.setBackground(Color.DARK_GRAY);
		menuBar.add(homeMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Restart");
		homeMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Exit");
		homeMenu.add(mntmNewMenuItem_1);
		
		JMenu serverMenu = new JMenu("Server");
		serverMenu.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		serverMenu.setForeground(Color.WHITE);
		serverMenu.setBackground(Color.DARK_GRAY);
		menuBar.add(serverMenu);
		
		JMenuItem mntmNewMenuItem_10 = new JMenuItem("Server config");
		serverMenu.add(mntmNewMenuItem_10);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Server status");
		serverMenu.add(mntmNewMenuItem_2);
		
		JMenu adminMenu = new JMenu("Admin");
		adminMenu.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		adminMenu.setForeground(Color.WHITE);
		adminMenu.setBackground(Color.DARK_GRAY);
		menuBar.add(adminMenu);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Users");
		adminMenu.add(mntmNewMenuItem_3);
		
		JMenu settingsMenu = new JMenu("Settings");
		settingsMenu.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		settingsMenu.setForeground(Color.WHITE);
		settingsMenu.setBackground(Color.DARK_GRAY);
		menuBar.add(settingsMenu);
		
		JMenu mnNewMenu_5 = new JMenu("Language");
		settingsMenu.add(mnNewMenu_5);
		
		JMenuItem mntmNewMenuItem_9 = new JMenuItem("English");
		mnNewMenu_5.add(mntmNewMenuItem_9);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Español");
		mnNewMenu_5.add(mntmNewMenuItem_4);
		
		JMenuItem mntmNewMenuItem_5 = new JMenuItem("Galiciain");
		mnNewMenu_5.add(mntmNewMenuItem_5);
		
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		helpMenu.setForeground(Color.WHITE);
		helpMenu.setBackground(Color.DARK_GRAY);
		menuBar.add(helpMenu);
		
		JMenuItem mntmNewMenuItem_6 = new JMenuItem("Help content");
		helpMenu.add(mntmNewMenuItem_6);
		
		JMenuItem mntmNewMenuItem_7 = new JMenuItem("Terms of use");
		helpMenu.add(mntmNewMenuItem_7);
		
		JMenuItem mntmNewMenuItem_8 = new JMenuItem("Privacy policy");
		helpMenu.add(mntmNewMenuItem_8);
	}
	
	private void redirectConsoleOutput(JTextArea textArea) {
        PrintStream consolePrintStream = new PrintStream(new CustomOutputStream(textArea));
        System.setOut(consolePrintStream);
        System.setErr(consolePrintStream);
    }
}

