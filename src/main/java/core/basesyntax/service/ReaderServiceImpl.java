package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReaderServiceImpl implements ReaderService {
    @Override
    public List<FruitTransaction> readFromFile(String filePath) {
        //List<String> rowsFile;
        //try {
        //    rowsFile = Files.readAllLines(Path.of(filePath));
        //} catch (IOException e) {
        //    throw new RuntimeException("Can't get data from file " + filePath);
        //}
        //return rowsFile.stream()
        //        .map(this::getFromCsvRow)
        //        .collect(Collectors.toList());

        // read file line by line to have line number
        List<FruitTransaction> fruitList = new ArrayList<>();
        int numStr = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = splitLine(line, numStr);
                if (numStr == 0) {
                    if (!fields[0].equals("type")) {
                        throw new RuntimeException("Wrong header in file on line 0!");
                    }
                    numStr++;
                    continue;
                }
                fruitList.add(getFromCsvRow(fields, numStr));
                numStr++;
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't get data from file " + filePath);
        }
        return fruitList;
    }

    private FruitTransaction getFromCsvRow(String[] arr, int numStr) {
        if (!isNumeric(arr[2])) {
            throw new RuntimeException("Quantity in position is not the correct "
                    + "number in line: " + numStr);
        }
        FruitTransaction fruitTransaction = new FruitTransaction();
        FruitTransaction.Operation operation = fruitTransaction.getOperationCode(arr[0]);
        if (operation == null) {
            throw new RuntimeException("Invalid transaction operation found "
                    + "in file in line: " + numStr);
        }
        fruitTransaction.setOperation(operation);
        fruitTransaction.setFruit(arr[1]);
        fruitTransaction.setQuantity(Integer.parseInt(arr[2]));
        Storage.fruitTransactions.add(fruitTransaction);
        return fruitTransaction;
    }

    public static boolean isNumeric(String s) {
        return s.chars().allMatch(Character::isDigit);
    }

    public static String[] splitLine(String line, int numStr) {
        String[] fields = line.split(",");
        if (fields.length != 3) {
            throw new RuntimeException("The number of columns in the file is not "
                    + "equal to 3 in line: " + numStr);
        }
        return fields;
    }
}
