package core.basesyntax.transaction;

import core.basesyntax.Operation;
import java.util.ArrayList;
import java.util.List;

public class TransactionConverterImpl implements TransactionConverter {
    private final int operationIndex = 0;
    private final int fruitIndex = 1;
    private final int quntityIndex = 2;
    private final String commaSeparatop = ",";
    private final int firstTransactionLineIndex = 1;

    @Override
    public List<FruitTransaction> convert(List<String> lines) {
        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        for (int i = firstTransactionLineIndex; i < lines.size(); i++) {
            String[] line = lines.get(i).split(commaSeparatop);
            Operation operation = Operation.getOperationFromCode(line[operationIndex]);
            FruitTransaction transaction = new FruitTransaction(operation, line[fruitIndex],
                    Integer.parseInt(line[quntityIndex]));
            fruitTransactionList.add(transaction);
        }
        return fruitTransactionList;
    }
}
