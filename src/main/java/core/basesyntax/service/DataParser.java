package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;

public interface DataParser {
    int ACTIVITY_INDEX = 0;
    int FRUIT_INDEX = 1;
    int NUMBER_INDEX = 2;

    static FruitTransaction getData(String data) {
        String[] info = data.split(",");
        return new FruitTransaction(FruitTransaction
                .Operation
                .determineOperation(info[ACTIVITY_INDEX]),
                info[FRUIT_INDEX],
                Integer.parseInt(info[NUMBER_INDEX]));
    }
}
