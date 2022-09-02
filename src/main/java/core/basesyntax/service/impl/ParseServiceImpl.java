package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.ParseService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ParseServiceImpl implements ParseService {
    private static final int COLUMN_NAMES_LINE = 0;
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;

    @Override
    public List<Transaction> transactionsParser(List<String> lines) {
        List<Transaction> transactionList = new ArrayList<>();
        for (int i = 1; i < lines.size(); i++) {
            transactionList.add(getTransaction(lines.get(i)));
        }
        return transactionList;
    }

    private Transaction getTransaction(String string) {
        String[] split = string.trim().split(",");
        return new Transaction(split[OPERATION_INDEX],
                new Fruit(split[FRUIT_INDEX]),
                Integer.parseInt(split[QUANTITY_INDEX]));
    }
}
