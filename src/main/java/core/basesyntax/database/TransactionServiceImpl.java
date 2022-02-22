package core.basesyntax.database;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.TransactionService;
import java.util.ArrayList;
import java.util.List;

public class TransactionServiceImpl implements TransactionService {
    @Override
    public List<FruitTransaction> transactionData(List<String> readFile) {
        if (readFile == null) {
            throw new NullPointerException("Input list can't be null.");
        } else if (readFile.isEmpty()) {
            throw new RuntimeException("Input list can't be empty.");
        }
        List<FruitTransaction> transactions = new ArrayList<>();
        String[] temp;
        readFile.remove(0);
        for (String line : readFile) {
            if (!line.matches("[a-z],[a-z]+,[0-9]+")) {
                throw new RuntimeException("Format of line isn't valid.");
            }
            temp = line.split(",");
            transactions.add(FruitTransaction.of(temp[0], temp[1], Integer.parseInt(temp[2])));
        }
        return transactions;
    }
}
