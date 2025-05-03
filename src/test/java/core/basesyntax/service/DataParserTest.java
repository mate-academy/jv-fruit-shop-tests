package core.basesyntax.service;

import core.basesyntax.errors.DataParserError;
import core.basesyntax.model.Operation;
import core.basesyntax.model.OperationType;
import core.basesyntax.service.impl.DataParserImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataParserTest {
    private static DataParser dataParser;

    @BeforeAll
    static void beforeAll() {
        dataParser = new DataParserImpl();
    }

    @Test
    void parse_nullDataString_notOk() {
        Assertions.assertThrows(DataParserError.class, () -> dataParser.parse(null));
    }

    @Test
    void parse_emptyDataString_notOk() {
        Assertions.assertThrows(DataParserError.class, () -> dataParser.parse(""));
    }

    @Test
    void parse_parametersNumberDoNotMatch_notOk() {
        // Operation & fruit
        Assertions.assertThrows(DataParserError.class, () -> dataParser.parse("b,banana"));
        // Fruit & count
        Assertions.assertThrows(DataParserError.class, () -> dataParser.parse("banana,10"));
        // Missed operation type
        Assertions.assertThrows(DataParserError.class, () -> dataParser.parse(",banana,10"));
        // Missed fruit
        Assertions.assertThrows(DataParserError.class, () -> dataParser.parse("b,,10"));
        // Missed count
        Assertions.assertThrows(DataParserError.class, () -> dataParser.parse("b,banana,"));
    }

    @Test
    void parse_invalidOperationType_notOk() {
        // Unsupported type
        Assertions.assertThrows(DataParserError.class, () -> dataParser.parse("w,banana,10"));
        // Extra space
        Assertions.assertThrows(DataParserError.class, () -> dataParser.parse("b ,banana,10"));
    }

    @Test
    void parse_invalidCount_notOk() {
        // Negative count
        Assertions.assertThrows(DataParserError.class, () -> dataParser.parse("b,banana,-10"));
        // Zero count
        Assertions.assertThrows(DataParserError.class, () -> dataParser.parse("b,banana,0"));
        // Not an integer
        Assertions.assertThrows(DataParserError.class, () -> dataParser.parse("b,banana,2+2"));
        // Real count
        Assertions.assertThrows(DataParserError.class, () -> dataParser.parse("b,banana,0.5"));
        Assertions.assertThrows(DataParserError.class, () -> dataParser.parse("b,banana,5.5"));
        // Extra space
        Assertions.assertThrows(DataParserError.class, () -> dataParser.parse("b, banana, 10"));
    }

    @Test
    void parse_validDataString_ok() {
        // Balance
        Operation operation = dataParser.parse("b,banana,50");
        Assertions.assertEquals(OperationType.BALANCE, operation.getOperationType(),
                "Does not match the type of operation: expected "
                        + OperationType.BALANCE + " exist " + operation.getOperationType());
        Assertions.assertEquals("banana", operation.getFruit(),
                "Does not match the fruit name: expected "
                        + "banana exist " + operation.getFruit());
        Assertions.assertEquals(50, operation.getCount(),
                "Does not match the fruits count: expected "
                        + "50 exist " + operation.getCount());
        // Supply
        operation = dataParser.parse("s,banana,20");
        Assertions.assertEquals(OperationType.SUPPLY, operation.getOperationType(),
                "Does not match the type of operation: expected "
                        + OperationType.SUPPLY + ", exist " + operation.getOperationType());
        // Purchase
        operation = dataParser.parse("p,banana,20");
        Assertions.assertEquals(OperationType.PURCHASE, operation.getOperationType(),
                "Does not match the type of operation: expected "
                        + OperationType.PURCHASE + ", exist " + operation.getOperationType());
        // Return
        operation = dataParser.parse("r,banana,10");
        Assertions.assertEquals(OperationType.RETURN, operation.getOperationType(),
                "Does not match the type of operation: expected "
                        + OperationType.RETURN + ", exist " + operation.getOperationType());
    }
}
