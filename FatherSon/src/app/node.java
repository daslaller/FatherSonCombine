package app;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicReference;

public class node {

    private NodeType nodeType;

    private String endian;
    private String primian;

    AtomicReference<String> currentSymbol = new AtomicReference<>();
    BlockingDeque<String> acceptedSymDeque = new LinkedBlockingDeque<>();
    private String[] acceptedCombinations;

    public boolean allowsIncrement = true;
    private node relationNode;

    node(String[] acceptedCombinations, node relationNode, NodeType thisNodeType) {

        this.relationNode = relationNode;
        // Objects.requireNonNull(relationNode, "Relative cant be null!");

        this.acceptedCombinations = Objects.requireNonNull(acceptedCombinations);
        initStartEnd();
        setNodeType(thisNodeType);
    }

    node(String[] acceptedCombinations) {
        this(acceptedCombinations, null, NodeType.GRANDCHILD);
        // setNodeType(NodeType.GRANDCHILD);

    }

    public String increment() {

        if (!symbol().equals(endian)) {

            String newC = acceptedSymDeque.poll();
            set(newC);
            if (nodeType != NodeType.GRANDCHILD) {
                return symbol() + relationNode.chilSym();
                // } else {
                // return symbol();
            }
        } else if (nodeType != NodeType.GRANDCHILD) {
            relationNode.increment();
            initStartEnd();
        } else {
            allowsIncrement = false;
        }
        return symbol();
    }

    public void set(String newS) {
        while (!currentSymbol.compareAndSet(currentSymbol.get(), newS))
            ;
    }

    public void initStartEnd() {
        this.acceptedSymDeque = new LinkedBlockingDeque<>(Arrays.asList(acceptedCombinations));
        this.endian = Objects.requireNonNull(acceptedSymDeque.peekLast());
        this.primian = Objects.requireNonNull(acceptedSymDeque.pollFirst());

        set(primian);
    }

    public String chilSym() {
        return nodeType != NodeType.GRANDCHILD ? symbol() + relationNode.chilSym() : symbol();
    }

    public String symbol() {
        return currentSymbol.get() != null ? currentSymbol.get() : "";
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    public void setNodeType(NodeType newNodeType) {
        this.nodeType = newNodeType;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return nodeType.toString();
    }
    
}

enum NodeType {
    FATHER, CHILD, GRANDCHILD
}