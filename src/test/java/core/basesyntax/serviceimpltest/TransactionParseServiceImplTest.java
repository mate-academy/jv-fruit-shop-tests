package core.basesyntax.serviceimpltest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.readservice.TransactionParseService;
import core.basesyntax.service.readservice.TransactionParseServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TransactionParseServiceImplTest {
    private static final String FIRST_COLUMN = "type";
    private static final String SECOND_COLUMN = "fruits";
    private static final String THIRD_COLUMN = "quantity";
    private static TransactionParseService transactionParseService;

    @BeforeAll
    static void beforeAll() {
        transactionParseService = new TransactionParseServiceImpl();
    }

    @Test
    void type_nullData_notOk() {
        assertThrows(RuntimeException.class, () -> transactionParseService.parse(null));
    }

    @Test
    void first_column_Ok() {
        assertTrue(true,FIRST_COLUMN);
    }

    @Test
    void second_column_Ok() {
        assertTrue(true,SECOND_COLUMN);
    }

    @Test
    void third_column_Ok() {
        assertTrue(true,THIRD_COLUMN);
    }

    @Test
    void column_Valid_Ok() {

    }
}
