package adventOfCode.year2015;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class day15
{

    public static void main(String[] args) throws FileNotFoundException
    {
        List<int[]> combinations = findCombinations(4);

        List<String> ravData = getRavData();

        List<Ingredient> ingredientList = collectIngredientList(ravData);

        Map<Integer,Integer> results = calculateTheResults(combinations, ingredientList);

        int max = results.entrySet()
                .stream()
                .filter(i -> i.getValue() == 500)
                .map(Map.Entry::getKey)
                .max(Integer::compareTo).orElseThrow(NullPointerException::new);

        System.out.println(max);
    }

    private static Map<Integer, Integer> calculateTheResults(List<int[]> combinations, List<Ingredient> ingredientList)
    {
        Ingredient sprinkles = ingredientList.get(0);
        Ingredient butterscotch = ingredientList.get(1);
        Ingredient chocolate = ingredientList.get(2);
        Ingredient candy = ingredientList.get(3);

        Map<Integer,Integer> listOfResults = new HashMap<>();

        for (int[] combination : combinations)
        {
            int spoonOfSprinkles = combination[0];
            int spoonOfButterscotch = combination[1];
            int spoonOfChocolate = combination[2];
            int spoonOfCandy = combination[3];

            int sprinklesCapacity = sprinkles.getCapacity() * spoonOfSprinkles;
            int butterscotchCapacity = butterscotch.getCapacity() * spoonOfButterscotch;
            int chocolateCapacity = chocolate.getCapacity() * spoonOfChocolate;
            int candyCapacity = candy.getCapacity() * spoonOfCandy;

            int totalCapacity = sprinklesCapacity + butterscotchCapacity + chocolateCapacity + candyCapacity;

            int sprinklesDurability = sprinkles.getDurability() * spoonOfSprinkles;
            int butterscotchDurability = butterscotch.getDurability() * spoonOfButterscotch;
            int chocolateDurability = chocolate.getDurability() * spoonOfChocolate;
            int candyDurability = candy.getDurability() * spoonOfCandy;

            int totalDurability = sprinklesDurability + butterscotchDurability + chocolateDurability + candyDurability;

            int sprinklesFlavor = sprinkles.getFlavor() * spoonOfSprinkles;
            int butterscotchFlavor = butterscotch.getFlavor() * spoonOfButterscotch;
            int chocolateFlavor = chocolate.getFlavor() * spoonOfChocolate;
            int candyFlavor = candy.getFlavor() * spoonOfCandy;

            int totalFlavor = sprinklesFlavor + butterscotchFlavor + chocolateFlavor + candyFlavor;

            int sprinklesTexture = sprinkles.getTexture() * spoonOfSprinkles;
            int butterscotchTexture = butterscotch.getTexture() * spoonOfButterscotch;
            int chocolateTexture = chocolate.getTexture() * spoonOfChocolate;
            int candyTexture = candy.getTexture() * spoonOfCandy;

            int totalTexture = sprinklesTexture + butterscotchTexture + chocolateTexture + candyTexture;

            if (totalCapacity < 0) totalCapacity = 0;
            if (totalDurability < 0) totalDurability = 0;
            if (totalFlavor < 0) totalFlavor = 0;
            if (totalTexture < 0) totalTexture = 0;

            int totalResult = totalCapacity * totalDurability * totalFlavor * totalTexture;


            int totalCalories = spoonOfSprinkles * sprinkles.getCalories()
                    + spoonOfButterscotch * butterscotch.getCalories()
                    + spoonOfChocolate * chocolate.getCalories()
                    + spoonOfCandy * candy.getCalories();

            listOfResults.put(totalResult,totalCalories);
        }

        return listOfResults;
    }

    private static List<Ingredient> collectIngredientList(List<String> ravData)
    {
        List<Ingredient> ingredientList = new ArrayList<>();

        for (String lineData : ravData)
        {
            String[] data = lineData.split(" ");
            Ingredient ingredient = new Ingredient(
                    data[0].substring(0, data[0].length() - 1),
                    extractData(data[2]),
                    extractData(data[4]),
                    extractData(data[6]),
                    extractData(data[8]),
                    Integer.parseInt(data[10])
            );

            ingredientList.add(ingredient);
        }

        return ingredientList;
    }

    private static int extractData(String data)
    {
        int i;

        if (data.length() > 2)
        {
            i = Integer.parseInt(data.substring(0, 2));
        } else
        {
            i = Integer.parseInt(data.substring(0, 1));
        }

        return i;
    }

    private static List<String> getRavData() throws FileNotFoundException
    {
        File file = new File("src/main/resources/year2015/input2015day15.txt");
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        return bufferedReader.lines().collect(Collectors.toList());
    }

    private static List<int[]> findCombinations(int numberOfElements)
    {
        List<int[]> combinations = new ArrayList<>();

        for (int i = 0; i <= 100; i++)
        {
            for (int j = 0; j <= 100; j++)
            {
                for (int k = 0; k <= 100; k++)
                {
                    for (int l = 0; l <= 100; l++)
                    {
                        if (i + j + k + l == 100)
                        {
                            int[] combination = new int[numberOfElements];

                            combination[0] = i;
                            combination[1] = j;
                            combination[2] = k;
                            combination[3] = l;

                            combinations.add(combination);

                        }
                    }
                }
            }
        }

        return combinations;
    }

}

class Ingredient
{
    private String name;
    private int capacity;
    private int durability;
    private int flavor;
    private int texture;
    private int calories;

    public Ingredient(String name, int capacity, int durability, int flavor, int texture, int calories)
    {
        this.name = name;
        this.capacity = capacity;
        this.durability = durability;
        this.flavor = flavor;
        this.texture = texture;
        this.calories = calories;
    }

    public String getName()
    {
        return name;
    }

    public int getCapacity()
    {
        return capacity;
    }

    public int getDurability()
    {
        return durability;
    }

    public int getFlavor()
    {
        return flavor;
    }

    public int getTexture()
    {
        return texture;
    }

    public int getCalories()
    {
        return calories;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setCapacity(int capacity)
    {
        this.capacity = capacity;
    }

    public void setDurability(int durability)
    {
        this.durability = durability;
    }

    public void setFlavor(int flavor)
    {
        this.flavor = flavor;
    }

    public void setTexture(int texture)
    {
        this.texture = texture;
    }

    public void setCalories(int calories)
    {
        this.calories = calories;
    }

    @Override
    public String toString()
    {
        return "Ingredient{" +
                "name='" + name + '\'' +
                ", capacity=" + capacity +
                ", durability=" + durability +
                ", flavor=" + flavor +
                ", texture=" + texture +
                ", calories=" + calories +
                '}';
    }
}
