package core.basesyntax.fruit.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class FruitDtoTest {
    private static final FruitDto FRUIT_DTO = new FruitDto("mango", 4);
    private static final FruitDto FRUIT_DTO_SAME = FRUIT_DTO;
    private static final FruitDto FRUIT_DTO1 = new FruitDto("mango", 5);
    private static final FruitDto FRUIT_DTO2 = new FruitDto("camellia", 4);
    private static final FruitDto FRUIT_DTO_NULL = null;

    @Test
    void equals_equalObject_ok() {
        assertTrue(new FruitDto("mango", 4).equals(FRUIT_DTO));
    }

    @Test
    void equals_notEqualObjectAmount_ok() {
        assertFalse(FRUIT_DTO1.equals(FRUIT_DTO));
    }

    @Test
    void equals_notEqualObjectName_ok() {
        assertFalse(FRUIT_DTO2.equals(FRUIT_DTO));
    }

    @Test
    void equals_sameNewObjects_ok() {
        assertTrue(new FruitDto("castania", 40)
                .equals(new FruitDto("castania", 40)));
    }

    @Test
    void equals_sameReferences_ok() {
        assertEquals(FRUIT_DTO == FRUIT_DTO_SAME, FRUIT_DTO.equals(FRUIT_DTO_SAME));
    }

    @Test
    void equals_nullChecker_notOk() {
        assertFalse(FRUIT_DTO.equals(null));
    }

    @Test
    void equals_differentObjectClasses_notOk() {
        assertFalse(FRUIT_DTO.equals(new FruitDtoParserImpl()));
    }

    @Test
    void equals_nullChecker_ok() {
        assertNull(FRUIT_DTO_NULL);
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

    @Test
    void hashCode_ordinaryHash_ok() {
        int expected = -1081427901;
        int actual = FRUIT_DTO.hashCode();
        assertEquals(expected, actual);
    }

}
