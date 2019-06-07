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

class NPStack
{
	static Random rand = new Random(); 
	static ArrayList<Tower> towerList = new ArrayList();		
	static Tower parentOne;
	static Tower parentTwo;

	public static void main(String[] args)
	{
		generateTowers(args[0], Integer.parseInt(args[1]));
		for (int i = 0; i < 5; i++) {
			// Get our two tallest towers
			selection();
			// Cross them over
			crossover();
		}
	}

	static void generateTowers(String filename, int numTowers) {
		try
		{
			//add the boxes into the program from the file
			BufferedReader boxReader = new BufferedReader(new FileReader(filename));
			String boxLine = boxReader.readLine();
			ArrayList<Box> boxList = new ArrayList<Box>();
			ArrayList<Box> tower = new ArrayList<Box>();
			Box[] anotherArray = new Box[5];

			//This variable is helpful for skipping over boxes that already exist in the tower in some other orientation
			int boxCounter = 0;
			
			String[] tempBox = new String[3];
			int count = 0;
			//add the boxes into a box class and put them all in a list
			while(boxLine != null)
			{
				// Get the disparate box elements
				tempBox = boxLine.split(" ");
				//each of these three boxes count as the same box, but this is its different rotations
				// We're using count as the ID for each box
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
			for(int i = 0; i < numTowers; i++)
			{
				System.out.println("-Loop-");
				//shuffle the list becuase that is easier than randomly selecting values from it
				Collections.shuffle(boxList);
				//This is looping to select the boxes themselves 
				for(int k = 0; k < boxList.size(); k++)
				{
					System.out.println("--Loop--");
					//we need a boolean to check whether a box already appears in the list
					// boolean present = false;
					//get the box from the list
					Box temp = boxList.get(k);
					if(tower.size() == 0)
					{
						tower.add(temp);
					}
					else
					{
						boolean present = false;
						// Loop through each box in the tower and check if temp already exists somewhere
						for (Box box : tower) {
							// If it does
							if (temp.getID() == box.getID()) {
								// Flag that it has been found and should be passed over
								present = true;
							}
						}
						if (!present) {
							//loop through the current tower
							for(int p = 0; p < tower.size(); p++)
							{
								System.out.println("----Loop----");
								//check to see if the new box is bigger than the current box
								if(p > 0)
								{
									if(temp.getLength() > tower.get(p).getLength() && temp.getWidth() > tower.get(p).getWidth() && temp.getLength() < tower.get(p - 1).getLength() && temp.getWidth() < tower.get(p - 1).getLength())
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
								else
								{
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
				towerList.add(new Tower(tower));
				tower = new ArrayList<Box>();
				boxCounter = 0;
			}
			// Print out each tower
			for (Tower t : towerList) {
				t.printTower();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	static void selection() {

		// First we get the tallest/fittest
		int maxHeight = Integer.MIN_VALUE;
		int maxHeightIndex = 0;
		for (int i = 0; i < towerList.size(); i++) {
			if (maxHeight <= towerList.get(i).getHeight()) {
				maxHeight = towerList.get(i).getHeight();
				maxHeightIndex = i;
			}
		}
		parentOne = towerList.get(maxHeightIndex);
		// Next we get the second tallest/fittest
		maxHeight = 0;
		int maxHeight2 = 0;
		for (int i = 0; i < towerList.size(); i++) {
			if (towerList.get(i).getHeight() > towerList.get(maxHeight).getHeight() ) {
				maxHeight2 = maxHeight;
				maxHeight = i;
			} else if (towerList.get(i).getHeight() > towerList.get(maxHeight2).getHeight()) {
				maxHeight2 = i;
			}
		}
		parentTwo = towerList.get(maxHeight2);
	}

	static void crossover() {
		System.out.println("////////////////////////////TALLEST TOWER//////////////////////////////////////////////");
		parentOne.printTower();
		System.out.println("////////////////////////////2nd TALLEST TOWER//////////////////////////////////////////////");		
		parentTwo.printTower();
		// Choose a random level that is less than the shorter of the two's heights
		int lesserNumberOfBoxes = Math.min(parentOne.getSize(), parentTwo.getSize());
		int switchLevel = rand.nextInt((lesserNumberOfBoxes - 1)) + 1;
		// Get the boxes of the parent towers and store them in new variables (We're about to change them & don't want to change the orignal towers)
		ArrayList<Box> parent1Boxes = parentOne.getBoxes();
		ArrayList<Box> parent2Boxes = parentTwo.getBoxes();

		boolean switch1Valid = false;		
		boolean switch2Valid = false;
		// While there are still valid swaps to check
		while(switchLevel < lesserNumberOfBoxes) {
			System.out.println("CURRENT SWITCH LEVEL: " + switchLevel);
			// Get the box of the current level from parent 1.
			Box parent1Box = parent1Boxes.get(switchLevel);
			// Get the box of the level down from parent 2
			Box parent2Base = parent2Boxes.get(switchLevel - 1);
			// Check if the box of the current level fits on the level down from parent 2.
			if ( (parent1Box.getLength() < parent2Base.getLength()) && (parent1Box.getWidth() < parent2Base.getWidth()) ) {
				switch1Valid = true;
			}			
			// Then we check if parent1Box exists in parent2Base
			for (int i = 0; i < switchLevel; i++) {
				// If it does, we can't swap
				if (parent1Box.getID() == parent2Boxes.get(i).getID()) {
					switch1Valid = false;
				}
			}

			// Get the box of the current level from parent 2
			Box parent2Box = parent2Boxes.get(switchLevel);
			// Get the box of the level down from parent 1
			Box parent1Base = parent1Boxes.get(switchLevel - 1);
			// Check if the box of the current level fits on the level down from parent 1.
			if ( (parent2Box.getLength() < parent1Base.getLength()) && (parent2Box.getWidth() < parent1Base.getWidth()) ) {
				switch2Valid = true;
			}
						
			// Then we check if parent2Box exists in parent1Base
			for (int i = 0; i < switchLevel; i++) {
				// If it does, we can't swap
				if (parent2Box.getID() == parent1Boxes.get(i).getID()) {
					switch1Valid = false;
				}
			}

			// If a swap fits within the base of both parents
			if (switch1Valid && switch2Valid) {
				// Do the swap by creating two new towers and populating them with some of parent 1 and some of parent 2
				Tower child1;
				ArrayList<Box> forChild1 = new ArrayList();
				// Add all the boxes below the switch level from parent 1
				for (int i = 0; i < switchLevel; i++) {
					forChild1.add(parent1Boxes.get(i));
				}
				// Add all the boxes above the switch level from parent 2
				for (int i = switchLevel; i < parent2Boxes.size(); i++) {
					forChild1.add(parent2Boxes.get(i));
				}
				child1 = new Tower(forChild1);
				// Create a second child using the same 
				Tower child2;
				ArrayList<Box> forChild2 = new ArrayList();
				// Add all the boxes below the switch level from parent 1
				for (int i = 0; i < switchLevel; i++) {
					forChild2.add(parent2Boxes.get(i));
				}
				// Add all the boxes above the switch level from parent 2
				for (int i = switchLevel; i < parent1Boxes.size(); i++) {
					forChild2.add(parent1Boxes.get(i));
				}
				child2 = new Tower(forChild2);


				System.out.println("////////////////////////////CHILD 1//////////////////////////////////////////////");
				child1.printTower();
				System.out.println("////////////////////////////CHILD 2//////////////////////////////////////////////");
				child2.printTower();
				addOffspring(child1, child2);
				break;
			} else {
				switchLevel++;
			}
			// If we run out of levels to compare
			if (switchLevel == lesserNumberOfBoxes) {
				// Print a message to that effect
				System.out.println("Could not find a valid set of offspring this generation");
				break;
			}
		}
	}

	static void addOffspring(Tower child1, Tower child2) {
		// If child 1 is taller, add it
		if (child1.getHeight() > child2.getHeight()) {
			towerList.add(child1);
		}
		// If child 2 is taller, add it
		else if (child1.getHeight() < child2.getHeight()) {
			towerList.add(child2);
		}
		// If neither is taller
		else {
			// If child 1 has more boxes, add it
			if (child1.getSize() > child2.getSize()) {
				towerList.add(child1);
			}
			// If child 2 has more boxes, add it
			else if (child1.getSize() < child2.getSize()) {
				towerList.add(child2);
			}
			// If neither is taller and neither has more boxes, just add child 1
			else {
				towerList.add(child1);
			}
		}
		cullTheWeak();
	}

	static void cullTheWeak() {
		int smallestHeight = Integer.MAX_VALUE;
		int smallestIndex = 0;
		for (int i = 0; i < towerList.size(); i++) {
			if (towerList.get(i).getHeight() < smallestHeight) {
				smallestHeight = towerList.get(i).getHeight();
				smallestIndex = i;
			}
		}
		System.out.println("Removing tower at index " + smallestIndex);
		towerList.remove(smallestIndex);
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

class Tower
{
	ArrayList<Box> boxes;
	int _height;

	public Tower(ArrayList<Box> boxArray) {
		boxes = boxArray;
		calculateHeight();
	}

	private void calculateHeight() {
		_height = 0;
		for (Box b : boxes) {
			_height += b.getHeight();
		}
	}

	public int getHeight() {
		return _height;
	}

	public int getSize() {
		return boxes.size();
	}

	public ArrayList<Box> getBoxes() {
		return boxes;
	}

	public void printTower() {
		System.out.println("Width Length Height ID");
		for (Box b : boxes) {
			System.out.println(b.getWidth() + " " + b.getLength() + " " + b.getHeight() + " " + b.getID());
		}
		System.out.println("Tower height: " + _height);
	}
}