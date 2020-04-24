// import File IO
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.DataOutputStream;

// import Exceptions
import java.io.FileNotFoundException;
import java.io.IOException;

import java.math.BigInteger;


public class RSAEncrypt
{
    // Convert the message into BigInteger
    public static BigInteger convertMessage(String filename)
        throws FileNotFoundException, IOException
    {
        // The file to read in
        File inFile = new File(filename);

        // The file to read the bytes from
        FileInputStream stream = new FileInputStream(inFile);
        byte[] byteData = new byte[(int)inFile.length()];   // Array to hold all bytes
        stream.read(byteData);  // Read bytes from the file
        stream.close(); // Close the input stream

        return (new BigInteger(byteData));
    }


    public static void main(String[] args)
        throws FileNotFoundException, IOException
    {
        if (args.length != 3)
        {
            System.out.println(
                "Please provide 3 arguments. " + args.length
                + ((args.length < 2)? " argument" : "arguments" ) + " found\n\n"
                + "Format: java RSAEncrypt [file_to_encrypt] [destination] [key_dat_file]\n"
                + "\te.g. java RSAEncrypt Path/address.txt Path/address.enc "
                + "data/key.dat\n"
                + "\t\tFirst argument \"Path/address.txt\" is the file to encrypt\n"
                + "\t\tSecond argument \"Path/address.enc\" is the destination file\n"
                + "\t\tThird argument \"data/key.dat\" is the key data file.\n"
                );

            System.exit(1);     // Exit the program with an error
        }

        // Used to check if there are anything wrong with the files
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

        String fileToEncrypt = args[0]; // Name of file to encrypt
        String dstOutputFile = args[1]; // Name of file to create
        String dataFile = args[2];      // Data file containing N, e, d

        RSA encrypt = new RSA(dataFile);

        BigInteger message = convertMessage(fileToEncrypt);

        // If the # of bytes of the message is greater than # of bytes of N
        // Give an error, # of bytes of the message should be less than # bytes
        // of N
        if ((message.toByteArray()).length > ((encrypt.modulusN).toByteArray()).length - 1)
        {
            System.out.println(
                "The number of bytes in the file \"" + fileToEncrypt + "\" exceeds"
                + " " + (((encrypt.modulusN).toByteArray()).length - 1) + " bytes.\n"
                + "File \"" + fileToEncrypt + "\" contains "
                + (message.toByteArray()).length + " bytes.\n\n"
                + "Please choose a file that contains less than or equal to "
                + (((encrypt.modulusN).toByteArray()).length - 1) + " bytes.\n"
            );

            System.exit(1);     // Exit with an error
        }

        File outFile = new File(dstOutputFile);
        DataOutputStream oStream = new DataOutputStream(new FileOutputStream(outFile));

        // Cipher Text = m^e mod N  where m (the message), e (encryptKey) is the
        // exponent, and N (the modulusN)
        BigInteger cipherText =
            (message).modPow(encrypt.encryptKey, encrypt.modulusN);

        // Write cipherText to the encrypted file
        oStream.write(cipherText.toByteArray());
        oStream.close();
    }
}
