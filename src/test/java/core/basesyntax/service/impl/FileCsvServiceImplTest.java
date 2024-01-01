package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.FileCsvService;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileCsvServiceImplTest {
    private static FileCsvService fileService;
    private static final String PATH_TO_FILE_WITH_ACTIVITIES
            = "src/main/resources/activities.csv";
    private static final String PATH_TO_EMPTY_FILE_WITH_ACTIVITIES
            = "src/main/resources/empty_activities.csv";
    private static final String PATH_TO_WRITING_ACTIVITIES
            = "src/main/resources/writing_activities.csv";
    private static final String WRONG_PATH_TO_FILE_WITH_ACTIVITIES
            = "src/main/resources/new_package/one_more/activities.csv";
    private static List<String> activities;
    private static List<String> activitiesWithoutBrackets;

    @BeforeAll
    static void beforeAll() {
        fileService = new FileCsvServiceImpl();
        activities = new ArrayList<>();
        activities.add("\"type\",\"fruit\",\"quantity\"");
        activities.add("\"b\",\"banana\",\"20\"");
        activities.add("\"b\",\"apple\",\"100\"");
        activities.add("\"s\",\"banana\",\"100\"");
        activities.add("\"p\",\"banana\",\"13\"");
        activities.add("\"r\",\"apple\",\"10\"");
        activities.add("\"p\",apple,\"20\"");
        activities.add("\"p\",\"banana\",\"5\"");
        activities.add("\"s\",\"banana\",\"50\"");

        activitiesWithoutBrackets = new ArrayList<>();
        activitiesWithoutBrackets.add("type,fruit,quantity");
        activitiesWithoutBrackets.add("b,banana,20");
        activitiesWithoutBrackets.add("b,apple,100");
        activitiesWithoutBrackets.add("s,banana,100");
        activitiesWithoutBrackets.add("p,banana,13");
        activitiesWithoutBrackets.add("r,apple,10");
        activitiesWithoutBrackets.add("p,apple,20");
        activitiesWithoutBrackets.add("p,banana,5");
        activitiesWithoutBrackets.add("s,banana,50");
    }

    @Test
    void readFile_ok() {
        List<String> actual = fileService.readFile(PATH_TO_FILE_WITH_ACTIVITIES);
        List<String> expected = activities;
        assertEquals(expected, actual);
    }

    @Test
    void readFile_wrongFilePath_notOk() {
        assertThrows(NoSuchElementException.class,
                () -> fileService.readFile(WRONG_PATH_TO_FILE_WITH_ACTIVITIES));
    }

    @Test
    void readFile_emptyFile_notOk() {
        List<String> actual = fileService.readFile(PATH_TO_EMPTY_FILE_WITH_ACTIVITIES);
        assertTrue(actual.isEmpty());
    }

    @Test
    void readFile_wrongData_notOk() {
        List<String> actual = fileService.readFile(PATH_TO_FILE_WITH_ACTIVITIES);
        List<String> expected = activities;
        actual.add("\"s\",\"banana\",\"50\"");
        assertNotEquals(expected, actual);
    }

    @Test
    void writeToFile_ok() {
        fileService.writeToFile(PATH_TO_WRITING_ACTIVITIES, activitiesWithoutBrackets);
        List<String> actual = fileService.readFile(PATH_TO_WRITING_ACTIVITIES);
        List<String> expected = activitiesWithoutBrackets;
        assertEquals(expected, actual);
    }

    @Test
    void writeToFile_emptyData_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileService.writeToFile(PATH_TO_WRITING_ACTIVITIES, new ArrayList<>()));
    }

    @Test
    void writeToFile_wrongFilePath_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileService.writeToFile(WRONG_PATH_TO_FILE_WITH_ACTIVITIES,
                        activitiesWithoutBrackets));
    }
}
