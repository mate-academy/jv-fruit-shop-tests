package core.basesyntax.process;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.storage.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RawDataHandlerTest {
    private static List<String> list;
    private static RawDataHandler rawDataHandler;

    @BeforeAll
    static void setUp() {
        rawDataHandler = new RawDataHandler();
    }

    @Test
    void parseRawData_parseCorrectFile_ok() {
        list = List.of("b,apple,80",
                "b,banana,100");
        FruitTransaction appleFruitTransaction = new FruitTransaction("apple", 80,
                FruitTransaction.Operation.BALANCE);
        FruitTransaction bananaFruitTransaction = new FruitTransaction("banana", 100,
                FruitTransaction.Operation.BALANCE);
        List<FruitTransaction> fruitTransactionList = rawDataHandler.parseRawData(list);
        assertEquals(fruitTransactionList.get(0), appleFruitTransaction);
        assertEquals(fruitTransactionList.get(1), bananaFruitTransaction);
    }

    @Test
    void parseRawData_parseCorruptedFile_notOk() {
        list = null;
        assertThrows(IllegalArgumentException.class, () -> rawDataHandler.parseRawData(list));
        list = List.of("");
        assertThrows(IllegalArgumentException.class, () -> rawDataHandler.parseRawData(list));
        list = List.of("b,,one");
        assertThrows(IllegalArgumentException.class, () -> rawDataHandler.parseRawData(list));
        list = List.of("10, banana, 10");
        assertThrows(IllegalArgumentException.class, () -> rawDataHandler.parseRawData(list));
    }
}
