package app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

public class App {
    static String[] acceptedCombinations = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o",
            "p", "q", "r", "s", "t", "u", "v", "w", "z", "y", "x", "å", "ä", "ö" };
    static int LIST_MAX_SIZE = 50_000;

    public static void main(String[] args) throws Exception {
        System.out.println(generateWord(5, true).size());
    }

    public static Map<Integer, List<String>> generateWord(int wordLength, boolean displayInfoDialog) {
        double expectedSize = Math.pow(acceptedCombinations.length, wordLength);
        double expectedListNumber = expectedSize / LIST_MAX_SIZE;
        if (displayInfoDialog) {
            JOptionPane.showMessageDialog(null,
                    "Expected size: " + expectedSize + " Expected number of lists: " + expectedListNumber);

        }

        Map<Integer, List<String>> lHashMap = new HashMap<>();
        var wordNodes = getWordGeneratorNode(wordLength);
        List<String> wordList = new ArrayList<>(LIST_MAX_SIZE);

        while (wordNodes.getKey().allowsIncrement) {
            wordList.add(wordNodes.getValue().increment());
            if (wordList.size() == LIST_MAX_SIZE) {
                lHashMap.put(lHashMap.size(), wordList);
                wordList = new ArrayList<>(LIST_MAX_SIZE);
            }
        }
        System.out.println((int) expectedListNumber == lHashMap.size() ? "Generated all lists successfully!"
                : "Failed in precision of generating lists");
        return lHashMap;
    }

    public static Entry<node, node> getWordGeneratorNode(int wordLength) {
        List<node> nodes = generateNodes(wordLength);
        System.out.println("Generated nodes: " + nodes);
        return Map.entry(nodes.get(0) /* grandchild */, nodes.get(nodes.size() - 1) /* father */);
    }

    public static List<node> generateNodes(int num) {
        List<node> nodeList = new ArrayList<>(50);
        if (num >= 2) {
            nodeList.add(new node(acceptedCombinations)); // Grandchild
            for (int i = 0; i < num - 1; i++) {
                nodeList.add(new node(acceptedCombinations, nodeList.get(nodeList.size() - 1), NodeType.CHILD));
            }
            nodeList.get(nodeList.size() - 1).setNodeType(NodeType.FATHER);
            return nodeList;
        } else {
            throw new ArrayIndexOutOfBoundsException("Bound must be greater than/equal to 2!");
        }
    }

}