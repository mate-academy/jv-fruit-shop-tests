package core.basesyntax.parse;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class OperationParserImplTest {
    private OperationParser operationParser;

    @BeforeEach
    void setUp() {
        operationParser = new OperationParserImpl();
    }

    @Test
    void getOperation_balance_ok() {
        assertEquals(
                FruitTransaction.Operation.BALANCE,
                operationParser.getOperation("b")
        );
    }

    @Test
    void getOperation_supply_ok() {
        assertEquals(
                FruitTransaction.Operation.SUPPLY,
                operationParser.getOperation("s")
        );
    }

    @Test
    void getOperation_purchase_ok() {
        assertEquals(
                FruitTransaction.Operation.PURCHASE,
                operationParser.getOperation("p")
        );
    }

    @Test
    void getOperation_return_ok() {
        assertEquals(
                FruitTransaction.Operation.RETURN,
                operationParser.getOperation("r")
        );
    }

    @Test
    void etOperation_invalidCode_notOk() {
        assertThrows(
                IllegalArgumentException.class,
                () -> operationParser.getOperation("x")
        );
    }

    @Test
    void etOperation_empty_notOk() {
        assertThrows(
                IllegalArgumentException.class,
                () -> operationParser.getOperation("")
        );
    }
}
