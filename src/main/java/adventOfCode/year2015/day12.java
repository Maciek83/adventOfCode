package adventOfCode.year2015;


import org.json.*;

import java.io.*;
import java.util.Scanner;


public class day12 {

    private static String filePath = "src/main/resources/year2015/input2015day12.json";


    static int getValue(Object object) throws Exception {
        if (object instanceof Integer) return (int) object;
        if (object instanceof String) return 0;

        int total = 0;
        if (object instanceof JSONArray) {
            for (int i = 0; i < ((JSONArray)object).length(); ++i) {
                try {
                    int val = getValue(((JSONArray)object).get(i));
                    total += val;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return total;
        }

        if (object instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) object;
            JSONArray names = jsonObject.names();
            for (int i = 0; i < names.length(); ++i) {
                String name = (String) names.get(i);
                if (jsonObject.get(name).equals("red")) {
                    return 0;
                } else {
                    total += getValue(jsonObject.get(name));
                }
            }
            return total;
        }

        return 0;
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(new File(filePath));
        String line = scanner.nextLine();
        try {
            JSONArray obj = new JSONArray(line);
            System.out.println(getValue(obj));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
