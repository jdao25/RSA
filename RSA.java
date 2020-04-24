// Importing File IO
import java.io.File;
import java.io.FileInputStream;
import java.io.DataInputStream;

// Import Exceptions
import java.io.FileNotFoundException;
import java.io.IOException;

import java.math.BigInteger; // Since we are dealing with large integers
import java.util.Random;    // For the random prime numbers


public class RSA
{
    private BigInteger largePrimeP;
    private BigInteger largePrimeQ;
    private BigInteger phi;
    public BigInteger modulusN;
    public BigInteger encryptKey;
    public BigInteger decryptKey;


    // Constructor
    // This contructor will be used for the key generation
    public RSA()
    {
        largePrimeP = findPrime();
        largePrimeQ = findPrime();
        phi = calculatePhi();
        modulusN = calculateN();
        encryptKey = BigInteger.valueOf(65537); // By Default
        decryptKey = encryptKey.modInverse(phi);
    }


    // Constructor
    // This constructor will be used for RSAEncrypt RSADecrypt
    public RSA(String keyFile)
        throws FileNotFoundException, IOException
    {
        File file = new File(keyFile);
        DataInputStream iStream = new DataInputStream(new FileInputStream(file));

        // Extracting the N, e, and d from the .dat file
        // N (modulusN), e (encryptKey), and d (decryptKey)
        for (int idx = 1; idx <= 3; idx++)
        {
            int arrLength = iStream.readInt();   // Used to count how many bytes to read
            byte[] byteData = new byte[arrLength];

            for (int i = 0; i < arrLength; i++)
                byteData[i] = iStream.readByte();

            if (idx == 1)
                this.modulusN = new BigInteger(byteData);
            else if (idx == 2)
                this.encryptKey = new BigInteger(byteData);
            else
                this.decryptKey = new BigInteger(byteData);
        }

        iStream.close();
    }


    // Get random prime number
    public BigInteger findPrime()
    {
        /*
            Find a random large Prime number between 2^511 and 2^513
        */

        // Create instance of Random class
        Random rand = new Random();

        // Define the limits min = 2^511, max = 2^513
        BigInteger maxLimit = new BigInteger("2").pow(513);
        BigInteger minLimit = new BigInteger("2").pow(511);
        int maxBitLength = maxLimit.bitLength();    // 514 bits

        // This will be the large prime we will return
        // Since maxBitLength = 514, max file size to encrypt is 129 bytes
        // Increase the the maxBitLength to increase the encrypt file size
        BigInteger largePrime = BigInteger.probablePrime(maxBitLength, rand);

        return largePrime;
    }


    public BigInteger calculateN()
    {
        /*
            N is calculated by multiplying two large prime numbers
        */

        return largePrimeP.multiply(largePrimeQ);
    }


    public BigInteger calculatePhi()
    {
        /*
            phi is (p - 1)*(q - 1) where p and q are two large prime numbers
        */

        return (largePrimeP.subtract(BigInteger.valueOf(1))).multiply(
            largePrimeQ.subtract(BigInteger.valueOf(1)));
    }
}
