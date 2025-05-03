package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.service.TransactionDataParse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TransactionDataParseImplTest {
    private TransactionDataParse transactionDataParse = new TransactionDataParseImpl();

    @Test
    void parseTransaction_ok() {
        FruitTransaction fruitTransactionValid =
                new FruitTransaction("apple", 10, Operation.SUPPLY);
        String validLine = "s,apple,10";
        FruitTransaction fruitTransactionActual = transactionDataParse.parseTransaction(validLine);

        Assertions.assertEquals(fruitTransactionActual, fruitTransactionValid);
    }

    @Test
    void parseTransaction_notOk() {
        FruitTransaction fruitTransactionValid =
                new FruitTransaction("apple", 1, Operation.SUPPLY);
        String validLine = "s,apple,10";
        FruitTransaction fruitTransactionActual = transactionDataParse.parseTransaction(validLine);

        Assertions.assertNotEquals(fruitTransactionActual, fruitTransactionValid);
    }
}
