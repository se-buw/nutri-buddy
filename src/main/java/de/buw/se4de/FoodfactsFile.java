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
import java.util.ArrayList;
import java.util.Random;


class FoodfactsFile{
    private static final String accountCsvFile = "src/main/resources/facts.csv";
    final Path path = Paths.get(accountCsvFile);

    public String foodFact() {
        // Returns all names from the csv file
        try (Reader reader = Files.newBufferedReader(path);
             @SuppressWarnings("deprecation")
             CSVParser csvParser = new CSVParser(reader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());)
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

            csvParser.close();
            reader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return "couldnt generate a fact" ;
    }


}