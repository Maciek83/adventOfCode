package adventOfCode.year2015.day3;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class day3
{
    public static Map<HousePosition, Integer> findPresentsInHouses(String routeData)
    {
        char [] directions = routeData.toCharArray();
        Map<HousePosition, Integer> housePresents = new HashMap<>();
        HousePosition santaStartPosition = new HousePosition(0, 0);
        HousePosition santaRobotStartPosition = new HousePosition(0, 0);

        housePresents.put(santaStartPosition, 2);

        for (int i = 0; i < directions.length; i++)
        {
            if (i % 2 == 0) // santa turn
                santaStartPosition = deliverPresent(directions[i], housePresents, santaStartPosition);
            else // robotTurn
                santaRobotStartPosition = deliverPresent(directions[i], housePresents, santaRobotStartPosition);
        }


        return housePresents;

    }

    private static HousePosition deliverPresent(char direction, Map<HousePosition, Integer> housePresents, HousePosition startPosition)
    {
        HousePosition newPosition = null;

        if (direction == '^')
        {
            newPosition = new HousePosition(startPosition.getX() + 1, startPosition.getY());

            replaceOrPutNewValue(housePresents, newPosition);
        }
        else if (direction == 'v')
        {
            newPosition = new HousePosition(startPosition.getX() - 1, startPosition.getY());

            replaceOrPutNewValue(housePresents, newPosition);
        }
        else if (direction == '>')
        {
            newPosition = new HousePosition(startPosition.getX(), startPosition.getY() + 1);

            replaceOrPutNewValue(housePresents, newPosition);
        }
        else if (direction == '<')
        {
            newPosition = new HousePosition(startPosition.getX(), startPosition.getY() - 1);

            replaceOrPutNewValue(housePresents, newPosition);
        }

        return newPosition;
    }

    private static void replaceOrPutNewValue(Map<HousePosition, Integer> housePresents, HousePosition newPosition)
    {
        if (checkIfSantaWasHereBefore(newPosition, housePresents))
        {
            HousePosition housePosition = housePresents.entrySet()
                    .stream()
                    .filter(housePositionIntegerEntry -> housePositionIntegerEntry.getKey().getX() == newPosition.getX() && housePositionIntegerEntry.getKey().getY() == newPosition.getY())
                    .findFirst()
                    .map(Map.Entry::getKey)
                    .get();

            housePresents.replace(housePosition, housePresents.get(housePosition) + 1);
        }
        else
        {
            housePresents.put(newPosition, 1);
        }
    }

    public static boolean checkIfSantaWasHereBefore(HousePosition housePosition, Map<HousePosition, Integer> housePositionIntegerMap)
    {
        return housePositionIntegerMap.entrySet()
                .stream()
                .anyMatch(housePositionIntegerEntry -> housePositionIntegerEntry.getKey().compareTo(housePosition) == 0);
    }

    public static void main(String[] args)
    {
        File file = new File("src/main/resources/year2015/day3/input2015day3.txt");

        try
        {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String routeData = bufferedReader.readLine();

            Map<HousePosition, Integer> housePositionIntegerMap = findPresentsInHouses(routeData);


            Map<HousePosition, Integer> filteredMap = housePositionIntegerMap.entrySet()
                    .stream()
                    .filter(housePositionIntegerEntry -> housePositionIntegerEntry.getValue() >= 1)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            System.out.println(filteredMap.size());


        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
