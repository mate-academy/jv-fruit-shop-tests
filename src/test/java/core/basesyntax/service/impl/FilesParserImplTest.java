
package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FilesParser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FilesParserImplTest {
    private static FilesParser filesParser;

    @BeforeAll
    static void beforeAll() {
        filesParser = new FilesParserImpl();
    }

    @Test
    void parseFruitTransactionValidData_Ok() {
        List<String> testData = new ArrayList<>(Arrays.asList(
                "type,fruit,quantity",
                "b,banana,100",
                "b,apple,50"));
        List<FruitTransaction> expectedResult =
                List.of(
                        new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 100),
                        new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 50)
                );
        assertIterableEquals(expectedResult,filesParser.parseFruitTransaction(testData));
    }

    @Test
    void parseFruitTransactionNotValidData_NotOk() {
        List<String> testData = new ArrayList<>(Arrays.asList(
                "type,fruit,quantity",
                "b,banana,100",
                "d,apple,50"));
        assertThrows(RuntimeException.class,
                () -> filesParser.parseFruitTransaction(testData));
    }

    @Test
    void parseFruitTransactionDataWithoutHeadLine_NotOk() {
        List<String> testData = new ArrayList<>(Arrays.asList(
                "b,banana,100",
                "b,apple,50"));
        List<FruitTransaction> expectedResult =
                List.of(
                        new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 100),
                        new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 50)
                );
        assertNotEquals(expectedResult,filesParser.parseFruitTransaction(testData));
    }
}
