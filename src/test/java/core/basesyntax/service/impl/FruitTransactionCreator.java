package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import java.util.List;

public class FruitTransactionCreator {
    protected static List<FruitTransaction> createValidTransactions() {
        FruitTransaction firstValidfruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,"banana",20);
        FruitTransaction secondValidfruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,"banana",10);
        FruitTransaction thirdValidfruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,"banana",13);
        FruitTransaction fourthValidfruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN,"banana",2);
        return List.of(firstValidfruitTransaction,
                secondValidfruitTransaction,thirdValidfruitTransaction,
                fourthValidfruitTransaction);
    }
}
