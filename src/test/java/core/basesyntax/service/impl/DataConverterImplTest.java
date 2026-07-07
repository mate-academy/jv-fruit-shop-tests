package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataConverterImplTest {
    private DataConverter dataConverter;

    @BeforeEach
    public void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    public void convertToTransaction_validLines_ok() {
        List<String> inputLines = List.of(
                "type,fruit,quantity",
                "b,banana,20"
        );

        List<FruitTransaction> result = dataConverter.convertToTransaction(inputLines);
        assertEquals(1, result.size());
        assertEquals("banana", result.get(0).getFruit());
    }
}



