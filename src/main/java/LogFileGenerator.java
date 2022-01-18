import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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
        long startTime = System.currentTimeMillis();
        List<String> dictionary = new ArrayList<>();
        Path output = Paths.get(outputPath);
        long count = 0;

        try {
            dictionary = Files.readAllLines(Paths.get(dictionaryPath));
            System.out.println("Load dictionary " + dictionaryPath + " size: " + dictionary.size());
        }catch (IOException io){
            System.out.println("Wrong set dictionary path " + io.getMessage());
        }

        try {
            Files.deleteIfExists(output);
            Files.createFile(output);
            System.out.println("Created output file: " + outputPath);
        }catch (IOException io){
            System.out.println("Wrong set output log path " + io.getMessage());
        }


        for(int i = 0; i < stringAmount; i++){
            if(i%2 == 0) {
                String call = CallGenerator.generate(telephoneRandomCount) + "\n";
                try{
                    Files.write(output, call.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
                    count++;
                }catch (IOException io){
                    System.out.println("Wrong set output log path " + io.getMessage());
                }
                //System.out.println(call);
            }
            else{
                String message = MessageGenerator.generate(telephoneRandomCount, messageWordCount, dictionary) + "\n";
                try{
                    Files.write(output, message.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
                    count++;
                }catch (IOException io){
                    System.out.println("Wrong set output log path " + io.getMessage());
                }
                //System.out.println(message);
            }

            if(i%1000 == 0){
                long intermediateTime = System.currentTimeMillis() - startTime;
                System.out.println("Outed " + i + " string, time elapsed " + intermediateTime);
            }
        }

        long finishTime = System.currentTimeMillis();
        long elapsed = finishTime - startTime;
        System.out.println("Elapsed time: " + elapsed);
        System.out.println("Created " + count + " string logs");
    }
}
