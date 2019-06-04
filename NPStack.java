// JESSE WILSHIER 1269801
// BEN HOS 1299084
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.Random;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

class REsearcher
{
	public static void main(String[] args)
	{
		try
		{
			//add the boxes into the program from the file
			BufferedReader boxReader = new BufferedReader(new FileReader(args[0]));
			String boxLine = boxReader.readLine();
			ArrayList<Box> boxList = new ArrayList<Box>();
			ArrayList<Box> tower = new ArrayList<Box>();
			ArrayList<ArrayList> towerList = new ArrayList();
			Box[] anotherArray = new Box[5];
			
			Random rand = new Random(); 
			
			String[] tempBox = new String[3];
			int count = 0;
			//add the boxes into a box class and put them all in a list
			while(boxLine != null)
			{
				tempBox = boxLine.split(" ");
				//each of these three boxes count as the same box, but this is its different rotations
				Box newBox = new Box(count, Integer.parseInt(tempBox[0]), Integer.parseInt(tempBox[1]), Integer.parseInt(tempBox[2]));
				boxList.add(newBox);
				newBox = new Box(count, Integer.parseInt(tempBox[1]), Integer.parseInt(tempBox[2]), Integer.parseInt(tempBox[0]));
				boxList.add(newBox);
				newBox = new Box(count, Integer.parseInt(tempBox[2]), Integer.parseInt(tempBox[0]), Integer.parseInt(tempBox[1]));
				boxList.add(newBox);
				boxLine = boxReader.readLine();
				count++;
			}
			
			//build initial towers
			//number of towers equal to the number entered in the command line
			for(int i = 0; i < Integer.parseInt(args[1]); i++)
			{
				//shuffle the list becuase that is easier than randomly selecting values from it
				Collections.shuffle(boxList);
				//This is looping to select the boxes themselves 
				for(int k = 0; k < anotherArray.length; k++)
				{
					//we need a boolean to check whether a box already appears in the list
					boolean present = false;
					//This variable is added to k so that if we encounter a box we already have we can increment this and get the next one along, without running into the upper bound of the for loop
					int boxCounter = 0;
					//get the box from the list
					Box temp = boxList.get(k + boxCounter);
					//loop through
					for(int m = 0; m < anotherArray.length; k++)
					{
						//check if the boxID of the temp box is the same as any in the tower so far
						if(temp.getID() == anotherArray[m].getID())
						{
							//decrement k so that we actual get 5 values
							k--;
							//increment boxCounter so that we can get the next box along in the array list
							boxCounter++;
							//set present to tru so that we know not to add it
							present = true;
							//return to the previous for loop
							break;
						}
					}
					//check whether it was there or not
					//if it was we do nothing, it should loop again
					//if it wasn't then we can add it to the tower
					if(present == false)
					{
						//It will probably be easiest to sort the boxes here
						if(tower.size() == 0)
						{
							tower.add(temp);
						}
						else
						{
							//loop through the current tower
							for(int p = 0; p < tower.size(); p++)
							{
								//check to see if the new box is bigger than the current box
								if(temp.getLength() > tower.get(p).getLength() && temp.getWidth() > tower.get(p).getWidth())
								{
									//it is
									//so we put the new box in here and hold on to the other box and move it upwards
									Box temporaryBox;
									temporaryBox = tower.get(p);
									tower.set(p, temp);
									temp = temporaryBox;
								}
								//it isn't
								//we'll potentially deal with that later
								else
								{
									
								}
							}
							//temp at the end could be very small so it wouldn't have been added ealier. This makes sure that it gets added
							if(temp.getLength() < tower.get(tower.size()-1).getLength() && temp.getWidth() < tower.get(tower.size()-1).getWidth())
							{
								tower.add(temp);
							}
							//if it gets to here it means that the box was more oblong than any other and it will not fit no matter where it is on the tower(in theory)
							else
							{
								
							}
						}
					}
				}				
				//add the tower to the list of towers
				towerList.add(tower);
				//clear the tower for the next build
				tower.clear();
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}

class Box
{
	private int _width;
	private int _length;
	private int _height;
	private int _id;
	private int _area;
	
	public Box(int id, int width, int length, int height)
	{
		_width = width;
		_length = length;
		_height = height;
		_id = id;
		_area = _width*_length;
	}
	
	public int getWidth()
	{
		return _width;
	}
	public int getLength()
	{
		return _length;
	}
	public int getHeight()
	{
		return _height;
	}
	public int getID()
	{
		return _id;
	}
}