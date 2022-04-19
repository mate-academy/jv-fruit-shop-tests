package core.basesyntax.service.impl;

import core.basesyntax.exceptions.DataException;
import core.basesyntax.exceptions.NullException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataParser;

public class DataParserImpl implements DataParser {
    private static final int ACTIVITY_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int NUMBER_INDEX = 2;

    @Override
    public FruitTransaction getData(String data) {
        checkData(data);
        String[] info = data.split(",");
        checkSplitData(info);
        return new FruitTransaction(FruitTransaction
                .Operation
                .determineOperation(info[ACTIVITY_INDEX]),
                info[FRUIT_INDEX],
                Integer.parseInt(info[NUMBER_INDEX]));
    }

    private void checkSplitData(String[] info) {
        if (info.length != 3
                || info[ACTIVITY_INDEX].isEmpty()
                || info[FRUIT_INDEX].isEmpty()
                || info[NUMBER_INDEX].isEmpty()) {
            throw new DataException("Invalid data");
        }
    }

    private void checkData(String data) {
        if (data == null) {
            throw new NullException("Data is null");
        }
    }
}
