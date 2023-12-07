package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.FruitShopException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private static final String HEADER = "type,fruit,quantity";
    private final DataConverter dataConverter = new DataConverterImpl();
    private List<String> dataFromFile;

    @BeforeEach
    void setUp() {
        dataFromFile = new ArrayList<>();
        dataFromFile.add(HEADER);
    }

    @Test
    void convertData_validData_ok() {
        dataFromFile.addAll(List.of(
                "    b,banana,20",
                "    b,apple,100",
                "    s,banana,100",
                "    p,banana,13",
                "    r,apple,10",
                "    p,apple,20",
                "    p,banana,5",
                "    s,banana,50"));
        List<FruitTransaction> expected =
                List.of(new FruitTransaction("b", "banana", 20),
                        new FruitTransaction("b", "apple", 100),
                        new FruitTransaction("s", "banana", 100),
                        new FruitTransaction("p", "banana", 13),
                        new FruitTransaction("r", "apple", 10),
                        new FruitTransaction("p", "apple", 20),
                        new FruitTransaction("p", "banana", 5),
                        new FruitTransaction("s", "banana", 50));
        List<FruitTransaction> actual = dataConverter.convertData(dataFromFile);
        assertEquals(expected, actual);
    }

    @Test
    void convertData_invalidData_notOk() {
        dataFromFile.addAll(List.of("EQG$oBq*~6~WNDoX",
                "54K7~nedZ89972tI",
                "ci4H6H2bl0YL5{g$",
                "PMd~~Jnw12KS}~bj"));
        assertThrows(FruitShopException.class,
                () -> dataConverter.convertData(dataFromFile));
    }

    @Test
    void convertData_notExistOperationType_notOk() {
        dataFromFile.add("j,banana,20");
        assertThrows(FruitShopException.class,
                () -> dataConverter.convertData(dataFromFile));
    }

    @Test
    void convertData_negativeQuantity_notOk() {
        dataFromFile.add("b,banana,-20");
        assertThrows(FruitShopException.class,
                () -> dataConverter.convertData(dataFromFile));
    }

    @Test
    void convertData_nullData_notOk() {
        assertThrows(FruitShopException.class,
                () -> dataConverter.convertData(null));
    }

    @Test
    void convertData_emptyData_notOk() {
        assertThrows(FruitShopException.class,
                () -> dataConverter.convertData(Collections.emptyList()));
    }
}
