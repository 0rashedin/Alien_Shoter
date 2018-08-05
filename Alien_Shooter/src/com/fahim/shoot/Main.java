package com.fahim.shoot;

import java.awt.HeadlessException;
import java.io.IOException;


/**
 *
 * @author fahim
 */

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			new Game().startGame();
		} catch (HeadlessException e) {
			  System.out.println(e.getMessage());
		} catch (IOException e) {
			  System.out.println(e.getMessage());
		} catch (IllegalArgumentException e) {
			  System.out.println(e.getMessage());
		}
	}
}
