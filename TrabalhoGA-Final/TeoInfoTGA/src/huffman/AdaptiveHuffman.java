package huffman;

import java.util.*;

import javax.swing.JOptionPane;

public class AdaptiveHuffman {
	private Node nytNode;
	private Node root;
	private char[] codeStr;
	private ArrayList<Character> alreadyExist;
	ArrayList<Node> nodeList;
	private String tempCode = "";
	private String codeword = "";

	public AdaptiveHuffman(char[] codeStr) {
		this.codeStr = codeStr;
		alreadyExist = new ArrayList<Character>();
		nodeList = new ArrayList<Node>();

		// Initialize the NYT node
		nytNode = new Node("NYT", 0);
		nytNode.parent = null;
		root = nytNode;
		nodeList.add(nytNode);
	}

	public ArrayList<String> encode() {
		ArrayList<String> result = new ArrayList<String>();
		char character;
		
		// Walk through the input, adding the characters to the tree
		for (int i = 0; i < codeStr.length; i++) {
			character = codeStr[i];
			result.add(getCode(character));
			updateTree(character);
		}
		// Show the codewords dictionary to the user
		JOptionPane.showMessageDialog(null, "Static Dictionary: \n" + codeword,
				"CodeWords", JOptionPane.INFORMATION_MESSAGE);
		
		inorder(root);
		return result;
	}

	
	public ArrayList<String> encodeBin() {
		ArrayList<String> result = new ArrayList<String>();
		char character = 0;
		
		// Represent NEW
		result.add("0");
		
		// Walk through the input, adding the characters to the tree
		for (int i = 0; i < codeStr.length; i++) {
			character = codeStr[i];
			result.add(getCodeBin(character));
			updateTree(character);
		}
		return result;
	}

	private String getCode(char c) {
		tempCode = "";

		// Store the code of the char 'c' in the 'tempCode', by searching in the tree
		getCodeByTree(root, String.valueOf(c), "");
		String result = tempCode;
		
		// In case the the node for 'c' was not found
		if (result == "") {
			// Find NYT node and add value to the tree
			getCodeByTree(root, "NYT", "");
			result = tempCode;
			result += c;
			
			// Store 'caracter - encoded value' as it will be saved to a file 
			if (!codeword.contains(Character.toString(c))) {
				codeword += c + " - " + toBinary((int) c) + "\n";
			}
			result += toBinary((int) c);
		}
		return result;
	}

	private String getCodeBin(char c) {
		tempCode = "";
		
		// Store the code of the char 'c' in the 'tempCode', by searching in the tree
		getCodeByTreeBin(root, String.valueOf(c), "");
		String result = tempCode;
		
		// In case the the node for 'c' was not found
		if (result == "") {
			// Find NYT node and add value to the tree
			getCodeByTreeBin(root, "NYT", "");
			result = tempCode;
			result += toBinary((int) c);
		}
		return result;
	}

	private void updateTree(char c) {
		Node toBeAdd = null;
		
		if (!isAlreadyExist(c)) {
			Node innerNode = new Node(null, 1); // Inner node with null letter
			Node newNode = new Node(String.valueOf(c), 1); // Store the symbol
			innerNode.left = nytNode;
			innerNode.right = newNode;
			innerNode.parent = nytNode.parent;
			
			if (nytNode.parent != null) // In the first time the nyt node is root
				nytNode.parent.left = innerNode;
			else {
				root = innerNode;
			}
			
			nytNode.parent = innerNode;
			newNode.parent = innerNode;
			nodeList.add(1, innerNode);
			nodeList.add(1, newNode);
			alreadyExist.add(c);
			toBeAdd = innerNode.parent;
		} else {
			toBeAdd = findNode(c);
		}
		
		while (toBeAdd != null) {
			Node bigNode = findBigNode(toBeAdd.frequency);
			if (toBeAdd != bigNode && toBeAdd.parent != bigNode
					&& bigNode.parent != toBeAdd)
				swapNode(toBeAdd, bigNode);
			toBeAdd.frequency++;
			toBeAdd = toBeAdd.parent;
		}
	}

	private boolean isAlreadyExist(char temp) {
		for (int i = 0; i < alreadyExist.size(); i++) {
			if (temp == alreadyExist.get(i))
				return true;
		}
		return false;
	}

	public String decode() {
		String result = "";
		String symbol = null;
		char temp = 0;
		Node p = root;

		// The first symbol is NYT, so find it by ASCII
		symbol = getByAsc(0);
		result += symbol;
		updateTree(symbol.charAt(0));
		p = root;

		for (int i = 9; i < codeStr.length; i++) {
			temp = codeStr[i];

			if (temp == '0') {
				p = p.left;
			} else
				p = p.right;
			
			// Get letter from node
			symbol = visit(p);

			// If a leaf is reached
			if (symbol != null) {
				if (symbol == "NYT") {
					symbol = getByAsc(i);
					i += 8;
				}
				result += symbol;
				updateTree(symbol.charAt(0));
				p = root;
			}
		}

		return result;
	}

	// Get ASCII code
	private String getByAsc(int index) {
		int asc = 0;
		int tempInt = 0;
		for (int i = 7; i >= 0; i--) {
			tempInt = codeStr[++index] - 48;
			asc += tempInt * Math.pow(2, i);
		}

		char ret = (char) asc;
		return String.valueOf(ret);
	}

	private String visit(Node p) {
		if (p.letter != null) {
			return p.letter;
		}
		return null;
	}

	private Node findNode(char c) {
		String temp = String.valueOf(c);
		Node tempNode = null;
		for (int i = 0; i < nodeList.size(); i++) {
			tempNode = nodeList.get(i);
			if (tempNode.letter != null && tempNode.letter.equals(temp))
				return tempNode;
		}
		return null;
	}

	private void swapNode(Node n1, Node n2) {
		int i1 = nodeList.indexOf(n1);
		int i2 = nodeList.indexOf(n2);
		nodeList.remove(n1);
		nodeList.remove(n2);
		nodeList.add(i1, n2);
		nodeList.add(i2, n1);
		Node p1 = n1.parent;
		Node p2 = n2.parent;
		if (p1 != p2) {
			if (p1.left == n1) {
				p1.left = n2;
			} else {
				p1.right = n2;
			}

			if (p2.left == n2) {
				p2.left = n1;
			} else {
				p2.right = n1;
			}
		} else {
			p1.left = n2;
			p1.right = n1;
		}
		n1.parent = p2;
		n2.parent = p1;

	}

	private Node findBigNode(int frequency) {
		Node temp = null;
		for (int i = nodeList.size() - 1; i >= 0; i--) {
			temp = nodeList.get(i);
			if (temp.frequency == frequency)
				break;
		}
		return temp;
	}
	
	//Search the tree looking for 'code'
	private void getCodeByTree(Node node, String letter, String code) {
		if (node.left == null && node.right == null) {
			if (node.letter != null && node.letter.equals(letter))
				tempCode = code;
		} else {
			if (node.left != null) {
				getCodeByTree(node.left, letter, code);
			}
			if (node.right != null) {
				getCodeByTree(node.right, letter, code);
			}
		}
	}
	
	//Search the tree looking for 'code'
	private void getCodeByTreeBin(Node node, String letter, String code) {
		if (node.left == null && node.right == null) {
			if (node.letter != null && node.letter.equals(letter))
				tempCode = code;
		} else {
			if (node.left != null) {
				getCodeByTreeBin(node.left, letter, code + "0");
			}
			if (node.right != null) {
				getCodeByTreeBin(node.right, letter, code + "1");
			}
		}
	}
	
	// Convert decimal to binary
	public static String toBinary(int decimal) {
		String result = "";
		for (int i = 0; i < 8; i++) {
			if (decimal % 2 == 0)
				result = "0" + result;
			else
				result = "1" + result;
			decimal /= 2;
		}
		return result;
	}

	public void inorder(Node node) {
		if (node != null) {
			inorder(node.left);
			System.out.println(node.frequency + ":" + node.letter);
			inorder(node.right);
		}
	}
}