package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidSeparatorException;
import core.basesyntax.exceptions.NullDataException;
import core.basesyntax.models.FruitTransaction;
import core.basesyntax.models.Operation;
import core.basesyntax.service.Parser;
import core.basesyntax.service.impl.TransactionParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionParserTest {
    private static final String FRUIT_NAME = "apple";
    private static final String SEPARATOR = ",";
    private static final String INVALID_SEPARATOR = ";";
    private static final int QUANTITY_FIRST = 10;
    private static final int QUANTITY_SECOND = 20;
    private static final int NEGATIVE_QUANTITY = -10;
    private static Parser<FruitTransaction> parser;
    private static List<String> data;
    private static List<FruitTransaction> exceptedResults;

    @BeforeEach
    void init() {
        data = new ArrayList<>();
        parser = new TransactionParser();
        exceptedResults = new ArrayList<>();
        Storage.fruitStorage.clear();
    }

    @Test
    void parseData_suitableData_Ok() {
        String parsingData = Operation.BALANCE.getCode()
                + SEPARATOR + FRUIT_NAME + SEPARATOR + QUANTITY_FIRST;
        data.add(parsingData);
        parsingData = Operation.SUPPLY.getCode()
                + SEPARATOR + FRUIT_NAME + SEPARATOR + QUANTITY_SECOND;
        data.add(parsingData);

        FruitTransaction temporaryTransaction = new FruitTransaction();
        temporaryTransaction.setFruit(FRUIT_NAME);
        temporaryTransaction.setQuantity(QUANTITY_FIRST);
        temporaryTransaction.setOperation(Operation.BALANCE);
        exceptedResults.add(temporaryTransaction);

        temporaryTransaction = new FruitTransaction();
        temporaryTransaction.setFruit(FRUIT_NAME);
        temporaryTransaction.setQuantity(QUANTITY_SECOND);
        temporaryTransaction.setOperation(Operation.SUPPLY);
        exceptedResults.add(temporaryTransaction);

        assertEquals(exceptedResults, parser.parseData(data),
                "The results needs to be the same");
    }

    @Test
    void parseData_negativeData_notOk() {
        String parsingData = Operation.BALANCE.getCode() + SEPARATOR
                + FRUIT_NAME + SEPARATOR + NEGATIVE_QUANTITY;
        data.add(parsingData);
        assertThrows(RuntimeException.class,
                () -> parser.parseData(data),
                "The fruit num can't be less the 0");
    }

    @Test
    void parseData_nullData_notOk() {
        assertThrows(NullDataException.class,
                () -> parser.parseData(null),
                "Data for pursing can't be null");
    }

    @Test
    void parseData_invalidSeparator_notOk() {
        String parsingData = Operation.BALANCE.getCode() + INVALID_SEPARATOR
                + FRUIT_NAME + INVALID_SEPARATOR + QUANTITY_FIRST;
        data.add(parsingData);
        assertThrows(InvalidSeparatorException.class,
                () -> parser.parseData(data),
                "The separator must be ','");
    }
}
