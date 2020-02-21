/**
	* Runs through minimum times of the Bridge & Torch problem using a given algorithm.
	*
	* @author Isaac Di Francesco
	* @version 2018-05-22
	*
	*/

import java.io.BufferedWriter;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class BridgeTorchAlgorithm {

	public static Scanner keyboard = new Scanner(System.in);
	public static final int MIN_PEOPLE = 4;
	public static final int MAX_PEOPLE = 50;
	public static int totalTime = 0;


	public static void main(String[] args) {

		//Open file to write to
		File f = new File("Algorithm.txt");
		try {
			BufferedWriter out = new BufferedWriter(new PrintWriter(f));

			for(int i = MIN_PEOPLE; i <= MAX_PEOPLE; i++) {
				totalTime = 0;
				Person[] peopleArray = new Person[i];

				//Set all speeds and fill array
				for(int j = 1; j <= i; j++) {
					peopleArray[j - 1] = new Person(j, j, false);
				}

				while (!allCrossed(peopleArray)) {
					//1. Cross 2 fastest people
					crossFastest(peopleArray);
					//1.5 Check if everyone has crossed
					if(allCrossed(peopleArray)) {
						break;
					}
					//2. Return fastest person
					returnFastest(peopleArray);
					//3. Cross two slowest people
					crossSlowest(peopleArray);
					//3.5 Check if everyone has crossed
					if(allCrossed(peopleArray)) {
						break;
					}
					//4. Return fastest person again
					returnFastest(peopleArray);
					//5. Cross last 2 people
					crossFastest(peopleArray);
					//5.5 Check if there still aren't people who have crossed
					if(!allCrossed(peopleArray)) {
						returnFastest(peopleArray);
					}
				}
				System.out.println("All people crossed the bridge in: " + totalTime + " minutes.");
				System.out.println("--------------------------------------------------\n");

				out.write(i + "\t" + totalTime + "\n");
			}
			out.close();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
		* Crosses the two fastest people on the starting side of the bridge.
		* @param peopleArray
		*
		*/

	private static void crossFastest(Person[] peopleArray) {
		int first = -1;
		int second = -1;
		for(int i = 0; i < peopleArray.length; i++) {
			if(!peopleArray[i].getCrossed()) {
				if(first == -1) {
					first = i;
				}
				else {
					//Compare current person to first person
					if(peopleArray[i].getSpeed() < peopleArray[first].getSpeed()) {
						second = first;
						first = i;
					}
					if(second == -1 || peopleArray[i].getSpeed() < peopleArray[second].getSpeed()) {
						second = i;
					}
					//If current person is slower than the first and second person, do nothing
				}
			}
		}
		peopleArray[first].crossBridge();
		peopleArray[second].crossBridge();
		totalTime += peopleArray[second].getSpeed();

		System.out.println("Person " + peopleArray[first].getID() + " and Person " + peopleArray[second].getID() + " crossed the bridge => Total time: " + totalTime + " minutes.");
	}

	/**
		* Crosses the two slowest people on the starting side of the bridge.
		* @param peopleArray
		*
		*/

	private static void crossSlowest(Person[] peopleArray) {
		int first = -1;
		int second = -1;
		for(int i = 0; i < peopleArray.length; i++) {
			if(!peopleArray[i].getCrossed()) {
				if(first == -1) {
					first = i;
				}
				else {
					//Compare current person to first person
					if(peopleArray[i].getSpeed() > peopleArray[first].getSpeed()) {
						second = first;
						first = i;
					}
					else if(second == -1 || peopleArray[i].getSpeed() > peopleArray[second].getSpeed()) {
						second = i;
					}
					//If current person is faster than the first and second person, do nothing
				}
			}
		}
		peopleArray[first].crossBridge();
		peopleArray[second].crossBridge();
		totalTime += peopleArray[first].getSpeed();
		System.out.println("Person " + peopleArray[first].getID() + " and Person " + peopleArray[second].getID() + " crossed the bridge => Total Time: " + totalTime + " minutes.");

	}

	/**
		* Returns the fastest person from the ending side of the bridge.
		* @param peopleArray
		*
		*/

	private static void returnFastest(Person[] peopleArray) {
		int fastest = -1;
		for(int i = 0; i < peopleArray.length; i++) {
			if(peopleArray[i].getCrossed()) {
				if(fastest == -1) {
					fastest = i;
				}
				else {
					if(peopleArray[i].getSpeed() < peopleArray[fastest].getSpeed()) {
						fastest = i;
					}
					//If current person is slower than fastest person, do nothing
				}
			}
		}
		peopleArray[fastest].crossBridge();
		totalTime += peopleArray[fastest].getSpeed();

		System.out.println("Person " + peopleArray[fastest].getID() + " returned with the torch => Total time: " + totalTime + " minutes.");
	}

	/**
		* Checks whether or not all people have crossed the bridge.
		* @param peopleArray
		* @return allCrossed
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
