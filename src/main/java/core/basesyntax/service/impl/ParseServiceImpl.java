package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.ParseService;
import java.util.ArrayList;
import java.util.List;

public class ParseServiceImpl implements ParseService {
    @Override
    public List<Transaction> parse(List<String> readDataFromFile) {
        List<Transaction> readTransactions = new ArrayList<>();

        for (int i = 1; i < readDataFromFile.size(); i++) {
            String[] transactionLine = readDataFromFile.get(i).split(",");
            readTransactions.add(new Transaction(transactionLine[0], new Fruit(transactionLine[1]),
                    Integer.parseInt(transactionLine[2])));
        }
        return readTransactions;
    }
}
