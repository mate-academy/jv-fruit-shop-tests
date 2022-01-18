package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.service.DateService;
import core.basesyntax.service.DateServiceImpl;
import core.basesyntax.service.PathService;
import core.basesyntax.service.PathServiceImpl;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class MyFileReaderListTest {

    private final MyFileReader myFileReader = new MyFileReaderList();

    @Test
    public void readDataFromFile_not_valid_fullPath_notOk() {
        String fileFullPath = "nkjnkkelcklewckdncn";
        File file = new File(fileFullPath);
        try {
            List<String> list = myFileReader.readDataFromFile(file);
        } catch (RuntimeException e) {
            return;
        }
        fail("In case of no existing file RuntimeException must be thrown");
    }

    @Test
    public void readDataFromFile_empty_string_notOk() {
        System.out.println("test 2");
        String fileFullPath = "";
        File file = new File(fileFullPath);
        try {
            List<String> list = myFileReader.readDataFromFile(file);
        } catch (RuntimeException e) {
            return;
        }
        fail("In case of no existing file RuntimeException must be thrown");
    }

    @Test
    public void writeToFile_readFromFile_compare_ok() {
        DateService dateService = new DateServiceImpl();
        String currentDateString = dateService.getCurrentDateString();
        PathService pathService = new PathServiceImpl();
        List<String> writtenList = new ArrayList<>();
        writtenList.add("b,testFruit1,11");
        writtenList.add("b,testFruit2,22");
        writtenList.add("b,testFruit3,33");
        MyFileWriter myFileWriter = new MyFileWriterList();
        String inputFullPath = pathService.getInputFullPath(currentDateString);
        myFileWriter.writeDataToFile(writtenList, inputFullPath);
        File inputFile = new File(inputFullPath);
        List<String> listFromFile = myFileReader.readDataFromFile(inputFile);
        assertEquals(writtenList, listFromFile);
    }

}
