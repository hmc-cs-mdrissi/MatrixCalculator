import java.io.*;
import java.util.*;

public class MatrixArray
{
	private HashMap<String,Matrix> pool= new HashMap<String,Matrix>();
	
	public void addMatrix(String name, Matrix m)
	{
		m.setName(name);
		pool.put(name, m);
	}
	
	public Matrix removeMatrix(String name)
	{
		return pool.remove(name);
	}
	
	public String toString()
	{
		String s= "";
		for(Matrix m: pool.values())
		{
			s+= m + "\n";
		}
		return s;
	}

	public void writeToFile(File f) throws IOException
	{
		FileOutputStream fos = new FileOutputStream(f);
		ObjectOutputStream output= new ObjectOutputStream(fos);
		
		for(Matrix m: pool.values())
		{
			output.writeObject(m);
		}
		
		output.close();
		fos.close();
	}

	public void fillFromFile(File f) throws IOException
	{
		FileInputStream fos= new FileInputStream(f);
		ObjectInputStream input= new ObjectInputStream(fos);
		try
		{
			while(true)
			{
				Matrix m= (Matrix)input.readObject();
				pool.put(m.getName(), m);
			}
		}
		catch(Exception e){}
		input.close();
		fos.close();
	}
	
	public void clear()
	{
		pool.clear();
	}
	
	public Matrix get(String name)
	{
		return pool.get(name);
	}

	public String printtKeys()
	{
		String temp= "";
		for(Matrix m: pool.values())
		{
			temp+= m.getName() + ", ";
		}	
		return temp;
	}
	
}
