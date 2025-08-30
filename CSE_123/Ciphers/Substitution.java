// Dhruv Aravind
// 10/9/2024
// CSE 123
// P0: Ciphers
// TA: Laura Khotemlyansky


import java.util.*;

//  This class is a Substitution Cipher. It takes in a shifter and uses that to encrypt or
//  decrypt a code.

public class Substitution extends Cipher {
    private String shifter;

    // Sets the shifter to null
    public Substitution () {
        this.shifter = null;
    }

    /* Behavior
     * - Sets the shifter of the object to the inputted String
     * Parameters:
     *  - shifter: The string that is going to be the shifter
     * Returns:
     *  - N/A
     * Exceptions:
     *  - If the length of shifter is not equal to the total encodable characters, 
     *    or if a character in shifter falls out of the encodable range or if there are
     *    duplicte character: An IllegalArgumentException is thrown.
     */
    public Substitution (String shifter) {
        setShifter(shifter);
    }

    /* Behavior: 
     *  - This method sets the shifter of this class to the inputted shifter
     * Parameters:
     *  - shifter: The shifter that is going to be used to decode and encode
     * Returns: 
     *  - N/A
     * Exceptions:
     *  - If the length of shifter is not equal to the total encodable characters, 
     *    or if a character in shifter falls out of the encodable range or if there are
     *    duplicte character: An IllegalArgumentException is thrown.
     * 
     */
    public void setShifter (String shifter) {
        if (shifter.length() != TOTAL_CHARS) {
            throw new IllegalArgumentException();
        }

        List<Character> chars = new ArrayList<Character>();
        for (int i = 0; i < shifter.length(); i++) {
            char c = shifter.charAt(i);
            if (c < MIN_CHAR || c > MAX_CHAR || chars.contains(c)){
                throw new IllegalArgumentException();
            }
            chars.add(c);
        }
        this.shifter = shifter;
    }

    /* Behavior: 
     *  - Encrypts a code by first checking if the character is in the shifter's range. If it is
     *    then the character of the code is switched with the respective character in the shifter.
     *    If it is not in the shifter's range the character is left as is and not changed.
     * Parameters:
     *  - code: The code that the user wants to be encrypted
     * Returns:
     *  - String: The new code after it has been encrypted by the shifter.
     * Exceptions:
     *  - Shifter == null: If the shifter has not been initialized than throws an 
     *    IllegalStateException 
     * 
     */
    public String encrypt(String code) {
        if (this.shifter == null) {
            throw new IllegalStateException();
        }
        String newCode = "";
        for (int i = 0; i < code.length(); i++) {
            char currChar = code.charAt(i);
            if (currChar < MIN_CHAR + this.shifter.length()) {
                newCode += this.shifter.charAt(currChar - MIN_CHAR);
            } else {
                newCode += currChar;
            }
        }
        return (newCode);
    }
    
    // Returns the shifter
    public String getShifter() {
        return shifter;
    }


    /* Behavior: 
     *  - Decrypts a code by first checking if the character is in the shifter. If it is
     *    then the character of the code is switched with the character the shifter aligns with.
     *    If it is not in the shifter the character is left as is and not changed.
     * Parameters:
     *  - code: The code that is going to be decrypted
     * Returns:
     *  - String: The new code that has been decrypted
     * Exceptions:
     *  - Shifter == null: If the shifter has not been initialized than throws an 
     *    IllegalStateException 
     * 
     */
    public String decrypt(String code) {
        if (this.shifter == null) {
            throw new IllegalStateException();
        }
        String newCode = "";
        for (int i = 0; i < code.length(); i++){
            char currChar = code.charAt(i);
            if (shifter.indexOf(currChar) >= 0) {
                newCode += (char) (MIN_CHAR + shifter.indexOf(currChar));
            } else {
                newCode += currChar;
            }
        }
        return (newCode);
    }
}