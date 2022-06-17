package core.basesyntax.service.impl;

import static core.basesyntax.model.FruitTransaction.Operation;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ProcessingData;
import java.util.ArrayList;
import java.util.List;

public class ProcessingDataImpl implements ProcessingData {
    private static final int FIRST_ROW = 0;
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final int MINIMAL_DATA_LIST_LENGTH = 2;
    private static final String SPLIT_SIGN = ",";

    @Override
    public List<FruitTransaction> parseData(List<String> data) {
        if (data == null || data.size() < MINIMAL_DATA_LIST_LENGTH) {
            throw new RuntimeException("Illegal format of data");
        }
        data.remove(FIRST_ROW);
        List<FruitTransaction> processedData = new ArrayList<>();
        data.stream()
                .map(d -> d.replaceAll(" ", "").split(SPLIT_SIGN))
                .forEach(s -> processedData.add(new FruitTransaction(
                        Operation.getOperationFromString(s[OPERATION_INDEX]),
                        s[FRUIT_INDEX],
                        Integer.parseInt(s[QUANTITY_INDEX]))));
        return processedData;
    }
}
