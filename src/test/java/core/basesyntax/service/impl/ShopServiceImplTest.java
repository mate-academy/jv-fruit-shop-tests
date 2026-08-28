package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {

    @Test
     void process_validTransactions_ok() {
        List<FruitTransaction> processedTransactions = new ArrayList<>();

        OperationHandler handler =
                 transaction -> processedTransactions.add(transaction);

        OperationStrategy operationStrategy =
                 operation -> handler;

        ShopService shopService =
                 new ShopServiceImpl(operationStrategy);

        List<FruitTransaction> transactions = List.of(
                 new FruitTransaction(
                         FruitTransaction.Operation.BALANCE,
                         "banana",
                         20
                 ),
                 new FruitTransaction(
                         FruitTransaction.Operation.BALANCE,
                         "apple",
                         100
                 )
         );

        shopService.process(transactions);

        assertEquals(2, processedTransactions.size());
    }
}
