package adventOfCode.year2015;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

// Does not give a good result because when both reindeers have the same distance i add point to each

public class day14 {
    private static Map<String, Map<Integer, Integer>> participantSecondDistance = new HashMap<>();

    private static List<String> getRavData() throws FileNotFoundException {
        File file = new File("src/main/resources/year2015/input2015day14.txt");
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        return bufferedReader.lines().collect(Collectors.toList());
    }

    private static List<String[]> groupParticipantsData(List<String> ravData) {
        List<String[]> participantsData = new ArrayList<>();

        for (String oneParticipantData : ravData) {
            String[] allData = oneParticipantData.split(" ");
            String[] runnerData = new String[4];

            String name = allData[0];
            String speedKmS = allData[3];
            String afterBurnerTime = allData[6];
            String restTime = allData[13];

            runnerData[0] = name;
            runnerData[1] = speedKmS;
            runnerData[2] = afterBurnerTime;
            runnerData[3] = restTime;

            participantsData.add(runnerData);
        }

        return participantsData;
    }

    private static Map<String, Integer> calculateDistanceTraveledByEachPlayer(List<String[]> participantsData, int availableTime) {
        Map<String, Integer> participantDistance = new HashMap<>();
        Map<Integer, Integer> secondDistance;
        String name;

        for (String[] oneParticipantData : participantsData) {
            secondDistance = new HashMap<>();
            name = oneParticipantData[0];

            int speedKMS = Integer.parseInt(oneParticipantData[1]);
            int boostTime = Integer.parseInt(oneParticipantData[2]);
            int restTime = Integer.parseInt(oneParticipantData[3]);

            int actualTravelTimeInSeconds = 0;
            int traveledDistance = 0;

            while (actualTravelTimeInSeconds + boostTime + restTime <= availableTime) {

                for (int i = 1; i <= boostTime; i++) {
                    actualTravelTimeInSeconds++;
                    traveledDistance += speedKMS;

                    secondDistance.put(actualTravelTimeInSeconds, traveledDistance);
                }

                for (int i = 1; i <= restTime; i++) {
                    actualTravelTimeInSeconds++;

                    secondDistance.put(actualTravelTimeInSeconds, traveledDistance);

                }

            }

            int timeAvailableAfterFullBoosts = availableTime - actualTravelTimeInSeconds;


            if (timeAvailableAfterFullBoosts >= boostTime) {
                for (int i = 1; i <= boostTime; i++) {
                    actualTravelTimeInSeconds++;
                    traveledDistance += speedKMS;

                    secondDistance.put(actualTravelTimeInSeconds, traveledDistance);
                }

            } else {
                for (int i = 1; i <= timeAvailableAfterFullBoosts; i++) {
                    actualTravelTimeInSeconds++;
                    traveledDistance += speedKMS;

                    secondDistance.put(actualTravelTimeInSeconds, traveledDistance);
                }
            }


            while (actualTravelTimeInSeconds < availableTime) {
                actualTravelTimeInSeconds++;
                secondDistance.put(actualTravelTimeInSeconds, traveledDistance);
            }


            participantDistance.put(name, traveledDistance);

            participantSecondDistance.put(name, secondDistance);
        }

        return participantDistance;

    }

    private static int findBiggestDistance(Map<String, Integer> participantDistanceTraveled) {
        return participantDistanceTraveled.values()
                .stream()
                .max(Comparator.comparing(integer -> integer)).orElseThrow(NullPointerException::new);
    }

    private static Map<String, Integer> distributePoints(Map<String, Map<Integer, Integer>> participantSecondDistance, int availableTime) {
        Map<String, Integer> participantPoints = new HashMap<>();

        List<String> remisWinners = new ArrayList<>();
        String winnerInSecond = null;

        for (int i = 1; i <= availableTime; i++) {
            int winningDistance = 0;

            for (Map.Entry<String, Map<Integer, Integer>> entry : participantSecondDistance.entrySet()) {
                int newDistance = entry.getValue().get(i);

                if (winningDistance < newDistance)
                {
                    winningDistance = newDistance;
                    winnerInSecond = entry.getKey();
                }
                else if (winningDistance == newDistance)
                {
                    remisWinners.add(entry.getKey());
                }
            }

            for (String remissWinner: remisWinners)
            {
                if (!participantPoints.containsKey(remissWinner)) {
                    participantPoints.put(remissWinner, 1);
                } else {
                    participantPoints.replace(remissWinner, participantPoints.get(remissWinner) + 1);
                }
            }

            remisWinners.clear();


            if (!participantPoints.containsKey(winnerInSecond)) {
                participantPoints.put(winnerInSecond, 1);
            } else {
                participantPoints.replace(winnerInSecond, participantPoints.get(winnerInSecond) + 1);
            }


        }

        return participantPoints;

    }

    public static void main(String[] args) throws FileNotFoundException {
        List<String> ravData = getRavData();

        List<String[]> participantsData = groupParticipantsData(ravData);

        Map<String, Integer> participantDistanceTraveled = calculateDistanceTraveledByEachPlayer(participantsData, 2503);

        int biggestDistance = findBiggestDistance(participantDistanceTraveled);

        System.out.println(biggestDistance + " - " + "biggest distance");

        Map<String, Integer> participantPoints = distributePoints(participantSecondDistance, 2503);

        for (Map.Entry<String, Integer> entry : participantPoints.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

    }

}
