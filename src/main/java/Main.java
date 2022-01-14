import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        long stringAmount = 100;
        int telephoneRandomCount = 10;
        int messageWordCount = 10;
        String rootPath = Paths.get("").toAbsolutePath().toString();
        String dictionaryPath = Paths.get(rootPath, "dictionary").toString();
        String outputPath = Paths.get(rootPath, "output").toString();

        if(args.length==0){
            System.out.println("first argument (Amount string in output logfile) expected, value is set default = 100");
            System.out.println("second argument (Amount randomize number in generated telephone) expected, value is set default = 10");
            System.out.println("third argument (Amount word in messages) expected, value is set default = 10");
            System.out.println("fourth argument (Path to dictionary for generate messages) expected, value is set to " + dictionaryPath);
            System.out.println("fifth argument (Path to output file) expected, value is set on " + outputPath);
        }else {
            stringAmount = Integer.parseInt(args[0]);
            telephoneRandomCount = Integer.parseInt(args[1]);
            if(telephoneRandomCount > 10 || telephoneRandomCount < 0){
                System.out.println("second argument (Amount randomize number in generated telephone) must be 0-10, value is set on 10");
            }
            messageWordCount = Integer.parseInt(args[2]);
            dictionaryPath = args[3];
            outputPath = args[4];
        }

        LogFileGenerator logGenerator = new LogFileGenerator(stringAmount, telephoneRandomCount, messageWordCount, dictionaryPath, outputPath);
        logGenerator.generateLog();
    }
}
