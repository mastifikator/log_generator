import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;

import java.util.ArrayList;
import java.util.List;

public class LogFileGenerator {
    private final long stringAmount;
    private final int telephoneRandomCount;
    private final int messageWordCount;
    private final String dictionaryPath;
    private final String outputURI;

    public LogFileGenerator(long stringAmount, int telephoneRandomCount, int messageWordCount, String dictionaryPath, String outputPath) {
        this.stringAmount = stringAmount;
        this.telephoneRandomCount = telephoneRandomCount;
        this.messageWordCount = messageWordCount;
        this.dictionaryPath = dictionaryPath;
        this.outputURI = outputPath;
    }

    public void generateLog() {
        SparkConf conf = new SparkConf().setMaster("yarn").setAppName("SparkParser");
        SparkSession spark = SparkSession.builder()
                .config(conf)
                .getOrCreate();

        long startTime = System.currentTimeMillis();

        Dataset<String> dictionaryDS = spark.read().textFile(dictionaryPath);
        System.out.println("Load dictionary " + dictionaryPath + " size: " + dictionaryDS.count());
        List<String> dictionary = dictionaryDS.collectAsList();

        List<String> transitList = new ArrayList<>();

        for (int i = 0; i < stringAmount; i++) {
            if (i % 2 == 0) {
                String call = CallGenerator.generate(telephoneRandomCount) + "\n";
                transitList.add(call);
            } else {
                String message = MessageGenerator.generate(telephoneRandomCount, messageWordCount, dictionary) + "\n";
                transitList.add(message);
            }
        }

        Dataset<String> outputDS = spark.createDataset(transitList, Encoders.STRING());
        outputDS.write().mode(SaveMode.Overwrite).text(outputURI);

        long finishTime = System.currentTimeMillis();
        long elapsed = finishTime - startTime;
        System.out.println("Elapsed time: " + elapsed);
        System.out.println("Created " + outputDS.count() + " string logs");
    }
}
