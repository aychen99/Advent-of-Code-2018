package AdventOfCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day4 {

	public static void main(String args[]) {
		// part1();
		part2();
	}
	
	public static void part1() {
		List<String[]> timestamps = timestampSorter();
		
		// Find guard that sleeps the most
		// 10000 covers all guard IDs, even if most are never used
		
		// new int[10000][2] INTENTIONALLY LEFT HERE AFTER WRITING THIS 
		// CODE AS A MARK OF SHAME FOR NOT REALIZING THAT I ONLY NEEDED 
		// A new int[10000][1]
		int[][] guardAndSleepTime = new int[10000][2];
		int currentGuard = 0;
		int fallTime = 0;
		int wakeTime = 0;
		for (int i = 0; i < timestamps.size(); i++) {
			String[] thisStamp = timestamps.get(i);
			if (thisStamp[5].contains("Guard")) {
				currentGuard = Integer.parseInt(thisStamp[5].split(" ")[1].
						substring(1));
			}
			if (thisStamp[5].contains("falls")) {
				fallTime = Integer.parseInt(thisStamp[4]);
			}
			if (thisStamp[5].contains("wakes")) {
				wakeTime = Integer.parseInt(thisStamp[4]);
				guardAndSleepTime[currentGuard][1] += (wakeTime - fallTime);
				wakeTime = 0;
				fallTime = 0;
			}
		}
		
		int sleepiestGuard = 0;
		for(int i = 0; i < 10000; i++) {
			if (guardAndSleepTime[i][1] > guardAndSleepTime[sleepiestGuard][1]) {
				sleepiestGuard = i;
			}
		}
		
		// Find minute that sleepiest guard is asleep the most
		int[] minutesOfMidnight = new int[60];
		boolean rightGuard = false;
		for (int i = 0; i < timestamps.size(); i++) {
			String[] thisStamp = timestamps.get(i);
			if (thisStamp[5].contains("" + sleepiestGuard)) {
				rightGuard = true;
			} else if (thisStamp[5].contains("Guard")) {
				rightGuard = false;
			}
			if (!rightGuard) {
				continue;
			}
			if (thisStamp[5].contains("falls")) {
				fallTime = Integer.parseInt(thisStamp[4]);
			}
			if (thisStamp[5].contains("wakes")) {
				wakeTime = Integer.parseInt(thisStamp[4]);
				for (int j = fallTime; j < wakeTime; j++) {
					minutesOfMidnight[j]++;
				}
				wakeTime = 0;
				fallTime = 0;
			}
		}
		
		int bestMinute = 0;
		for (int i = 0; i < 60; i++) {
			if (minutesOfMidnight[bestMinute] < minutesOfMidnight[i]) {
				bestMinute = i;
			}
		}
		
		System.out.println(sleepiestGuard * bestMinute);
	}
	
	// After writing part1 and part2, it is easy to see that they could 
	// have been written more efficiently and combined at the beginning
	// if part 2 of the puzzle was known already.
	public static void part2() {
		List<String[]> timestamps = timestampSorter();
		
		// First log time asleep on each minute for each guard
		// 10000 covers all guard IDs, even if most are never used
		int[][] guardAndSleepTime = new int[10000][60];
		int currentGuard = 0;
		int fallTime = 0;
		int wakeTime = 0;
		for (int i = 0; i < timestamps.size(); i++) {
			String[] thisStamp = timestamps.get(i);
			if (thisStamp[5].contains("Guard")) {
				currentGuard = Integer.parseInt(thisStamp[5].split(" ")[1].substring(1));
			}
			if (thisStamp[5].contains("falls")) {
				fallTime = Integer.parseInt(thisStamp[4]);
			}
			if (thisStamp[5].contains("wakes")) {
				wakeTime = Integer.parseInt(thisStamp[4]);
				for (int j = fallTime; j < wakeTime; j++) {
					guardAndSleepTime[currentGuard][j]++;
				}
				wakeTime = 0;
				fallTime = 0;
			}
		}
		
		// Find the minute with the most sleep time of any guard, while 
		// storing the ID of the guard that slept the most on that minute. 
		// Then, print out the ID of the guard multiplied by the minute. 

		int sleepiestGuard = 0;
		int sleepiestMinute = 0;
		int amountSleptOnSleepiestMinute = 0;
		for (int i = 0; i < 10000; i++) {
			for (int j = 0; j < 60; j++) {
				if (guardAndSleepTime[i][j] > amountSleptOnSleepiestMinute) {
					sleepiestGuard = i;
					sleepiestMinute = j;
					amountSleptOnSleepiestMinute = guardAndSleepTime[i][j];
				}
			}
		}

		System.out.println(sleepiestGuard * sleepiestMinute);
	}
	
	public static List<String[]> timestampSorter() {
		Scanner sc = new Scanner(System.in);

		List<String> rawTimestamps = new ArrayList<String>();
		List<String[]> unsortedTimestamps = new ArrayList<String[]>();
		List<String[]> timestamps = new ArrayList<String[]>();
		// Sorted timestamps store time in "year, month, day, hour,
		// minute, and finally guard # OR falls asleep/wakes up" format.

		// Record all timestamps
		while (true) {
			String timestamp = sc.nextLine();
			if (timestamp.equals("exiting")) {
				break;
			}
			rawTimestamps.add(timestamp);
		}

		// Organize each timestamp string into a String array, with
		// year, month, day, hour, minute,
		// and then guard # or "falls asleep/wakes up"
		for (String timestamp : rawTimestamps) {
			String[] unsortedTimestamp = new String[6];
			unsortedTimestamp[0] = timestamp.substring(1, 5);
			unsortedTimestamp[1] = timestamp.substring(6, 8);
			unsortedTimestamp[2] = timestamp.substring(9, 11);
			unsortedTimestamp[3] = timestamp.substring(12, 14);
			unsortedTimestamp[4] = timestamp.substring(15, 17);
			unsortedTimestamp[5] = timestamp.substring(19);
			unsortedTimestamps.add(unsortedTimestamp);
		}

		// Sort all timestamps; using inefficient sorting for now
		int numberOfTimestamps = unsortedTimestamps.size();
		while (numberOfTimestamps > timestamps.size()) {
			// It appears that the only year is 1518; nevertheless,
			// We will still take the precaution of checking for a
			// different year.
			String[] earliestRemainingTimestamp;
			earliestRemainingTimestamp = unsortedTimestamps.get(0);

			for (int i = 0; i < unsortedTimestamps.size(); i++) {
				String[] compareThis = unsortedTimestamps.get(i);
				// Compare years
				int compareThisYear = Integer.parseInt(compareThis[0]);
				int eRTYear = Integer.parseInt(earliestRemainingTimestamp[0]);
				if (compareThisYear < eRTYear) {
					earliestRemainingTimestamp = compareThis;
					continue;
				} else if (compareThisYear == eRTYear) {
					// Compare months
					int compareThisMonth = Integer.parseInt(compareThis[1]);
					int eRTMonth = Integer.parseInt(earliestRemainingTimestamp[1]);
					if (compareThisMonth < eRTMonth) {
						earliestRemainingTimestamp = compareThis;
						continue;
					} else if (compareThisMonth == eRTMonth) {
						// Compare days
						int compareThisDay = Integer.parseInt(compareThis[2]);
						int eRTDay = Integer.parseInt(earliestRemainingTimestamp[2]);
						if (compareThisDay < eRTDay) {
							earliestRemainingTimestamp = compareThis;
							continue;
						} else if (compareThisDay == eRTDay) {
							// Compare hours
							int compareThisHour = Integer.parseInt(compareThis[3]);
							int eRTHour = Integer.parseInt(earliestRemainingTimestamp[3]);
							if (compareThisHour < eRTHour) {
								earliestRemainingTimestamp = compareThis;
								continue;
							} else if (compareThisHour == eRTHour) {
								// Compare minutes
								if (Integer.parseInt(compareThis[4]) < Integer
										.parseInt(earliestRemainingTimestamp[4])) {
									earliestRemainingTimestamp = compareThis;
									continue;
								}
							}
						}
					}
				}
			}

			timestamps.add(earliestRemainingTimestamp);
			unsortedTimestamps.remove(earliestRemainingTimestamp);
		}

		sc.close();

		return timestamps;
	}
	
}
