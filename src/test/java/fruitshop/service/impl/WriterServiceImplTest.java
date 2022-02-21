package fruitshop.service.impl;

import static org.junit.Assert.assertTrue;

import fruitshop.service.WriterService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class WriterServiceImplTest {
    private static final String CORRECT_PATH = "src/test/resources/output.txt";
    private static final String INCORRECT_PATH = "";

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    private WriterService writerService = new WriterServiceImpl();
    private byte[] data;

    @Test
    public void writeToFile_correctDataAndPath_ok() {
        data = new byte[]{102, 114, 117, 105, 116, 44, 113, 117, 97, 110, 116, 105, 116, 121, 13,
                10, 98, 97, 110, 97, 110, 97, 44, 49, 53, 50, 13, 10, 97, 112, 112, 108, 101, 44,
                57, 48};
        boolean actual = writerService.writeToFile(data, CORRECT_PATH);
        assertTrue(actual);
    }

    @Test
    public void writeToFile_incorrectData_notOk() {
        data = null;
        exceptionRule.expect(NullPointerException.class);
        exceptionRule.expectMessage("Data is null");
        writerService.writeToFile(data, CORRECT_PATH);
    }

    @Test
    public void writeToFile_incorrectPath_notOk() {
        data = new byte[]{102, 114, 117, 105, 116, 44, 113, 117, 97, 110, 116, 105, 116, 121, 13,
                10, 98, 97, 110, 97, 110, 97, 44, 49, 53, 50, 13, 10, 97, 112, 112, 108, 101, 44,
                57, 48};
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Can't write data to file");
        writerService.writeToFile(data, INCORRECT_PATH);
    }
}
