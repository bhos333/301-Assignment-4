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

class REsearcher
{
	public static void main(String[] args)
	{
		try
		{
			//add the boxes into the program from the file
			BufferedReader boxReader = new BufferedReader(new FileReader(args[0]));
			boxLine = boxReader.readLine();
			ArrayList<Box> boxList = new ArrayList<Box>();
			ArrayList<Box> tower = new ArrayList<Box>();
			ArrayList<ArrayList> towerList = new ArrayList();
			int[] anotherArray = new int[5];
			
			Random rand = new Random(); 
			
			int[] tempBox = new int[3];
			count = 0;
			//add the boxes into a box class and put them all in a list
			while(boxLine != null)
			{
				tempBox = boxLine.split(" ");
				//each of these three boxes count as the same box, but this is its different rotations
				Box newBox = new Box(count, tempBox[0], tempBox[1], tempBox[2]);
				boxList.add(newBox);
				Box newBox = new Box(count, tempBox[1], tempBox[2], tempBox[0]);
				boxList.add(newBox);
				Box newBox = new Box(count, tempBox[2], tempBox[0], tempBox[1]);
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
					Box temp = boxList.get(k + boxCounter)
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
						//add the box to the tower 
						tower.add(temp);
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
	private int _id
	
	public Box(int id, int width, int length, int height)
	{
		_width = width;
		_length = length;
		_height = height;
		_id = id;
	}
	
	public int getWidth()
	{
		return _width;
	}
	public int getLength()
	{
		return _length;
	}
	public int getHeight();
	{
		return _height;
	}
	public int getID();
	{
		return _id;
	}
}