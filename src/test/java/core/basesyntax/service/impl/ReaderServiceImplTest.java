package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReaderServiceImplTest {
    private static ReaderServiceImpl reader;

    @BeforeAll
    public static void setUp() {
        reader = new ReaderServiceImpl();
    }

    @Test
    public void readFileTest() {
        List<String> lines = reader.read("src/test/resources/reportToReadTest.csv");
        assertEquals(3, lines.size());
        assertEquals("b,apple,10", lines.get(1));
    }

    @Test
    public void readNonExistingFileTest() {
        assertThrows(RuntimeException.class,
                () -> reader.read("src/test/resources/missingFile.csv"));
    }
}
