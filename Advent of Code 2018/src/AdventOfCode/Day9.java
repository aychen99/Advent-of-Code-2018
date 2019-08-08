package AdventOfCode;

public class Day9 {

	public static void main(String args[]) {
		calculateHighScore(10, 1618); // 8317
		calculateHighScore(13, 7999); // 146373
		calculateHighScore(17, 1104); // 2764
		calculateHighScore(21, 6111); // 54718
		calculateHighScore(30, 5807); // 37305
		calculateHighScore(418, 71339); // Part 1
		calculateHighScore(418, 7133900); // Part 2
	}
	
	public static void calculateHighScore(int numPlayers, int largestMarble) {
		long[] playerScores = new long[numPlayers];
		MarbleDeque marbles = new MarbleDeque();
		
		marbles.insert(new Node(0));
		
		for (int i = 1; i <= largestMarble; i++) {
			if (i % 23 == 0) {
				int currentPlayer = i % numPlayers;
				playerScores[currentPlayer] += i;
				marbles.setHeadNodeTo(-7);
				playerScores[currentPlayer] += marbles.getNodeAtIndex(0).getScore();
				marbles.removeByIndex(0);
			} else {
				marbles.setHeadNodeTo(2);
				marbles.insert(new Node(i));
				marbles.setHeadNodeTo(-1);
			}
		}
		
		long highScore = 0;
		for (int i = 0; i < numPlayers; i++) {
			if (playerScores[i] > highScore) {
				highScore = playerScores[i];
			}
		}
		
		System.out.println(highScore);
	}
	
}

class Node {
	private int _marbleNum;
	public Node _next;
	public Node _prev;
	
	public Node(int marbleNum) {
		this._marbleNum = marbleNum;
	}
	
	public int getScore() {
		return _marbleNum;
	}
}

class MarbleDeque {
	private Node _headNode;
	
	public MarbleDeque(Node head) {
		_headNode = head;
	}
	
	public MarbleDeque() {
		this(null);
	}
	
	// Inserts at end of deque
	public void insert(Node node) {
		if (_headNode == null) {
			_headNode = node;
			_headNode._next = node;
			_headNode._prev = node;
		} else {
			Node prevNode = _headNode._prev;
			node._prev = prevNode;
			node._next = _headNode;
			_headNode._prev = node;
			prevNode._next = node;
		}
	}
	
	public void remove(Node node) {
		Node prevNode = node._prev;
		Node nextNode = node._next;
		prevNode._next = nextNode;
		nextNode._prev = prevNode;
		if (_headNode == node) {
			_headNode = nextNode;
		}
	}
	
	public void removeByIndex(int index) {
		this.remove(this.getNodeAtIndex(index));
	}
	
	public Node getNodeAtIndex(int index) {
		Node node = _headNode;
		if (index < 0) {
			for (int i = 0; i > index; i--) {
				node = node._prev;
			}
		} else {
			for (int i = 0; i < index; i++) {
				node = node._next;
			}
		}
		return node;
	}
	
	public void setHeadNodeTo(int index) {
		_headNode = getNodeAtIndex(index);
	}
}