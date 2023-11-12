package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class FruitMapperImplTest {
    private final FruitMapper fruitMapper = new FruitMapperImpl();

    @Test
    void mapLinesIntoTransactions_validStringList_ok() {
        List<String> csvData = new ArrayList<>();
        csvData.add("b,banana,20");
        csvData.add("s,banana,100");
        csvData.add("p,banana,5");

        final List<FruitTransaction> expectedTransactions = new ArrayList<>();
        FruitTransaction transaction1 = new FruitTransaction();
        transaction1.setOperation(FruitTransaction.Operation.BALANCE);
        transaction1.setFruit("banana");
        transaction1.setQuantity(20);

        FruitTransaction transaction2 = new FruitTransaction();
        transaction2.setOperation(FruitTransaction.Operation.SUPPLY);
        transaction2.setFruit("banana");
        transaction2.setQuantity(100);

        FruitTransaction transaction3 = new FruitTransaction();
        transaction3.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction3.setFruit("banana");
        transaction3.setQuantity(5);

        expectedTransactions.add(transaction1);
        expectedTransactions.add(transaction2);
        expectedTransactions.add(transaction3);

        List<FruitTransaction> actualTransactions = fruitMapper.mapLinesIntoTransactions(csvData);

        assertEquals(expectedTransactions, actualTransactions);
    }

    @Test
    void mapLinesIntoTransactions_incompleteLine_notOk() {
        List<String> csvData = new ArrayList<>();
        csvData.add("b,banana");

        assertThrows(RuntimeException.class, () -> fruitMapper.mapLinesIntoTransactions(csvData));
    }

    @Test
    void mapLinesIntoTransactions_emptyLine_notOk() {
        List<String> csvData = new ArrayList<>();
        csvData.add("");

        assertThrows(RuntimeException.class, () -> fruitMapper.mapLinesIntoTransactions(csvData));
    }
}
