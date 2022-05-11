import java.util.ArrayList;

public class Graphics implements GraphicsInterface 
{
	CanvasInterface canva;
	ArrayList <Position> alreadythere = new ArrayList <Position>();
	public void setCanvas(CanvasInterface canvas) 
	{
		canva = canvas;
	}

	public void fillWithColor(Position startingPosition, Color color)
			throws GraphicsInterface.WrongStartingPosition, GraphicsInterface.NoCanvasException 
	{
		if(canva == null)
			throw new NoCanvasException();
		else
		{
			try 
			{
				canva.setColor(startingPosition, color);
			}
			catch (CanvasInterface.BorderColorException border)
			{
				try
				{
					canva.setColor(startingPosition, border.previousColor);
				}
				catch(Exception e)
				{
					
				}
				throw new WrongStartingPosition();
			}
			catch (CanvasInterface.CanvasBorderException cborder)
			{
				throw new WrongStartingPosition();
			}
			alreadythere.add(startingPosition);
			Position pos1 = new Position2D(startingPosition.getRow()+1 , startingPosition.getCol());
			Position pos2 = new Position2D(startingPosition.getRow()-1 , startingPosition.getCol());
			Position pos3 = new Position2D(startingPosition.getRow() , startingPosition.getCol()+1);
			Position pos4 = new Position2D(startingPosition.getRow() , startingPosition.getCol()-1);
			fillpixel(pos1,color);
			fillpixel(pos2,color);
			fillpixel(pos3,color);
			fillpixel(pos4,color);
		}
	}
	void fillpixel(Position pos , Color color)
	{
		for(int i = 0 ; i < alreadythere.size(); i++)
		{
			Position analysis = alreadythere.get(i);
			if(analysis.getRow() == pos.getRow() && analysis.getCol() == pos.getCol() )
				return;
		}
		alreadythere.add(pos);
		try 
		{
			canva.setColor(pos, color);
		}
		catch (CanvasInterface.BorderColorException border)
		{
			try
			{
				canva.setColor(pos, border.previousColor);
			}
			catch(Exception e)
			{
				
			}
			return;
		}
		catch (CanvasInterface.CanvasBorderException cborder)
		{
			return;
		}
		Position pos1 = new Position2D(pos.getRow()+1 , pos.getCol());
		Position pos2 = new Position2D(pos.getRow()-1 , pos.getCol());
		Position pos3 = new Position2D(pos.getRow() , pos.getCol()+1);
		Position pos4 = new Position2D(pos.getRow() , pos.getCol()-1);
		fillpixel(pos1,color);
		fillpixel(pos2,color);
		fillpixel(pos3,color);
		fillpixel(pos4,color);
	}
	public class Position2D implements Position
	{
		int row;
		int col;
		public int getRow()
		{
			return row;
		}
		public int getCol()
		{
			return col;
		}
		Position2D(int row , int col)
		{
			this.row = row;
			this.col = col;
		}
	}
}
// Idk how it works, but it works
// ( ͡° ͜ʖ ͡°)
