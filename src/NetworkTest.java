import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Before;
import org.junit.Test;


public class NetworkTest {
	
	private final ByteArrayOutputStream console = new ByteArrayOutputStream();
	
	@Before
	public void setUpStream() {
		System.setOut(new PrintStream(console));
	}
	
	@Test
	public void fileSentTest() throws IOException {
		
		FileInputStream fStream = null;
		BufferedInputStream bStream = null;
		OutputStream oStream = null;
		ServerSocket s = new ServerSocket(NetworkConnection.portNum);
		Socket incoming = null;
		
		NetworkConnection.connect(fStream, bStream, oStream, s, incoming);
		
		assertTrue(s.isBound());
		assertTrue(s.isClosed());
	}

}
