package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class FruitMapperImplTest {
    private static final String BANANA = "banana";
    private final FruitMapper fruitMapper = new FruitMapperImpl();

    @Test
    void mapLinesIntoTransactions_validStringList_ok() {
        List<String> csvData = new ArrayList<>();
        csvData.add("b,banana,20");
        csvData.add("s,banana,100");
        csvData.add("p,banana,5");

        final List<FruitTransaction> expectedTransactions = new ArrayList<>();
        FruitTransaction balanceTransaction = new FruitTransaction();
        balanceTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        balanceTransaction.setFruit(BANANA);
        balanceTransaction.setQuantity(20);

        FruitTransaction supplyTransaction = new FruitTransaction();
        supplyTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        supplyTransaction.setFruit(BANANA);
        supplyTransaction.setQuantity(100);

        FruitTransaction purchaseTransaction = new FruitTransaction();
        purchaseTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        purchaseTransaction.setFruit(BANANA);
        purchaseTransaction.setQuantity(5);

        expectedTransactions.add(balanceTransaction);
        expectedTransactions.add(supplyTransaction);
        expectedTransactions.add(purchaseTransaction);

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
