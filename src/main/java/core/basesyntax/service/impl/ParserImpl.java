package core.basesyntax.service.impl;

import core.basesyntax.model.TransactionDto;
import core.basesyntax.service.Parser;
import core.basesyntax.service.Validator;
import java.util.ArrayList;
import java.util.List;

public class ParserImpl implements Parser<TransactionDto> {
    private static final String INPUT_TITLE = "type,fruit,quantity";
    private static final String COMA_SEPARATOR = ",";
    private static final int INDEX_OF_OPERATION = 0;
    private static final int INDEX_OF_PRODUCT = 1;
    private static final int INDEX_OF_QUANTITY = 2;
    private final Validator validator;

    public ParserImpl(Validator validator) {
        this.validator = validator;
    }

    @Override
    public List<TransactionDto> parseLines(List<String> inputData) {
        validator.validate(inputData);
        List<TransactionDto> transactionS = new ArrayList<>();
        inputData.stream()
                .filter(e -> !e.equals(INPUT_TITLE))
                .map(e -> e.split(COMA_SEPARATOR))
                .forEach(e -> transactionS.add(new TransactionDto(e[INDEX_OF_OPERATION],
                        e[INDEX_OF_PRODUCT], Integer.parseInt(e[INDEX_OF_QUANTITY]))));
        return transactionS;
    }
}
