package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

class HelloWorldTest {
    @Test
    void main_ok() {
        assertDoesNotThrow(() -> HelloWorld.main(new String[]{}));
    }
}
