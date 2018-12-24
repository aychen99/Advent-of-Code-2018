package AdventOfCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day1 {
	
	public static void main(String args[]) {
		Scanner scan = new Scanner(System.in);

		int totalFrequency = 0;
		List<Integer> list = new ArrayList<Integer>();
		List<Integer> stuffToAdd = new ArrayList<Integer>();

		while (scan.hasNextLine()) {
			int freq = scan.nextInt();
			if (freq == -666666666) {
				break;
			}
			stuffToAdd.add(freq);
			totalFrequency += freq;
			for (Integer pastValue: list) {
				if (pastValue == totalFrequency) {
					System.out.println(totalFrequency);
					System.exit(0);
				}
			}
			list.add(totalFrequency);
		}
		
		boolean frequencyNotFound = true;
		while(frequencyNotFound) {
			for (Integer toAdd: stuffToAdd) {
				totalFrequency += toAdd;
				for (Integer pastValue: list) {
					if (pastValue == totalFrequency) {
						System.out.println(totalFrequency);
						System.exit(0);
					}
				}
			}
		}

		System.out.println(totalFrequency);
		
		scan.close();
	}
}
