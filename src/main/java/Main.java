import Cryptosystems.PlayFair;
import Cryptosystems.RSA;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
//        byte[] bytes = "ahmed".getBytes(StandardCharsets.UTF_8);
//        System.out.println(Arrays.toString(bytes));
//        BigInteger bi = new BigInteger(bytes);
//        System.out.println(bi);
//        System.out.println(Arrays.toString(bi.toByteArray()));

        RSA rsa = new RSA();
        String s = "AlohaThere";
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        System.out.println(Arrays.toString(bytes));

        byte[] encrypted = rsa.encrypt(bytes);
        System.out.println(encrypted.length);
        System.out.println("Encrypted Bytes: " + Arrays.toString(encrypted));
        System.out.println("Encrypted String: " + new String(encrypted));

        byte[] decrypted = rsa.decrypt(encrypted);
        System.out.println("Decrypted Bytes: " + Arrays.toString(decrypted));
        System.out.println("Decrypted String: " + new String(decrypted));

        /**Testing with sender and receiver*/

        RSA senderRSA = new RSA();
        RSA receiverRsa = new RSA();

        String message = "This is a message";
        // when sender wanna send a message to receiver use receiverRSA to encrypt it
        byte[] encryptedMessage = receiverRsa.encrypt(message.getBytes(StandardCharsets.UTF_8));

        // message delivered to receiver, he uses his own private key to decrypt it
        byte[] deliveredMessage = receiverRsa.decrypt(encryptedMessage);
        System.out.println(new String(deliveredMessage));


        /************************* Play Fair ***************************/
        PlayFair playFair = new PlayFair("Since By Man");

        String playFairCipher = playFair.encrypt("We Need Million");
        System.out.println(playFairCipher);

        String decodedPlayFair = playFair.decrypt(playFairCipher);
        System.out.println(decodedPlayFair);
    }
}
