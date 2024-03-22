package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.record.Operation;
import core.basesyntax.record.Record;
import core.basesyntax.service.RecordMapper;
import java.util.ArrayList;
import java.util.List;

public class FruitRecordMapper implements RecordMapper {
    private static final int INDEX_OF_FIRST_ELEMENT = 0;
    private static final int MIN_LENGTH_OF_PRODUCT_NAME = 3;
    private String line;

    @Override
    public List<Record> getRecordsFromLines(List<String> lines) {
        if (lines.isEmpty() || lines.get(INDEX_OF_FIRST_ELEMENT).isEmpty()) {
            return List.of();
        }
        List<Record> records = new ArrayList<>();
        for (String line : lines) {
            this.line = line;
            String[] record = line.split(COMMA);
            if (record.length != MIN_LENGTH_OF_PRODUCT_NAME) {
                throw new IllegalArgumentException("Incorrect line format. Line=" + line);
            }
            Fruit fruit = new Fruit(getProductName(record), getQuantity(record));
            records.add(new Record(getOperation(record), fruit));
        }
        return records;
    }

    private String getProductName(String[] record) {
        String productName = record[PRODUCT_INDEX];
        if (productName.length() < MIN_LENGTH_OF_PRODUCT_NAME) {
            throw new IllegalArgumentException("Product name length cannot be less than 3. Line="
                    + line);
        }
        return record[PRODUCT_INDEX];
    }

    private int getQuantity(String[] record) {
        int quantity;
        try {
            quantity = Integer.parseInt(record[QUANTITY_INDEX]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Quantity is not a number. Line=" + line);
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative. Line=" + line);
        }
        return quantity;
    }

    private Operation getOperation(String[] record) {
        if (record[OPERATION_INDEX].isEmpty()) {
            throw new IllegalArgumentException("Operation type cannot be empty. Line=" + line);
        }
        return Operation.getByCode(record[OPERATION_INDEX]);
    }
}
