package main;

/**
 * <h1>Separates string to be calculated</h1>
 * <p>
 * <b>Note:</b> This class is only intended to be used by Calculator
 *
 * @author Ray Zhu
 * @since 2019-02-10
 */
final class Separator {

    /**
     * Removes whitespace in a string
     * 
     * @param string the string to be manipulated
     * @return the string without whitespace
     */
    private static String removeWhiteSpace(String string) {
        return string.replaceAll("\\s+", "");
    }

    /**
     * Checks if character is '+' or '-'
     * 
     * @param character the character to be checked
     * @return if character is a sign
     */
    private static boolean isSign(char character) {
        return character == '+' || character == '-' ? true : false;
    }

    /**
     * Combines two signs
     * 
     * @param first the first sign to be checked
     * @param second the second sign to be checked
     * @return the sign combined
     */
    private static char combineSign(char first, char second) {
        return (first == '+') ? (second == '+' ? '+' : '-') : (second == '+' ? '-' : '+');
    }

    /**
     * Combines signs (- and +) together
     * 
     * @param original The string to be manipulated
     * @return Manipulated string
     */
    public static String combineSigns(String original) {
        original = removeWhiteSpace(original);
        char[] manipulated = new char[original.length()];
        char[] originalCharArray = original.toCharArray();
        char signToBePut = '+';
        int length = 1;

        for (int i = 0; i < originalCharArray.length; i++) {

            char character = originalCharArray[i];
            boolean isSign = isSign(character);
            boolean nextCharIsSign;
            boolean lastCharWasSign;

            if (originalCharArray.length < i+2) {
                nextCharIsSign = false;
            } else {
                nextCharIsSign = isSign(originalCharArray[i+1]);
            }

            if (i == 0) {
                lastCharWasSign = false;
            } else {
                lastCharWasSign = isSign(originalCharArray[i-1]);
            }

            if (isSign) {
                if (nextCharIsSign) {
                    signToBePut = combineSign(signToBePut, originalCharArray[i + 1]);
                } else if (!lastCharWasSign) {
                    manipulated[length - 1] = character;
                } else {
                    manipulated[length - 1] = signToBePut;
                }
            } else {
                signToBePut = originalCharArray[i + 1];
                manipulated[length - 1] = character;
            }

            length += 1;
        }
        return new String(manipulated);
    }
}