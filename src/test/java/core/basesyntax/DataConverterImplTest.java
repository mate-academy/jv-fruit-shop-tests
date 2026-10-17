package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import core.basesyntax.service.impl.DataConverterImpl;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DataConverterImplTest {
    @AfterEach
    void tearDown() {
        Storage.fruitModels.clear();
    }

    @Test
    void convertToTransactions_validData_ok() {
        List<String> inputLines = List.of(
                "operation,fruit,quantity",
                "b,banana,10");
        DataConverter dataConverter = new DataConverterImpl();
        List<FruitTransaction> result = dataConverter.convertToTransaction(inputLines);
        Assertions.assertNotNull(result, "Result cant be null");
        Assertions.assertEquals(1,result.size(),"Result shoud be 1");

        FruitTransaction transaction = result.get(0);
        Assertions.assertEquals(FruitTransaction.Operation.BALANCE, transaction.getOperation());
        Assertions.assertEquals("banana", transaction.getFruit());
        Assertions.assertEquals(10, transaction.getQuantity());
    }

    @Test
    void convertToTransactions_emptyList_ok() {
        List<String> inputLines = Collections.EMPTY_LIST;
        DataConverter dataConverter = new DataConverterImpl();
        List<FruitTransaction> result = dataConverter.convertToTransaction(inputLines);
        Assertions.assertNotNull(result, "Result cant be null");
        Assertions.assertTrue(result.isEmpty(),"Result should be empty");
    }
}
