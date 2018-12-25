package AdventOfCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day6 {

	public static void main(String args[]) {
		// part1();
		part2();
	}
	
	public static void part1() {
		// Manhattan distance: in this puzzle, only need to sum up 
		// change in x and change in y
		
		// Extending grid should allow easy determination 
		// of which coordinates have infinite areas. If in a straight line 
		// in any direction (up, down, left, right, and diagonal) 
		// for 200 units the dot on the grid "belongs" to 
		// the coordinate, then that coordinate can be deemed to have 
		// infinite area.
		
		int[][] coordinates = coordinatesScanner();
		
		// Shift coordinates right and down by 200 to allow a grid with 
		// arrays
		for (int i = 0; i < coordinates.length; i++) {
			for (int j = 0; j < 2; j++) {
				coordinates[i][j] += 200;
			}
		}
		
		int[][] grid = new int[1001][1001];
		// Run through every part of the grid with every coordinate to 
		// see which one is closest to it, and mark that on the grid
		for (int i = 0; i < 1001; i++) {
			for (int j = 0; j < 1001; j++) {
				// -1 represents shared closest coordinates
				int closestCoordinate = 0;
				int shortestDistance = 1000;
				for (int k = 0; k < coordinates.length; k++) {
					int xDistance = Math.abs(coordinates[k][0] - i);
					int yDistance = Math.abs(coordinates[k][1] - j);
					int manhattanDistance = xDistance + yDistance;
					if (manhattanDistance < shortestDistance) {
						closestCoordinate = k;
// It was here that forgetting to assign 
// the new shortest distance to the 
// shortestDistance variable led me to being 
// stuck for an hour...
						shortestDistance = manhattanDistance;
					} else if (manhattanDistance == shortestDistance) {
						closestCoordinate = -1;
					}
				}
				grid[i][j] = closestCoordinate;
			}
		}
		
		// Cross out all coordinates that have infinite areas
		//	by making them null.
		// Infinite areas will occur only if in a straight line either 
		// up, down, left, or right, or in a diagonal, the dots on 
		// the grid all belong to that coordinate.
		for (int i = 0; i < coordinates.length; i++) {
			int coordX = coordinates[i][0];
			int coordY = coordinates[i][1];
			if (grid[coordX + 200][coordY] == i ||
					grid[coordX - 200][coordY] == i ||
					grid[coordX][coordY + 200] == i ||
					grid[coordX][coordY - 200] == i || 
					grid[coordX + 200][coordY + 200] == i ||
					grid[coordX - 200][coordY + 200] == i ||
					grid[coordX + 200][coordY - 200] == i ||
					grid[coordX - 200][coordY - 200] == i ) {
				coordinates[i] = null;
			}
		}
		
		// Run through the grid to see what the largest area is
		int largestArea = 0;
		for (int i = 0; i < coordinates.length; i++) {
			if (coordinates[i] == null) {
				continue;
			} else {
				int area = 0;
				for (int j = 0; j < 1001; j++) {
					for (int k = 0; k < 1001; k++) {
						if (grid[j][k] == i) {
							area++;
						}
					}
				}
				if (area > largestArea) {
					largestArea = area;
				}
			}
		}
		
		System.out.println(largestArea);
		
		
	}
	
	public static void part2() {
		int[][] coordinates = coordinatesScanner();

		// Shift coordinates right and down by 200 to allow a grid with
		// arrays
		for (int i = 0; i < coordinates.length; i++) {
			for (int j = 0; j < 2; j++) {
				coordinates[i][j] += 200;
			}
		}
		
		// Again using the 1001x1001 grid for its sufficient largeness
		int sizeOfDesiredRegion = 0;
		// Calculate total distance of each dot on the grid; if it 
		// is less than 10000, then it is in the desired region, and 
		// the size of the desired region increases by 1.
		for (int i = 0; i < 1001; i++) {
			for (int j = 0; j < 1001; j++) {
				int totalDistance = 0;
				for (int k = 0; k < coordinates.length; k++) {
					int xDistance = Math.abs(coordinates[k][0] - i);
					int yDistance = Math.abs(coordinates[k][1] - j);
					int manhattanDistance = xDistance + yDistance;
					totalDistance += manhattanDistance;
				}
				if (totalDistance < 10000) {
					sizeOfDesiredRegion++;
				}
			}
		}
		
		System.out.println(sizeOfDesiredRegion);
	}
	
	public static int[][] coordinatesScanner() {
		List<int[]> coordinates = new ArrayList<int[]>();
		Scanner sc = new Scanner(System.in);
		for(; ;) {
			String newCoord = sc.nextLine();
			if (newCoord.equals("exiting")) {
				break;
			}
			String[] coordinateS = newCoord.split(", ");
			int[] coordinate = {Integer.parseInt(coordinateS[0]), 
					Integer.parseInt(coordinateS[1])};
			coordinates.add(coordinate);
		}
		
		sc.close();
		
		int[][] coordinateArray = new int[coordinates.size()][2];
		for (int i = 0; i < coordinates.size(); i++) {
			coordinateArray[i][0] = coordinates.get(i)[0];
			coordinateArray[i][1] = coordinates.get(i)[1];
		}
		
		return coordinateArray;
	}
}
