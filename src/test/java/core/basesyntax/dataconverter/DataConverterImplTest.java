package core.basesyntax.dataconverter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class DataConverterImplTest {
    private static DataConverterImpl dc;

    @BeforeAll
    static void beforeAll() {
        dc = new DataConverterImpl();
    }
}