package core.basesyntax.service.impl;

import core.basesyntax.model.Transaction;
import core.basesyntax.service.Parser;
import core.basesyntax.service.Validator;
import java.util.ArrayList;
import java.util.List;

public class ParserImpl implements Parser {
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private final Validator validator;

    public ParserImpl(Validator validator) {
        this.validator = validator;
    }

    @Override
    public List<Transaction> parse(List<String> lines) {
        List<Transaction> transactions = new ArrayList<>();
        validator.validate(lines);
        for (String line : lines) {
            String[] fruitOperation = line.split(",");
            transactions.add(new Transaction(fruitOperation[OPERATION_INDEX],
                    fruitOperation[FRUIT_INDEX],
                    Integer.parseInt(fruitOperation[QUANTITY_INDEX])));
        }
        return transactions;
    }
}
