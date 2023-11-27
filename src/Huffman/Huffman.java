package Huffman;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import static java.util.Objects.requireNonNull;

public class Huffman {
    private Node root;
    private final String text;
    private Map<Character, Integer> charFrequencies;
    private final Map<Character, String> huffmanCode;

    public Huffman(String text) {
        this.text = text;
        calculateCharFrequency();
        huffmanCode = new HashMap<>();
    }

    private void calculateCharFrequency() {
        charFrequencies = new HashMap<>();
        for (char character : text.toCharArray()) {
            charFrequencies.put(character, charFrequencies.getOrDefault(character, 0) + 1);
        }
    }

    public String encode() {
        // Build Huffman tree
        Queue<Node> queue = new PriorityQueue<>();
        charFrequencies.forEach((character, frequency) ->
                queue.add(new Leaf(character, frequency))
        );
        while (queue.size() > 1) {
            queue.add(new Node(queue.poll(), requireNonNull(queue.poll())));
        }
        root = queue.poll();

        // Serialize and store Huffman tree
        String serializedTree = serializeTree(root);

        // Generate Huffman codes
        generateHuffman(root, "");

        // Return encoded text along with the serialized tree
        return serializedTree + '|' + getEncodedText();
    }

    private String serializeTree(Node node) {
        StringBuilder serializedTree = new StringBuilder();
        if (node instanceof Leaf leaf) {
            serializedTree.append("L").append(leaf.getCharacter());
        } else {
            serializedTree.append("I");
            serializedTree.append(serializeTree(node.getLeftNode()));
            serializedTree.append(serializeTree(node.getRightNode()));
        }
        return serializedTree.toString();
    }

    private int treeIndex = 0;

    private Node deserializeTree(String serializedTree) {
        char nodeType = serializedTree.charAt(treeIndex++);
        if (nodeType == 'L') {
            char character = serializedTree.charAt(treeIndex++);
            return new Leaf(character, 0);
        } else if (nodeType == 'I') {
            Node left = deserializeTree(serializedTree);
            Node right = deserializeTree(serializedTree);
            return new Node(left, right);
        } else {
            throw new IllegalArgumentException("Invalid serialized tree format");
        }
    }

    private String getEncodedText() {
        StringBuilder encodedText = new StringBuilder();
        for (char character : text.toCharArray()) {
            encodedText.append(huffmanCode.get(character));
        }
        return encodedText.toString();
    }

    private void generateHuffman(Node node, String code) {
        if (node instanceof Leaf leaf) {
            huffmanCode.put(leaf.getCharacter(), code);
            return;
        }
        generateHuffman(node.getLeftNode(), code.concat("0"));
        generateHuffman(node.getRightNode(), code.concat("1"));
    }

    public String decode(String encodedTextWithTree) {
        // Check if the delimiter '|' exists
        int delimiterIndex = encodedTextWithTree.indexOf('|');
        if (delimiterIndex == -1) {
            throw new IllegalArgumentException("Invalid encoded text format - missing serialized tree delimiter");
        }

        // Extract serialized tree and encoded text
        String serializedTree = encodedTextWithTree.substring(0, delimiterIndex);
        String encodedText = encodedTextWithTree.substring(delimiterIndex + 1);

        // Reconstruct Huffman tree
        treeIndex = 0;
        root = deserializeTree(serializedTree);

        // Decode using the reconstructed tree
        StringBuilder decodedText = new StringBuilder();
        Node node = root;
        for (char bit : encodedText.toCharArray()) {
            if (bit == '0') {
                node = node.getLeftNode();
            } else {
                node = node.getRightNode();
            }

            if (node instanceof Leaf leaf) {
                decodedText.append(leaf.getCharacter());
                node = root;
            }
        }
        return decodedText.toString();
    }


}
