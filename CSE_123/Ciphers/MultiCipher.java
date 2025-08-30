// Dhruv Aravind
// 10/9/2024
// CSE 123
// P0: Ciphers
// TA: Laura Khotemlyansky

import java.util.*;

// Class that uses multiple ciphers to encode or decode a code

public class MultiCipher extends Cipher {
    private List<Cipher> ciphers; 

    /* Behavior: 
     *  - Constructor that sets the ciphers List with the provided input
     * Parameters: 
     *  - ciphersInput: A list containing different Ciphers objects
     * Returns:
     *  - N/A
     * Exceptions:
     *  - ciphersInput == null. If ciphersInput was not initialized than an
     *    IllegalArgumentException is thrown.
     */
    public MultiCipher(List<Cipher> ciphersInput) {
        if (ciphersInput == null) {
            throw new IllegalArgumentException();
        }
        this.ciphers = ciphersInput;
    }
    
    /* Behvior:
     * - Uses the multiple cipher objects recorded in the ciphers list to encrypt a inputted code.
     * Parameter:
     *  - code: The code that is going to be encrypted
     * Returns:
     *  - String: Returns the encoded String
     * Exceptions:
     *  - N/A
     */

    public String encrypt(String code) {
        for (Cipher cipher : ciphers) {
            code = cipher.encrypt(code);
        }
        return (code);
    }
    
    /* Behvior:
     * - Uses the multiple cipher objects recorded in the ciphers list to encrypt a inputted code.
     * Parameter:
     *  - code: The code that is going to be decrypted
     * Returns:
     *  - String: Returns the decrypted String
     * Exceptions:
     *  - N/A
     */
    public String decrypt(String code){
        for (int i = ciphers.size() - 1; i >= 0; i--) {
            code = ciphers.get(i).decrypt(code);
        }
        return (code);
    }
}