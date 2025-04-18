package service.impl;

import java.util.List;
import model.FruitTransaction;
import model.FruitTransaction.OperationType;
import service.ParseService;

public class ParseServiceImpl implements ParseService {
    private static final String SEPARATOR = ",";
    private static final int INDEX_OPERATION = 0;
    private static final int INDEX_FRUIT = 1;
    private static final int INDEX_QUANTITY = 2;
    private static final int FIELDS_LENGTH = 3;
    private static final int MIN_QUANTITY = 0;

    @Override
    public List<FruitTransaction> parseList(List<String> filePath) {
        return filePath.stream()
            .skip(1)
            .map(this::parseCsvLine)
            .toList();
    }

    @Override
    public FruitTransaction parseCsvLine(String line) {
        if (line.trim().isEmpty()) {
            throw new IllegalArgumentException("Input line cannot be null or empty");
        }

        String[] fields = line.split(SEPARATOR);
        if (fields.length != FIELDS_LENGTH) {
            throw new IllegalArgumentException("Invalid CSV format: expected 3 fields, but got "
                    + fields.length + " in line: " + line);
        }

        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(OperationType.fromCode(fields[INDEX_OPERATION]));
        if (fields[INDEX_FRUIT].trim().isEmpty()) {
            throw new IllegalArgumentException("Fruit name cannot be empty in line: " + line);
        }
        fruitTransaction.setFruit(fields[INDEX_FRUIT]);

        try {
            fruitTransaction.setQuantity(Integer.parseInt(fields[INDEX_QUANTITY]));
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Invalid quantity format in line");
        }

        if (fruitTransaction.getQuantity() <= MIN_QUANTITY) {
            throw new IllegalArgumentException("Quantity cannot be negative or zero in line: "
                + line);
        }
        return fruitTransaction;
    }
}
