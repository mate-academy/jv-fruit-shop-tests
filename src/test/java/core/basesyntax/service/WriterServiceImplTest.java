package core.basesyntax.service;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private static final String FIRST_LINE = "fruit,quantity";
    private static final String WORD_DELI = ",";
    private static final String NEW_LINE = System.lineSeparator();
    private static final String PATH_TEST = "src/test/resources/";
    private static final String FILE_NAME = "fruitsResult.csv";
    private static WriterService writerService;

    @BeforeAll
    static void beforeAll() {
        writerService = new WriterServiceImpl();
    }

    @Test
    void writeToFile_test_OK() {
        String fruitTotal = createStringResult();
        boolean result = writerService.writeToFile(fruitTotal, PATH_TEST + FILE_NAME);
        Assert.assertTrue("An error occurred while creating the file: "
                + PATH_TEST + FILE_NAME, result);
    }

    @Test
    void writeToFile_emptyFileName_NotOK() {
        Throwable thrown = Assert.assertThrows(RuntimeException.class, () -> {
            writerService.writeToFile("", "");
        });
        Assert.assertNotNull(thrown.getMessage());
    }

    public String createStringResult() {
        StringBuilder builder = new StringBuilder();
        builder.append(FIRST_LINE).append(NEW_LINE);
        builder.append("apple").append(WORD_DELI)
                .append("80").append(NEW_LINE);
        return builder.toString();
    }
}
