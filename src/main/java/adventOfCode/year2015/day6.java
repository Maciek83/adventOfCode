package adventOfCode.year2015;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class day6
{

    private static int[][] createLights(int number)
    {
        int[][] lights = new int[number][number];

        for (int i = 0; i < number; i++)
        {
            for (int j = 0; j < number; j++)
            {
                lights[i][j] = 0;
            }
        }

        return lights;
    }

    private static void switchLights(int rowXStartCorner, int columnYStartCorner, int rowXEndCorner, int columnYEndCorner, int[][] lights, String instruction)
    {

        switch (instruction)
        {
            case ("turn on"):
                for (int i = rowXStartCorner; i <= rowXEndCorner; i++)
                {
                    for (int j = columnYStartCorner; j <= columnYEndCorner; j++)
                    {
                        lights[i][j] = lights[i][j] + 1;
                    }
                }
                break;

            case ("turn off"):
                for (int i = rowXStartCorner; i <= rowXEndCorner; i++)
                {
                    for (int j = columnYStartCorner; j <= columnYEndCorner; j++)
                    {
                        if (lights[i][j]>0)
                        lights[i][j] = lights[i][j] -1;
                    }
                }
                break;
            case ("toggle"):
                for (int i = rowXStartCorner; i <= rowXEndCorner; i++)
                {
                    for (int j = columnYStartCorner; j <= columnYEndCorner; j++)
                    {
                        lights[i][j] = lights[i][j] + 2;
                    }
                }

        }

    }


    public static void main(String[] args)
    {
        int[][] lights = createLights(1000);
        int totalBrightness = 0;

        File file = new File("src/main/resources/year2015/input2015day6.txt");

        try
        {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            List<String> rawData = bufferedReader.lines().collect(Collectors.toList());


            for (String s : rawData)
            {
                String[] data = s.split(",");

                String[] d = data[0].split(" ");

                String instruction = null;
                int rowXStartCorner = 0;

                if (d.length == 3)
                {
                    instruction = d[0] + " " + d[1];
                    rowXStartCorner = Integer.parseInt(d[2]);
                }
                else if (d.length == 2)
                {
                    instruction = d[0];
                    rowXStartCorner = Integer.parseInt(d[1]);
                }
                String[] d1 = data[1].split(" ");

                int columnYStartCorner = Integer.parseInt(d1[0]);
                int rowXEndCorner = Integer.parseInt(d1[2]);

                int columnYEndCorner = Integer.parseInt(data[2]);

                switchLights(rowXStartCorner, columnYStartCorner, rowXEndCorner, columnYEndCorner, lights, instruction);

            }

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }


        for (int[] light : lights)
        {
            for (int i : light)
            {
                totalBrightness += i;
            }
        }

        System.out.println(totalBrightness);

    }
}
