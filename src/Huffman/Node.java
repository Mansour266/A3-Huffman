package Huffman;

public class Node implements Comparable<Node> {
    private final int frequency;
    private Node leftNode;
    private Node rightNode;

    public Node(Node leftNode, Node rightNode) {
        this.leftNode = leftNode;
        this.rightNode = rightNode;
        this.frequency = leftNode.getFrequency() + rightNode.getFrequency();
    }

    public Node(int freq) {
        frequency = freq;
    }

    private int getFrequency() {
        return frequency;
    }

    @Override
    public int compareTo(Node node) {
        return Integer.compare(frequency, node.getFrequency());
    }

    public Node getLeftNode() {
        return leftNode;
    }

    public Node getRightNode() {
        return rightNode;
    }
}
//==================================================================================================
class Leaf extends Node {
    private final char character;

    public Leaf(char character, int frequency) {
        super(frequency);
        this.character = character;
    }

    public Character getCharacter() {
        return character;
    }
}
