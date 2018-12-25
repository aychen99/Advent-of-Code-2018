package AdventOfCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day7 {

	public static void main(String args[]) {
		// part1();
		part2();
	}
	
	public static void part1() {
		int[][] taskSequence = taskSequenceScheduler(instructionsTrimmer(instructionsScanner()));
		
		// Now check which steps can be completed, and each time you 
		// complete a step, tell all the other steps.
		// Record the order in which the steps are completed.
		// Remove a step once it is completed so we don't complete it 
		// twice.
		String orderOfSteps = "";
		
		for(; ;) {
			for (int i = 0; i < 26; i++) {
				// If step already completed, then ignore
				if (taskSequence[i] == null) {
					continue;
				}
				
				// If step not yet completed, check to see if it can be
				// done
				boolean canCompleteStep = true;
				for (int j = 0; j < 26; j++) {
					if (taskSequence[i][j] == 1) {
						canCompleteStep = false;
						break;
					}
				}

				// If it can be done, complete the step and tell all 
				// the other steps, and also mark this step as complete 
				// by setting it to null
				if (canCompleteStep) {
					orderOfSteps = orderOfSteps.concat("" + (char) ('A' + i));
					for (int k = 0; k < 26; k++) {
						if (taskSequence[k] == null) {
							continue;
						} else {
							taskSequence[k][i] = 0;
						}
					}
					taskSequence[i] = null;
					break;
				}
			}
			
			// All steps have only been completed if every step is now 
			// null.
			boolean allStepsCompleted = true;
			for (int i = 0; i < 26; i++) {
				if (taskSequence[i] != null) {
					allStepsCompleted = false;
					break;
				}
			}
			
			if (allStepsCompleted) {
				break;
			}
		}
		
		System.out.println(orderOfSteps);
		
	}
	
	public static void part2() {
		int[][] taskSequence = taskSequenceScheduler(instructionsTrimmer(instructionsScanner()));
		
		// It's obviously do-able without asynchronous programming
		
		
		int totalTimeTaken = 0;
		boolean allStepsCompleted = false;
		// Since there are 5 workers, the array to store how much time is
		// left will be 5 long.
		int[] timeLeftPerWorker = new int[5];
		int[] currentStepOfWorker = {-1, -1, -1, -1, -1};
		
		// We can model this so that a second passes ONLY at the end 
		// of each loop iteration.
		for(; ;) {
			// Check if any worker has completed the step assigned.
			// If so, free up the worker, and mark the step as complete.
			for (int i = 0; i < 5; i++) {
				if (timeLeftPerWorker[i] <= 0) {
					if (currentStepOfWorker[i] != -1) {
						taskSequence[currentStepOfWorker[i]] = null;
						for (int j = 0; j < 26; j++) {
							if (taskSequence[j] == null) {
								continue;
							} else {
								taskSequence[j][currentStepOfWorker[i]] = 0;
							}
						}
						currentStepOfWorker[i] = -1;
					}
				}
			}
			
			// Once done with step completion checks, assign workers work.
			for (int i = 0; i < 26; i++) {
				// See if any worker can be assigned.
				// If not, don't check any steps to see if they can 
				// be completed; instead, move on to the next second
				// to see if any worker is freed up.
				boolean allWorkersBusy = true;
				for (int j = 0; j < 5; j++) {
					if (currentStepOfWorker[j] == -1) {
						allWorkersBusy = false;
						break;
					}
				}
				if (allWorkersBusy) {
					break;
				}
				
				// Make sure there isn't already a worker assigned to 
				// this step.
				boolean workerAssignedAlready = false;
				for (int j = 0; j < 5; j++) {
					if (currentStepOfWorker[j] == i) {
						workerAssignedAlready = true;
						break;
					}
				}
				if (workerAssignedAlready) {
					continue;
				}
				
				// If a worker can be assigned, then assign if possible.
				// Check all steps to see if there is spare work available
				
				// If step already completed, then ignore
				if (taskSequence[i] == null) {
					continue;
				}

				// If step not yet completed, check to see if it can
				// be done.
				boolean canCompleteStep = true;
				for (int j = 0; j < 26; j++) {
					if (taskSequence[i][j] == 1) {
						canCompleteStep = false;
						break;
					}
				}

				// If it can be done, and a worker can be assigned,
				// assign the worker.
				if (canCompleteStep) {
					for (int j = 0; j < 5; j++) {
						if (currentStepOfWorker[j] == -1) {
							currentStepOfWorker[j] = i;
							timeLeftPerWorker[j] = 60 + (i + 1);
							break;
						}
					}
				}
			}
			
			// All steps have only been completed if every step is now 
			// null.
			allStepsCompleted = true;
			for (int i = 0; i < 26; i++) {
				if (taskSequence[i] != null) {
					allStepsCompleted = false;
					break;
				}
			}
			
			if (allStepsCompleted) {
				break;
			}
			
			// If not all steps have been completed, let a second pass.
			totalTimeTaken++;
			for (int i = 0; i < 5; i++) {
				timeLeftPerWorker[i]--;
			}
		}
		
		System.out.println(totalTimeTaken);
	}
	
	public static String[] instructionsScanner() {
		Scanner sc = new Scanner(System.in);
		List<String> instructionsList = new ArrayList<String>();
		for(; ;) {
			String nextInstruction = sc.nextLine();
			if (nextInstruction.equals("exiting")) {
				break;
			}
			instructionsList.add(nextInstruction);
		}
		
		sc.close();
		return instructionsList.toArray(new String[instructionsList.size()]);
	}
	
	public static String[] instructionsTrimmer(String[] instructions) {
		for (int i = 0; i < instructions.length; i++) {
			instructions[i] = instructions[i].replace("Step ", "");
			instructions[i] = instructions[i].replace("must be finished before step ", "");
			instructions[i] = instructions[i].replace(" can begin.", "");
			// This leaves only the step that must be finished first, 
			// followed by a space, followed by the step that can begin 
			// after the first step is finished.
		}
		return instructions;		
	}
	
	public static int[][] taskSequenceScheduler(String[] instructions) {
		// Put all the step requirements into a manageable format by
		// creating a 26x26 array. The outer array represents each step;
		// the inner array represents whether that step requires each of 
		// the other steps to be completed before it can begin.
		// Let a 1 mean that the step needs to be completed first;
		// let a 0 mean that the step has been/does not need to be 
		// completed.
		int[][] taskSequence = new int[26][26];
		
		for (int i = 0; i < instructions.length; i++) {
			char secondStep = instructions[i].charAt(2);
			char firstStep = instructions[i].charAt(0);
			
			taskSequence[secondStep - 'A'][firstStep - 'A'] = 1;
		}
		
		return taskSequence;
	}
}
