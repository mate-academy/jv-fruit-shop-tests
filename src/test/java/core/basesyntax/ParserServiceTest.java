package core.basesyntax;

import static core.basesyntax.servise.impl.FruitTransaction.Operation.PURCHASE;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.ParserServiceException;
import core.basesyntax.servise.ParserService;
import core.basesyntax.servise.impl.FruitTransaction;
import core.basesyntax.servise.impl.ParserServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ParserServiceTest {
    private static final int INDEX_OF_TRANSACTION = 3;
    private static final String EXPECTED_FRUIT = "banana";
    private static final int EXPECTED_QUANTITY = 13;
    private static ParserService parserService;
    private static List<String> MISSING_DATA;
    private static List<String> INVALID_FORMAT_DATA;
    private static List<String> INVALID_NUMBER;
    private final List<String> inputList = new ArrayList<>();
    private int listSize;

    @BeforeAll
    public static void setUp() {
        parserService = new ParserServiceImpl();
        MISSING_DATA = List.of("s,banana,", "s,,100", ",banana,100", "");
        INVALID_FORMAT_DATA = List.of("s,banana,100,b,apple,20", "sbanana100", "s banana 100",
                "s-banana-100");
        INVALID_NUMBER = List.of("s,banana, 7", "s,banana,7 ", "s,banana,q7e", "s,banana,s");
    }

    @BeforeEach
    public void beforeTest() {
        inputList.add("type,fruit,quantity");
        inputList.add("b,banana,20");
        inputList.add("b,apple,100");
        inputList.add("s,banana,100");
        inputList.add("p," + EXPECTED_FRUIT + "," + EXPECTED_QUANTITY);
        inputList.add("r,apple,10");
        listSize = inputList.size();
    }

    @Test
    public void parserService_inputListIsEmptyOrNull_notOk() {
        assertAll("Test failed! Input List is empty",
                () -> assertThrows(ParserServiceException.class,
                        () -> parserService.parsingData(null)),
                () -> assertThrows(ParserServiceException.class,
                        () -> parserService.parsingData(new ArrayList<>()))
        );
    }

    @Test
    public void parserService_missingData_notOk() {
        MISSING_DATA.forEach(data -> {
            inputList.set(INDEX_OF_TRANSACTION, data);

            assertThrows(ParserServiceException.class,
                    () -> parserService.parsingData(inputList));
        });
    }

    @Test
    public void parserService_invalidFormatData_notOk() {
        INVALID_FORMAT_DATA.forEach(data -> {
            inputList.set(INDEX_OF_TRANSACTION, data);

            assertThrows(ParserServiceException.class,
                    () -> parserService.parsingData(inputList));
        });
    }

    @Test
    public void parserService_invalidNumber_notOk() {
        INVALID_NUMBER.forEach(data -> {
            inputList.set(INDEX_OF_TRANSACTION, data);

            assertThrows(ParserServiceException.class, () -> parserService.parsingData(inputList));
        });
    }

    @Test
    public void parserService_validData_Ok() {
        List<FruitTransaction> actual = parserService.parsingData(inputList);

        assertAll("Test failed! List doesn't contain transaction: "
                + PURCHASE + EXPECTED_FRUIT + EXPECTED_QUANTITY,
                () -> assertEquals(PURCHASE, actual.get(INDEX_OF_TRANSACTION).getOperation()),
                () -> assertEquals(EXPECTED_FRUIT, actual.get(INDEX_OF_TRANSACTION).getFruit()),
                () -> assertEquals(EXPECTED_QUANTITY, actual.get(INDEX_OF_TRANSACTION)
                        .getQuantity()),
                () -> assertEquals(--listSize, actual.size())
        );
    }
}
