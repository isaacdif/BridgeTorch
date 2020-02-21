
public class Person {

	private int speed;
	private int ID;
	private boolean crossed = false;

	public Person(int speed, int ID, boolean crossed) {
		this.speed = speed;
		this.ID = ID;
		this.crossed = crossed;
	}

	public int getID() {
		return this.ID;
	}

	public int getSpeed() {
		return this.speed;
	}

	public boolean getCrossed() {
		return this.crossed;
	}

	public void crossBridge() {
		this.crossed = !this.crossed;	//Toggle which side of the bridge the person is on
	}
}
