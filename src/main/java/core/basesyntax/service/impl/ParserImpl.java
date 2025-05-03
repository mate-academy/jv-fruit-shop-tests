package core.basesyntax.service.impl;

import core.basesyntax.model.TransactionLine;
import core.basesyntax.service.Parser;
import java.util.ArrayList;
import java.util.List;

public class ParserImpl implements Parser {
    private static final int NORMAL_STRING_LENGTH = 3;
    private static final int EMPTY_LIST_SIZE = 1;

    @Override
    public List<TransactionLine> parser(List<String> list) {
        List<TransactionLine> transactionLines = new ArrayList<>();
        if (list == null || list.size() == EMPTY_LIST_SIZE) {
            throw new RuntimeException("");
        }
        list.remove(0);
        for (String stringTransaction : list) {
            new ValidatorImpl().validate(stringTransaction.trim());
            String[] splitLine = stringTransaction.split(",");
            if (splitLine.length != NORMAL_STRING_LENGTH) {
                throw new RuntimeException("line size must be = " + NORMAL_STRING_LENGTH
                    + ", but actual line size = " + splitLine.length);
            }
            transactionLines.add(new TransactionLine(splitLine[0],
                    splitLine[1], Integer.parseInt(splitLine[2])));
        }
        return transactionLines;
    }
}

