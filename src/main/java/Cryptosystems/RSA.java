package Cryptosystems;

import java.math.BigInteger;
import java.util.Random;

public class RSA {
    private BigInteger p;
    private BigInteger q;
    private BigInteger n;
    private BigInteger z;
    private BigInteger E;
    private BigInteger d;
    private int bitLength = 1024; // could be 512, 2048
    public RSA() {
        p = BigInteger.probablePrime(bitLength, new Random());
        q = BigInteger.probablePrime(bitLength, new Random());
        n = p.multiply(q);
        z = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        // Choose an integer e such that 1 < e < z and gcd(e, z) = 1; that is, e and λ(n) are co-prime.
        E = BigInteger.probablePrime(bitLength / 2, new Random()); // working with multiples of 2 improves performance of generating prime numbers
        while (E.gcd(z).compareTo(BigInteger.ONE) > 0) { // return 1 if gcd(E, z) greater than One
            E = BigInteger.probablePrime(bitLength / 2, new Random());
        }
        d = E.modInverse(z);
    }

    public byte[] encrypt(byte[] message) {
        return (new BigInteger(message)).modPow(E, n).toByteArray();
    }

    public byte[] decrypt(byte[] cipher) {
        return (new BigInteger(cipher)).modPow(d, n).toByteArray();
    }

    // exposing public key
    public BigInteger getN() {
        return n;
    }

    public BigInteger getE() {
        return E;
    }
}
