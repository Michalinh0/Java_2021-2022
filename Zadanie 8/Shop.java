import java.util.Map;
import java.util.HashMap;

public class Shop implements ShopInterface 
{
	Map <String, Integer> stock = new HashMap <String, Integer>();
	Map <String , Object > blocks = new HashMap <String , Object>();
	@Override
	public void delivery(Map<String, Integer> goods) 
	{
		synchronized(stock)
		{
			for(String i : goods.keySet())
			{
				if(stock.containsKey(i))
				{
					stock.put(i, stock.get(i) + goods.get(i));
					//stock().notify();
					synchronized(blocks(i))
					{
						blocks(i).notifyAll();
					}
				}
				else
				{
					stock.put(i,goods.get(i));
					//stock().notify();
					synchronized(blocks(i))
					{
						blocks(i).notifyAll();
					}
				}
			}
		}
	}

	@Override
	public boolean purchase(String productName, int quantity) 
	{
		synchronized (blocks(productName))
		{
			if(!stock.containsKey(productName))
			{
				try
				{
					blocks(productName).wait();
				}
				catch(Exception e)
				{
					
				}
				if(stock.get(productName) >= quantity)
				{
					stock.put(productName, stock.get(productName) - quantity);
					return true;
				}
				else
					return false;
			}
			else if(stock.get(productName) >= quantity)
			{
				stock.put(productName, stock.get(productName) - quantity);
				return true;
			}
			else
			{
				try
				{
					blocks(productName).wait();
				}
				catch(Exception e)
				{
					
				}
				if(stock.get(productName) >= quantity)
				{
					stock.put(productName, stock.get(productName) - quantity);
					return true;
				}
				else
					return false;
			}
		}
	}

	@Override
	public Map<String, Integer> stock() 
	{
		return stock;
	}
	public Object blocks(String product)
	{
		if(blocks.containsKey(product))
		{
			return blocks.get(product);
		}
		else
		{
			blocks.put(product, new Object());
			return blocks.get(product);
		}
	}
}
// It should work :(
