import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author Daniel Galarza on 9/22/16.
 *
 * Purpose: The purpose of this class is to facilitate a tree full of words(from a dictionary).
 * (created from a dictionary file)
 *
 *
 *
 * References: https://www.youtube.com/watch?v=Xt2ouYSxWkw
 */

public class DictionaryTree {

    private static final int SIZE_OF_TREE = 26;     /* Number of children nodes referenced by the root (a - z = 26) */

    static Node root;                               /* The root of the tree */
    static int numberOfWordsWeHaveSeen;             /* Keeps track of the number of words we've seen */


    /**
     * DictionaryTree constructor, which creates the tree root.
     */
    public DictionaryTree() {
        root = new Node('\0', SIZE_OF_TREE);       /* Creating the root node of the dictionary. */
    }


    /**
     * This method allows the insertion of any word, starting with a letter a-z only, into the dictionary tree.
     *
     * @param word  The word that must to go in the tree.
     */
    public static void addWordToTree(String word) {

        Node current = root;
        int index;
        char letter;

        /* Analyze each letter in the word and put it in the tree */
        for(int i = 0; i < word.length(); i++) {

            /* 'a' has value of 97, ex: 'c' = 99, 99-97 = 2, so 'c' is in position 2 of array, referenced by the current node */
            letter = word.charAt(i);
            index = letter - 'a';

            /* if child node doesn't exists, create a new node/child for the parent node. */
            if(current.children[index] == null) {
                current.children[index] = new Node(letter, SIZE_OF_TREE);
            }//if

            /* Move to next child. */
            current = current.children[index];
        } //for

        /* End of word, set flag for this node to signal that it is a valid word, up to this point/node */
        current.isValidWord = true;
    }//addWordToTree


    /**
     * This method adds every word from a text file, considering that there is one word per line, and adds it to the a
     * new tree.
     *
     * @param dictionaryFile    Name of the file to read from.
     */
    public static void addDictionaryFileToTree(String dictionaryFile) {

        try {

            /* File we want to open */
            File file = new File(dictionaryFile);
            Scanner fileReader = new Scanner(file);

            String word;

            /* Reading the file, one line at a time */
            while(fileReader.hasNextLine()) {
                word = fileReader.next().toLowerCase();
                addWordToTree(word);
            }//while
        }//try

        catch (IOException e) {
            e.printStackTrace();
            System.out.print("Cannot open file: " + dictionaryFile);
        }//catch
    }//addDictionaryFileToTree


    /**
     * This method is in charge of analyzing the prefix of a possible word. If the prefix is a valid prefix, it calls
     * the findPossibleWordHelper() method in order actually find possible words starting with the prefix. If the prefix
     * is NOT a valid, it will return.
     *
     * @param   prefix      Prefix of the words we are trying to find.
     * @param   textArea    Text area used to display valid words.
     */
    public static void findPossibleWords(String prefix, JTextArea textArea) {

        char letter;

        /* Index of children array */
        int index;

        Node current = root;

        /* Traverses the tree, up to the end of the prefix*/
        for(int i = 0; i < prefix.length(); i++) {

            /* 'a' has value of 97, ex: 'c' = 99, 99-97 = 2, so 'c' is in position 2 of array, referenced by the current node */
            letter = prefix.charAt(i);
            index = letter - 'a';

            /*  if current node doesn't exist, then prefix does NOT create a valid word. */
            if (current == null) {
                textArea.append("'" + prefix + "'" + " is not in the dictionary");
                return;
            }//if

            /* Move to next child. */
            current = current.children[index];
        }//for

        /* We've seen "0" words up to this point */
        numberOfWordsWeHaveSeen = 0;

        /* An array that will contain letters which make up a valid word in the dictionary */
        char[] letterList = new char[40];

        /* Traverses the tree, in order to access everything after the prefix (its children) */
        findPossibleWordsHelper(current, letterList, 0, prefix, textArea);  //maybe send current.children
    }//findPossibleWords


    /**
     *  This method is the helper of the findPossibleWords() method. It recursively searches the tree for possible words
     *  starting with the prefix, which is passed in. (Searches from left to right.)
     *
     * @param rootPrefix        prefix of possible words we are trying to find.
     * @param lettersInWord     Array of letters that make up a valid word.
     * @param depth             Depth of root node. Should always be 0.
     * @param prf               Prefix of words we are trying to find.
     * @param textArea          Text area used to display valid words.
     */
    public static void findPossibleWordsHelper(Node rootPrefix, char[] lettersInWord, int depth, String prf, JTextArea textArea)
    {
        Node current = rootPrefix;
        String prefix = prf;

        /* If a node is empty, go back to parent node, and then go to next child node... */
        if (current == null) {
            return;
        }//if

        /* if current node exists(isn't null), then search its children */
        for (int i = 0; i < current.children.length; i++) {
            lettersInWord[depth] = current.prefix;
            findPossibleWordsHelper(current.children[i], lettersInWord, depth + 1, prefix, textArea);
        }//for

        /* If the current node has the 'isValidWord' flag on, display it it */
        if (current.isValidWord) {

            /* We've found a word, so add it to the total of words we've seen */
            numberOfWordsWeHaveSeen++;

            /* if we've found 10 or less, valid words... */
            if(numberOfWordsWeHaveSeen <= 10) {

                /* if the prefix is only one char, just build the word using what's in lettersInWord[] array */
                if (prefix.length() == 1) {

                    for (int j = 0; j <= depth; j++) {
                        textArea.append(Character.toString(lettersInWord[j]));
                    }//for

                    textArea.append("\n");
                }

                /* if the prefix's length is longer than one, then we want to concatenate prefix with everything after index 0  */
                else if (prefix.length() > 1) {

                    textArea.append(prefix);

                    for (int j = 1; j <= depth; j++) {
                        textArea.append(Character.toString(lettersInWord[j]));
                    }//for

                    textArea.append("\n");
                }//if-else
            }//if
        }//if
    }//traverseTree

}//DictionaryTree
