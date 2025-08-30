// Dhruv Aravind
// 10/9/2024
// CSE 123
// P0: Ciphers
// TA: Laura Khotemlyansky

// Class that uses a Caesar Shift cipher to encrypt or decrypt a code.

public class CaesarShift extends Substitution {

    /* Behavior:
     *  - Uses the shift value to create a new shifter and then sets the object's shifter to    
     *    this new one.
     * Parameters:
     *  - int shift: The value that the shifter is going to be moved
     * Returns:
     *  - N/A
     * Exceptions:
     *  - Throws an IllegalArgumentException if the shift value is less than or equal to 0
     */
    public CaesarShift(int shift) {
        setShift(shift);
    }
    
    /* Behavior:
     *  - Uses the shift value to create a new shifter and then sets the object's shifter to 
     *    this new one.
     * Parameters:
     *  - int shift: The value that the shifter is going to be moved
     * Returns:
     *  - N/A
     * Exceptions:
     *  - Throws an IllegalArgumentException if the shift value is less than or equal to 0
     */
    private void setShift(int shift) {
        if (shift <= 0) {
            throw new IllegalArgumentException();
        }
        String newShifter = "";
        for (int i = 0; i < TOTAL_CHARS; i++) {
            int pos = (i + shift) % TOTAL_CHARS;
            newShifter += (char) (MIN_CHAR + pos);
        }
        super.setShifter(newShifter); 
    }         
}