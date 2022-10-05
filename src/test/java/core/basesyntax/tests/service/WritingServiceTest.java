package core.basesyntax.tests.service;

import core.basesyntax.service.WritingService;
import core.basesyntax.service.impl.WritingServiceImpl;
import org.junit.Before;
import org.junit.Test;

public class WritingServiceTest {
    private WritingService writingService;

    @Before
    public void setUp() throws Exception {
        writingService = new WritingServiceImpl();
    }

    @Test
    public void writingService_CorrectData_Ok() {
        String filePath = "src/test/java/test_resouces/toFile.csv";
        String text = "test Text";
        writingService.writeToFile(text, filePath);
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
