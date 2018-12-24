package AdventOfCode;

import java.util.Scanner;

public class Day3 {

	public static void main(String args[]) {
		// part1();
		part2();
	}
	
	public static void part1() {
		Scanner sc = new Scanner(System.in);
		int[][] claimedCoordinates = new int[1000][1000];
		int[][] overlappedCoordinates = new int[1000][1000];
		
		while(true) {
			String elfID = sc.next();
			if (elfID.equals("exiting")) {
				break;
			}
			
			// Get rid of the @ sign
			sc.next();
			
			String[] coordString = sc.next().split(",");
			coordString[1] = coordString[1].substring(0, coordString[1].length() - 1);
			int[] coord = {Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1])};
			
			String[] areaString = sc.next().split("x");
			int[] area = {Integer.parseInt(areaString[0]), Integer.parseInt(areaString[1])};
			
			for (int i = coord[0]; i < coord[0] + area[0]; i++) {
				for (int j = coord[1]; j < coord[1] + area[1]; j++) {
					if (claimedCoordinates[i][j] == 1) {
						overlappedCoordinates[i][j] = 1;
					}
				}
			}
			
			for (int i = coord[0]; i < coord[0] + area[0]; i++) {
				for (int j = coord[1]; j < coord[1] + area[1]; j++) {
					claimedCoordinates[i][j] = 1;
				}
			}
			
		}
		
		// Find final number of overlapped square inches
		int overlappedSquares = 0;
		
		for (int i = 0; i < 1000; i++) {
			for (int j = 0; j < 1000; j++) {
				if (overlappedCoordinates[i][j] == 1) {
					overlappedSquares++;
				}
			}
		}
		
		System.out.println(overlappedSquares);
		
		sc.close();
	}
	
	public static void part2() {
		Scanner sc = new Scanner(System.in);
		int[][] claimedCoordinates = new int[1000][1000];
		int[][] overlappedCoordinates = new int[1000][1000];
		int[][] elfClaimMap = new int[1409][5];
		
		while(true) {
			String elfIDString = sc.next();
			if (elfIDString.equals("exiting")) {
				break;
			}
			
			int elfID = Integer.parseInt(elfIDString.substring(1));
			
			elfClaimMap[elfID - 1][0] = elfID;
			
			// Get rid of the @ sign
			sc.next();
			
			String[] coordString = sc.next().split(",");
			coordString[1] = coordString[1].substring(0, coordString[1].length() - 1);
			int[] coord = {Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1])};
			
			elfClaimMap[elfID - 1][1] = coord[0];
			elfClaimMap[elfID - 1][2] = coord[1];
			
			String[] areaString = sc.next().split("x");
			int[] area = {Integer.parseInt(areaString[0]), Integer.parseInt(areaString[1])};
			
			elfClaimMap[elfID - 1][3] = area[0];
			elfClaimMap[elfID - 1][4] = area[1];
			
			for (int i = coord[0]; i < coord[0] + area[0]; i++) {
				for (int j = coord[1]; j < coord[1] + area[1]; j++) {
					if (claimedCoordinates[i][j] == 1) {
						overlappedCoordinates[i][j] = 1;
					}
				}
			}
			
			for (int i = coord[0]; i < coord[0] + area[0]; i++) {
				for (int j = coord[1]; j < coord[1] + area[1]; j++) {
					claimedCoordinates[i][j] = 1;
				}
			}
			
		}
		
		for (int i = 0; i < elfClaimMap.length; i++) {
			boolean noOverlap = true;
			
			for (int j = elfClaimMap[i][1]; j < elfClaimMap[i][1] + elfClaimMap[i][3]; j++) {
				for (int k = elfClaimMap[i][2]; k < elfClaimMap[i][2] + elfClaimMap[i][4]; k++) {
					if (overlappedCoordinates[j][k] > 0) {
						noOverlap = false;
					}
				}
			}
			
			if (noOverlap) {
				System.out.println(i + 1);
				System.exit(0);
			}
		}
		
		
		sc.close();
	}
}
