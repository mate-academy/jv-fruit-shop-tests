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

public class MyFileWriterListTest {

    private final MyFileWriter myFileWriter = new MyFileWriterList();

    @Test
    public void writeDataToFile_invalidPath_notOk() {
        List<String> data = new ArrayList<>();
        String fileFullPath = "gvlkiytv7fhgv";//not ok
        try {
            myFileWriter.writeDataToFile(data, fileFullPath);
        } catch (NullPointerException e) {
            return;
        }
        fail("NullPointerException must be thrown if fileFullPath is invalid!");
    }

    @Test
    public void writeDataToFile_emptyPath_notOk() {
        List<String> data = new ArrayList<>();
        String fileFullPath = "";//not ok
        try {
            myFileWriter.writeDataToFile(data, fileFullPath);
        } catch (NullPointerException e) {
            return;
        }
        fail("NullPointerException must be thrown if fileFullPath is empty!");
    }

    @Test
    public void writeToFile_readFromFile_compare_ok() {
        List<String> listForInput = new ArrayList<>();
        listForInput.add("type,fruit,quantity");
        listForInput.add("b,banana,20");
        listForInput.add("b,apple,100");
        listForInput.add("s,banana,100");
        listForInput.add("p,banana,13");
        listForInput.add("r,apple,10");
        listForInput.add("p,apple,20");
        listForInput.add("p,banana,5");
        listForInput.add("s,banana,50");
        DateService dateService = new DateServiceImpl();
        String currentDateString = dateService.getCurrentDateString();
        PathService pathService = new PathServiceImpl();
        String inputFullPath = pathService.getInputFullPath(currentDateString);
        myFileWriter.writeDataToFile(listForInput, inputFullPath);
        MyFileReader myFileReader = new MyFileReaderList();
        File inputFile = new File(inputFullPath);
        List<String> listFromFile = myFileReader.readDataFromFile(inputFile);
        assertEquals(listForInput, listFromFile);
    }

}
