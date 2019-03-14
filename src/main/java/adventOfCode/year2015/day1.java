package adventOfCode.year2015;



public class day1
{
    public static int countFloorNumber(String info)
    {
        int startingFloor = 0;

        char [] arrayOfInstruction = info.toCharArray();

        for (int i = 0; i < arrayOfInstruction.length; i++)
        {
            if (startingFloor == -1)
                System.out.println("First time in floor -1 " + i);

            if(arrayOfInstruction[i] == '(')
                startingFloor++;
            else if (arrayOfInstruction[i] == ')')
                startingFloor--;

        }

        return startingFloor;
    }

    public static void main(String[] args)
    {
        String s = args[0].trim();

        int whatFloor = countFloorNumber(s);

        System.out.println(whatFloor);

    }
}
