import java.util.List;
import java.util.ArrayList;

public class Loops implements GeneralLoops 
{
	List <Integer> upper = new ArrayList<Integer>();
	List <Integer> lower = new ArrayList<Integer>();
	int nest = 1;
	
	public void setLowerLimits(List<Integer> limits) 
	{
		lower = limits;
		if (limits.size()>nest)
		{
			nest = limits.size();
		}
	}
	public void setUpperLimits(List<Integer> limits) 
	{
		upper = limits;
		if (limits.size()>nest)
		{
			nest = limits.size();
		}
	}

	public List<List<Integer>> getResult() 
	{
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		List<Integer> hi = new ArrayList<Integer>();
		if(upper.size()!=nest)
		{
			for(int i=0;i<nest;i++)
				hi.add(0);
		}
		else
			hi = upper;
		List<Integer> lo = new ArrayList<Integer>();
		if(lower.size()!=nest)
		{
			for(int i=0;i<nest;i++)
				lo.add(0);
		}
		else
			lo = lower;
		List<Integer> temp = new ArrayList<Integer>();
		temp = lo;
		if(temp.size()==0)
		{
			temp.add(0);
			result.add(temp);
			return result;
		}
		result.add(temp);
		temp = new ArrayList<Integer>(temp);
		while (!temp.equals(hi))
		{
			temp = new ArrayList<Integer>(temp);
			change(temp,hi,lo,temp.size()-1);
			result.add(temp);
		}
		return result;
	}
	
	List<Integer> change(List<Integer> x , List<Integer> hi , List<Integer> lo ,int index)
	{
		if(x.get(index)!=hi.get(index))
		{
			x.set(index, x.get(index)+1);
			return x;
		}
		else if (index!=0)
		{
			x.set(index,lo.get(index));
			return change(x,hi,lo,index-1);
		}
		else
		{
			return null;
		}
	}
}
