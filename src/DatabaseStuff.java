import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Scanner;

public class DatabaseStuff 
{
	  public void SaveGame(int selection) throws FileNotFoundException
	  {
	    Connection c = null;
	    Statement stmt = null;
	    String file = null,name;
	    int id;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:save_files");
	      c.setAutoCommit(false);
	      System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      ResultSet rs = stmt.executeQuery( "SELECT * FROM 'Saved Games' WHERE 'Saved Games'.'FileNum' = "+ selection +";" );
	      while ( rs.next() ) {
	         id = rs.getInt("FileNum");
	         name = rs.getString("File Name");
	         file  = rs.getString("Save File");
	         /*System.out.println( "ID = " + id );
	         System.out.println( "NAME = " + name );
	         System.out.println( "FILE = " + file );
	         System.out.println();*/
	      }
	      rs.close();
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Operation done successfully");
	    if(!file.isEmpty())
	    {
		    PrintWriter writer = new PrintWriter(file);
		    writer.print("The game was saved.");
		    writer.close();
	    }
	  }
	  
	  public void LoadGame(int selection) throws FileNotFoundException
	  {
	    Connection c = null;
	    Statement stmt = null;
	    String file = null,name;
	    int id;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:save_files");
	      c.setAutoCommit(false);
	      System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      ResultSet rs = stmt.executeQuery( "SELECT * FROM 'Saved Games' WHERE 'Saved Games'.'FileNum' = "+ selection +";" );
	      while ( rs.next() ) {
	         id = rs.getInt("FileNum");
	         name = rs.getString("File Name");
	         file  = rs.getString("Save File");
	         System.out.println( "ID = " + id );
	         System.out.println( "NAME = " + name );
	         System.out.println( "FILE = " + file );
	         System.out.println();
	      }
	      rs.close();
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Operation done successfully");
	    if(!file.isEmpty())
	    {
			try {
				Scanner input = new Scanner(new File(file));
				while (input.hasNextLine())
				{
					String line = input.nextLine();
					System.out.println(line);
					System.out.println("The Game was loaded.");
				}
				input.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.print("File not found");
				}
	    }
	  }
	  
	  public void UpdateFile(int selection, String newName)
	  {
		  Connection c = null;
		    Statement stmt = null;
		    String file = null,name;
		    int id;
		    try {
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:save_files");
		      c.setAutoCommit(false);
		      System.out.println("Opened database successfully");

		      stmt = c.createStatement();
		      String sql = "UPDATE 'Saved Games' set 'File Name' = " + newName +" WHERE 'Saved Games'.'FileNum' = "+ selection +";";
		      stmt.executeUpdate(sql);
		      c.commit();		      
		   /*   while ( rs.next() ) {
		         id = rs.getInt("FileNum");
		         name = rs.getString("File Name");
		         file  = rs.getString("Save File");
		         System.out.println( "ID = " + id );
		         System.out.println( "NAME = " + name );
		         System.out.println( "FILE = " + file );
		         System.out.println();
		      }
		      rs.close();*/
		      stmt.close();
		      c.close();
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
		    System.out.println("Operation done successfully");
	  }
}
