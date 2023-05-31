package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ParserImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ParserImplTest {
    private static Parser parser;
    private static List<String> testInputList;
    private static FruitTransaction bananaBalanceOperation;
    private static FruitTransaction bananaSupplyOperation;
    private static FruitTransaction bananaPurchaseOperation;
    private static FruitTransaction bananaReturnOperation;

    @BeforeAll
    static void beforeAll() {
        parser = new ParserImpl();
        testInputList = new ArrayList<>();
        bananaBalanceOperation = new FruitTransaction();
        bananaSupplyOperation = new FruitTransaction();
        bananaPurchaseOperation = new FruitTransaction();
        bananaReturnOperation = new FruitTransaction();
    }

    @BeforeEach
    void setUp() {
        testInputList.add("type,fruit,quantity");
        testInputList.add("b,banana,100");
        testInputList.add("s,banana,50");
        testInputList.add("p,banana,70");
        testInputList.add("r,banana,10");

        bananaBalanceOperation.setOperation(FruitTransaction.Operation.getCode("b"));
        bananaBalanceOperation.setFruit("banana");
        bananaBalanceOperation.setAmount(100);

        bananaSupplyOperation.setOperation(FruitTransaction.Operation.getCode("s"));
        bananaSupplyOperation.setFruit("banana");
        bananaSupplyOperation.setAmount(50);

        bananaPurchaseOperation.setOperation(FruitTransaction.Operation.getCode("p"));
        bananaPurchaseOperation.setFruit("banana");
        bananaPurchaseOperation.setAmount(70);

        bananaReturnOperation.setOperation(FruitTransaction.Operation.getCode("r"));
        bananaReturnOperation.setFruit("banana");
        bananaReturnOperation.setAmount(10);
    }

    @AfterEach
    void tearDown() {
        testInputList.clear();
    }

    @Test
    void parse_emptyList_Ok() {
        List<FruitTransaction> actual = parser.parse(new ArrayList<>());
        assertTrue(actual.isEmpty());
    }

    @Test
    void parse_validDataList_Ok() {
        List<FruitTransaction> actual = parser.parse(testInputList);
        assertEquals(4,actual.size());

        assertTrue(actual.contains(bananaBalanceOperation));
        assertTrue(actual.contains(bananaSupplyOperation));
        assertTrue(actual.contains(bananaPurchaseOperation));
        assertTrue(actual.contains(bananaReturnOperation));
    }
}
