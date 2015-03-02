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
		JPanel buttonPanel = new JPanel ();
		buttonPanel.setLayout(new GridLayout(3,3));
		
		selectionField = new JTextField();
		buttonPanel.add(selectionField);
		
		filenameField = new JTextField();
		buttonPanel.add(filenameField);
		
		nameField = new JTextField();
		buttonPanel.add(nameField);
		buttonPanel.add(new JLabel());
		status = new JLabel();
		status.setText("No save selected.");
		buttonPanel.add(status);
		buttonPanel.add(new JLabel());
		
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
		buttonPanel.add(updateButton);
		
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
		buttonPanel.add(loadButton);
		
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
		buttonPanel.add(saveButton);
		pack();
		
		buttonPanel.setOpaque(true);
		buttonPanel.setVisible(true);
		
		add(buttonPanel);
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