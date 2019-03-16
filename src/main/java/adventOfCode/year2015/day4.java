package adventOfCode.year2015;


import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class day4
{
    public static void main(String[] args) throws NoSuchAlgorithmException
    {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        int append = 1;

        while (true)
        {
            String toCheck = args[0]+append;
            messageDigest.update(toCheck.getBytes());

            byte[] digest = messageDigest.digest();

            String myHash = DatatypeConverter.printHexBinary(digest);

            if(myHash.startsWith("000000"))
            {
                System.out.println("The anwser is " + append);
                break;
            }

            append++;
        }
    }
}
