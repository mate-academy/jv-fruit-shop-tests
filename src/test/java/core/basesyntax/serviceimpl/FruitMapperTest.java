package core.basesyntax.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.DataMapper;
import core.basesyntax.transaction.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitMapperTest {
    private static final String DEFAULT_LINE = "type,fruit,quantity";
    private static final String LINE_WITH_BALANCE_BANANAS = "b,banana,20";
    private static final String LINE_WITH_SUPPLY_APPLES = "s,apple,20";
    private static final String LINE_WITH_RETURN_BANANAS = "r,banana,20";
    private static final String LINE_WITH_PURCHASE_APPLES = "p,apple,50";
    private static final String LINE_WITH_INVALID_TRANSACTION1 = "p,apple";
    private static final String LINE_WITH_INVALID_TRANSACTION2 = "f,ads";

    private static List<String> linesWithTransactions;
    private static List<String> linesWithInvalidTransactions;
    private static List<FruitTransaction> validFruitTransactions;
    private static DataMapper fruitMapper;

    @BeforeAll
    static void beforeAll() {
        linesWithTransactions = new ArrayList<>();
        linesWithInvalidTransactions = new ArrayList<>();
        validFruitTransactions = new ArrayList<>();
        fruitMapper = new FruitMapper();
        linesWithTransactions.add(DEFAULT_LINE);
        linesWithTransactions.add(LINE_WITH_BALANCE_BANANAS);
        linesWithTransactions.add(LINE_WITH_SUPPLY_APPLES);
        linesWithTransactions.add(LINE_WITH_RETURN_BANANAS);
        linesWithTransactions.add(LINE_WITH_PURCHASE_APPLES);

        FruitTransaction fruitTransaction1 = new FruitTransaction();
        fruitTransaction1.setFruit("banana");
        fruitTransaction1.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction1.setQuantity(20);

        FruitTransaction fruitTransaction2 = new FruitTransaction();
        fruitTransaction2.setFruit("apple");
        fruitTransaction2.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction2.setQuantity(20);

        FruitTransaction fruitTransaction3 = new FruitTransaction();
        fruitTransaction3.setFruit("banana");
        fruitTransaction3.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransaction3.setQuantity(20);

        FruitTransaction fruitTransaction4 = new FruitTransaction();
        fruitTransaction4.setFruit("apple");
        fruitTransaction4.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction4.setQuantity(50);

        validFruitTransactions.add(fruitTransaction1);
        validFruitTransactions.add(fruitTransaction2);
        validFruitTransactions.add(fruitTransaction3);
        validFruitTransactions.add(fruitTransaction4);

        linesWithInvalidTransactions.add(LINE_WITH_INVALID_TRANSACTION1);
        linesWithInvalidTransactions.add(LINE_WITH_INVALID_TRANSACTION2);
    }

    @Test
    void map_ValidData_Ok() {
        List<FruitTransaction> mappedTransactions = fruitMapper.mapData(linesWithTransactions);
        assertEquals(validFruitTransactions.size(), mappedTransactions.size());
        for (int i = 0; i < validFruitTransactions.size(); i++) {
            FruitTransaction expectedTransaction = validFruitTransactions.get(i);
            FruitTransaction actualTransaction = mappedTransactions.get(i);
            assertEquals(expectedTransaction, actualTransaction);
        }
    }

    @Test
    void map_InvalidData_NotOk() {
        assertThrows(RuntimeException.class,
                () -> fruitMapper.mapData(linesWithInvalidTransactions));
    }
}
