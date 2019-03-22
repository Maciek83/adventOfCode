package adventOfCode.year2015;

import org.apache.commons.text.StringEscapeUtils;

import java.io.*;

public class day8
{
    public static void main(String[] args) throws IOException
    {

        File file = new File("src/main/resources/year2015/input2015day8.txt");
        FileReader fileReader = new FileReader(file);
        BufferedReader bf = new BufferedReader(fileReader);
        int totalLength = 0, codeLength = 0, part2Length = 0;


        String line = null;

        while ((line = bf.readLine()) != null)
        {
            totalLength += line.length();
            codeLength += line.length();
            part2Length += StringEscapeUtils.escapeJava(line).length() + 2;
            int offset = 0;
            while (offset < line.length())
            {
                int curChar = line.codePointAt(offset);
                offset += Character.charCount(curChar);
                if (curChar == 34)
                { // if quotation
                    codeLength--;
                }
                else if (curChar == 92)
                {  // if slash
                    codeLength--;
                    curChar = line.codePointAt(offset);
                    if (curChar == 120)
                    { // if hex
                        codeLength -= 2;
                        offset += Character.charCount(curChar);
                    }
                    else
                    {
                        offset += Character.charCount(curChar);
                    }
                }


            }
        }
        System.out.println("Part 1: Total length: " + totalLength + " Code Length: " + codeLength + " Answer: " + (totalLength - codeLength));
        System.out.println("Part 2: Encoded Length: " + part2Length + " Total length: " + totalLength + " Answer: " + (part2Length - totalLength));
    }


}