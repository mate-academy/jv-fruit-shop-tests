package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static FileWriterImpl fileWriterImpl;
    private static final String TEST_STRING = "TEST_STRING";
    private static final String EMPTY_PATH = "";
    private static final String CORRECT_PATH = "src/test/resources/report_fruit_shop.csv";
    private static final String CORRECT_STRING = "b,apple,100";

    @BeforeClass
    public static void beforeClass() throws Exception {
        fileWriterImpl = new FileWriterImpl();
    }

    @Test
    public void write_emptyPath_Ok() {
        try {
            fileWriterImpl.write(TEST_STRING, EMPTY_PATH);
        } catch (RuntimeException e) {
            return;
        }
        fail("should throw exception: 'The path to the database is not correct' + path");
    }

    @Test
    public void write_nullPath_Ok() {
        try {
            fileWriterImpl.write(TEST_STRING, null);
        } catch (RuntimeException e) {
            return;
        }
        fail("should throw exception: 'The path to the database is not correct' + path");
    }

    @Test
    public void write_correctPath_Ok() {
        boolean expected = true;
        assertEquals(expected, fileWriterImpl.write(TEST_STRING, CORRECT_PATH));
    }

    @Test
    public void write_correctString_Ok() {
        boolean expected = true;
        assertEquals(expected, fileWriterImpl.write(CORRECT_STRING, CORRECT_PATH));
    }

    @Test
    public void write_emptyString_Ok() {
        try {
            fileWriterImpl.write(EMPTY_PATH, CORRECT_PATH);
        } catch (RuntimeException e) {
            return;
        }
        fail("should throw exception: 'Incorrect data in the string to write'");
    }
}
