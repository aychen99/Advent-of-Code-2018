package AdventOfCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Day2 {

	public static void main(String args[]) {
		// part1();
		part2();
	}
	
	public static void part1() {
		Scanner sc = new Scanner(System.in);
		List<String> boxIDs = new ArrayList<String>();
		int twiceRepeatedIDs = 0;
		int thriceRepeatedIDs = 0;
		
		while(true) {
			String newBoxID = sc.nextLine();
			
			if (newBoxID.equals("exiting now")) {
				break;
			}
			
			boxIDs.add(newBoxID);
		}
		
		for (String boxID: boxIDs) {
			List<Character> chars = new ArrayList<Character>();
			for (int i = 0; i < boxID.length(); i++) {
				chars.add(boxID.charAt(i));
			}
			for (int i = 0; i < chars.size(); i++) {
				int count = 0;
				for (int j = 0; j < chars.size(); j++) {
					if (chars.get(i).equals(chars.get(j)) ) {
						count++;
					}
				}
				if (count == 2) {
					twiceRepeatedIDs++;
					break;
				}
			}
		}
		
		for (String boxID: boxIDs) {
			List<Character> chars = new ArrayList<Character>();
			for (int i = 0; i < boxID.length(); i++) {
				chars.add(boxID.charAt(i));
			}
			for (int i = 0; i < chars.size(); i++) {
				int count = 0;
				for (int j = 0; j < chars.size(); j++) {
					if (chars.get(i).equals(chars.get(j)) ) {
						count++;
					}
				}
				if (count == 3) {
					thriceRepeatedIDs++;
					break;
				}
			}
		}
		
		System.out.println(twiceRepeatedIDs * thriceRepeatedIDs);
		
		sc.close();
	}
	
	public static void part2() {
		Scanner sc = new Scanner(System.in);
		List<String> boxIDs = new ArrayList<String>();
		
		while(true) {
			String newBoxID = sc.nextLine();
			
			if (newBoxID.equals("")) {
				break;
			}
			
			boxIDs.add(newBoxID);
		}
		
		int boxIDLength = boxIDs.get(0).length();
		
		String firstCorrectID = "";
		String secondCorrectID = "";
		String similarCharacters = "";
		
		// Loop through every boxID in the list.
		// Check the current boxID vs all the other ones.
		//		This obviously requires a second loop.
		// When checking, if the matching letters differ from the 
		//		total length of each boxID by just one, we have 
		// 		found our match. Requires a third loop...
		//		Store the two strings.
		// Find the letter that's different between the two strings.
		// Print that letter.
		for (int i = 0; i < boxIDs.size(); i++) {
			String currentID = boxIDs.get(i);
			int matchingLetters = 0;
			for (int j = i + 1; j < boxIDs.size(); j++) {
				String comparedID = boxIDs.get(j);
				for (int k = 0; k < boxIDLength; k++) {
					if (currentID.charAt(k) == comparedID.charAt(k)) {
						matchingLetters++;
					}
				}
				if (matchingLetters == boxIDLength - 1) {
					firstCorrectID = currentID;
					secondCorrectID = comparedID;
					break;
				}
				
				matchingLetters = 0;
			}
			if (matchingLetters == boxIDLength - 1) {
				break;
			}
		}
		
		for(int i = 0; i < boxIDLength; i++) {
			if(firstCorrectID.charAt(i) == secondCorrectID.charAt(i)) {
				similarCharacters = similarCharacters.concat("" + firstCorrectID.charAt(i));
			}
		}
		
		System.out.println(similarCharacters);
		
		sc.close();
	}
}
