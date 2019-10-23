package app;

import java.util.ArrayList;
import java.util.List;

public class App {
    static String[] acceptedCombinations = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o",
            "p", "q", "r", "s", "t", "u", "v", "w", "z", "y", "x", "å", "ä", "ö" };

    public static void main(String[] args) throws Exception {
        // node grandChild = new node(acceptedCombinations);
        // node child = new node(acceptedCombinations, grandChild, NodeType.CHILD);
        // node child2 = new node(acceptedCombinations, child, NodeType.CHILD);
        // node father = new node(acceptedCombinations, child2, NodeType.FATHER);

        List<node> nodes = generateNodes(80);
        System.out.println("Generated nodes: " + nodes);

        node grandChild = nodes.get(0);
        node father = nodes.get(nodes.size() - 1);

        while (grandChild.allowsIncrement) {
            System.out.println(father.increment());
        }
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