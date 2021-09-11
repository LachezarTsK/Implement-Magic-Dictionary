import java.util.Arrays;

public class MagicDictionary {

    TrieNode root;

    public MagicDictionary() {
        root = new TrieNode();
    }

    public void buildDict(String[] dictionary) {

        for (String word : dictionary) {
            TrieNode current = root;
            for (int i = 0; i < word.length(); i++) {
                char ch = word.charAt(i);
                if (current.branches[ch - 'a'] == null) {
                    current.branches[ch - 'a'] = new TrieNode();
                }
                current = current.branches[ch - 'a'];
            }
            current.isWord = true;
        }
    }

    public boolean search(String searchWord) {

        char[] word = searchWord.toCharArray();
        int charsToReplace = word.length;
        TrieNode current = root;
        int index = 0;

        while (index < charsToReplace) {
            char currentChar = word[index];
            if (checkWithReplacement(Arrays.copyOfRange(word, index, word.length), currentChar, current)) {
                return true;
            }
            if (current.branches[currentChar - 'a'] == null) {
                return false;
            }
            current = current.branches[currentChar - 'a'];

            /*
            Restoring the original character. The algorithm will work just fine without it, 
            it could be even a few nano seconds faster, but just for the sake of completeness.
             */
            word[index++] = currentChar;
        }

        //Impossible to find a word that has exactly one character replaced.
        return false;
    }

    public boolean checkWithReplacement(char[] word, char currentChar, TrieNode node) {

        for (char replacementChar = 'a'; replacementChar <= 'z'; replacementChar++) {

            if (replacementChar != currentChar) {

                /*
                Since the check is with replacement of characters, when 
                'current.branches[ch - 'a'] == null' the algorithm only
                breaks the loop, instead of terminating the method by returning 'false'.
                
                'wordBreakBeforeEndOfLoop' is implemented in order to avoid
                improper return of 'current.isWord = true'.
                 */
                boolean wordBreak_beforeEndOfLoop = false;
                TrieNode current = node;
                word[0] = replacementChar;

                for (int i = 0; i < word.length; i++) {
                    char ch = word[i];
                    if (current.branches[ch - 'a'] == null) {
                        wordBreak_beforeEndOfLoop = true;
                        break;
                    }
                    current = current.branches[ch - 'a'];
                }
                if (wordBreak_beforeEndOfLoop == false && current.isWord) {
                    return true;
                }
            }
        }
        return false;
    }
}

class TrieNode {

    final int CHARS_IN_ALPHABET = 26;
    boolean isWord;
    TrieNode[] branches;

    public TrieNode() {
        branches = new TrieNode[CHARS_IN_ALPHABET];
    }
}
