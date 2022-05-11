
class Decode extends DecoderInterface 
{
	public String s1 = "";
	int count = 0;
	int code = 0;
	int prev;
	public void input(int bit) 
	{
		if(bit==48 || bit==0)
		{
			if(prev==1)
			{
				if(code==0)
				{
					s1+="0";
					code = count;
				}
				else
				{
					int x = count/code;
					s1+=(x-1);
				}
			}
			count = 0;
			prev = 0;
		}
		else
		{
			count ++;
			prev = 1;
		}
		System.out.println("Input = "+bit+" , Count = "+count+" , Code = "+code+" , prev = "+prev);
	}
	
	public String output() 
	{
		return s1;
	}

	public void reset() 
	{
		code = 0;
		prev = 0;
		s1 = "";
	}
	/*public static void main(String[] args)
	{
		Decode k1 = new Decode();
		String p1 = ("000010000110010111010");
		for(int i=0;i<p1.length();i++)
		{
			k1.input(p1.charAt(i));
			//System.out.println(p1.charAt(i)+" inputted");
		}
		System.out.println(k1.output());
		k1.input(0);
		k1.input(0);
		k1.input(0);
		k1.input(0);
		k1.input(1);
		k1.input(0);
		k1.input(0);
		k1.input(0);
		k1.input(0);
		k1.input(0);
		k1.input(1);
		k1.input(1);
		k1.input(0);
		k1.input(0);
		k1.input(1);
		k1.input(0);
		k1.input(1);
		k1.input(1);
		k1.input(1);
		k1.input(0);
		k1.input(1);
		k1.input(0);
		System.out.println(k1.output());
	}*/
}
