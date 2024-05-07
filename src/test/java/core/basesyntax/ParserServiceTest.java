package core.basesyntax;

import static core.basesyntax.servise.impl.FruitTransaction.Operation.SUPPLY;
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
    private static ParserService parserService;
    private final List<String> inputList = new ArrayList<>();

    @BeforeAll
    public static void setUp() {
        parserService = new ParserServiceImpl();
    }

    @BeforeEach
    public void beforeTest() {
        inputList.add("type,fruit,quantity");
        inputList.add("b,banana,20");
        inputList.add("b,apple,100");
        inputList.add("s,banana,100");
        inputList.add("p,banana,13");
        inputList.add("r,apple,10");
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
        inputList.set(3, "s,banana,");
        assertThrows(ParserServiceException.class, () -> parserService.parsingData(inputList));
        inputList.set(3, "s,,100");
        assertThrows(ParserServiceException.class, () -> parserService.parsingData(inputList));
        inputList.set(3, ",banana,100");
        assertThrows(ParserServiceException.class, () -> parserService.parsingData(inputList));
        inputList.set(3, "");
        assertThrows(ParserServiceException.class, () -> parserService.parsingData(inputList));
    }

    @Test
    public void parserService_invalidNumber_notOk() {
        inputList.set(3, "s,banana, 7");
        assertThrows(ParserServiceException.class, () -> parserService.parsingData(inputList));
        inputList.set(3, "s,banana,7 ");
        assertThrows(ParserServiceException.class, () -> parserService.parsingData(inputList));
        inputList.set(3, "s,banana,q7e");
        assertThrows(ParserServiceException.class, () -> parserService.parsingData(inputList));
    }

    @Test
    public void parserService_validDataCheckOutList_Ok() {
        String expectedFruit = "banana";
        int expectedQuantity = 100;
        List<FruitTransaction> actual = parserService.parsingData(inputList);
        assertAll("Test failed! List doesn't contain transaction: "
                + SUPPLY + expectedFruit + expectedQuantity,
                () -> assertEquals(SUPPLY, actual.get(2).getOperation()),
                () -> assertEquals(expectedFruit, actual.get(2).getFruit()),
                () -> assertEquals(expectedQuantity, actual.get(2).getQuantity()),
                () -> assertEquals(5, actual.size())
        );
    }
}
