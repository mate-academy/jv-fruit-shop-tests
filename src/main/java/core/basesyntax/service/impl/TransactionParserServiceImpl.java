package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParserService;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionParserServiceImpl implements TransactionParserService {
    private static final int FIRST_LINE = 0;
    private static final String DEFAULT_TITLE = "type,fruit,quantity";
    private static final int TYPE_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final String COMMA_SEPARATOR = ",";
    private static final int DEFAULT_LINE_LENGTH = 3;
    private static final String VALID_LINE_REGEX = "^[bspr],[a-z]+,\\d+$";

    @Override
    public List<FruitTransaction> parse(List<String> lines) {
        if (lines == null) {
            throw new RuntimeException("Input list is null");
        }
        if (!isTitleValid(lines)) {
            throw new RuntimeException("Invalid names of columns");
        }
        return lines.stream()
                .skip(1)
                .map(l -> {
                    if (!isRowValid(l) || l.split(COMMA_SEPARATOR).length != DEFAULT_LINE_LENGTH) {
                        throw new RuntimeException("Invalid data in lines");
                    }
                    return rowToFruitTransaction(l);
                })
                .collect(Collectors.toList());
    }

    private boolean isTitleValid(List<String> lines) {
        return lines.get(FIRST_LINE).equals(DEFAULT_TITLE);
    }

    private boolean isRowValid(String line) {
        return line.matches(VALID_LINE_REGEX);
    }

    private FruitTransaction rowToFruitTransaction(String line) {
        String[] row = line.split(COMMA_SEPARATOR);
        return new FruitTransaction(
                FruitTransaction.Operation.getByCode(row[TYPE_INDEX]),
                row[FRUIT_INDEX],
                Integer.parseInt(row[QUANTITY_INDEX]));
    }
}
