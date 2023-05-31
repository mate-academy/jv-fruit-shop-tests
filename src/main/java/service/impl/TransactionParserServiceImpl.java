package service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import model.FruitTransaction;
import service.TransactionParserService;

public class TransactionParserServiceImpl implements TransactionParserService {
    private static final String FIRST_COLUMN_NAME = "type";
    private static final String SECOND_COLUMN_NAME = "fruit";
    private static final String THIRD_COLUMN_NAME = "quantity";
    private static final String COLUMNS_SEPARATOR = ",";
    private static final int TYPE_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final String ONLY_NUMBERS_REGEX = "[0-9]+";

    @Override
    public List<FruitTransaction> parse(String dataFromCsv) {
        if (dataFromCsv == null) {
            throw new RuntimeException("Input data shouldn't be null");
        }
        List<String> dataSplinted = List.of(dataFromCsv.split(System.lineSeparator()));
        List<FruitTransaction> resultList = new ArrayList<>();
        if (!isColumnsValid(dataSplinted.get(0))) {
            throw new RuntimeException("Invalid columns in data");
        }
        return dataSplinted.stream()
                .skip(1)
                .map(l -> {
                    if (!isRowValid(l)) {
                        throw new RuntimeException("Invalid row: " + l);
                    }
                    String[] row = l.split(COLUMNS_SEPARATOR);
                    String operation = row[TYPE_INDEX];
                    String fruit = row[FRUIT_INDEX];
                    int quantity = Integer.parseInt(row[QUANTITY_INDEX]);
                    return new FruitTransaction(FruitTransaction.Operation
                            .getByCode(operation), fruit,quantity);
                })
                .collect(Collectors.toList());
    }

    private boolean isColumnsValid(String columns) {
        String[] columnsSplit = columns.split(COLUMNS_SEPARATOR);
        return columnsSplit.length == 3
                && Objects.equals(columnsSplit[TYPE_INDEX], FIRST_COLUMN_NAME)
                && Objects.equals(columnsSplit[FRUIT_INDEX], SECOND_COLUMN_NAME)
                && Objects.equals(columnsSplit[QUANTITY_INDEX], THIRD_COLUMN_NAME);
    }

    private boolean isRowValid(String row) {
        String[] rowSplit = row.split(COLUMNS_SEPARATOR);
        return (rowSplit.length == 3)
                && rowSplit[2].matches(ONLY_NUMBERS_REGEX);
    }
}
