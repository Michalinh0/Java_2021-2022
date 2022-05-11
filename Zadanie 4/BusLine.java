import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.lang.Math;

public class BusLine implements BusLineInterface
{
	ArrayList <Busroute> list = new ArrayList <Busroute>();
	Map<String, List<Position>> inters = new HashMap<String, List<Position>>();
	ArrayList<ArrayList<Position>> Tracks = new ArrayList<ArrayList<Position>>();
	Map<String,List<String>> whichones = new HashMap<String,List<String>>();
	Map<BusLineInterface.LinesPair, Set<Position>> paired = new HashMap<BusLineInterface.LinesPair, Set<Position>>();
	Map<String,List<Position>> pts = new HashMap<String,List<Position>>();
	int howmanytracks = 0;
	List<Position> temp = new ArrayList<Position>();
	List<String> tempwo = new ArrayList<String>();
	Set<Position> temp1 = new HashSet<Position>();
	Set<Position> temp2 = new HashSet<Position>();
	public void addBusLine(String busLineName, Position firstPoint, Position lastPoint) 
	{
		Busroute bus = new Busroute(busLineName, firstPoint, lastPoint);
		list.add(bus);
	}

	public void addLineSegment(String busLineName, LineSegment lineSegment) 
	{
		for(int i = 0 ; i < list.size(); i++)
		{
			if(list.get(i).name.equals(busLineName))
			{
				list.get(i).streets.add(new LineSegment (lineSegment.getFirstPosition(),lineSegment.getLastPosition()));
				break;
			}
		}
	}

	public void findIntersections() 
	{
		ArrayList<ArrayList<Position>> tracks = new ArrayList<ArrayList<Position>>();
		for(int i = 0 ; i < list.size(); i++)
		{
			Busroute x = list.get(i);
			ArrayList<Position> track = new ArrayList<Position>();
			Position current = x.beginning;
			for(int j = 0 ; j < x.streets.size() ; j++)
			{
				if(current!=x.ending)
				{	
					Position end = FindTheEnd(x,current);
					track = new ArrayList<Position>(AddPositions(x,current,end,new ArrayList<Position>(track)));
					current = end;
				}		
			}
			tracks.add(track);
			howmanytracks++;
		}
		Tracks = new ArrayList<ArrayList<Position>>(tracks);
		for(int i = 0 ; i < list.size() ; i++)
		{
			temp.clear();
			tempwo.clear();
			ArrayList <Position> a = tracks.get(i);
			for(int j = 1 ; j < a.size()-1 ; j++)
			{
				Position current = a.get(j);
				LookForIntersections(i,j,current,tracks);
			}
			if(!temp.isEmpty())
			{
				inters.put(list.get(i).name, new ArrayList<Position>(temp));
				whichones.put(list.get(i).name, new ArrayList<String>(tempwo));
			}
		}
	}

	public Map<String, List<Position>> getLines()
	{
		for(int i = 0 ; i <list.size(); i++)
		{
			if(inters.containsKey(list.get(i).name))
				pts.put(list.get(i).name,Tracks.get(i));
		}
		return pts;
	}

	public Map<String, List<Position>> getIntersectionPositions() 
	{
		return inters;
	}

	public Map<String, List<String>> getIntersectionsWithLines() 
	{
		return whichones;
	}

	public Map<BusLineInterface.LinesPair, Set<Position>> getIntersectionOfLinesPair() 
	{
		for(int i = 0 ; i < list.size() ; i++)
		{
			Set <Position> set = new HashSet<Position>();
			String k = list.get(i).name;
			List <String> nazwa = whichones.get(k);
			if(nazwa==null)
			{
				nazwa = new ArrayList<String>();
			}
			for(int j = 0 ; j < list.size(); j++)
			{
				set.clear();
				for(int l = 0 ; l < nazwa.size(); l++)
				{
					if(nazwa.get(l).equals(list.get(j).name))
					{
						set.add(inters.get(k).get(l));
					}
				}
				paired.put(new Pair(k,list.get(j).name),new HashSet<Position>(set));
			}
		}
		return paired;
	}
	public class Busroute
	{
		String name;
		Position beginning;
		Position ending;
		ArrayList<LineSegment> streets;
		Busroute(String Name, Position beg , Position end)
		{
			name = Name;
			beginning = beg;
			ending = end;
			streets = new ArrayList<LineSegment>();
		}
	}
	public Position FindTheEnd(Busroute x, Position beginning)
	{
		Position finalpos = null;
		for(int i = 0 ; i < x.streets.size(); i++)
		{
			LineSegment a = x.streets.get(i);
			if(a.getFirstPosition().equals(beginning))
			{
				finalpos = a.getLastPosition();
				break;
			}
		}
		return finalpos;
	}
	public ArrayList<Position> AddPositions(Busroute x ,Position beg, Position end,ArrayList<Position> track)
	{
		int xaxis = 0;
		int yaxis = 0;
		if(end.getCol() - beg.getCol() > 0)
			yaxis = 1;
		else if(end.getCol() - beg.getCol() < 0)
			yaxis = -1;
		if(end.getRow() - beg.getRow() > 0)
			xaxis = 1;
		else if(end.getRow() - beg.getRow() < 0)
			xaxis = -1;
		int diff = 0;
		if(Math.abs(end.getRow() - beg.getRow()) > diff)
			diff = Math.abs(end.getRow() - beg.getRow());
		else if(Math.abs(end.getCol() - beg.getCol()) > diff)
			diff = Math.abs(end.getCol() - beg.getCol());
		int startx = beg.getRow();
		int starty = beg.getCol();
		for(int i = 0 ; i < diff ; i++)
		{
			int xcord = startx + i*xaxis;
			int ycord = starty + i*yaxis;
			Position add = new Position2D (ycord,xcord);
			track.add(add);
		}
		if(end.equals(x.ending))
			track.add(end);
		return track;
	}
	public void LookForIntersections(int route , int where, Position current,ArrayList<ArrayList<Position>> tracks)
	{
		if(temp.contains(current))
		{	
			temp.add(current);
			tempwo.add(list.get(route).name);
		}
		for(int i = 0 ; i < howmanytracks; i++)
		{
			int x = 0;
			if(i==route)
				x = where+1;
			else
				x = 1;
			ArrayList <Position> curr = tracks.get(i);
			Busroute now = list.get(i);
			temp1.clear();
			temp2.clear();
			List <String> whichlines = new ArrayList <String>();
			Set <Position> positions = new HashSet <Position>();
			Set <Position> positions2 = new HashSet <Position>();
			while(x < curr.size()-1)
			{
				Position pos = curr.get(x);
				if (pos.equals(current))
				{
					if(perpendicular(route,where,i,x,tracks))
					{
						temp.add(pos);
						tempwo.add(list.get(i).name);
						temp1.add(pos);
						temp2.add(pos);
						whichlines.add(now.name);
						positions.add(pos);
						positions2.add(pos);
					}
				}
				x++;
			}
		}
	}
	public boolean perpendicular(int route1, int where1, int route2, int where2,ArrayList<ArrayList<Position>> tracks)
	{
		ArrayList <Position> r1 = tracks.get(route1);
		ArrayList <Position> r2 = tracks.get(route2);
		Position w1 = r1.get(where1);
		Position w2 = r2.get(where2);
		Position w1prev = r1.get(where1-1);
		Position w1next = r1.get(where1+1);
		Position w2prev = r2.get(where2-1);
		Position w2next = r2.get(where2+1);
		int dir1p = direction(w1prev , w1);
		int dir1n = direction(w1 , w1next);
		int dir2p = direction(w2prev , w2);
		int dir2n = direction(w2 , w2next);
		if(dir1p != dir1n || dir2p != dir2n)
			return false;
		else
		{
			if(Math.abs(dir1p - dir2p) % 2 == 0)
				return true;
			else 
				return false;
		}
	}
	public int direction(Position point1 , Position point2) // Point 1 = startowy , point 2 = docelowy
	{
		// 1. N
		// 2. NE
		// 3. E
		// 4. SE
		// 5. S
		// 6. SW
		// 7. W
		// 8. NW
		int dir;
		int xdiff = point2.getRow() - point1.getRow();
		int ydiff = point2.getCol() - point1.getCol();
		if (ydiff == 0)
		{
			if(xdiff == 1)
				dir = 5;
			else
				dir = 1;
		}
		else if(ydiff == 1)
		{
			if(xdiff == 1)
				dir = 4;
			else if(xdiff == 0)
				dir = 3;
			else
				dir = 2;
		}
		else
		{
			if(xdiff == 1)
				dir = 6;
			else if(xdiff == 0)
				dir = 7;
			else
				dir = 8;
		}
		return dir;
	}
	public class Pair implements LinesPair
	{
		String name1;
		String name2;
		public String getFirstLineName() 
		{
			return name1;
		}
		public String getSecondLineName() 
		{
			
			return name2;
		}
		Pair(String n1 , String n2)
		{
			name1 = n1;
			name2 = n2;
		}
		public String toString() 
		{
			return "LineSegment [" + name1 + ", " + name2 + "]";
		}
	}
}
//Hope it works :)