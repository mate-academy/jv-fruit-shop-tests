package fruitshop.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OperationTest {
    @Test
    void getOperationByCode_Ok() {
        Operation expected = Operation.PURCHASE;
        String givenLetter = "p";
        Operation result = Operation.getByCode(givenLetter);

        Assertions.assertEquals(result, expected,
                "Should have got "
                        + expected
                        + " but got "
                        + result);
    }

    @Test
    void getOperationByCodeThrowsException_Ok() {
        String wrongChar = "1";
        Assertions.assertThrows(RuntimeException.class, () -> Operation.getByCode(wrongChar));
    }
}
