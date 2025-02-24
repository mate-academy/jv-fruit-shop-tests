package core.basesyntax.impl;

import core.basesyntax.service.ProcessData;
import core.basesyntax.transactor.FruitTransaction;
import core.basesyntax.transactor.Operation;
import java.util.List;
import java.util.stream.Stream;

public class ProcessDataImpl implements ProcessData {
    private static final String COMMA = ",";
    private static final int HEAD_INFO_INDEX = 1;
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_TYPE_INDEX = 1;
    private static final int INDEX_OF_AMOUNT = 2;

    @Override
    public List<FruitTransaction> process(String inputData) {
        String[] records = inputData.split(System.lineSeparator());
        return Stream.of(records).skip(HEAD_INFO_INDEX)
                .map(this::convertDataToTransaction).toList();
    }

    private Operation getOperation(String code) {
        return Stream.of(Operation.values())
                .filter(o -> o.getCode().equals(code))
                .findFirst().orElseThrow(() -> new RuntimeException(
                        "No operation following code: " + code));
    }

    private FruitTransaction convertDataToTransaction(String inputDataLine) {
        if (inputDataLine == null || inputDataLine.isEmpty()) {
            throw new IllegalArgumentException("The entrance row cannot be empty");
        }
        String[] data = inputDataLine.split(COMMA);
        if (data.length < 3) {
            throw new IllegalArgumentException("Invalid input data format: "
                    + inputDataLine);
        }
        if (data[OPERATION_INDEX].isEmpty()
                || data[FRUIT_TYPE_INDEX].isEmpty()
                || data[INDEX_OF_AMOUNT].isEmpty()) {
            throw new IllegalArgumentException("Data contains empty values: "
                    + inputDataLine);
        }
        return new FruitTransaction(getOperation(data[OPERATION_INDEX]),
                data[FRUIT_TYPE_INDEX], Integer.parseInt(data[INDEX_OF_AMOUNT]));
    }
}
