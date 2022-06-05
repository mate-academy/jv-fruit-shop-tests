package core.basesyntax;

import java.io.File;
import java.util.List;

public class ReportMaker {
    private final MyFileReader myFileReader;
    private final FruitCounter fruitCounter;
    private final MyFileWriter myFileWriter;

    public ReportMaker() {
        myFileReader = new MyFileReaderImpl();
        fruitCounter = new FruitCounterImpl();
        myFileWriter = new MyFileWriterImpl();
    }

    public File makeReport(File file) {
        List<String> info = myFileReader.getDryInfo(file);
        info = fruitCounter.countFruits(info);
        return myFileWriter.writeReport(info);
    }
}
