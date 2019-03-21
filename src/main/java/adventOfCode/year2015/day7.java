package adventOfCode.year2015;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class day7
{
    static Map<String, String> instmap = new HashMap<>();
    static Map<String, Character> wiremap = new HashMap<>();

    static boolean isInteger(String s)
    {
        try
        {
            Integer.parseInt(s);
        }
        catch (NumberFormatException | NullPointerException e)
        {
            return false;
        }
        return true;
    }

    static void findValue(String wire)
    {
        String instruction = instmap.get(wire);

        System.out.println("Finding " + wire + ", instruction: " + instruction);
        String[] instarray = instruction.split(" ");
        Character arg1, arg2, result = null;
        switch (instarray[0])
        {
            case "SET":
                if (isInteger(instarray[1]))
                {
                    arg1 = (char) Integer.parseInt(instarray[1]);
                }
                else
                {
                    if ((wiremap.get(instarray[1])) == null)
                    {
                        findValue(instarray[1]);
                    }
                    arg1 = wiremap.get(instarray[1]);
                }
                wiremap.put(wire, doOp("SET", arg1, null));
                break;
            case "NOT":
                if ((wiremap.get(instarray[1])) == null)
                {
                    findValue(instarray[1]);
                }
                arg1 = wiremap.get(instarray[1]);
                result = doOp("NOT", arg1, null);
                wiremap.put(wire, result);
                break;
            case "AND":
                if (isInteger(instarray[1]))
                {
                    arg1 = (char) Integer.parseInt(instarray[1]);
                }
                else
                {
                    if ((wiremap.get(instarray[1])) == null)
                    {
                        findValue(instarray[1]);
                    }
                    arg1 = wiremap.get(instarray[1]);
                }
                if ((wiremap.get(instarray[2])) == null)
                {
                    findValue(instarray[2]);
                }
                arg2 = wiremap.get(instarray[2]);
                result = doOp("AND", arg1, arg2);
                wiremap.put(wire, result);
                break;
            case "OR":
                if ((wiremap.get(instarray[1])) == null)
                {
                    findValue(instarray[1]);
                }
                arg1 = wiremap.get(instarray[1]);
                if ((wiremap.get(instarray[2])) == null)
                {
                    findValue(instarray[2]);
                }
                arg2 = wiremap.get(instarray[2]);
                result = doOp("OR", arg1, arg2);
                wiremap.put(wire, result);
                break;
            case "LSHIFT":
                if ((wiremap.get(instarray[1])) == null)
                {
                    findValue(instarray[1]);
                }
                arg1 = wiremap.get(instarray[1]);
                arg2 = (char) Integer.parseInt(instarray[2]);
                result = doOp("LSHIFT", arg1, arg2);
                wiremap.put(wire, result);
                break;
            case "RSHIFT":
                if ((wiremap.get(instarray[1])) == null)
                {
                    findValue(instarray[1]);
                }
                arg1 = wiremap.get(instarray[1]);
                arg2 = (char) Integer.parseInt(instarray[2]);
                result = doOp("RSHIFT", arg1, arg2);
                wiremap.put(wire, result);
                break;
        }
    }

    static char doOp(String operation, Character x, Character y)
    {
        switch (operation)
        {
            case "SET": // e.g. 123 -> x, xy -> x
                return x;
            case "NOT": // e.g. NOT y -> x
                return (char) (~x);
            case "AND":
                return (char) (x & y);
            case "OR":
                return (char) (x | y);
            case "LSHIFT":
                return (char) (x << y);
            case "RSHIFT":
                return (char) (x >>> y);
        }
        return (char) -1;
    }

    public static void main(String[] args) throws IOException
    {
        String line = null;
        BufferedReader br = new BufferedReader(new FileReader(new File("src/main/resources/year2015/input2015day7.txt")));
        while ((line = br.readLine()) != null)
        {
            String[] temparray1 = line.split(" -> ");
            String wire = temparray1[1];
            String[] temparray2 = temparray1[0].split(" ");
            String instruction = null;
            switch (temparray2.length)
            {
                case 1: // e.g. 123 -> x, xy -> x
                    instruction = "SET" + " " + temparray2[0];
                    break;
                case 2: // e.g. NOT y -> x
                    instruction = temparray2[0] + " " + temparray2[1];
                    break;
                case 3:
                    instruction = temparray2[1] + " " + temparray2[0] + " " + temparray2[2];
                    break;
            }
            instmap.put(wire, instruction);
            System.out.println(line);
        }

        findValue("a");
        String key = "a";
        int result = (int) wiremap.get(key);
        System.out.println(key + ": " + result);
        System.out.println("Resetting wires, setting b to " + result + " and finding a again...");
        wiremap.clear();
        wiremap.put("b", (char) result);
        findValue("a");
        int result2 = (int) wiremap.get(key);
        System.out.println(key + ": " + result);
        System.out.println("b = " + result + ", " + key + ": " + result2);
    }
}

