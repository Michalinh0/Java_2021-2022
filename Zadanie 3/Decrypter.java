import java.util.Map;
import java.util.HashMap;

public class Decrypter implements DecrypterInterface 
{
	String text;
	
	public void setInputText(String encryptedDocument) 
	{
		text = encryptedDocument;
	}

	public Map<Character, Character> getCode() 
	{
		Map<Character, Character> map = new HashMap<Character, Character>();
		String key [] = {"Wydział","Fizyki,","Astronomii","i","Informatyki","Stosowanej"};
		int count = 0;
		if(text==null)
			return map;
		for(String word : text.split("\n"))
		{
			for(String word2 : word.split("\t"))
			{
				for(String word3 : word2.split(" "))
				{
					if(word3.length()==0)
						continue;
					//System.out.println(count);
					//System.out.println("Word analyzed : "+word3+" , len = "+word3.length());
					if(word3.length()==key[count].length())
					{
						for(int i=0;i<word3.length();i++)
						{
							char klucz = key[count].charAt(i);
							char value = word3.charAt(i);
							//System.out.printf("Comparing %c and %c\n",klucz,value);
							//System.out.println("Current map : "+map);
							if(!map.containsKey(klucz)&&!map.containsValue(value))
							{
								//System.out.printf("New entry : key = %c , value = %c\n",klucz,value);
								map.put(klucz, value);
							}
							else if(!map.containsKey(klucz)&&map.containsValue(value))
							{
								int x = count;
								count = -1;
								map.clear();
								//System.out.println("Map reset due to one character having double meaning");
								if(x!=0 && word3.length()==7)
								{
									for(int j=0;j<7;j++)
									{
										klucz = key[0].charAt(j);
										value = word3.charAt(j);
										//System.out.printf("Comparing %c and %c\n",klucz,value);
										//System.out.println("Current map : "+map);
										if(!map.containsKey(klucz)&&!map.containsValue(value))
										{
											//System.out.printf("New entry : key = %c , value = %c\n",klucz,value);
											map.put(klucz, value);
										}
										else if(!map.containsKey(klucz)&&map.containsValue(value))
										{
											map.clear();
										}
									}
									count++;						
								}
								break;
							}
							else if(map.containsKey(klucz)&&map.get(klucz)!=value)
							{
								int x = count;
								count = -1;
								map.clear();
								//System.out.println("Map reset due to one character having double meaning");
								if(x!=0 && word3.length()==7)
								{
									for(int j=0;j<7;j++)
									{
										klucz = key[0].charAt(j);
										value = word3.charAt(j);
										//System.out.printf("Comparing %c and %c\n",klucz,value);
										//System.out.println("Current map : "+map);
										if(!map.containsKey(klucz)&&!map.containsValue(value))
										{
											//System.out.printf("New entry : key = %c , value = %c\n",klucz,value);
											map.put(klucz, value);
										}
										else if(!map.containsKey(klucz)&&map.containsValue(value))
										{
											map.clear();
										}
									}
									count++;						
								}
								break;
							}
			
						}
						count++;
					}
					else
					{
						count=0;
						boolean zero = false;
						map.clear();
						//System.out.println("Map reset due to length mismatch");
						if(word3.length()==7)
						{
							for(int j=0;j<7;j++)
							{
								char klucz = key[0].charAt(j);
								char value = word3.charAt(j);
								//System.out.printf("Comparing %c and %c\n",klucz,value);
								//System.out.println("Current map : "+map);
								if(!map.containsKey(klucz)&&!map.containsValue(value))
								{
									//System.out.printf("New entry : key = %c , value = %c\n",klucz,value);
									map.put(klucz, value);
								}
								else if(!map.containsKey(klucz)&&map.containsValue(value))
								{
									map.clear();
									zero = true;
									break;
								}
							}
							count++;
							if(zero)
								count = 0;
						}
						
					}
					if(count==6)
						break;
				}
				if(count==6)
					break;
			}
			if(count==6)
				break;
		}
		map.remove(',');
		//System.out.println(map);
		return map;
	}

	public Map<Character, Character> getDecode() 
	{
		Map<Character, Character> map = new HashMap<Character, Character>();
		map = getCode();
		Map<Character, Character> mapinv = new HashMap<Character, Character>();
		for(Character i : map.keySet())
		{
			mapinv.put(map.get(i), i);
		}
		return mapinv;
		//
	}
	public static void main(String args[])
	{
		Decrypter dec = new Decrypter();
		dec.setInputText(">#Q3nN& Rn3#Xn, fmżLyByYnn n JB:yLYNż#Xn lżymy7NBs, >#Q3nN&   Rn3#Xn,      fmżLyByYnn n JB:yLYNż#Xn lżymy7NBsV lżymy7NBsV iLNX[7");
		System.out.println(dec.getCode());
		
	}
}