package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.CantWorkWithThisFileException;
import core.basesyntax.service.FruitsFromFile;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitsFromFileImplTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final int NUMBER = 123;
    private FruitsFromFile fruitsFromFile;

    @BeforeEach
    void setUp() {
        fruitsFromFile = new FruitsFromFileImpl();
    }

    @Test
    void getFruitsFromFile_NullInputList_NotOk() {
        List<FruitTransaction> fruitTransactions = null;
        assertThrows(CantWorkWithThisFileException.class,
                () -> fruitsFromFile.getFruitsFromFile(fruitTransactions));
    }

    @Test
    void getFruitsFromFile_CorrectInput_Ok() {
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        fruitTransactions.add(new FruitTransaction(
                Operation.BALANCE,BANANA, NUMBER));
        fruitTransactions.add(new FruitTransaction(
                Operation.BALANCE,APPLE, NUMBER));
        fruitTransactions.add(new FruitTransaction(
                Operation.SUPPLY,BANANA, NUMBER));
        fruitTransactions.add(new FruitTransaction(
                Operation.PURCHASE,BANANA, NUMBER));
        fruitTransactions.add(new FruitTransaction(
                Operation.RETURN,APPLE, NUMBER));
        fruitTransactions.add(new FruitTransaction(
                Operation.PURCHASE,APPLE, NUMBER));
        fruitTransactions.add(new FruitTransaction(
                Operation.PURCHASE,BANANA, NUMBER));
        fruitTransactions.add(new FruitTransaction(
                Operation.SUPPLY,BANANA, NUMBER));

        List<String> expected = new ArrayList<>();
        expected.add(BANANA);
        expected.add(APPLE);
        List<String> result = fruitsFromFile.getFruitsFromFile(fruitTransactions);
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), result.get(i));
        }
    }
}
