package core.basesyntax.tests.service;

import static org.junit.Assert.assertFalse;

import core.basesyntax.service.WritingService;
import core.basesyntax.service.impl.WritingServiceImpl;
import org.junit.Test;

public class WritingTest {
    private WritingService writingService = new WritingServiceImpl();

    @Test
    public void writingService_CorrectData_Ok() {
        String filePath = "src/test/java/test_resouces/toFile.csv";
        String text = "test Text";
        boolean thrown = false;
        try {
            writingService.writeToFile(text, filePath);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        assertFalse(thrown);
    }

    @Test (expected = RuntimeException.class)
    public void writingService_NullDataOfPath_NotOk() {
        writingService.writeToFile("123", null);
    }

    @Test (expected = RuntimeException.class)
    public void writingService_NullDataOfText_NotOk() {
        String filePath = "src/test/java/test_resoucestoFile.csv";
        writingService.writeToFile(null, filePath);
    }

    @Test (expected = RuntimeException.class)
    public void writingService_EmptyDataOfPath_NotOk() {
        writingService.writeToFile("1324", "");
    }
}
