import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LogFileGenerator {
    private final long stringAmount;
    private final int telephoneRandomCount;
    private final int messageWordCount;
    private final String dictionaryPath;
    private final String outputPath;

    public LogFileGenerator(long stringAmount, int telephoneRandomCount, int messageWordCount, String dictionaryPath, String outputPath){
        this.stringAmount = stringAmount;
        this.telephoneRandomCount = telephoneRandomCount;
        this.messageWordCount = messageWordCount;
        this.dictionaryPath = dictionaryPath;
        this.outputPath = outputPath;
    }

    public void generateLog(){
        List<String> dictionary = new ArrayList<>();

        try {
            dictionary = Files.readAllLines(Paths.get(dictionaryPath));
        }catch (IOException io){
            System.out.println("Wrong set dictionary path " + io.getMessage());
        }

        List<String> outputLogs = new ArrayList<>();

        for(int i = 0; i < stringAmount; i++){
            if(i%2 == 0) {
                String call = CallGenerator.generate(telephoneRandomCount);
                outputLogs.add(call);
                System.out.println(call);
            }
            else{
                String message = MessageGenerator.generate(telephoneRandomCount, messageWordCount, dictionary);
                outputLogs.add(message);
                System.out.println(message);
            }
        }

        Path output = Paths.get(outputPath);

        try {
            Files.deleteIfExists(output);
            Files.createFile(output);
            Files.write(output, outputLogs, StandardCharsets.UTF_8);
        }catch (IOException io){
            System.out.println("Wrong set output log path " + io.getMessage());
        }
    }
}
