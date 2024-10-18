package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.ArrayList;
import java.util.List;

public class DataConverterImpl implements DataConverter {
    public static final int TYPE = 0;
    public static final int FRUIT = 1;
    public static final int QUANTITY = 2;
    public static final String COMA = ",";

    @Override
    public List<FruitTransaction> convertToTransaction(List<String> inputReport) {
        if (inputReport.isEmpty()) {
            throw new RuntimeException("List " + inputReport + "is empty");
        }
        List<FruitTransaction> result = new ArrayList<>();
        for (String fruitReport : inputReport) {
            String[] splitInputReport = fruitReport.split(COMA);
            result.add(new FruitTransaction(
                    FruitTransaction.Operation.getOperation(splitInputReport[TYPE]),
                    splitInputReport[FRUIT],
                    Integer.parseInt(splitInputReport[QUANTITY])));
        }
        return result;
    }
}

