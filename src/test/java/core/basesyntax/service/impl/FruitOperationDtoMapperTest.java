package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitOperationDto;
import java.util.function.Function;
import org.junit.Test;

public class FruitOperationDtoMapperTest {
    private static final String DATA = "b,apple,10";

    @Test
    public void fruitOperationDtoMapper_getData_Ok() {
        Function<String, FruitOperationDto> mapper = new FruitOperationDtoMapper();
        FruitOperationDto actual
                = new FruitOperationDto(FruitOperationDto.Type.BALANCE, "apple", 10);
        FruitOperationDto expected = mapper.apply(DATA);
        assertEquals(expected, actual);
    }
}
