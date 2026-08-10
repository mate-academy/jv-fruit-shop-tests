package core.basesyntax.service.impl;

import static core.basesyntax.service.impl.ReportCreator.REPORT_HEADER;
import static core.basesyntax.service.impl.ReportCreator.createReport;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

class ReportCreatorTest {
    private static final String fruitName = "testfruit";
    private static final int fruitQuantity = 123;

    @BeforeEach
    void setUp(TestInfo testInfo) {
        if (testInfo.getTags().contains("SkipSetup")) {
            return;
        }
        Fruit testFruit = new Fruit();
        testFruit.setName(fruitName);
        testFruit.setQuantity(fruitQuantity);
        Storage.addFruit(testFruit);
    }

    @AfterEach
    void cleanUp(TestInfo testInfo) {
        if (testInfo.getTags().contains("SkipCleanup")) {
            return;
        }
        Storage.removeFruit(fruitName);
    }

    @Test
    void createReport_reportCreated_ok() {
        String expectedReport = REPORT_HEADER + System.lineSeparator()
                + fruitName + "," + fruitQuantity + System.lineSeparator();
        assertEquals(expectedReport,createReport(),
                "Correct report should have been created");
    }

    @Test
    @Tag("SkipSetup")
    @Tag("SkipCleanup")
    void createReport_storageEmpty_notOk() {
        assertThrows(IllegalArgumentException.class, ReportCreator::createReport,
                "Storage is not empty");
    }
}
