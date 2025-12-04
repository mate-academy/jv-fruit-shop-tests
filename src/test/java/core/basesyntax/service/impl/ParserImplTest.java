package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.Parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParserImplTest {
    private Parser parser;

    @BeforeEach
    void setUp() {
        parser = new ParserImpl();
    }

    @Test
    void parse_dataIsNull_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> parser.parse(null));
    }
}
