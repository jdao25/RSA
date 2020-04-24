import java.io.File;


/*
    The purpose of this class is to check if the user entered valid file paths
    when using RSA encrypting program
*/


public class FileVerification
{
    public String file;
    public String path;
    public String filename;


    public FileVerification(String file)
    {
        this.file = file;
        this.filename = getFilename();
        this.path = getPath();
    }


    // From the file, get the filename
    public String getFilename()
    {
        // Will be used to hold the filename
        String filename = new String("");

        // If the filename contains a .txt, .pdf, etc.
        if (file.contains("."))
        {
            filename = (file.contains("\\")) ?
                file.substring(file.lastIndexOf('\\') + 1, file.lastIndexOf('.'))
                : file.substring(file.lastIndexOf('/') + 1, file.lastIndexOf('.'));
        }
        else
        {
            filename = (file.contains("\\")) ?
                file.substring(file.lastIndexOf('\\') + 1)
                : file.substring(file.lastIndexOf('/') + 1);
        }

        return filename;
    }


    // From the file, get the path
    public String getPath()
    {
        // From the filename extract the path
        String path = new String("");

        if (file.contains("\\"))
            path = file.substring(0, file.lastIndexOf('\\') + 1);
        else if (file.contains("/"))
            path = file.substring(0, file.lastIndexOf('/') + 1);

        return path;
    }


    // Check if the path is a valid path
    public Boolean isValidPath()
    {
        return (new File(path)).exists();
    }


    // Check if the filename exists
    public Boolean isFileExist()
    {
        return (new File(file)).exists();
    }
}
