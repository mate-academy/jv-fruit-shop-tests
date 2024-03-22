package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParser;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionParserImplTest {
    private TransactionParser testTransactionParser;
    private final List<String> inputTestList = List.of("type,fruit,quantity", "    b,banana,20",
            "    b,apple,100", "    s,banana,100", "    p,banana,13", "    r,apple,10",
            "    p,apple,20", "    p,banana,5", "    s,banana,50");
    private final List<String> inputTestListWithNegativeQuantity = List.of("type,fruit,quantity",
            "    b,banana,20", "    b,apple,100", "    s,banana,-100", "    p,banana,13",
            "    r,apple,10", "    p,apple,20", "    p,banana,5", "    s,banana,50");
    private final List<String> inputListForOperationTypeTest = List.of("type,fruit,quantity",
            "12345b,banana,20", "&&&ll  -b,apple,100", "007s,banana,100", "BIGp,banana,13",
            "simple_r,apple,10", "HTTPp,apple,20", "ship_p,banana,5", "buss,banana,50");
    private final List<String> inputTestListWithIncorrectOperationType =
            List.of("type,fruit,quantity", "    b,banana,20", "    b,apple,100",
                    "    s,banana,100", "    f,banana,13", "    r,apple,10",
                    "    p,apple,20", "    p,banana,5", "    s,banana,50");

    @BeforeEach
    void setUp() {
        testTransactionParser = new FruitTransactionParserImpl();
    }

    @Test
    void converterToTransactions_checkedCorrectSkipFirstLineInInputList_Ok() {
        String firstLine = "type,fruit,quantity";
        List<FruitTransaction> fruitTransactions
                = testTransactionParser.converterToTransactions(inputTestList);
        boolean contains = fruitTransactions.contains(firstLine);
        assertFalse(contains, "Can not skip firstLine in input data");
    }

    @Test
    void converterToTransactions_inputNegativeQuantityPerOperation_NotOk() {
        assertThrows(RuntimeException.class,
                () -> testTransactionParser
                        .converterToTransactions(inputTestListWithNegativeQuantity));
    }

    @Test
    void converterToTransactions_checkIgnoreSymbolBeforeOperationType_OK() {
        int expectedSize = 8;
        int actualSize = testTransactionParser
                .converterToTransactions(inputListForOperationTypeTest)
                .size();
        assertEquals(expectedSize, actualSize,
                "Incorrect symbol before operation type in read data");
    }

    @Test
    void converterToTransactions_checkIncorrectOperationType_NotOk() {
        assertThrows(IllegalArgumentException.class, () -> testTransactionParser
                .converterToTransactions(inputTestListWithIncorrectOperationType));
    }

    @Test
    void converterToTransactions_correctConvertDataToObject_Ok() {
        List<String> inputList =
                List.of("type,fruit,quantity", "    b,banana,20");
        List<FruitTransaction> fruitTransactions
                = testTransactionParser.converterToTransactions(inputList);
        FruitTransaction.Operation expectedOperation =
                FruitTransaction.Operation.BALANCE;
        String expectedFruit = "banana";
        int expectedQuantity = 20;

        FruitTransaction.Operation actualOperation
                = fruitTransactions.get(0).getOperation();
        String actualFruit = fruitTransactions.get(0).getFruit();
        int actualQuantity = fruitTransactions.get(0).getQuantity();

        assertEquals(expectedOperation, actualOperation,
                "Can not convert data to object");
        assertEquals(expectedFruit, actualFruit,
                "Can not convert data to object");
        assertEquals(expectedQuantity, actualQuantity,
                "Can not convert data to object");
    }
}
