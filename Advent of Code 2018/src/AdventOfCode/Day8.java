package AdventOfCode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day8 {

	public static void main(String args[]) {
		try {
			// part1();
			part2();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void part1() throws FileNotFoundException {
		int[] tree = treeScanner();
		
		System.out.println(metadataSum(tree, new int[] {0}));
	}
	
	public static void part2() throws FileNotFoundException {
		int[] tree = treeScanner();
		
		System.out.println(nodeValue(tree, new int[] {0}));
	}
	
	public static int[] treeScanner() throws FileNotFoundException {
		Scanner in = new Scanner(new File("src/AdventOfCode/Day8Trees.txt"));
		List<Integer> treeList = new ArrayList<Integer>();
		while(in.hasNext()) {
			treeList.add(Integer.parseInt(in.next()));
		}
		
		int[] tree = new int[treeList.size()];
		for (int i = 0; i < treeList.size(); i++) {
			tree[i] = treeList.get(i);
		}
		in.close();
		
		return tree;
	}
	
	// Recursive way of going through the tree and finding the sum of 
	// all the metadata
	public static int metadataSum(int[] tree, int[] currentIndex) {
		int sum = 0;
		// If there are child nodes, use recursion to find the value 
		// of the metadata within each, while also recursively ensuring 
		// the index of the tree is updated correctly.
		// As for the metadata entries, just sum them up after 
		// looking at all the child nodes. The index at which the 
		// metadata begin will be correct either with or without child 
		// nodes.
		int childNodes = tree[currentIndex[0]];
		currentIndex[0]++;
		int metadataNumber = tree[currentIndex[0]];
		currentIndex[0]++;
		for (int i = 0; i < childNodes; i++) {
			sum += metadataSum(tree, currentIndex);
		}
		for (int i = 0; i < metadataNumber; i++) {
			sum += tree[currentIndex[0]];
			currentIndex[0]++;
		}
		
		return sum;
	}
	
	// Recursive way of going through the tree to find the value of 
	// a node
	public static int nodeValue(int[] tree, int[] currentIndex) {
		int nodeValue = 0;
		
		int childNodes = tree[currentIndex[0]];
		currentIndex[0]++;
		int metadataNumber = tree[currentIndex[0]];
		currentIndex[0]++;
		
		// If 0 child nodes, just sum up metadata.
		if (childNodes == 0) {
			for (int i = 0; i < metadataNumber; i++) {
				nodeValue += tree[currentIndex[0]];
				currentIndex[0]++;
			}
		} else {
			// Store the values of all the child nodes so that we 
			// can refer to them once we get to the metadata
			int[] childNodeValues = new int[childNodes];
			for (int i = 0; i < childNodes; i++) {
				// Run through each child node and get the node value.
				childNodeValues[i] = nodeValue(tree, currentIndex);
			}
			
			// Now run through the metadata and retrieve the values of 
			// the indicated child nodes.
			for (int i = 0; i < metadataNumber; i++) {
				if (tree[currentIndex[0]] < 1 || 
						tree[currentIndex[0]] > childNodes) {
					// Do nothing if indicated child node doesn't exist
				} else {
					nodeValue += childNodeValues[tree[currentIndex[0]] - 1];
				}
				currentIndex[0]++;
			}
		}
		
		return nodeValue;
	}
}
