package de.buw.se4de;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.*;
import java.util.ArrayList;


class AccountFile{
    private String accountCsvFile;
    private Path path;

    AccountFile(String accountCsvFile){
        this.accountCsvFile = accountCsvFile;
        path = Paths.get(accountCsvFile);
    }


    public String readAccount(int index, String data)
    {
        // Returns all names from the csv file
        String result = "Error, Account " + index + " with data " + data + " not found";
        try (Reader reader = Files.newBufferedReader(path);

             @SuppressWarnings("deprecation")
             CSVParser csvParser = new CSVParser(reader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim()))
        {
            // Reading data from record
            int i = 0;
            for (CSVRecord csvRecord : csvParser)
            {
                if(i == index)
                {
                    result = csvRecord.get(data);
                }
                ++i;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return result;
    }

    public int addAccount(String account)
    {
        // Adds a new account record to the accounts csv
        int returnStatus = -1;
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND);
             @SuppressWarnings("deprecation")
             CSVPrinter csvPrinter = new CSVPrinter(writer,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader()))
        {
            // Add record after removing leading and trailing spaces

            account = account + ",0,0,0";
            String[] accountData = account.split(",");
            // Adds account only if it didn't already exist
            if(searchAccount(accountData[0].strip()) == -1 && accountData[0]!= "" && accountData[1]!= "" && accountData[2]!= "" && accountData[3]!= "" && accountData[4]!= "")
            {
                csvPrinter.printRecord(accountData[0].strip(), accountData[1].strip(), accountData[2].strip(), accountData[3].strip(), accountData[4].strip(), accountData[5].strip(),accountData[6].strip(), accountData[7].strip());
                returnStatus = 1;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return returnStatus;
    }

    public ObservableList<String> readAllAccounts(String data)
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
                result.add(csvRecord.get(data));
            }
        }
        catch (IOException e)
        {
            result.add("Error, Accounts data " + data + " not found");
            e.printStackTrace();
        }

        return result;
    }

    public int searchAccount(String name)
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

    public void editAccount(String newEntry, int dataIndex, int loginindex)
    {
        ArrayList<String[]> csvBody = new ArrayList<>();

        try (Reader reader = Files.newBufferedReader(path);
             @SuppressWarnings("deprecation")
             CSVParser csvParser = new CSVParser(reader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim()))
        {
            // Copy whole file into String Array
            for(CSVRecord csvRecord : csvParser)
            {
                csvBody.add(new String[] { csvRecord.get(0), csvRecord.get(1), csvRecord.get(2), csvRecord.get(3), csvRecord.get(4), csvRecord.get(5), csvRecord.get(6), csvRecord.get(7)});
            }

            // Clear whole file

            // Edit entry
            String[] recordToEdit = csvBody.get(loginindex);
            recordToEdit[dataIndex] = newEntry.strip();
            csvBody.set(loginindex, recordToEdit);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


        try (BufferedWriter writer = Files.newBufferedWriter(path);
             @SuppressWarnings("deprecation")
             CSVPrinter csvPrinter = new CSVPrinter(writer,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader()))
        {
            // Print header row
            csvPrinter.printRecord(("name"), ("height"), ("gender"), ("weight"), ("goal_weight"), ("nutritions_day"), ("login"), ("streak"));
            // Print whole String Array back into file
            for (String[] strings : csvBody) {
                csvPrinter.printRecord(strings[0], strings[1], strings[2], strings[3], strings[4], strings[5], strings[6], strings[7]);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}