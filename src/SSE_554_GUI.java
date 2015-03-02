import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.nio.file.*;
import java.sql.*;
import java.util.*;

import javax.sql.*;
import javax.sql.rowset.*;
import javax.swing.*;

public class SSE_554_GUI {
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){
			public void run() {
				JFrame frame = new SSEFrame();
				frame.setTitle("SSE GUI");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.pack();
				frame.setVisible(true);
			}
		});
	}
	
}

class SSEFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField selectionField;
	private JTextField filenameField;
	private JTextField nameField;
	private JLabel status;
	private JButton updateButton;
	private JButton loadButton;
	private JButton saveButton;
	
	public SSEFrame() {
		JPanel panel = new JPanel ();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		selectionField = new JTextField();
		c.weightx = 0.5;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		panel.add(new JLabel("Number: "), c);
		c.gridx = 0;
		c.gridy = 1;
		panel.add(selectionField, c);
		
		filenameField = new JTextField();
		c.gridx = 3;
		c.gridy = 0;
		panel.add(new JLabel("File name: "), c);
		c.gridwidth = 2;
		c.gridx = 3;
		c.gridy = 1;
		panel.add(filenameField, c);
		
		nameField = new JTextField();
		c.gridwidth = 1;
		c.gridx = 5;
		c.gridy = 0;
		panel.add(new JLabel("Name: "), c);
		c.gridwidth = 3;
		c.gridx = 5;
		c.gridy = 1;
		panel.add(nameField, c);
		c.ipadx = 20;
		c.gridwidth = 2;
		
		status = new JLabel();
		status.setText("No save selected.");
		c.ipady = 40;
		c.gridwidth = 2;
		c.gridx = 3;
		c.gridy = 2;
		panel.add(status, c);
		c.ipady = 0;
		
		updateButton = new JButton("Update");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					updateFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 3;
		panel.add(updateButton, c);
		
		loadButton = new JButton("Load");
		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					loadGame();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		c.gridwidth = 2;
		c.gridx = 3;
		c.gridy = 3;
		panel.add(loadButton, c);
		
		saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					saveGame();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		c.gridwidth = 2;
		c.gridx = 5;
		c.gridy = 3;
		panel.add(saveButton, c);
		pack();
		
		//panel.setOpaque(true);
		panel.setVisible(true);
		
		add(panel);
	}
	

	public void updateFile() throws IOException {
		FileInputStream fIStream = null;
		BufferedInputStream bIStream = null;
		OutputStream oStream = null;
		File f = new File("//192.168.1.229/Users/Public/ServerSide/save_files");
		int sel = Integer.parseInt(selectionField.getText());
		String name = nameField.getText();
		
		NetworkClient.sendFile(fIStream, bIStream, oStream, f);
		DatabaseStuff.UpdateFile(sel, name, f);
		status.setText("Database Updated.");
	}
	
	public void loadGame() throws IOException {
		FileOutputStream fOStream = null;
		BufferedOutputStream bOStream = null;
		Socket s = null;
		File f = NetworkClient.getFile(fOStream, bOStream, s);	
		int sel = Integer.parseInt(selectionField.getText());
		
		System.out.println(f.getName());
		
		String name = DatabaseStuff.LoadGame(sel, f);
		
		filenameField.setText(f.getName());
		nameField.setText(name);
		status.setText("Game Loaded.");
	}
	
	public void saveGame() throws IOException {
		File f = new File(filenameField.getText());
		int sel = Integer.parseInt(selectionField.getText());
		
		DatabaseStuff.SaveGame(sel, f);
		
		status.setText("Game Saved.");
	}
}