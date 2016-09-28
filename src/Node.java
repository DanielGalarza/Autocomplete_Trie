/**
 * Created by danielgalarza on 9/22/16.
 *
 * This class describes a "node/leaf" in the tree.
 */

public class Node {

    char prefix;            /* Stores the leading prefix of a word, starting at root of the tree. */
    boolean isValidWord;    /* Let's us know whether or not we have reached a valid word. */
    int size;               /* Size of the list of references, starting at the root node */

    Node[] children;        /* Will contain references to child nodes that share the same prefix */

    /**
     * Node constructor.
     *
     * @param prefix    The letter belonging to this node/leaf.
     * @param size      The size of each tree.
     */
    public Node(char prefix, int size) {
        this.prefix = prefix;
        this.isValidWord = false;
        this.size = size;
        children = new Node[size];
    } //Node constructor

} //Node
