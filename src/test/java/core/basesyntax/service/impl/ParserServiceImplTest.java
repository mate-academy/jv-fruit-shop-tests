package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.exception.ParserException;
import core.basesyntax.service.ParserService;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ParserServiceImplTest {
    private static final String VALID_FILE_TITLE = "type,fruit,quantity" + System.lineSeparator();
    private static final String PATH_WRONG_COLUMNS_FILE = "src/main/resources/wrongColumns.txt";
    private static final String CONTENT_WRONG_COLUMNS_FILE =
            "a,b,c" + System.lineSeparator() + "b,banana,30";
    private static final String PATH_WRONG_ROW_FILE = "src/main/resources/wrongRow.txt";
    private static final String CONTENT_WRONG_ROW_FILE
            = VALID_FILE_TITLE
            + "10,abc,20" + System.lineSeparator() + "ww,asd,aas1";
    private static final String PATH_CORRECT_FILE = "src/main/resources/correct.txt";
    private static final String CONTENT_CORRECT_FILE
            = VALID_FILE_TITLE
            + "1,apple,20" + System.lineSeparator() + "33,orange,3";
    private static final String COLUMNS_DELIMITER = ",";
    private ParserService parserService;

    @Before
    public void setUp() {
        parserService = new ParserServiceImpl();
        try (BufferedWriter bwWrongColumns
                     = new BufferedWriter(new FileWriter(PATH_WRONG_COLUMNS_FILE));
                BufferedWriter bwWrongRow
                        = new BufferedWriter(new FileWriter(PATH_WRONG_ROW_FILE));
                BufferedWriter bwCorrect
                        = new BufferedWriter(new FileWriter(PATH_CORRECT_FILE))) {
            bwWrongColumns.write(CONTENT_WRONG_COLUMNS_FILE);
            bwWrongRow.write(CONTENT_WRONG_ROW_FILE);
            bwCorrect.write(CONTENT_CORRECT_FILE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @After
    public void after() {
        try {
            Files.deleteIfExists(Path.of(PATH_WRONG_ROW_FILE));
            Files.deleteIfExists(Path.of(PATH_CORRECT_FILE));
            Files.deleteIfExists(Path.of(PATH_WRONG_COLUMNS_FILE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void parseDataFromCsv_argumentIsNull_notOk() {
        try {
            parserService.parseInput(null);
        } catch (ParserException e) {
            return;
        }
        fail("parseDataFromCsv should throw an ParserException if the argument is null");
    }

    @Test
    public void parseDataFromCsv_wrongColumnsFormat_notOk() {
        try {
            parserService.parseInput(PATH_WRONG_COLUMNS_FILE);
        } catch (ParserException e) {
            return;
        }
        fail("parseDataFromCsv method should check the columns for correctness");
    }

    @Test
    public void parseDataFromCsv_wrongRowFormat_notOk() {
        try {
            parserService.parseInput(PATH_WRONG_ROW_FILE);
        } catch (ParserException e) {
            return;
        }
        fail("parseDataFromCsv method should check the row for correctness");
    }

    @Test
    public void parseDataFromCsv_correctData_ok() {
        String actual = VALID_FILE_TITLE
                + parserService.parseInput(PATH_CORRECT_FILE).stream()
                    .map(l -> l.stream().collect(Collectors.joining(COLUMNS_DELIMITER)))
                    .collect(Collectors.joining(System.lineSeparator()));
        assertEquals("Read data must match", CONTENT_CORRECT_FILE, actual);
    }

}
