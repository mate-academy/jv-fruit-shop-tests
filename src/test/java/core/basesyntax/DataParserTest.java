package core.basesyntax;

import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.DataParserService;
import core.basesyntax.serviceimpl.DataParserServiceImpl;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DataParserTest {
    private static DataParserService dataParserService;
    private static List<String> testList;

    @BeforeAll
    static void beforeAll() {
        dataParserService = new DataParserServiceImpl();
    }

    @DisplayName("Valid input data parsing")
    @Test
    void dataParser_ValidData_Ok() {
        testList = List.of("b,Banana,200");
        List<FruitTransaction> expected = List.of(new FruitTransaction(
                Operation.BALANCE, "Banana", 200));
        Assertions.assertEquals(expected, dataParserService.getTransactions(testList));
    }

    @DisplayName("Invalid input data tests")
    @Test
    void dataParser_InvalidOperationType_NotOk() {
        testList = List.of("x,Banana,0");
        Assert.assertThrows(InvalidDataException.class,
                () -> dataParserService.getTransactions(testList));
    }

    @Test
    void dataParser_InvalidFieldsAmount_NotOk() {
        testList = List.of("p,Papaya");
        Assert.assertThrows(InvalidDataException.class,
                () -> dataParserService.getTransactions(testList));
    }

    @Test
    void dataParser_NegativeQuantityValue_NotOk() {
        testList = List.of("r,Watermelon,-1");
        Assert.assertThrows(InvalidDataException.class,
                () -> dataParserService.getTransactions(testList));
    }

    @Test
    void dataParser_StringQuantityValue_NotOk() {
        testList = List.of("b,Melon,nine");
        Assert.assertThrows(InvalidDataException.class,
                () -> dataParserService.getTransactions(testList));
    }

    @Test
    void getTransactions_invalidInput_invalidDataExceptionThrown() {
        testList = List.of("INVALID_INPUT");
        Assert.assertThrows(InvalidDataException.class,
                () -> dataParserService.getTransactions(testList));
    }
}
