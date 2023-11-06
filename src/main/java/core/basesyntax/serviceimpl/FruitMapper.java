package core.basesyntax.serviceimpl;

import core.basesyntax.service.DataMapper;
import core.basesyntax.transaction.FruitTransaction;
import java.util.ArrayList;
import java.util.List;

public class FruitMapper implements DataMapper {
    private static final String COMMA_SPLIT = ",";
    private static final String INVALID_TRANSACTION_MESSAGE = "The next "
            + "transaction is invalid: ";

    public List<FruitTransaction> mapData(List<String> lines) {
        List<FruitTransaction> fruitTransactionsList = new ArrayList<>();
        lines.remove(0);
        for (String line : lines) {
            String[] lineData = line.split(COMMA_SPLIT);
            if (lineData.length != 3) {
                throw new RuntimeException(INVALID_TRANSACTION_MESSAGE + line);
            }
            FruitTransaction fruitTransaction = new FruitTransaction();
            fruitTransaction.setOperation(FruitTransaction.Operation.getOperation(lineData[0]));
            fruitTransaction.setFruit(lineData[1]);
            fruitTransaction.setQuantity(Integer.parseInt(lineData[2]));
            fruitTransactionsList.add(fruitTransaction);
        }
        return fruitTransactionsList;
    }
}
