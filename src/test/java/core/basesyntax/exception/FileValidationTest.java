package core.basesyntax.exception;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileValidationTest {
    private static List<String> readFile;

    @BeforeClass
    public static void setUp() {
        readFile = new ArrayList<>();
    }

    @Test
    public void checkFile_ValidData_Ok() {
        readFile.add("b,banana,20");
        readFile.add("b,apple,35");
        readFile.add("p,apple,5");
        boolean actual = new FileValidation().checkFile(readFile);
        assertTrue(actual);
    }

    @Test (expected = RuntimeException.class)
    public void checkFile_IncorrectLength_NotOk() {
        readFile.add("b,20");
        readFile.add("b,apple,35");
        readFile.add("p,apple,15");
        boolean actual = new FileValidation().checkFile(readFile);
        assertFalse(actual);
        readFile.clear();
        readFile.add("p,apple,20,banana,30");
        actual = new FileValidation().checkFile(readFile);
        assertFalse(actual);
    }

    @Test (expected = RuntimeException.class)
    public void checkFile_IncorrectQuantity_NotOk() {
        readFile.add("b,apple,");
        readFile.add("p,apple,15");
        boolean actual = new FileValidation().checkFile(readFile);
        assertFalse(actual);
    }

    @Test (expected = RuntimeException.class)
    public void checkFile_NegativeQuantity_NotOk() {
        readFile.add("b,apple,-2");
        boolean actual = new FileValidation().checkFile(readFile);
        assertFalse(actual);
    }

    @Test (expected = RuntimeException.class)
    public void checkFile_EmptyFile_NotOk() {
        readFile.add(" ");
        boolean actual = new FileValidation().checkFile(readFile);
        assertFalse(actual);
    }

    @After
    public void afterEachTest() {
        readFile.clear();
    }
}
