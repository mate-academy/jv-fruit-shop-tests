package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.DataConverter;

class DataConverterImplTest {
    private DataConverter dataConverter;
    private List<String> lines;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
        lines = List.of(
                "operation,fruit,quantity",
                "b,banana,100",
                "s,apple,50",
                "p,banana,30",
                "r,apple,10"
        );
    }

    @Test
    void convert_validLines_Ok() {
        List<FruitTransaction> transactions =
                dataConverter.convertToTransaction(lines);
        FruitTransaction fruitTransaction = transactions.get(0);
        assertEquals(4, transactions.size());
        assertEquals(FruitTransaction.Operation.BALANCE,
                fruitTransaction.getOperation());
        assertEquals("banana", fruitTransaction.getFruit());
        assertEquals(100, fruitTransaction.getQuantity());
    }

    @Test
    void convert_linesIsNull_NotOk() {
        assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(null));
    }

    @Test
    void convert_linesIsEmpty_NotOk() {
        assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(new ArrayList<>()));
    }

    @Test
    void convert_invalidOperation_NotOk() {
        List<String> list = List.of("operation,fruit,quantity",
                "x,banana,100");
        assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(list));
    }

    @Test
    void convert_negativeQuantity_NotOk() {
        List<String> list = List.of("operation,fruit,quantity",
                "b,banana,-8");
        assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(list));
    }

    @Test
    void convert_invalidFormatOfLine_NotOk() {
        List<String> list = List.of("operation,fruit,quantity",
                "b,banana");
        assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(list));
    }
}
