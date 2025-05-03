package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.FruitTransactionParser;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FruitTransactionParserImpl implements FruitTransactionParser {
    private static final int TITLE_LINE_INDEX = 0;
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final String SPLIT_COMMA = ",";

    @Override
    public List<FruitTransaction> parseList(List<String> data) {
        List<String> info = new ArrayList<>(data);
        info.remove(TITLE_LINE_INDEX);
        return info.stream()
                .map(this::getFruitTransaction)
                .collect(Collectors.toList());
    }

    private FruitTransaction getFruitTransaction(String data) {
        String[] info = data.split(SPLIT_COMMA);
        if (Integer.parseInt(info[QUANTITY_INDEX]) < 0) {
            throw new RuntimeException("Quantity cannot be negative");
        }
        return new FruitTransaction(Operation.parseOperation(info[OPERATION_INDEX]),
                info[FRUIT_INDEX], Integer.parseInt(info[QUANTITY_INDEX]));
    }
}
