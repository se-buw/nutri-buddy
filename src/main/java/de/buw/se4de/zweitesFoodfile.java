package de.buw.se4de;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Random;

class zweitesFoodfile{

    //private /*static final*/ String fooditemCsvFile = "src/main/resources/food_items.csv";
    //final Path path = Paths.get(fooditemCsvFile);

    private String fooditemCsvFile;
    private Path path;

    zweitesFoodfile(String fooditemCsvFile){
        this.fooditemCsvFile = fooditemCsvFile;
        path = Paths.get(fooditemCsvFile);
    }
    public String get_Nutritions(String food) {
        String result = "";

        try (Reader reader = Files.newBufferedReader(path);
             CSVParser csvParser = new CSVParser(reader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim()))
        {
            // Reading data from record
            for (CSVRecord csvRecord : csvParser)
            {
                String currentFood = csvRecord.get(0); // Annahme: Die Lebensmittelinformationen befinden sich in der ersten Spalte der CSV

                if (currentFood.equals(food))
                {
                    result = csvRecord.get(1); // Der zweite Wert (Index 1) in der CSV-Spalte "nutritions"
                    break; // Beenden Sie die Schleife, wenn das gewünschte Lebensmittel gefunden wurde
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return result;
    }

    public ObservableList<String> readAllFoodItems()
    {
        // Returns all names from the csv file
        ObservableList<String> result = FXCollections.observableArrayList();
        try (Reader reader = Files.newBufferedReader(path);
             @SuppressWarnings("deprecation")
             CSVParser csvParser = new CSVParser(reader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim()))
        {
            // Reading data from record
            for (CSVRecord csvRecord : csvParser)
            {
                if(csvRecord.get(2).isEmpty())
                {
                    result.add(csvRecord.get(0) + " | Kcal: " +  csvRecord.get(1));
                }
                else
                {
                    String ingredientsOutputString = "";
                    String ingredientsOneString = csvRecord.get(2);
                    String[] ingredientsArray = ingredientsOneString.split(" ");
                    for (String s : ingredientsArray) {
                        String[] ingredientAmountSep = s.split("_");
                        ingredientsOutputString += ", " + ingredientAmountSep[1] + "g " + ingredientAmountSep[0];
                    }
                    result.add(csvRecord.get(0) + " | Kcal: " +  csvRecord.get(1) + " | Ingredients: " + ingredientsOutputString);
                }
            }
        }
        catch (IOException e)
        {
            result.add("Error");
            e.printStackTrace();
        }

        return result;
    }

    // -----------------------------------------------------------------------------------

    public int addFoodItem(String foodItem)
    {
        // Adds a new account record to the accounts csv
        int returnStatus = -1;
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND);
             @SuppressWarnings("deprecation")
             CSVPrinter csvPrinter = new CSVPrinter(writer,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader()))
        {
            foodItem = foodItem.concat(", ");
            // Add record after removing leading and trailing spaces
            System.out.println(foodItem);
            String[] foodData = foodItem.split(",");
            // Adds account only if it didn't already exist
            if(searchFoodItem(foodData[0].strip()) == -1)
            {
                csvPrinter.printRecord(foodData[0].strip(), foodData[1].strip(), foodData[2].strip());
                returnStatus = 1;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return returnStatus;
    }

    // -----------------------------------------------------------------------------------
    // Searches for Food item by Name and returns index of found record; -1 if not found
    public int searchFoodItem(String name)
    {
        // Returns all names from the csv file
        try (Reader reader = Files.newBufferedReader(path);
             @SuppressWarnings("deprecation")
             CSVParser csvParser = new CSVParser(reader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim()))
        {
            // Reading
            int i = 0;
            for (CSVRecord csvRecord : csvParser)
            {
                String nameCheck = csvRecord.get("name");
                if(nameCheck.equals(name))
                {
                    return i;
                }
                ++i;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return (-1);
    }

    // -------------------------------------------------------------------------------------
    // Pick a random food Fact and return the String
    public String foodFact() {
        // Returns all names from the csv file
        try (Reader reader = Files.newBufferedReader(path);
             @SuppressWarnings("deprecation")
             CSVParser csvParser = new CSVParser(reader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim()))
        {
            // Reading
            Random rand = new Random();
            String index = rand.nextInt(18)+1+"";
            for (CSVRecord csvRecord : csvParser)
            {
                String indexCheck = csvRecord.get("index");
                if(indexCheck.equals(index))
                {
                    return csvRecord.get(1)+"\n"+ "#FoodFacts";
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return "couldnt generate a fact" ;
    }
}