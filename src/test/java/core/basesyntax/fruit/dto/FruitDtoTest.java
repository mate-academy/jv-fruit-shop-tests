package core.basesyntax.fruit.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class FruitDtoTest {
    private static final FruitDto FRUIT_DTO = new FruitDto("mango", 4);
    private static final FruitDto FRUIT_DTO1 = new FruitDto("mango", 5);
    private static final FruitDto FRUIT_DTO2 = new FruitDto("camellia", 4);

    @Test
    void equals_equalObject_ok() {
        assertEquals(new FruitDto("mango", 4), FRUIT_DTO);
    }

    @Test
    void equals_notEqualObjectAmount_ok() {
        assertNotEquals(FRUIT_DTO1, FRUIT_DTO);
    }

    @Test
    void equals_notEqualObjectName_ok() {
        assertNotEquals(FRUIT_DTO2, FRUIT_DTO);
    }

    @Test
    void hashcode_equalObject_ok() {
        assertEquals(new FruitDto("mango", 4), FRUIT_DTO);
    }

    @Test
    void hashcode_notEqualObjectAmount_ok() {
        assertNotEquals(FRUIT_DTO1, FRUIT_DTO);
    }

    @Test
    void hashcode_notEqualObjectName_ok() {
        assertNotEquals(FRUIT_DTO2, FRUIT_DTO);
    }
}
