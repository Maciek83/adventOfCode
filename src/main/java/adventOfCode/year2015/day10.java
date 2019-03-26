package adventOfCode.year2015;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class day10 {

    public String findAnswer(String string, int counter) {
        if (counter == 50) {
            return string;
        } else {
            StringBuilder result = new StringBuilder();

            Pattern pattern = Pattern.compile("(\\w)\\1*");

            Matcher matcher = pattern.matcher(string);

            while (matcher.find()) {
                String match = matcher.group();

                result.append(match.length());
                result.append(match.charAt(0));

            }

            int newCounter = counter + 1;

            return findAnswer(result.toString(), newCounter);
        }

    }

    public static void main(String[] args) {
        String data = "3113322113";

        String answer = new day10().findAnswer(data,0);

        System.out.println(answer.length());

    }
}
