// import File IO
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.DataOutputStream;

// import Exceptions
import java.io.FileNotFoundException;
import java.io.IOException;

import java.math.BigInteger;


public class RSADecrypt
{
    public static void main(String[] args)
        throws FileNotFoundException, IOException
    {
        if (args.length != 3)
        {
            System.out.println(
                "Please provide 3 arguments. " + args.length
                + ((args.length < 2)? " argument" : "arguments" ) + " found\n\n"
                + "Format: java RSADecrypt [file_to_decrypt] [destination] [key_dat_file]\n"
                + "\te.g. java RSADecrypt Path/address.enc Path/address.txt "
                + "data/key.dat\n"
                + "\t\tFirst argument \"Path/address.enc\" is the file to decrypt\n"
                + "\t\tSecond argument \"Path/address.txt\" is the destination file\n"
                + "\t\tThird argument \"data/key.dat\" is the key data file.\n"
                );

            System.exit(1);     // Exit the program with an error
        }

        boolean errorFound = false;

        for (int idx = 0; idx <= 2; idx++)
        {
            // Filename args[0], args[1], and args[2]
            String filename = args[idx];
            FileVerification checkFile = new FileVerification(filename);

            if (!checkFile.isValidPath())
            {
                System.out.println(
                    "For \"" + args[idx] + "\" the path \"" + checkFile.path + "\""
                    + " does not exist.\n");

                errorFound = true;
            }
        }

        // If there was an error checking the files
        if (errorFound)
            System.exit(1);

        String fileToDecrypt = args[0]; // Name of file to encrypt
        String dstOutputFile = args[1]; // Name of file to create
        String dataFile = args[2];      // Data file containing N, e, d

        RSA decrypt = new RSA(dataFile);

        File encryptFile = new File(fileToDecrypt);
        FileInputStream stream = new FileInputStream(encryptFile);

        byte[] cipherBytes = new byte[(int)encryptFile.length()];
        stream.read(cipherBytes);   // Store the cipherText Bytes in a byte[]
        stream.close();

        // Convert the cipherText bytes to a BigInteger
        BigInteger cipherText = new BigInteger(cipherBytes);

        // Decrypt = c^d mod N where c (the cipherText), d (the decryptKey) is the
        // exponent, and N (modulusN)
        BigInteger decryptedMessage =
            cipherText.modPow(decrypt.decryptKey, decrypt.modulusN);

        File decryptedFile = new File(dstOutputFile);
        DataOutputStream oStream = new DataOutputStream(new FileOutputStream(decryptedFile));

        oStream.write(decryptedMessage.toByteArray());  // Write the decrypted message
        oStream.close();
    }
}
