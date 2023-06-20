package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParseService;
import core.basesyntax.service.TransactionException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ParseServiceImplTest {
    private static final String EXCEPTION_MESSAGE
            = TransactionException.class.toString();
    private static final String HEADER = "type,fruit,quantity";
    private static final String INVALID_LINE = "b,banana,500,earlyGreen";
    private static final String VALID_LINE = "s,banana,500";
    private static final String LINE_WITH_NEGATIVE_QUANTITY_INDEX = "s,apple,-20";
    private static final String LINE_WITH_INCORRECT_QUANTITY_INDEX = "s,banana,green";
    private static final String LINE_WITH_INCORRECT_TYPE_INDEX = "x,apple,20";
    private static final String EMPTY_LINE = "";
    private static ParseService parseService;
    private static List<String> records;

    @BeforeAll
    static void beforeAll() {
        parseService = new ParseServiceImpl();
        records = new ArrayList<>();
        records.add(HEADER);
    }

    @Test
    void parser_validLine_notOk() {
        records.add(INVALID_LINE);
        Assertions.assertThrows(TransactionException.class, () -> parseService.parse(records),
                String.format("%s should throw for incorrect line: ", EXCEPTION_MESSAGE));
    }

    @Test
    void parser_returnValidFruitTransaction_Ok() {
        records.add(VALID_LINE);
        List<FruitTransaction> fruitTransactions = parseService.parse(records);
        Assertions.assertEquals(1, fruitTransactions.size());
        FruitTransaction fruitTransaction = fruitTransactions.get(0);
        Assertions.assertEquals(FruitTransaction.Operation.SUPPLY, fruitTransaction.getOperation());
        Assertions.assertEquals("banana",fruitTransaction.getFruit());
        Assertions.assertEquals(500, fruitTransaction.getQuantity());
    }

    @Test
    void parser_validQuantityIndexForParsing_notOk() {
        records.add(LINE_WITH_INCORRECT_QUANTITY_INDEX);
        Assertions.assertThrows(TransactionException.class, () -> parseService.parse(records),
                String.format("%s should throw for incorrect input parameter of parsing",
                        EXCEPTION_MESSAGE));
    }

    @Test
    void parser_negativeQuantityIndexValue_notOk() {
        records.add(LINE_WITH_NEGATIVE_QUANTITY_INDEX);
        Assertions.assertThrows(TransactionException.class, () -> parseService.parse(records),
                String.format("%s should throw for negative value of quantity",
                EXCEPTION_MESSAGE));
    }

    @Test
    void parser_validTypeOperation_notOk() {
        records.add(LINE_WITH_INCORRECT_TYPE_INDEX);
        Assertions.assertThrows(TransactionException.class, () -> parseService.parse(records),
                String.format("%s should throw for incorrect operation type parameter",
                        EXCEPTION_MESSAGE));
    }

    @Test
    void parser_emptyRecords() {
        records.add(EMPTY_LINE);
        Assertions.assertThrows(TransactionException.class, () -> parseService.parse(records),
                String.format("%s should throw for empty records", EXCEPTION_MESSAGE));
    }
}
