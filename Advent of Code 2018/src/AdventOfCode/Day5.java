package AdventOfCode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day5 {
	
	public static void main(String args[]) {
		try {
			// part1();
			part2();
		} catch (FileNotFoundException e) {
			System.out.println("Redefine which file you're looking for");
		}
	}
	
	public static void part1() throws FileNotFoundException {
		String polymer = polymerScan();
		polymer = polymerReaction(polymer);
		
		System.out.println(polymer.length());
	}
	
	public static void part2() throws FileNotFoundException {
		String polymer = polymerScan();
		int shortestPolymerLength = polymer.length();
		
		// Cycle through all characters to see which one's removal will 
		// lead to the shortest reacted polymer length
		for (char unitToRemove = 'A'; unitToRemove <= 'Z'; unitToRemove++) {
			System.out.println(unitToRemove);
			String testPolymer = polymer.replace("" + unitToRemove, "");
			testPolymer = testPolymer.replace(new String("" + unitToRemove).toLowerCase(), 
					"");
			int newLength = polymerReaction(testPolymer).length(); 
			if (newLength < shortestPolymerLength) {
				shortestPolymerLength = newLength;
			}
		}
		
		System.out.println(shortestPolymerLength);
	}
	
	public static String polymerScan() throws FileNotFoundException {
		File file = new File("src/AdventOfCode/Day5Polymer.txt");
		Scanner in = new Scanner(file);
		String polymer = in.next();
		in.close();
		return polymer;
	}
	
	public static String polymerReaction(String polymer) {
		for (; ;) {
			String prereactionPolymer = polymer;
			
			// For all reactions that occur, replace the old letters with 
			// empty spaces and "compress" the string at the very end 
			// to ensure that reactions happen in the correct stages, and 
			// that reactions in one stage don't spark premature chain 
			// reactions.
			for (int i = 0; i < polymer.length() - 1; i++) {
				String firstLetter = polymer.substring(i, i+1);
				String secondLetter = polymer.substring(i+1, i+2);
				if (firstLetter.equalsIgnoreCase(secondLetter)) {
					if (firstLetter.equals(secondLetter)) {
						// The letters are the same case, do nothing.
					} else {
						polymer = polymer.substring(0, i) + "  " + 
								polymer.substring(i+2, polymer.length());
					}
				}
			}
			
			polymer = polymer.replace(" ", "");
			
			if (polymer.equals(prereactionPolymer)) {
				// All reactions done
				break;
			}
		}
		
		return polymer;
	}
	
}
