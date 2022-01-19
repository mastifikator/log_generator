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
        List<String> dictionary = dictionaryDS.collectAsList();

        long dictionaryTime = System.currentTimeMillis();
        System.out.println("Load dictionary " + dictionaryPath + " size " + dictionaryDS.count());
        System.out.println("Time elapsed: " + (dictionaryTime - startTime));


        List<String> transitList = new ArrayList<>();
        long count = 0;

        for (int i = 0; i < stringAmount; i++) {
            if (i % 2 == 0) {
                String call = CallGenerator.generate(telephoneRandomCount) + "\n";
                transitList.add(call);
                count++;
            } else {
                String message = MessageGenerator.generate(telephoneRandomCount, messageWordCount, dictionary) + "\n";
                transitList.add(message);
                count++;
            }
        }

        long generateTime = System.currentTimeMillis();
        System.out.println("Generated " + count + " string");
        System.out.println("Time elapsed: " + (generateTime - startTime));


        Dataset<String> outputDS = spark.createDataset(transitList, Encoders.STRING());
        outputDS.write().mode(SaveMode.Overwrite).text(outputURI);

        long finishTime = System.currentTimeMillis();
        System.out.println("Elapsed time: " + (finishTime - startTime));
        System.out.println("Created " + outputDS.count() + " string logs");
    }
}
