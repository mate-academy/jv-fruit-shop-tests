package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.ParserService;
import java.util.ArrayList;
import java.util.List;

public class ParserServiceImpl implements ParserService {
    private static final int ZERO_INDEX = 0;
    private static final int FIRST_INDEX = 1;
    private static final int SECOND_INDEX = 2;
    private static final String COMA = ",";

    @Override
    public List<Transaction> parse(List<String> lines) {
        List<Transaction> transactionList = new ArrayList<>();
        for (int i = 1; i < lines.size(); i++) {
            transactionList.add(getTransaction(lines.get(i)));
        }
        return transactionList;
    }

    private Transaction getTransaction(String string) {
        String[] splittedStrings = string.trim().split(COMA);
        return new Transaction(splittedStrings[ZERO_INDEX],
                new Fruit(splittedStrings[FIRST_INDEX]),
                Integer.parseInt(splittedStrings[SECOND_INDEX]));
    }
}
