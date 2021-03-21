package Encryptors;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TranspositionEncryptorTest {

    String key = "securit";
    TranspositionEncryptor encryptor = new TranspositionEncryptor(key);

    @Test
    void encrypt() {
        // given
        String message = "weneedmoresnownow";
        String message2 = "something nice";

        String expectedCipher = "newerodocenbwonmwdesa";
        String expectedCipher2 = "m oghctisnieen";

        assertEquals(expectedCipher, encryptor.encrypt(message));
        assertEquals(expectedCipher2, encryptor.encrypt(message2));
    }

    @Test
    void decrypt() {
        // given
        String cipher = "newerodocenbwonmwdesa";
        String cipher2 = "m oghctisnieen";

        String expectedPlain = "weneedmoresnownowabcd";
        String expectedPlain2 = "something nice";
        assertEquals(expectedPlain, encryptor.decrypt(cipher));
        assertEquals(expectedPlain2, encryptor.decrypt(cipher2));
    }
}