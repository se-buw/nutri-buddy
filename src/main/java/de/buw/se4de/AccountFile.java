package de.buw.se4de;

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

import javafx.collections.FXCollections;
import javafx.collections.*;

class AccountFile{
    public String readAccount(int index, String data)
    {
        // Returns all names from the csv file
        // gibt zb. Name von Index i zurück ()
        String result = "Error, Account " + index + " with data " + data + "not found";
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

    // -----------------------------------------------------------------------------------
    // Returns value of given data from all accounts from the csv file

    // -----------------------------------------------------------------------------------
    // Returns value of given data from all accounts from the csv file, nur genutzt um einmal alle Namen ausgeben zu lassen
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

            csvParser.close();
            reader.close();
        }
        catch (IOException e)
        {
            result.add("Error, Accounts data " + data + "not found");
            e.printStackTrace();
        }

        return result;
    } //

    // -----------------------------------------------------------------------------------

    // Searches for Account by Name and returns index of found record; -1 if not found
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

            csvParser.close();
            reader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return (-1);
    }

    // -----------------------------------------------------------------------------------

    public boolean addAccount(String account)
    {
        // Adds a new account record to the accounts csv
        boolean returnStatus = false;
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND);
             @SuppressWarnings("deprecation")
             CSVPrinter csvPrinter = new CSVPrinter(writer,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader()))
        {
            // Add record after removing leading and trailing spaces

            account = account + ",0,0,0";
            String[] accountData = account.split(",");
            // Adds account only if it didn't already exist
            if(searchAccount(accountData[0].strip()) == -1)
            {
                csvPrinter.printRecord(accountData[0].strip(), accountData[1].strip(), accountData[2].strip(), accountData[3].strip(), accountData[4].strip(), accountData[5].strip(),accountData[6].strip(), accountData[7].strip());
                returnStatus = true;
            }

            csvPrinter.close();
            writer.close();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return returnStatus;
    } //

    // -----------------------------------------------------------------------------------
    // Changes the dataIndex from the currently logged in account to the newEntry
    public void editAccount(String newEntry, int dataIndex)
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
            String[] recordToEdit = csvBody.get(loggedInAccountIndex);
            recordToEdit[dataIndex] = newEntry.strip();
            csvBody.set(loggedInAccountIndex, recordToEdit);

            reader.close();
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

            csvPrinter.close();
            writer.close();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

        final String accountCsvFile = "src/main/resources/accounts.csv";
        final Path path = Paths.get(accountCsvFile);
        protected  int	loggedInAccountIndex = -1;
}
