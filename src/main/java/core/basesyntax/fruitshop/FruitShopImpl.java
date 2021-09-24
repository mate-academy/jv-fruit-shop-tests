package core.basesyntax.fruitshop;

import core.basesyntax.db.Storage;
import core.basesyntax.fruitrecord.FruitRecord;
import core.basesyntax.service.ActivitiesStrategy;
import core.basesyntax.service.FileReader;
import core.basesyntax.service.FileReaderImpl;
import core.basesyntax.service.FruitRecordToList;
import core.basesyntax.service.FruitRecordToListImpl;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.FruitServiceImpl;
import core.basesyntax.service.WriteFile;
import core.basesyntax.service.WriteFileImpl;
import java.util.List;

public class FruitShopImpl implements FruitShop {
    private ActivitiesStrategy strategy;
    private String filePath;
    private String reportPath;

    public FruitShopImpl(ActivitiesStrategy strategy, String filePath, String reportPath) {
        this.strategy = strategy;
        this.filePath = filePath;
        this.reportPath = reportPath;
    }

    @Override
    public String createNewReport() {
        FileReader file = new FileReaderImpl();
        String stringFile = file.read(filePath);
        FruitRecordToList recordToList = new FruitRecordToListImpl();
        List<FruitRecord> fruitRecordList = recordToList.fruitRecordToList(stringFile);
        Storage.records.addAll(fruitRecordList);
        FruitService record = new FruitServiceImpl(strategy);
        record.recordToMap(Storage.records);
        WriteFile writeFile = new WriteFileImpl();
        writeFile.writeWithMapToFile(Storage.fruitsQuantity, reportPath);
        return "Report created!";
    }
}
