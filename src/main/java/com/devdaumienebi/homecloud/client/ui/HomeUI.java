package com.devdaumienebi.homecloud.client.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import com.devdaumienebi.homecloud.HomeCloudApplication;
import com.devdaumienebi.homecloud.client.utils.StorageChooser;

import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class HomeUI {

	private JFrame frmHomeCloud;
	private JTextField txtStoragePath;
	private ApplicationContext applicationContext;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomeUI window = new HomeUI();
					window.frmHomeCloud.setVisible(true);
					//SpringApplication.run(HomeCloudApplication.class, args); -- To run the sprinboot app
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
		frmHomeCloud.setBounds(100, 100, 800, 500);
		//450,300
		frmHomeCloud.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frmHomeCloud.getContentPane().add(panel, BorderLayout.CENTER);
		
		JLabel lblImage = new JLabel("");
		lblImage.setHorizontalAlignment(SwingConstants.CENTER);
		lblImage.setIcon(new ImageIcon(HomeUI.class.getResource("/com/devdaumienebi/homecloud/resources/icon_128_128.png")));
		lblImage.setSize(new Dimension(200, 200));
		
		JPanel panel_1 = new JPanel();
		
		JButton startBtn = new JButton("Start Server");
		startBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(applicationContext == null) {
					applicationContext = SpringApplication.run(HomeCloudApplication.class);
				}else {
					JOptionPane.showMessageDialog(null, "The server is already running", "Server running", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		panel_1.add(startBtn);
		
		JButton stopBtn = new JButton("Stop Server");
		stopBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(applicationContext != null) {
					//()-> Lambda expression to return 0 for the ExitGenerator
					SpringApplication.exit(applicationContext,()-> 0);
					applicationContext = null;
				}else {
					JOptionPane.showMessageDialog(null, "The server is already shutdown", "Server shutdown", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		panel_1.add(stopBtn);
		
		JPanel panel_2 = new JPanel();
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(lblImage, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 784, Short.MAX_VALUE)
				.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 784, Short.MAX_VALUE)
				.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 784, Short.MAX_VALUE)
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addGap(51)
					.addComponent(lblImage, GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE))
		);
		
		JLabel lblNewLabel = new JLabel("Storage Location");
		panel_2.add(lblNewLabel);
		
		JButton btnNewButton_2 = new JButton("Select");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StorageChooser sc = new StorageChooser();
				String storagePath = sc.setStoragePath();
				txtStoragePath.setText(storagePath);
			}
		});
		panel_2.add(btnNewButton_2);
		
		txtStoragePath = new JTextField();
		txtStoragePath.setEditable(false);
		panel_2.add(txtStoragePath);
		txtStoragePath.setColumns(35);
		panel.setLayout(gl_panel);
		
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		menuBar.setForeground(Color.WHITE);
		menuBar.setBackground(Color.DARK_GRAY);
		frmHomeCloud.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Home");
		mnNewMenu.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		mnNewMenu.setForeground(Color.WHITE);
		mnNewMenu.setBackground(Color.DARK_GRAY);
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Restart");
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Exit");
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JMenu mnNewMenu_1 = new JMenu("Server");
		mnNewMenu_1.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		mnNewMenu_1.setForeground(Color.WHITE);
		mnNewMenu_1.setBackground(Color.DARK_GRAY);
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Server status");
		mnNewMenu_1.add(mntmNewMenuItem_2);
		
		JMenu mnNewMenu_2 = new JMenu("Admin");
		mnNewMenu_2.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		mnNewMenu_2.setForeground(Color.WHITE);
		mnNewMenu_2.setBackground(Color.DARK_GRAY);
		menuBar.add(mnNewMenu_2);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Users");
		mnNewMenu_2.add(mntmNewMenuItem_3);
		
		JMenu mnNewMenu_3 = new JMenu("Settings");
		mnNewMenu_3.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		mnNewMenu_3.setForeground(Color.WHITE);
		mnNewMenu_3.setBackground(Color.DARK_GRAY);
		menuBar.add(mnNewMenu_3);
		
		JMenu mnNewMenu_5 = new JMenu("Language");
		mnNewMenu_3.add(mnNewMenu_5);
		
		JMenuItem mntmNewMenuItem_9 = new JMenuItem("English");
		mnNewMenu_5.add(mntmNewMenuItem_9);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Español");
		mnNewMenu_5.add(mntmNewMenuItem_4);
		
		JMenuItem mntmNewMenuItem_5 = new JMenuItem("Galego");
		mnNewMenu_5.add(mntmNewMenuItem_5);
		
		JMenu mnNewMenu_4 = new JMenu("Help");
		mnNewMenu_4.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		mnNewMenu_4.setForeground(Color.WHITE);
		mnNewMenu_4.setBackground(Color.DARK_GRAY);
		menuBar.add(mnNewMenu_4);
		
		JMenuItem mntmNewMenuItem_6 = new JMenuItem("Help content");
		mnNewMenu_4.add(mntmNewMenuItem_6);
		
		JMenuItem mntmNewMenuItem_7 = new JMenuItem("Terms of use");
		mnNewMenu_4.add(mntmNewMenuItem_7);
		
		JMenuItem mntmNewMenuItem_8 = new JMenuItem("Privacy policy");
		mnNewMenu_4.add(mntmNewMenuItem_8);
	}
}
