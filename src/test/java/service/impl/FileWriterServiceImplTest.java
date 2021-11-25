package service.impl;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import service.FileWriterService;

public class FileWriterServiceImplTest {
    private static FileWriterService fileWriterService;
    private static List<String> expected;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        fileWriterService = new FileWriterServiceImpl();
        expected = new ArrayList<>();
    }

    @Test
    public void filerWriter_emptyWay_notOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("can`t write in file");
        fileWriterService.write("");
    }

    @Test
    public void filerWriter_nullWay_notOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("the path cannot be null");
        fileWriterService.write(null);
    }

    @Test
    public void filerWriter_validData_tOk() {
        Assert.assertTrue(fileWriterService.write("src\\test\\resources\\output.csv"));
    }

    @After
    public void tearDown() throws Exception {
        expected.clear();
    }
}
