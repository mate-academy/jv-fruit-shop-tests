package core.basesyntax.service.transaction.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.ParsingException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.transaction.FruitTransactionParsingService;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvFruitTransactionParsingServiceTest {
    private static final List<String> EMPTY_DATA = Collections.emptyList();
    private static final List<String> INVALID_DATA = List.of("asd,a", "asdas.as,12asd", "sadasd");
    private static final List<String> INVALID_HEADERS_DATA = List.of("asd,a", "b,banana,20");
    private static final List<String> INVALID_TRANSACTIONS_DATA = List.of(
            "type,fruit,quantity",
            "ad",
            "asdq1"
    );
    private static final List<String> ONLY_HEADERS_DATA = List.of("type,fruit,quantity");
    private static final List<String> VALID_DATA = List.of(
            "type,fruit,quantity",
            "b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,13",
            "r,apple,10",
            "p,apple,20",
            "p,banana,5",
            "s,banana,50"
    );
    private static final List<FruitTransaction> EXPECTED_VALID_RESULT = List.of(
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13),
            new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5),
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50)
    );

    private static FruitTransactionParsingService fruitTransactionParsingService;

    @BeforeAll
    static void beforeAll() {
        fruitTransactionParsingService = new CsvFruitTransactionParsingService();
    }

    @Test
    void parse_emptyData_ok() {
        assertThrows(
                ParsingException.class,
                () -> fruitTransactionParsingService.parse(EMPTY_DATA)
        );
    }

    @Test
    void parse_invalidData_notOk() {
        assertThrows(
                ParsingException.class,
                () -> fruitTransactionParsingService.parse(INVALID_DATA)
        );
    }

    @Test
    void parse_invalidHeadersData_notOk() {
        assertThrows(
                ParsingException.class,
                () -> fruitTransactionParsingService.parse(INVALID_HEADERS_DATA)
        );
    }

    @Test
    void parse_invalidTransactionsData_notOk() {
        assertThrows(
                ParsingException.class,
                () -> fruitTransactionParsingService.parse(INVALID_TRANSACTIONS_DATA)
        );
    }

    @Test
    void parse_onlyHeadersData_ok() {
        List<FruitTransaction> actual = fruitTransactionParsingService.parse(ONLY_HEADERS_DATA);
        assertEquals(0, actual.size());
    }

    @Test
    void parse_validData_ok() {
        List<FruitTransaction> actual = fruitTransactionParsingService.parse(VALID_DATA);
        assertIterableEquals(EXPECTED_VALID_RESULT, actual);
    }
}
