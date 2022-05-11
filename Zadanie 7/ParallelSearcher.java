import java.util.ArrayList;

public class ParallelSearcher implements ParallelSearcherInterface
{

	ArrayList <Searcher> threads = new ArrayList<Searcher>();
	double total = 0;
	HidingPlaceSupplier current;
	public void set(HidingPlaceSupplierSupplier supplier) 
	{
		do
		{
			seek();
			current = supplier.get(total);
		}
		while(current!= null);
	}
	
	void seek()
	{
		//System.out.println("Did it came here?");
		if(current != null)
		{	
			threads.clear();
			total = 0;
			Search pow = new Search();
			for(int i = 0 ; i < current.threads();i++)
			{
				Searcher th = new Searcher(pow);
				threads.add(th);
				threads.get(i).start();
				System.out.println("Running thread nr " + i);
			}
			for(int i = 0 ; i < threads.size(); i++)
			{
				try
				{
					threads.get(i).join();
				}
				catch (Exception e)
				{
					
				}
			}
			System.out.println(total);
		}	
	}
	
	class Searcher extends Thread
	{
		HidingPlaceSupplier.HidingPlace analysis;
		Search search;
		Searcher(Search pow)
		{
			search = pow;
		}
		public void run()
		{
			System.out.println("Running");
			do
			{
				boolean isthere = false;
				synchronized(search)
				{
					analysis = current.get();
				}
				if(analysis!=null)
					isthere = search.check(analysis);
				synchronized(search)
				{
					if(isthere)
						search.add(analysis);
				}
			}
			while(analysis != null);
		}
	}
	class Search
	{
		public boolean check(HidingPlaceSupplier.HidingPlace analysis)
		{
			return analysis.isPresent();
				
		}
		public void add(HidingPlaceSupplier.HidingPlace analysis)
		{
			System.out.println("Total przed : "+total);
			total+=analysis.openAndGetValue();
			System.out.println("Total po : "+total);
		}
	}
}