package adventOfCode.year2015;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class day5
{
    public static List<String> findNiceStrings(List<String> notNiceStrings)
    {

        List<String> niceStrings = new ArrayList<>();

        for (String s : notNiceStrings)
        {
            if (getNumberOfVowels(s) == 3
                    && haveTwoSameLettersInRow(s) && !haveSpecificLetters(s))
                niceStrings.add(s);
        }

        return niceStrings;
    }

    public static List<String> findNiceStrings2(List<String> notNiceStrings)
    {
       List<String> niceStrings = new ArrayList<>();

        for (String s: notNiceStrings)
        {
            if (haveTwiceXX(s) && haveOnceXYX(s))
                niceStrings.add(s);
        }

        return niceStrings;
    }

    private static boolean haveTwiceXX(String data)
    {
        Pattern pattern = Pattern.compile("(\\w)(\\w)(\\w*)\\1\\2+");

        Matcher matcher = pattern.matcher(data);


        return matcher.find();
    }

    private static boolean haveOnceXYX(String data)
    {
        Pattern pattern = Pattern.compile("(\\w)(\\w)\\1");

        Matcher matcher = pattern.matcher(data);

        return matcher.find();
    }

    private static int getNumberOfVowels(String data)
    {
        Pattern pattern = Pattern.compile("[aeiou]");

        Matcher matcher = pattern.matcher(data);

        int numberOfVowels = 0;

        while (matcher.find())
        {
            numberOfVowels++;

            if (numberOfVowels == 3)
                break;
        }

        return numberOfVowels;
    }

    private static boolean haveTwoSameLettersInRow(String data)
    {
        Pattern pattern = Pattern.compile("(\\w)\\1");

        Matcher matcher = pattern.matcher(data);

        return matcher.find();
    }

    private static boolean haveSpecificLetters(String data)
    {
        Pattern pattern = Pattern.compile("(ab|cd|pq|xy)");

        Matcher matcher = pattern.matcher(data);

        return matcher.find();
    }

    public static void main(String[] args)
    {
        File file = new File("src/main/resources/year2015/input2015day5.txt");

        try
        {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            List<String> list = bufferedReader.lines().collect(Collectors.toList());

            int answer = findNiceStrings(list).size();
            int answer2 = findNiceStrings2(list).size();

            System.out.println(answer);
            System.out.println(answer2);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }


    }
}
