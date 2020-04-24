// File IO
import java.io.File;
import java.io.FileOutputStream;
import java.io.DataOutputStream;

// importing Exceptions
import java.io.FileNotFoundException;
import java.io.IOException;

import java.math.BigInteger;


public class RSAGenKey
{
    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        // Check command line parameters
        // There should be one arugment pass
        if (args.length != 1)
        {
            // Print the error message
            System.out.println(
                "Please only provide one argument. Found " + args.length
                + ((args.length > 1)? " arguments" : " argument") + " found.\n\n"
                + "Format: java RSAGenKey [key_dat_file_to_create]\n"
                + "\te.g. java RSAGenKey data/key.dat\n"
                + "\t\twhere \"data/\" is the path to create the file "
                + "\"key.dat\"\n");

            System.exit(1); // Exit with an error
        }

        String fileToSave = args[0];
        FileVerification file = new FileVerification(fileToSave);

        // If the path doesn't exist then create the directory
        if (!file.isValidPath())
            (new File(file.getPath())).mkdir();

        RSA genKey = new RSA();

        File outputFile = new File(fileToSave);   // The file I'm writting to
        DataOutputStream oStream = new DataOutputStream(new FileOutputStream(outputFile));

        byte[] temp;

        for (int idx = 1; idx <= 3; idx++)
        {
            if (idx == 1)
                temp = (genKey.modulusN).toByteArray();
            else if (idx == 2)
                temp = (genKey.encryptKey).toByteArray();
            else
                temp = (genKey.decryptKey).toByteArray();

            // Array size will be used to separate the n, e, d in the .dat file
            oStream.writeInt(temp.length);

            oStream.write(temp); // Write the bytes to the .dat file
        }

        oStream.close();
    }
}
