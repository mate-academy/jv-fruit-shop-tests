package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.DataFileParserImpl;
import core.basesyntax.service.impl.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataFileParserImplTest {
    private static final List<String> VALID_LINES = new ArrayList<>(List.of(
            "type,fruit,quantity",
            "b,banana,20"));
    private static final List<String> INVALID_LINES = new ArrayList<>();
    private static final FruitTransaction VALID_TRANSACTION = new FruitTransaction(
            FruitTransaction.Operation.BALANCE, "banana", 20);
    private DataFileParser fileParser;

    @BeforeEach
    void setUp() {
        fileParser = new DataFileParserImpl();

    }

    @Test
    void parser_validList_Ok() {
        List<FruitTransaction> output = fileParser.parse(VALID_LINES);
        FruitTransaction fruit = output.get(0);
        Assertions.assertEquals(VALID_TRANSACTION, fruit);
    }

    @Test
    void parser_emptyList_notOk() {
        RuntimeException emptyListException = assertThrows(RuntimeException.class,
                () -> fileParser.parse(INVALID_LINES));
        assertEquals("Input data is empty", emptyListException.getMessage());
    }
}
