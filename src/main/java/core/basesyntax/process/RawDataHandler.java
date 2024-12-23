package core.basesyntax.process;

import core.basesyntax.storage.FruitTransaction;
import java.util.List;
import java.util.stream.Collectors;

public class RawDataHandler {
    private static final byte ARRAY_LENGTH = 3;
    private static final int OPERATION_TYPE_INDEX = 0;
    private static final int FRUIT_TYPE_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;

    public List<FruitTransaction> parseRawData(List<String> list) {
        if (list == null) {
            throw new IllegalArgumentException("Input list cannot be null");
        }

        return list.stream()
                .map(element -> {
                    String[] arr = element.split(",");
                    if (arr.length != ARRAY_LENGTH) {
                        throw new IllegalArgumentException("Invalid format");
                    }
                    try {
                        String fruitType = arr[FRUIT_TYPE_INDEX];
                        int quantity = Integer.parseInt(arr[QUANTITY_INDEX]);
                        FruitTransaction.Operation operation =
                                FruitTransaction.Operation.getByCode(arr[OPERATION_TYPE_INDEX]);
                        return new FruitTransaction(fruitType, quantity, operation);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Quantity must be a valid integer", e);
                    } catch (NullPointerException e) {
                        throw new IllegalArgumentException("Invalid operation type", e);
                    }
                })
                .collect(Collectors.toList());
    }
}
