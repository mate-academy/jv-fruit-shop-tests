package core.basesyntax.service.impl;

import core.basesyntax.model.TransactionDto;
import core.basesyntax.service.Parser;

public class ParserImpl implements Parser {
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_NAME_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final String WORDS_SEPARATOR = ",";
    
    @Override
    public TransactionDto parseLine(String line) {
        String[] fruitData = line.split(WORDS_SEPARATOR);
        return new TransactionDto(fruitData[OPERATION_INDEX],
                fruitData[FRUIT_NAME_INDEX], Integer.parseInt(fruitData[QUANTITY_INDEX]));
    }
}
