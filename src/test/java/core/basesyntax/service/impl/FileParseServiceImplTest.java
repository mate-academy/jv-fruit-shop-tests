package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FileParseService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileParseServiceImplTest {
    private FileParseService fileParseService;

    @BeforeEach
    public void setUp() {
        fileParseService = new FileParseServiceImpl();
    }

    @Test
    public void parseDataFromCV_ValidData_ReturnsCorrectFruitTransactions() {
        List<String> testData = Arrays.asList(
                "b,Apple,10",
                "s,Banana,5",
                "p,Orange,8"
        );

        List<FruitTransaction> expectedTransactions = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "Apple", 10),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "Banana", 5),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "Orange", 8)
        );

        List<FruitTransaction> result = fileParseService.parseDataFromCV(testData);

        assertEquals(expectedTransactions, result);
    }

    @Test
    public void parseDataFromCV_EmptyData_ReturnsEmptyList() {
        List<String> emptyData = Arrays.asList();

        List<FruitTransaction> result = fileParseService.parseDataFromCV(emptyData);

        assertEquals(0, result.size());
    }
}
