package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationDefStrategy;
import core.basesyntax.service.DataConverter;
import java.util.ArrayList;
import java.util.List;

public class DataConverterImpl implements DataConverter {
    private static final int HEADER_INDEX = 0;
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private OperationDefStrategy operationDefStrategy;

    public DataConverterImpl(OperationDefStrategy operationDefStrategy) {
        this.operationDefStrategy = operationDefStrategy;
    }

    @Override
    public List<FruitTransaction> convertToTransaction(List<String> list) {
        List<FruitTransaction> result = new ArrayList<>();
        list.remove(HEADER_INDEX);
        for (String line : list) {
            String[] parts = line.split(",");
            result.add(new FruitTransaction(
                    operationDefStrategy.get(parts[OPERATION_INDEX]),
                    parts[FRUIT_INDEX],
                    Integer.parseInt(parts[QUANTITY_INDEX])
            ));
        }
        return result;
    }
}
