package adventOfCode.year2015.day3;

public class HousePosition implements Comparable<HousePosition>
{
    int x;
    int y;

    public HousePosition(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    @Override
    public int compareTo(HousePosition o)
    {
        int result = Integer.compare(this.x, o.getX());

        if (result == 0)
            result = Integer.compare(this.y,o.getY());

        return result;
    }
}
