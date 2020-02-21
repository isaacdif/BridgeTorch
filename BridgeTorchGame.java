/**
	* Allows user to play the Bridge & Torch puzzle game.
	*
	* @author Isaac Di Francesco
	* @version 2018-05-22
	*
	*/

import java.util.Scanner;

public class BridgeTorchGame {

	public static Scanner keyboard = new Scanner(System.in);

	public static final int NUM_PEOPLE = chooseNum();
	public static Person[] peopleArray = new Person[NUM_PEOPLE];
	public static int totalTime = 0;

	public static void main(String[] args) {

		int userInput;

		//Determine walk speeds
		for(int i = 0; i < NUM_PEOPLE; i++) {
			System.out.print("Enter speed for person " + (i + 1) + ": ");
			userInput = keyboard.nextInt();
			//Add person to people peopleArray
			peopleArray[i] = new Person(userInput, (i + 1), false);
		}

		//Run algorithm
		while (!allCrossed()) {
			//1. Cross 2 fastest people
			totalTime = crossFastest();
			//1.5 Check if everyone has crossed
			if(allCrossed()) {
				break;
			}
			//2. Return fastest person
			totalTime = returnFastest();
			//3. Cross two slowest people
			totalTime = crossSlowest();
			//3.5 Check if everyone has crossed
			if(allCrossed()) {
				break;
			}
			//4. Return fastest person again
			totalTime = returnFastest();
			//5. Cross last 2 people
			totalTime = crossFastest();
			//5.5 Check if there still aren't people who have crossed
			if(!allCrossed()) {
				totalTime = returnFastest();
			}
		}
		playGame(peopleArray);
	}

	/**
		* Runs through the steps in the Bridge & Torch game.
		* @param peopleArray
		*
		*/

	public static void playGame(Person[] peopleArray) {
		System.out.println("\nThe algorithm crossed all people in " + totalTime + " minutes. Can you beat the algorithm?");
		int newTotalTime = 0;
		for (int i = 0; i < peopleArray.length; i++) {
			peopleArray[i].crossBridge();
		}
		//Run the game
		while (!allCrossed()) {
			//Cross two people
			newTotalTime = crossGame(newTotalTime);
			//Check if everyone has crossed
			if(allCrossed()) {
				break;
			}
			//Choose person to return
			newTotalTime = returnGame(newTotalTime);
		}

		if(newTotalTime < totalTime) {
			System.out.println("\nCongratulations! You managed to cross everyone faster than the algorithm!");
		}
		else if(newTotalTime == totalTime) {
			System.out.println("\nYou tied the algorithm's time!");
		}
		else {
			System.out.println("\nSorry! You weren't able to beat the algorithm's time. :(");
		}
	}

	/**
		* Determines which two people cross the bridge in the game.
		* @param newTotalTime
		* @return newTotalTime
		*
		*/

	public static int crossGame (int newTotalTime) {
		int first;
		int second;
		boolean correctInput = false;
		do {
			System.out.println("\nChoose two people to cross the bridge:");
			for (int i = 0; i < peopleArray.length; i++){
				if (!peopleArray[i].getCrossed()) {
					System.out.println("Person " + (i + 1));
				}
			}
			System.out.print("First selection: ");
			first = keyboard.nextInt();
			System.out.print("Second selection: ");
			second = keyboard.nextInt();

			if(first < 1 || first > peopleArray.length || second < 1 || second > peopleArray.length) {
				System.out.println("\nOne or both of the entered values are not valid! Please enter a valid selection.");
			}
			else if(peopleArray[first - 1].getCrossed() || peopleArray[second - 1].getCrossed()) {
				System.out.println("\nOne or both people are on the other side of the bridge! Please enter a valid selection.");
			}
			else {
				correctInput = true;
			}
		} while (!correctInput);

		if (peopleArray[first - 1].getSpeed() > peopleArray[second - 1].getSpeed()) {
			newTotalTime += peopleArray[first - 1].getSpeed();
		}
		else {
			newTotalTime += peopleArray[second - 1].getSpeed();
		}
		peopleArray[first - 1].crossBridge();
		peopleArray[second - 1].crossBridge();
		System.out.println("Person " + peopleArray[first - 1].getID() + " and Person " + peopleArray[second - 1].getID() + " cross the bridge => Total time: " + newTotalTime + " minutes.");
		return newTotalTime;
	}

	/**
		* Determines who returns back from the other side of the bridge in game.
		* @param newTotalTime
		* @return newTotalTime
		*
		*/

	public static int returnGame(int newTotalTime) {
		int input;
		boolean correctInput = false;
		do {
			System.out.println("\nChoose one person to return to starting side: ");
			for (int i = 0; i < peopleArray.length; i++) {
				if (peopleArray[i].getCrossed()) {
					System.out.println("Person " + (i + 1));
				}
			}
			System.out.print("Selection: ");
			input = keyboard.nextInt();

			if(input < 1 || input > peopleArray.length) {
				System.out.println("\nThe entered value  is not valid! Please enter a valid selection.");
			}
			else if(!peopleArray[input - 1].getCrossed()) {
				System.out.println("\nThe selected person is on the other side of the bridge! Please enter a valid selection.");
			}
			else {
				correctInput = true;
			}
		} while (!correctInput);

		newTotalTime += peopleArray[input - 1].getSpeed();
		peopleArray[input - 1].crossBridge();
		System.out.println("Person " + peopleArray[input - 1].getID() + " returns => Total time: " + newTotalTime + " minutes.");
		return newTotalTime;
	}

	/**
		* Determines the number of people that must cross the bridge.
		* @return num
		*
		*/

	public static int chooseNum() {
		System.out.print("Choose a number of people that must cross the bridge (at least 4): ");
		int num = keyboard.nextInt();
		while (num < 4) {
			System.out.println("That's less than 4. Choose again: ");
			num = keyboard.nextInt();
		}
		return num;
	}

	/**
		* Crosses the two fastest people on the starting side of the bridge.
		* @return totalTime
		*
		*/

	private static int crossFastest() {
		int first = -1;
		int second = -1;
		for(int i = 0; i < peopleArray.length; i++) {
			if(!peopleArray[i].getCrossed()) {	//Person has not crossed yet
				if(first == -1) {
					first = i;
				}
				else {
					//Compare current person to first person
					if(peopleArray[i].getSpeed() < peopleArray[first].getSpeed()) {
						second = first;
						first = i;
					}
					else if(second == -1 || peopleArray[i].getSpeed() < peopleArray[second].getSpeed()) {
						second = i;
					}
					//If current person is slower than the first and second person, do nothing
				}
			}
		}
		peopleArray[first].crossBridge();
		peopleArray[second].crossBridge();
		totalTime += peopleArray[second].getSpeed();
		return totalTime;
	} // End crossFastest

	/**
		* Crosses the two slowest people on the starting side of the bridge.
		* @return totalTime
		*
		*/

	private static int crossSlowest() {
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
		return totalTime;
	} // End crossSlowest

	/**
		* Returns the fastest person from the ending side of the bridge.
		* @return totalTime
		*
		*/

	private static int returnFastest() {
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
		return totalTime;
	} // End returnFastest

	/**
		* Checks whether or not all people have crossed the bridge.
		* @return allCrossed
		*
		*/

	private static boolean allCrossed() {
		boolean allCrossed = true;
		for(int i = 0; i < peopleArray.length; i++) {
			if(!peopleArray[i].getCrossed()) {
				allCrossed = false;
				break;
			}
		}
		return allCrossed;

	} // End allCrossed
} // End Class
