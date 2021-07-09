package main;

/**
 * This is a Chess Game implemented in Java.
 * @author Simon Schupp
 * @version 0.0.1
 */
public class Main {
	GUI game;
	
	/**
	 * When the program gets executed the object "main" gets created,
	 * which calls the class game and gives itself as parameter.
	 * This is done, so that both class know each other.
	 */
	public static void main(String[] args) {
		Main main;
		main = new Main();
	}
	
	public Main() {
		game = new GUI(this);
	}
}

