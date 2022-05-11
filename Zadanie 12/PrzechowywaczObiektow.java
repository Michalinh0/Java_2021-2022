import java.sql.Connection;
import java.util.Optional;
import java.io.IOException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.lang.StringBuilder;
import java.util.Optional;
import java.sql.PreparedStatement;

public class PrzechowywaczObiektow implements PrzechowywaczI {

	Connection con;
	public void setConnection(Connection connection) 
	{
		con = connection;
	}

	
	public int save(int path, Object obiektDoZapisu) throws IllegalArgumentException 
	{
		String pathname = null;
		boolean found = false;
		try (ResultSet rs = con.createStatement().executeQuery("SELECT * FROM Katalogi"))
		{
			while(rs.next())
			{
				if(rs.getInt("idKatalogu")==path)
				{
					
					pathname = rs.getString("katalog");
					return putfile(obiektDoZapisu, con, pathname, path);			
				}
			}
			if(!found)
				throw new IllegalArgumentException();
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}
	
	public int putfile(Object obiektDoZapisu, Connection con, String pathname, int path)
	{
		int x = howmany();
		String filename = null;
		StringBuilder sb = new StringBuilder();
		sb.append("plik");
		sb.append(x);
		filename = sb.toString();
		try
		{
			FileOutputStream output = new FileOutputStream(pathname + File.separator + filename);
			ObjectOutputStream out = new ObjectOutputStream(output);
			out.writeObject(obiektDoZapisu);
			out.close();
			output.close();
			PreparedStatement ps = con.prepareStatement("INSERT INTO Pliki VALUES ( ? , ? , ? )");
			ps.setInt(1, x);
			ps.setInt(2, path);
			ps.setString(3, filename);
			ps.executeUpdate();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return x;
	}
	
	public int howmany ()
	{
		int x = 0;
		ResultSet rs = null;
		try
		{
			Statement stat = con.createStatement();
			rs = stat.executeQuery("SELECT * FROM Pliki");
			while(rs.next())
			{
				if(rs.getInt("idPliku") >= x)
					x = rs.getInt("idPliku")+1;
			}
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return x;
	}

	
	public Optional<Object> read(int obiektDoOdczytu) 
	{
		ResultSet rs;
		FileInputStream file = null;
		ObjectInputStream in = null;
		String filename = null;
		String pathname = null;
		ResultSet rs2;
		Optional <Object> opt = Optional.empty();
		try
		{
			Statement stat = con.createStatement();
			rs = stat.executeQuery("SELECT * FROM Pliki");
			while(rs.next())
			{
				if(rs.getInt("idPliku")==obiektDoOdczytu)
				{	
					filename = rs.getString("plik");
					int x = rs.getInt("idKatalogu");
					rs2 = stat.executeQuery("SELECT * FROM Katalogi");
					while(rs2.next())
					{
						if(rs2.getInt("idKatalogu")==x)
						{
							pathname = rs2.getString("katalog");
							break;
						}
					}
					rs.close();
					rs2.close();
					break;
				}			
			}
			if(filename == null)
				return opt;
			file = new FileInputStream(pathname + File.separator + filename);
			in = new ObjectInputStream(file);
			opt = Optional.ofNullable(in.readObject());
			in.close();
			file.close();
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return opt;
	}
}
// Prayge
// Niech pepe the frog dopomoze w przepuszczeniu tego zadania
