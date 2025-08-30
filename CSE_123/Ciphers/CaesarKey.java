// Dhruv Aravind
// 10/9/2024
// CSE 123
// P0: Ciphers
// TA: Laura Khotemlyansky

// This class is a Ceasar Key Cipher that uses a Ceasar Key to encrypt and decrypt a code 

public class CaesarKey extends Substitution {

    /* Behavior:
     *  - Takes the inputted key and uses it to create a shifter by moving the elements in the 
     *    key to the front of the shifter. Then sets the object's shifter with the new one
     * Parameter:
     *  - key: The letters that are going to the start of the shifter
     * Returns:
     *  - N/A
     * Exceptptions:
     *  - Key.length() == 0; If length of the key is 0 then IllegealArgumentException is thrown
     *  - key.charAt(i) > MAX_Char or < MIN_CHAR; If a character in key is outside encodable range
     *    then IllegalArgumentException is thrown
     *  - newKey.indexOf(key.charAt(i)) != -1; If there is a duplicate character in key then 
     *    IllegalArgumentException is thrown
     */
    public CaesarKey (String key) {
        setShift(key);
    }

    /* Behavior:
     *  - Takes the inputted key and uses it to create a shifter by moving the elements in the 
     *    key to the front of the shifter. Then sets the object's shifter with the new one
     * Parameter:
     *  - key: The letters that are going to the start of the shifter
     * Returns:
     *  - N/A
     * Exceptptions:
     *  - Key.length() == 0; If length of the key is 0 then IllegealArgumentException is thrown
     *  - key.charAt(i) > MAX_Char or < MIN_CHAR; If a character in key is outside encodable range
     *    then IllegalArgumentException is thrown
     *  - newKey.indexOf(key.charAt(i)) != -1; If there is a duplicate character in key then 
     *    IllegalArgumentException is thrown
     */
    private void setShift (String key) {
        if (key.length() == 0) {
            throw new IllegalArgumentException();
        }
        String newKey = "";
        for (int i = 0; i < key.length(); i++) {
            if (newKey.indexOf(key.charAt(i)) != -1 || key.charAt(i) < MIN_CHAR ||
                key.charAt(i) > MAX_CHAR) {
                throw new IllegalArgumentException();
            } 
        }

        String newShifter = key;
        for (int i = MIN_CHAR; i <= MAX_CHAR; i++) {
            if (newShifter.indexOf((char) i) == -1) {
                newShifter += (char) i;
            }
        }
        super.setShifter(newShifter);
    }
}