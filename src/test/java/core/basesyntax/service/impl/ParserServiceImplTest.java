package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParserServiceImplTest {
    private ParserServiceImpl parserService;

    @BeforeEach
    void setUp() {
        parserService = new ParserServiceImpl();
    }

    @Test
    void getFromCsvRow_null_notOk() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                    () -> parserService.getFromCsvRow(null));
        assertEquals("The file contains no data", exception.getMessage());
    }

    @Test
    void getFromCsvRow_InvalidFormat_NotOk() {
        List<String> list = List.of(" type,fruit,quantity", "b,banana");
        IllegalArgumentException illegalArgumentException =
                assertThrows(IllegalArgumentException.class,
                    () -> parserService.getFromCsvRow(list));

        assertEquals("Invalid data format in line: b,banana",
                illegalArgumentException.getMessage());
    }

    @Test
    void getFromCsvRow_unknownOperation_notOk() {
        List<String> list = List.of(" type,fruit,quantity", "x,banana,20");
        assertThrows(IllegalArgumentException.class, () -> parserService.getFromCsvRow(list));
    }

    @Test
    void getFromCsvRow_negativeQuantity_notOk() {
        List<String> list = List.of(" type,fruit,quantity", "b,banana,-20");
        IllegalArgumentException illegalArgumentException =
                assertThrows(IllegalArgumentException.class,
                        () -> parserService.getFromCsvRow(list));
        assertEquals("Quantity can't be negative: -20", illegalArgumentException.getMessage());
    }

    @Test
    void getFromCsvRow_invalidQuantity_notOk() {
        List<String> list = List.of(" type,fruit,quantity", "b,banana,$$$");
        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> parserService.getFromCsvRow(list));
        assertEquals("Invalid quantity value: '$$$' in line: b,banana,$$$",
                runtimeException.getMessage());
    }

    @Test
    void getFromCsvRow_Ok() {
        List<String> list = List.of(" type,fruit,quantity", "b,banana,20", "s,apple,5");
        List<FruitTransaction> listFruit = parserService.getFromCsvRow(list);
        assertEquals(2, listFruit.size());
        FruitTransaction firstTransaction = listFruit.get(0);
        assertEquals(Operation.BALANCE,firstTransaction.getOperation());
        assertEquals("banana", firstTransaction.getFruit());
        assertEquals(20, firstTransaction.getQuantity());

        FruitTransaction secondTransaction = listFruit.get(1);
        assertEquals(Operation.SUPPLY,secondTransaction.getOperation());
        assertEquals("apple", secondTransaction.getFruit());
        assertEquals(5, secondTransaction.getQuantity());
    }
}

