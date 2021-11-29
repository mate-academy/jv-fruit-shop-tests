package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.TransactionDtoParse;
import core.basesyntax.service.Validator;
import java.util.ArrayList;
import java.util.List;

public class TransactionDtoParseImpl implements TransactionDtoParse {
    private static final int FIRST_VALUE_LINE = 1;
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_TYPE_LINE_INDEX = 1;
    private static final int FRUIT_COUNT_LINE_INDEX = 2;
    private static final String REPORT_SEPARATOR = ",";
    private final Validator validator = new ValidatorImpl();

    @Override
    public List<TransactionDto> parseData(List<String> data) {
        validator.validateInformation(data);
        List<TransactionDto> parseData = new ArrayList<>();
        for (int i = FIRST_VALUE_LINE; i < data.size(); i++) {
            String[] inputArray = data.get(i).split(REPORT_SEPARATOR);
            parseData.add(new TransactionDto(inputArray[OPERATION_INDEX],
                    new Fruit(inputArray[FRUIT_TYPE_LINE_INDEX]),
                    Integer.parseInt(inputArray[FRUIT_COUNT_LINE_INDEX])));
        }
        return parseData;
    }
}
