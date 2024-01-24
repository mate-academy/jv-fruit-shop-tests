package core.basesyntax;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MainTest {

    @Test
    void mainDoesNotThrowException() {
        Assertions.assertDoesNotThrow(() -> Main.main(new String[]{}));
    }
}
