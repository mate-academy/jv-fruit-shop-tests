package fruitshop.service;

import fruitshop.service.impl.ReaderServiceImpl;
import fruitshop.service.impl.WriterServiceImpl;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WriterServiceTest {
    private static final String WRITE_TEST_FILE_NAME = "testFile.csv";
    private static final int FIRST_SYMBOL_INDEX = 0;
    private static final int DECREASED_INDEX = 2;
    private ReaderService reader;
    private WriterService writer;

    @BeforeAll
    void setUp() {
        reader = new ReaderServiceImpl();
        writer = new WriterServiceImpl();
    }

    @Test
    void writerServiceImpl_Ok() {
        String expected = "TEST TEXT"
                + System.lineSeparator()
                + "12345";
        writer.writeToFile(expected, WRITE_TEST_FILE_NAME);
        String actual = reader.readFromFile(WRITE_TEST_FILE_NAME)
                .stream()
                .map(string -> string + System.lineSeparator())
                .collect(Collectors.joining());
        actual = actual.substring(FIRST_SYMBOL_INDEX,
                actual.length() - DECREASED_INDEX);

        Assertions.assertEquals(actual, expected,
                "Writer should have written "
                        + expected
                        + " but wrote "
                        + actual);
    }
}
