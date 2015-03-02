import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;


public class Tests {
	private static final File File = null;
	
	@Test
	public void SaveGameTest() throws FileNotFoundException
	{
		File f = new File("save_files");
		DatabaseStuff.SaveGame(1,f);
		File file = new File("save001.txt");
		try {
			Scanner input = new Scanner(file);
			while (input.hasNextLine())
			{
				String line = input.nextLine();
				assertEquals("The game was saved.",line);
			}
			input.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.print("File not found");
			}
	}
	
	@Test
	public void LoadGameTest() throws FileNotFoundException
	{
		File f = new File("save_files");
		String name = DatabaseStuff.LoadGame(1, f);
		assertEquals("Keith Russ",name);
	}
	
	@Test
	public void UpdateFileTest() throws FileNotFoundException
	{
		File f = new File("save_files");
		DatabaseStuff.UpdateFile(1, "Keith Russel", f);
		String name = DatabaseStuff.LoadGame(1, f);
		assertEquals("Keith Russel",name);
	}
	
	@Test
	public void fileTransferTest() throws IOException { //includes send and receiving a file from both client and server side
		final FileOutputStream fOStream = null;
		final BufferedOutputStream bOStream = null;
		ServerSocket s = null;
		final Socket incoming = null;
		//Socket clientS = null;
		final FileInputStream fIStream = null;
		final BufferedInputStream bIStream = null;
		final OutputStream oStream = null;
		
		
		NetworkClient.getFile(fOStream, bOStream, incoming);
		
		File f = new File("//192.168.1.229/Users/Public/ClientSide/databaseR.txt");
		
		try {
			Thread.sleep(5000); //waits before calling sendFile()
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		NetworkClient.sendFile(fIStream, bIStream, oStream, f);
		//assertTrue(s.isBound());
		//assertTrue(s.isClosed());
		
		
		try {
			Thread.sleep(2000); //waits for files to appear
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue(f.exists());
		
		File ff = new File("//192.168.1.229/Users/Public/ServerSide/databaseRR.txt");
		
		assertTrue(ff.exists());

	}
	
//	@Test
//	public void savedFileExistsTest() throws IOException {
//		File f = new File("C:/Users/Louis/Desktop/databaseRR.txt");
//		assertTrue(f.exists());
//	}

}
