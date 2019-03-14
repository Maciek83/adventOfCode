package adventOfCode.year2015;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class day2
{
    private static int countPaperSquareFeet(List<String> data)
    {
        int totalCount = 0;

        for (String d: data)
        {
            String[] dimensions = d.split("x");

            int length = Integer.parseInt(dimensions[0]);
            int width = Integer.parseInt(dimensions[1]);
            int height = Integer.parseInt(dimensions[2]);

            int firstSurface = length*width;
            int secondSurface = width*height;
            int thirdSurface = height*length;

            int smallestSurface = 0;

            if (firstSurface<=secondSurface && firstSurface<=thirdSurface)
                smallestSurface = firstSurface;
            if (secondSurface<=firstSurface && secondSurface<=thirdSurface)
                smallestSurface = secondSurface;
            if (thirdSurface<=firstSurface && thirdSurface<=secondSurface)
                smallestSurface = thirdSurface;

            int surface = 2*firstSurface + 2*secondSurface + 2*thirdSurface + smallestSurface;

            totalCount += surface;
        }

        return totalCount;
    }

    private static int countRibbonSquareFeet(List<String> data)
    {
        int totalCount = 0;

        for (String d: data)
        {
            String[] dimensions = d.split("x");

            int length = Integer.parseInt(dimensions[0]);
            int width = Integer.parseInt(dimensions[1]);
            int height = Integer.parseInt(dimensions[2]);

            List<Integer> integerList = new ArrayList<>();
            integerList.add(length);
            integerList.add(width);
            integerList.add(height);

            Collections.sort(integerList);


            int surface1 = integerList.get(0) * 2 + integerList.get(1) * 2;
            int surface2 = length* width * height;

            totalCount += surface1 + surface2;
        }

        return totalCount;
    }

    public static void main(String[] args)
    {
        File file = new File("src/main/resources/year2015/input2015day2.txt");
        List<String> data = new ArrayList<>();
        try
        {
            FileReader fileReader = new FileReader(file);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            bufferedReader.lines().forEach(data::add);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        int resultPaper = countPaperSquareFeet(data);
        System.out.println(resultPaper);
        int resultRibbon = countRibbonSquareFeet(data);
        System.out.println(resultRibbon);
    }
}
