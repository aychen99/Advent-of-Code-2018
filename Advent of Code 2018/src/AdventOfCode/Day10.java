package AdventOfCode;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Day10 {

	public static void main(String args[]) {
		part1();
	}
	
	public static void part1() {
		LightPoint[] input = receiveInput();
		int iteration = setToWhenLightPointsAreDensest(input);
		printMessage(input);
	}
	
	public static void part2() {
		// The variable "iteration" in part1
		// is the answer to part 2
	}
	
	public static int setToWhenLightPointsAreDensest(LightPoint[] points) {
		int xDiff = Integer.MAX_VALUE;
		int yDiff = Integer.MAX_VALUE;
		int iteration = 0;
		
		for (int i = 1; i < 20000; i++) {
			int minx = Integer.MAX_VALUE;
			int maxx = Integer.MIN_VALUE;
			int miny = Integer.MAX_VALUE;
			int maxy = Integer.MIN_VALUE;
			for (LightPoint point: points) {
				point.updatePosition();
				if (point.x < minx) {
					minx = point.x;
				} else if (point.x > maxx) {
					maxx = point.x;
				} else if (point.y < miny) {
					miny = point.y;
				} else if (point.y > maxy) {
					maxy = point.y;
				}
			}
			
			if (maxx - minx < xDiff && maxy - miny < yDiff) {
				xDiff = maxx - minx;
				yDiff = maxy - miny;
				iteration = i;
			}
			
		}
		
		for (int i = 19999; i > iteration; i--) {
			for (LightPoint point : points) {
				point.undoUpdate();
			}
		}
		
		return iteration;
	}
	
	public static void printMessage(LightPoint[] points) {
		char[][] message = new char[401][401];
		for (int i = 0; i < message.length; i++) {
			for (int j = 0; j < message.length; j++) {
				message[i][j] = ' ';
			}
		}
		
		for (int i = 0; i < points.length; i++) {
			message[points[i].x+200][points[i].y+200] = '*';
		}
	
		for (int i = 0; i < message.length; i++) {
			for (int j = 0; j < message.length; j++) {
				System.out.print(message[j][i]);
			}
			System.out.println();
		}
	}
	
	public static LightPoint[] receiveInput() {
		Scanner sc = new Scanner(System.in);
		List<LightPoint> points = new ArrayList<LightPoint>();
		
		while (true) {
			String nextPoint = sc.nextLine();
			if (nextPoint.contains("exit")) {
				break;
			}
			nextPoint = nextPoint.replace("position=<", "");
			nextPoint = nextPoint.replace("> velocity=<", "|");
			nextPoint = nextPoint.replace(">", "");
			nextPoint = nextPoint.replaceAll(" ", "");
			int firstComma = nextPoint.indexOf(",");
			int positionVelocitySplitter = nextPoint.indexOf("|");
			int secondComma = nextPoint.lastIndexOf(",");
			int x = Integer.parseInt(nextPoint.substring(0, firstComma));
			int y = Integer.parseInt(nextPoint.substring(firstComma+1, positionVelocitySplitter));
			int vx = Integer.parseInt(nextPoint.substring(positionVelocitySplitter+1, secondComma));
			int vy = Integer.parseInt(nextPoint.substring(secondComma+1));
			
			points.add(new LightPoint(new int[] {x, y}, new int[] {vx, vy}));
		}
		
		sc.close();
		return points.toArray(new LightPoint[points.size()]);
	}
	
}

class LightPoint {
	int x;
	int y;
	int vx;
	int vy;
	
	LightPoint(int[] initialPosition, int[] velocity) {
		x = initialPosition[0];
		y = initialPosition[1];
		vx = velocity[0];
		vy = velocity[1];
	}
	
	public void updatePosition() {
		x += vx;
		y += vy;
	}
	
	public void undoUpdate() {
		x -= vx;
		y -= vy;
	}
}
