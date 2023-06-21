package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParseService;
import core.basesyntax.service.TransactionException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParseServiceImplTest {
    private static final String HEADER = "type,fruit,quantity";
    private static final String INVALID_LINE = "b,banana,500,earlyGreen";
    private static final String VALID_LINE = "s,banana,500";
    private static final String LINE_WITH_NEGATIVE_QUANTITY_INDEX = "s,apple,-20";
    private static final String LINE_WITH_INCORRECT_QUANTITY_INDEX = "s,banana,green";
    private static final String LINE_WITH_INCORRECT_TYPE_INDEX = "x,apple,20";
    private static final String EMPTY_LINE = "";
    private static List<String> records;
    private ParseService parseService;

    @BeforeEach
    void setUp() {
        parseService = new ParseServiceImpl();
        records = new ArrayList<>();
        records.add(HEADER);
    }

    @Test
    void parser_invalidLine_notOk() {
        records.add(INVALID_LINE);
        Assertions.assertThrows(TransactionException.class, () -> parseService.parse(records));
    }

    @Test
    void parser_validFruitTransaction_ok() {
        records.add(VALID_LINE);
        List<FruitTransaction> fruitTransactions = parseService.parse(records);
        FruitTransaction fruitTransaction = fruitTransactions.get(0);
        Assertions.assertEquals(FruitTransaction.class, fruitTransaction.getClass());
    }

    @Test
    void parser_invalidQuantityIndex_notOk() {
        records.add(LINE_WITH_INCORRECT_QUANTITY_INDEX);
        Assertions.assertThrows(TransactionException.class, () -> parseService.parse(records));
    }

    @Test
    void parser_invalidQuantityIndex_negativeValue_notOk() {
        records.add(LINE_WITH_NEGATIVE_QUANTITY_INDEX);
        Assertions.assertThrows(TransactionException.class, () -> parseService.parse(records));
    }

    @Test
    void parser_invalidTypeOperation_notOk() {
        records.add(LINE_WITH_INCORRECT_TYPE_INDEX);
        Assertions.assertThrows(TransactionException.class, () -> parseService.parse(records));
    }

    @Test
    void parser_emptyLine_notOk() {
        records.add(EMPTY_LINE);
        Assertions.assertThrows(TransactionException.class, () -> parseService.parse(records));
    }
}
