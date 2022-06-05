package core.basesyntax;

import java.io.File;
import java.util.List;

public class ReportMaker {
    private static final MyFileReader myFileReader = new MyFileReader();
    private static final FruitCounter fruitCounter = new FruitCounter();
    private static final MyFileWriter myFileWriter = new MyFileWriter();

    public File makeReport(File file) {
        List<String> info = myFileReader.getDryInfo(file);
        info = fruitCounter.countFruits(info);
        return myFileWriter.writeReport(info);
    }
}
