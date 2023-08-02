package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.exceptions.WrongDataBaseException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParseService;
import core.basesyntax.service.implementations.ParseServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ParseServiceTest {
    private static final ParseService parseService = new ParseServiceImpl();
    private static List<String> testData;

    @BeforeEach
    void setUp() {
        testData = new ArrayList<>();
    }

    @Test
    void isFruitTransactionOkay() {
        testData.add("type,fruit,quantity");
        testData.add("b,banana,20");
        testData.add("b,apple,20");
        testData.add("s,banana,20");
        testData.add("r,banana,20");
        testData.add("r,apple,20");
        testData.add("p,banana,20");
        List<FruitTransaction> expected = new ArrayList<>();
        FruitTransaction.Operation balanceOperation = FruitTransaction.Operation.fromCode("b");
        FruitTransaction.Operation returnOperation = FruitTransaction.Operation.fromCode("r");
        FruitTransaction.Operation purchaseOperation = FruitTransaction.Operation.fromCode("p");
        FruitTransaction.Operation supplyOperation = FruitTransaction.Operation.fromCode("s");
        expected.add(new FruitTransaction(balanceOperation, "banana", 20));
        expected.add(new FruitTransaction(balanceOperation, "apple", 20));
        expected.add(new FruitTransaction(supplyOperation, "banana", 20));
        expected.add(new FruitTransaction(returnOperation, "banana", 20));
        expected.add(new FruitTransaction(returnOperation, "apple", 20));
        expected.add(new FruitTransaction(purchaseOperation, "banana", 20));
        List<FruitTransaction> transactions = parseService.parseDataToTransaction(testData);
        assertEquals(transactions, expected);
    }

    @Test
    void isFruitTransactionEmptyOkay() {
        List<FruitTransaction> transactions = parseService.parseDataToTransaction(testData);
        assertTrue(transactions.isEmpty());
    }

    @Test
    void dataWrongOperationNotOkay() {
        testData.add("type,fruit,quantity");
        testData.add("z,banana,20");
        testData.add("s,banana,20");
        assertThrows(WrongDataBaseException.class,
                () -> parseService.parseDataToTransaction(testData));
    }

    @Test
    void dataMissingQuantityNotOkay() {
        testData.add("type,fruit,quantity");
        testData.add("z,banana");
        testData.add("s,banana");
        assertThrows(WrongDataBaseException.class,
                () -> parseService.parseDataToTransaction(testData));
    }

    @Test
    void dataInvalidQuantityNotOkay() {
        testData.add("type,fruit,quantity");
        testData.add("b,banana,ILikeDota2");
        testData.add("s,banana,SomedayYouGonnaRealizeYou`ve Been sleepwalking through it all");
        assertThrows(WrongDataBaseException.class,
                () -> parseService.parseDataToTransaction(testData));
    }

    @Test
    void dataHeaderAbsentNotOkay() {
        testData.add("b,banana,20");
        testData.add("s,banana,20");
        assertThrows(WrongDataBaseException.class,
                () -> parseService.parseDataToTransaction(testData));
    }

    @Test
    void dataWrongHeaderNotOkay() {
        testData.add("type,fruit,pony");
        testData.add("b,banana,20");
        testData.add("s,banana,20");
        assertThrows(WrongDataBaseException.class,
                () -> parseService.parseDataToTransaction(testData));
    }
}
