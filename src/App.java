import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by danielgalarza on 9/25/16.
 *
 * This class is responsible for initializing the program, with GUI and Dictionary file.
 *
 */
public class App {

    private JTextField textField;
    private JPanel mainPanel;
    private JTextArea textArea;

    static String FILE = "src/dictionary.txt";  /* Name of dictionary file */
    static DictionaryTree tree;
    static String word = "";                    /* Word we display in the text area */


    /**
     *  App constructor.
     */
    public App() {

        textField.addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {
                renderAutoCompleteResults(e, textArea, textField);
                super.keyTyped(e);
            }//keyTyped
        });
    }//App Constructor


    /**
     * This method takes care of the input from the user and executes the appropriate methods to run the autocomplete
     * tree.
     *
     * @param e             The key event to process.
     * @param textArea      The text area to print results to.
     * @param textField     the text field that takes user input.
     */
    public static void renderAutoCompleteResults(KeyEvent e, JTextArea textArea, JTextField textField) {

        word = word + Character.toString(e.getKeyChar());
        textArea.setText("");

        /* Set the word to whatever is in the text field when the delete/backspace key is pressed. */
        if(e.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE) {
            word = textField.getText();

            /* Fix so that app doesn't crash when we hit the delete/backspace when the text field is empty */
            innerloop:
            if(word.isEmpty()) {
                break innerloop;
            } else {
                String[] parts = word.split(" ");           // splitting the string at the spaces
                String lastWord = parts[parts.length - 1];  // we want the last word of the string in the text-box
                word = lastWord;                            // Initializes 'word' to the last word so that we have a valid search in the tree
                tree.findPossibleWords(lastWord, textArea);
            }
        }

        /* Set 'word' to empty string when the 'space' key is pressed, so that we may do a new search with the last
         * word in the text box */
        else if(e.getExtendedKeyCode() == KeyEvent.VK_SPACE) {
            word = "";
        }

        else {
            tree.findPossibleWords(word, textArea);
        }//if-else

    }//renderAutoCompleteResults


    /**
     * This method takes care of setting up the GUI
     */
    public static void setUpFrame() {
        JFrame frame = new JFrame("Dictionary Tree");
        frame.setContentPane(new App().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(500, 500, 400, 500);
        frame.setVisible(true);
    }//setUpFrame


    /**
     * Main method.
     *
     * @param args
     */
    public static void main(String[] args) {
        setUpFrame();
        tree = new DictionaryTree();
        tree.addDictionaryFileToTree(FILE);
    }//main
}//App
