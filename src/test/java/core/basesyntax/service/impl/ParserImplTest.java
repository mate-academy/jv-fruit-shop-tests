package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.Parser;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ParserImplTest {
    private static final List<String> VALID_FILE_DATA = List
            .of("r,Mango,100", "s,Banana,55", "p,Apple,150");
    private static final List<String> INVALID_FILE_DATA = List
            .of("r,Mango", "s,Banana", "p,Apple");
    private static final List<String> INVALID_QUANTITY_DATA = List
            .of("r,Mango,-100", "s,Mango,100", "p,Apple,200");
    private static final List<String> INVALID_FRUIT_NAME_DATA = List
            .of("r,,-100", "s,*,100", "p,/,200");
    private static final List<String> INVALID_OPERATION_DATA = List
            .of("b,Mango,100", "g,Banana,55", "p,Apple,150");
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_NAME_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final int VALID_QUANTITY_150 = 150;
    private static final String BANANA = "Banana";
    private static Parser parser;

    @BeforeAll
    static void beforeAll() {
        parser = new ParserImpl();
    }

    @Test
    void parseListToTransactionList_validData_ok() {
        List<FruitTransaction> fruitTransactions = parser
                .parseListToTransactionList(VALID_FILE_DATA);
        assertEquals(Operation.RETURN, fruitTransactions.get(OPERATION_INDEX).getOperation());
        assertEquals(BANANA, fruitTransactions.get(FRUIT_NAME_INDEX).getFruit());
        assertEquals(VALID_QUANTITY_150, fruitTransactions.get(QUANTITY_INDEX).getQuantity());
    }

    @Test
    void parseListToTransactionList_emptyData_ok() {
        List<FruitTransaction> fruitTransactions = parser
                .parseListToTransactionList(new ArrayList<>());
        assertTrue(fruitTransactions.isEmpty());
    }

    @Test
    void parseListToTransactionList_invalidData_notOk() {
        assertThrows(RuntimeException.class,
                () -> parser.parseListToTransactionList(INVALID_FILE_DATA),
                "Should be RuntimeException for this data: " + INVALID_FILE_DATA);
    }

    @Test
    void parseListToTransactionList_invalidQuantity_notOk() {
        assertThrows(RuntimeException.class,
                () -> parser.parseListToTransactionList(INVALID_QUANTITY_DATA),
                "Should be RuntimeException for this quantity: " + INVALID_QUANTITY_DATA);
    }

    @Test
    void parseListToTransactionList_invalidFruitName_notOk() {
        assertThrows(RuntimeException.class,
                () -> parser.parseListToTransactionList(INVALID_FRUIT_NAME_DATA),
                "Should be RuntimeException for this data: " + INVALID_FRUIT_NAME_DATA);
    }

    @Test
    void parseListToTransactionList_invalidOperation_notOk() {
        assertThrows(NoSuchElementException.class,
                () -> parser.parseListToTransactionList(INVALID_OPERATION_DATA),
                "Should be NoSuchElementException for this data: " + INVALID_OPERATION_DATA);
    }
}
