package service.impl;

import java.util.List;
import java.util.stream.Collectors;
import model.FruitTransaction;
import service.DataConverter;

public class DataConverterImpl implements DataConverter {
    private static final int OPERATION_TYPE_INDEX = 0;
    private static final int FRUIT_TYPE_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final String SEPARATOR = ",";
    private static final int HEADER_INDEX = 1;
    private static final String PROPER_HEADER = "type,fruit,quantity";

    @Override
    public List<FruitTransaction> convertToTransaction(List<String> fileData) {
        if (!fileData.get(0).trim().equals(PROPER_HEADER)) {
            throw new IllegalArgumentException("The header is not appropriate");
        }
        return fileData.stream()
                .skip(HEADER_INDEX)
                .map(this::createFruitTransactionInstance)
                .collect(Collectors.toList());
    }

    private FruitTransaction createFruitTransactionInstance(String lineFromFile) {
        try {
            String[] fields = lineFromFile.split(SEPARATOR);
            String fruit = fields[FRUIT_TYPE_INDEX];
            int quantity = Integer.parseInt(fields[QUANTITY_INDEX]);

            if (fruit.isBlank() || quantity < 0) {
                throw new IllegalArgumentException(
                        "Name of fruit is absent or the quantity is negative");
            }

            return new FruitTransaction(fields[OPERATION_TYPE_INDEX].trim(),
                    fields[FRUIT_TYPE_INDEX], quantity);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Data from the file is corrupted", e);
        }
    }
}
