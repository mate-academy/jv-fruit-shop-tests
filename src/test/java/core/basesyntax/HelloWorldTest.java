package core.basesyntax;

import core.basesyntax.fortests.FileReaderTest;
import core.basesyntax.fortests.FileReaderTestImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Feel free to remove this class and create your own.
 */
public class HelloWorldTest {
    private static final String FILE1 = "file1valid.csv";
    private static final String FILE2 = "file2empty.csv";
    private static final String FILE3 = "file3invalid.csv";
    private static final String FILE4 = "file4invalid.csv";
    private static final String FILE5 = "file5invalid.csv";
    private static final String FILE6 = "file6invalid.csv";
    private static final String FILE7 = "file7invalid.csv";
    private static final String EXPECTED_RESULT = "expectedResult.csv";
    private static final String FINAL_REPORT = "finalReport.csv";

    private HelloWorld helloWorld = new HelloWorldImpl();

    @Test
    void transfer_FileNotExists_NotOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> {
                    helloWorld.getFileOfReport("FILE9");
                });
    }

    @Test
    void transfer_EmptyFile_NotOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> {
                    helloWorld.getFileOfReport(FILE2);
                });
    }

    @Test
    void transfer_FieldFruitIsNull_NotOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> {
                    helloWorld.getFileOfReport(FILE3);
                });
    }

    @Test
    void transfer_AmountIsLessThanZero_NotOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> {
                    helloWorld.getFileOfReport(FILE4);
                });
    }

    @Test
    void transfer_BalanceEqualsZero_NotOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> {
                    helloWorld.getFileOfReport(FILE5);
                });
    }

    @Test
    void transfer_OperationIsNull_NotOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> {
                    helloWorld.getFileOfReport(FILE6);
                });
    }

    @Test
    void transfer_AmountIsLessThanZero_NotNull() {
        Assertions.assertThrows(RuntimeException.class,
                () -> {
                    helloWorld.getFileOfReport(FILE7);
                });
    }

    @Test
    void transfer_ValidData_Ok() {
        FileReaderTest fileReaderTest = new FileReaderTestImpl();
        String expected = fileReaderTest.readFile(EXPECTED_RESULT);
        FileReaderTest fileReaderTest1 = new FileReaderTestImpl();
        String actual = fileReaderTest1.readFile(FINAL_REPORT);
        helloWorld.getFileOfReport(FILE1);
        Assert.assertEquals(actual, expected);
    }
}
