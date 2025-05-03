package core.basesyntax.service.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dto.FruitTransactionDto;
import core.basesyntax.exception.DataFileCorruptedException;
import core.basesyntax.model.Operation;
import java.util.List;
import org.junit.jupiter.api.Test;

class FruitTransactionParserTest {
    private static final List<String> VALID_DATA = List.of(
            "type,fruit,quantity",
            "b,banana,107",
            "b,apple,100",
            "s,banana,51",
            "p,banana,10"
    );
    private static final List<String> INVALID_DATA = List.of(
            "fruit,quantity",
            "banana,107",
            "apple,100",
            "banana,100"
    );
    private static final List<FruitTransactionDto> EXPECTED_LIST_OF_TRANSACTIONS = List.of(
            new FruitTransactionDto(Operation.BALANCE, "banana", 107),
            new FruitTransactionDto(Operation.BALANCE, "apple", 100),
            new FruitTransactionDto(Operation.SUPPLY, "banana", 51),
            new FruitTransactionDto(Operation.PURCHASE, "banana", 10)
    );
    private static final String TRANSACTION_ERROR_MESSAGE = "Invalid number of columns in data";
    private static final String DATA_NOT_FOUND_ERROR_MESSAGE = "No data found in file";
    private final FruitTransactionParser transactionParser = new FruitTransactionParser();

    @Test
    void parse_dtoValidData_Ok() {
        List<FruitTransactionDto> actualTransactions = transactionParser.parse(VALID_DATA);
        assertIterableEquals(EXPECTED_LIST_OF_TRANSACTIONS, actualTransactions);
    }

    @Test
    void parse_InvalidData_NotOk() {
        var exception = assertThrows(DataFileCorruptedException.class, () ->
                transactionParser.parse(INVALID_DATA));
        assertEquals(TRANSACTION_ERROR_MESSAGE, exception.getMessage());
    }

    @Test
    void parse_DataIsNull_NotOk() {
        var exception = assertThrows(DataFileCorruptedException.class, () ->
                transactionParser.parse(null));
        assertEquals(DATA_NOT_FOUND_ERROR_MESSAGE, exception.getMessage());
    }
}
