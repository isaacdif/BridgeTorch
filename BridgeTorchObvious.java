/**
	* Runs through minimum times of the Bridge & Torch problem using the "obvious" method.
	*
	* @author Isaac Di Francesco
	* @version 2018-05-22
	*
	*/

import java.io.BufferedWriter;
import java.io.File;
import java.io.PrintWriter;

public class BridgeTorchObvious {

	public static final int MIN_PEOPLE = 4;
	public static final int MAX_PEOPLE = 50;
	public static int totalTime = 0;

	public static void main(String[] args) {

		try {
			//Open file to write to
			File f = new File("Obvious.txt");
			BufferedWriter out = new BufferedWriter(new PrintWriter(f));

			for(int i = MIN_PEOPLE; i <= MAX_PEOPLE; i++) {
				totalTime = 0;
				Person[] peopleArray = new Person[i];

				for(int j = 1; j <= peopleArray.length; j++) {
					peopleArray[j - 1] = new Person(j, j, false);
				}

				//Run algorithm
				int nextPerson = 1;	//Start at 1 since person at position 0 is always crossing
				while(!allCrossed(peopleArray)) {
					//Cross fastest and next person
					crossTwo(peopleArray, nextPerson);
					if(!allCrossed(peopleArray)) {
						returnFirst(peopleArray);
					}
					nextPerson++;
				}
				out.write(i + "\t" + totalTime + "\n");
			}
			out.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
		* Crosses the fastest person & another person on the starting side of the bridge.
		* @param peopleArray, otherPerson
		*
		*/

	private static void crossTwo(Person[] peopleArray, int otherPerson) {
		peopleArray[0].crossBridge();
		peopleArray[otherPerson].crossBridge();

		totalTime += peopleArray[otherPerson].getSpeed();
		System.out.println("Person " + peopleArray[0].getID() + " and Person " + peopleArray[otherPerson].getID() + " crossed the bridge => Total time: " + totalTime + " minutes.");
	}

	/**
		* Returns the fastest person on the ending side of the bridge.
		* @param peopleArray
		*
		*/

	private static void returnFirst(Person[] peopleArray) {
		peopleArray[0].crossBridge();

		totalTime += peopleArray[0].getSpeed();

		System.out.println("Person " + peopleArray[0].getID() + " returned with the torch => Total time: " + totalTime + " minutes.");
	}

	/**
		* Checks whether or not all people have crossed the bridge.
		* @param peopleArray
		*
		*/

	private static boolean allCrossed(Person[] peopleArray) {
		boolean allCrossed = true;
		for(int i = 0; i < peopleArray.length; i++) {
			if(!peopleArray[i].getCrossed()) {
				allCrossed = false;
				break;
			}
		}
		return allCrossed;
	}
}
