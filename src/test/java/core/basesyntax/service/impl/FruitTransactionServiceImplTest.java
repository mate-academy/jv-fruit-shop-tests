package core.basesyntax.service.impl;

import core.basesyntax.model.dto.FruitDto;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionServiceImplTest {
    private static final String filePath = "src/main/resources/dataInputTest.csv";
    private FruitTransactionServiceImpl fruitTransactionService;
    private List<FruitDto> result = new ArrayList<>();

    @BeforeEach
    void setUp() {
        fruitTransactionService = new FruitTransactionServiceImpl();
    }

    @Test
    public void parseInfo_Ok() {
        List<FruitDto> result = fruitTransactionService.parser("b,banana,20");
        List<FruitDto> expected = new ArrayList<>();
        expected.add(new FruitDto("banana", 20, "b"));
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void parsingNullOrEmpty() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            fruitTransactionService.parser(null);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            fruitTransactionService.parser("");
        });
    }
}

