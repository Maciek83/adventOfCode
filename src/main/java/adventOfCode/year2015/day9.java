package adventOfCode.year2015;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class day9
{
    private Map<String, Integer> routesMap = new HashMap<>();
    private List<String> cities = new ArrayList<>();
    private String[] citiesTable;
    private List<String> routes = new ArrayList<>();
    private Map<String, Integer> allRoutesAndDistance = new HashMap<>();


    private void addToRoutesMap(String route, int distance)
    {
        routesMap.put(route, distance);
    }

    private void findAllCities()
    {
        for (String s : routesMap.keySet())
        {
            String[] data = s.split("to");
            if (alreadyHave(data[0].trim()))
                cities.add(data[0].trim());
            if (alreadyHave(data[1].trim()))
                cities.add(data[1].trim());
        }

        citiesTable = cities.toArray(new String[0]);
    }
    private boolean alreadyHave(String city)
    {
      return cities.stream().noneMatch(s -> s.equals(city));
    }

    private void printArr(String[] a, int n)
    {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n; i++)
        {
            stringBuilder.append(a[i]).append(" ");
//            System.out.print(a[i] + " ");
        }

        routes.add(stringBuilder.toString());

//        System.out.println();
    }
    //Generating permutation using Heap Algorithm
    private void heapPermutation(String[] a, int size, int n)
    {
        // if size becomes 1 then prints the obtained
        // permutation
        if (size == 1)
            printArr(a, n);

        for (int i = 0; i < size; i++)
        {
            heapPermutation(a, size - 1, n);

            // if size is odd, swap first and last
            // element
            if (size % 2 == 1)
            {
                String temp = a[0];
                a[0] = a[size - 1];
                a[size - 1] = temp;
            }

            // If size is even, swap ith and last
            // element
            else
            {
                String temp = a[i];
                a[i] = a[size - 1];
                a[size - 1] = temp;
            }
        }

    }

    private void findDistancesForRoutes()
    {
        int totalDistance=0;

        for (String route: routes)
        {
            String [] routeElements = route.split(" ");

            for (int i = 0; i < routeElements.length-1; i++)
            {
                for (Map.Entry<String,Integer> entry: routesMap.entrySet())
                {
                    String [] specificRoute = entry.getKey().split("to");

                    if (routeElements[i].equals(specificRoute[0].trim()) && routeElements[i+1].equals(specificRoute[1].trim()))
                    {
                        totalDistance+=entry.getValue();
                    }
                    else if(routeElements[i].equals(specificRoute[1].trim()) && routeElements[i+1].equals(specificRoute[0].trim()))
                    {
                        totalDistance+=entry.getValue();
                    }
                }
            }

            allRoutesAndDistance.put(route.trim(),totalDistance);

            totalDistance = 0;
        }
    }

    private int findShortestRoute()
    {
        return allRoutesAndDistance.values()
                .stream()
                .min(Comparator.comparing(Integer::shortValue)).orElseThrow(NullPointerException::new);

    }

    private int findLongestRoute()
    {
        return allRoutesAndDistance.values()
                .stream()
                .max(Comparator.comparing(Integer::shortValue)).orElseThrow(NullPointerException::new);
    }

    public static void main(String[] args) throws FileNotFoundException
    {
        day9 day9 = new day9();

        File file = new File("src/main/resources/year2015/input2015day9.txt");
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String[] allData = bufferedReader.lines().toArray(String[]::new);

        for (String string : allData)
        {
            String[] rawData = string.split("=");

            day9.addToRoutesMap(rawData[0].trim(), Integer.parseInt(rawData[1].trim()));

        }

        day9.findAllCities();

        day9.heapPermutation(day9.citiesTable, day9.citiesTable.length, day9.citiesTable.length);

        day9.findDistancesForRoutes();

        System.out.println(day9.findShortestRoute());
        System.out.println(day9.findLongestRoute());
    }
}
