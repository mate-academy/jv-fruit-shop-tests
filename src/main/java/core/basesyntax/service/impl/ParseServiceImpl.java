package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParseService;
import core.basesyntax.service.TransactionException;
import java.util.List;
import java.util.stream.Collectors;

public class ParseServiceImpl implements ParseService {
    private static final String CSV_DELIMITER = ",";
    private static final int TYPE_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final int VALID_QUANTITY_OF_FIELDS = 3;
    private static final String EMPTY_LINE = "";
    private static final String BALANCE = "b";
    private static final String SUPPLY = "s";
    private static final String PURCHASE = "p";
    private static final String RETURN = "r";
    private static final String EXCEPTION_MESSAGE_FOR_EMPTY_LINE = "Line is empty: ";
    private static final String EXCEPTION_MESSAGE_FOR_INCORRECT_LINE
            = "Incorrect line: ";
    private static final String EXCEPTION_MESSAGE_FOR_INCORRECT_QUANTITY_INDEX
            = "Can't parse! Value isn't digit: ";
    private static final String EXCEPTION_MESSAGE_FOR_NEGATIVE_QUANTITY_INDEX
            = "Quantity can't be negative or zero: ";
    private static final String EXCEPTION_MESSAGE_FOR_INCORRECT_TYPE_INDEX
            = "Incorrect type operation: ";

    @Override
    public List<FruitTransaction> parse(List<String> records) {
        return records.subList(1, records.size()).stream()
                .map(this::getFromCsvRow)
                .collect(Collectors.toList());
    }

    private FruitTransaction getFromCsvRow(String line) {
        if (line.equals(EMPTY_LINE)) {
            throw new TransactionException(EXCEPTION_MESSAGE_FOR_EMPTY_LINE + line);
        }
        String[] fields = line.split(CSV_DELIMITER);
        if (fields.length != VALID_QUANTITY_OF_FIELDS) {
            throw new TransactionException(EXCEPTION_MESSAGE_FOR_INCORRECT_LINE + line);
        }
        if (!isInteger(fields[QUANTITY_INDEX])) {
            throw new TransactionException(EXCEPTION_MESSAGE_FOR_INCORRECT_QUANTITY_INDEX
                    + fields[QUANTITY_INDEX]);
        }
        if (Integer.parseInt(fields[QUANTITY_INDEX]) <= 0) {
            throw new TransactionException(EXCEPTION_MESSAGE_FOR_NEGATIVE_QUANTITY_INDEX
                    + fields[QUANTITY_INDEX]);
        }
        if (!BALANCE.equals(fields[TYPE_INDEX]) && !PURCHASE.equals(fields[TYPE_INDEX])
                && !RETURN.equals(fields[TYPE_INDEX]) && !SUPPLY.equals(fields[TYPE_INDEX])) {
            throw new TransactionException(EXCEPTION_MESSAGE_FOR_INCORRECT_TYPE_INDEX
                    + fields[TYPE_INDEX]);
        }
        return new FruitTransaction(FruitTransaction.Operation.getByCode(fields[TYPE_INDEX]),
                fields[FRUIT_INDEX], Integer.parseInt(fields[QUANTITY_INDEX]));
    }

    private boolean isInteger(String quantity) {
        try {
            Integer.valueOf(quantity);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
