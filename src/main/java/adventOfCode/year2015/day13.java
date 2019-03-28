package adventOfCode.year2015;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class day13 {

    private static List<String[]> seatPermutations = new ArrayList<>();

    private static List<String> getData() throws FileNotFoundException {
        String pathName = "src/main/resources/year2015/input2015day13.txt";

        File file = new File(pathName);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        return bufferedReader.lines().collect(Collectors.toList());
    }

    private static Map<String, Map<String, Integer>> createMapForWhoWhoLike(List<String> ravData) {

        Map<String, Map<String, Integer>> personAndDislikeMap = new HashMap<>();

        for (String line : ravData) {
            String[] data = line.split(" ");

            String name = data[0];
            String addOrSubtract = data[2];
            String howMuchLike = data[3];
            String personSittingNextTo = data[10].substring(0, data[10].length() - 1);

            int valueOfLike = Integer.parseInt(howMuchLike);

            if (!personAndDislikeMap.containsKey(name)) {
                Map<String, Integer> personAndValue = new HashMap<>();

                putPersonAndHowMuchLike(addOrSubtract, personSittingNextTo, personAndValue, valueOfLike);

                personAndDislikeMap.put(name, personAndValue);
            } else {
                Map<String, Integer> personAndValue = personAndDislikeMap.get(name);

                putPersonAndHowMuchLike(addOrSubtract, personSittingNextTo, personAndValue, valueOfLike);
            }

        }

        return personAndDislikeMap;
    }

    private static void putPersonAndHowMuchLike(String addOrSubtract, String personSittingNextTo, Map<String, Integer> personAndHowMuchLike, int valueOfLike) {
        if (addOrSubtract.equals("lose")) {
            personAndHowMuchLike.put(personSittingNextTo, -valueOfLike);
        } else {
            personAndHowMuchLike.put(personSittingNextTo, valueOfLike);
        }
    }

    private static String[] getNames(Map<String, Map<String, Integer>> personAndHowMuchLike) {
        List<String> names = new ArrayList<>(personAndHowMuchLike.keySet());

        String[] personsSittingAroundTable = new String[names.size()];

        for (int i = 0; i < names.size(); i++) {

            personsSittingAroundTable[i] = names.get(i);
        }
        return personsSittingAroundTable;
    }

    private static void findSittingPermutations(String[] a, int size, int n) {

        // if size becomes 1 then prints the obtained
        // permutation
        if (size == 1) {
//            System.out.println(Arrays.toString(a));
            String[] permutation = a.clone();
            seatPermutations.add(permutation);
        }
        for (int i = 0; i < size; i++) {
            findSittingPermutations(a, size - 1, n);

            // if size is odd, swap first and last
            // element
            if (size % 2 == 1) {
                String temp = a[0];
                a[0] = a[size - 1];
                a[size - 1] = temp;
            }

            // If size is even, swap ith and last
            // element
            else {
                String temp = a[i];
                a[i] = a[size - 1];
                a[size - 1] = temp;
            }
        }

    }

    private static String findNeighbourOnTheRight(String[] permutation, int i) {
        String neighbourOnTheRight;
        if (i+1 >= permutation.length)
        {
            neighbourOnTheRight = permutation[0];
        }
        else
        {
            neighbourOnTheRight = permutation[i+1];
        }

        return neighbourOnTheRight;
    }

    private static String findNeighbourOnTheLeft(String[] permutation, int i) {

        String neighborOnTheLeft;

        if(i - 1 < 0)
        {
            neighborOnTheLeft = permutation[permutation.length-1];
        }
        else
        {
            neighborOnTheLeft = permutation[i-1];
        }

        return neighborOnTheLeft;
    }

    private static List<Integer> getValuesForPermutations(Map<String, Map<String, Integer>> mapOfLike) {
        List<Integer> likeValues = new ArrayList<>();

        for (String[] permutation : seatPermutations) {

            int valueOfLike = 0;

            for (int i = 0; i < permutation.length; i++)
            {
                String examinedSitting = permutation[i];
                String neighbourOnTheLeft = findNeighbourOnTheLeft(permutation, i);
                String neighbourOnTheRight = findNeighbourOnTheRight(permutation, i);

                valueOfLike += mapOfLike.get(examinedSitting).get(neighbourOnTheLeft);
                valueOfLike += mapOfLike.get(examinedSitting).get(neighbourOnTheRight);
            }

            likeValues.add(valueOfLike);

        }
        return likeValues;
    }

    public static void main(String[] args) throws FileNotFoundException {

        List<String> ravData = getData();

        Map<String, Map<String, Integer>> mapOfLike = createMapForWhoWhoLike(ravData);

        String[] personsSittingAroundTable = getNames(mapOfLike);

        findSittingPermutations(personsSittingAroundTable, personsSittingAroundTable.length, personsSittingAroundTable.length);

        List<Integer> likeValues = getValuesForPermutations(mapOfLike);

        int max = findOptimalValue(likeValues);

        System.out.println(max + " is optimal Value");

    }

    private static int findOptimalValue(List<Integer> likeValues)
    {
        return likeValues.stream().max(Comparator.comparing(integer -> integer)).orElseThrow(NullPointerException::new);
    }


}
