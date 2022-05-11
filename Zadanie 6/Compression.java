import java.util.Map;
import java.util.Collections;
import java.util.HashMap;
import java.lang.Math;

public class Compression implements CompressionInterface
{
	String sequence = "";
	int size;
	int wordsize;
	Map <String , String > header;
	public void addWord(String word) 
	{
		if(wordsize == 0)
		{
			wordsize = word.length();
			sequence = sequence + " " + word;
		}
		else if(wordsize != word.length())
		{
		
		}
		else
			sequence = sequence + " " + word;
	}
	
	public void compress() 
	{
		Map <String,Integer> reps = new HashMap<String,Integer>();
		reps = count();
		//System.out.println(size);
		//System.out.println(reps);
		header = new HashMap <String,String>();
		Map <String,Integer> copy = new HashMap<String,Integer>();
		int smallest = size;
		for(int len = 1; len < wordsize ; len++)
		{
			for(String a : reps.keySet())
				copy.put(a, reps.get(a));
			//System.out.println(reps);
			//System.out.println(copy);
			int x = (int) Math.pow(2, (double)len-1);
			String [] tab = new String [x];
			//System.out.println(x);
			for(int i = 0 ; i < x ; i++)
			{
				int maxvalue = 0;
				if(copy.size()!=0)
					maxvalue = Collections.max(copy.values());
				if(maxvalue==1)
					break;
				for(String z : copy.keySet())
				{
					if(copy.get(z)==maxvalue)
					{
						if(copy.get(z)*(wordsize-len)<=wordsize + len)
							break;
						tab[i] = z;
						copy.remove(z);
						//System.out.println("Removing");
						break;
					}
				}
				//System.out.println(copy);
			}
			int convsize = 0;
			int y = 0;
			do // header size
			{
				convsize += wordsize;
				convsize += len;
				y++;
			}
			while(y<x);
			for(String word : sequence.split("\\s+"))
			{
				if(word.length()==0)
					continue;
				if(checkifthere(word,tab,x))
					convsize += len;
				else
					convsize += wordsize+1;
				//System.out.println("   "+convsize);
			}
			//System.out.println("	"+convsize);
			if (convsize < smallest)
			{
				//System.out.println("Entering with score of "+convsize);
				int p = 0;
				header = new HashMap <String,String>();
				while(p<x)
				{
					String input = "0";
					if(len > 1)
					{
						input += binaryformatted(p,len-1);
						//System.out.println(input);
					}
					header.put(tab[p],input);
					//System.out.println("New map entry : "+tab[p]+" "+ input);
					p++;
				}
				smallest = convsize;
				header.remove(null);
			}
		}
	}
	String binaryformatted(int p,int len)
	{
		return String.format("%" + len + "s", Integer.toBinaryString(p)).replaceAll(" ", "0"); 
	}
	
	boolean checkifthere(String word , String [] tab,int x)
	{
		int y = 0;
		boolean isthere = false;
		while(y<x)
		{
			if(word.equals(tab[y]))
			{
				isthere = true;
				break;
			}
			
			y++;
		}
		return isthere;
	}
	
	private Map<String,Integer> count() 
	{
		Map <String,Integer> reps = new HashMap<String,Integer>();
		for(String word : sequence.split("\\s+"))
		{
			if(word.length()==0)
				continue;
			if(!reps.containsKey(word))
				reps.put(word,1);
			else
				reps.put(word, reps.get(word)+1);	
			size += wordsize;
		}
		return reps;
	}
	
	public Map<String, String> getHeader() 
	{
		Map<String, String> mapinv = new HashMap<String, String>();
		for(String i : header.keySet())
		{
			mapinv.put(header.get(i), i);
		}
		return mapinv;
	}

	public String getWord() 
	{
		String word;
		word = sequence.substring(1, wordsize+1);
		sequence = sequence.substring(wordsize+1,sequence.length());
		if(header.containsKey(word))
			return header.get(word);
		else
		{
			String out = "1"+word;
			return out;
		}
	}
}
