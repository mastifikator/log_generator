import java.util.List;

public class MessageGenerator {
    public static String generate(int telephoneRandomCount, int amountWordInMessage, List<String> dictionary){
        StringBuilder messageStringBuilder = new StringBuilder();
        messageStringBuilder.append("the subscriber ");
        messageStringBuilder.append(Utils.generatePhone(telephoneRandomCount));
        messageStringBuilder.append(" send message \"");
        messageStringBuilder.append(Utils.generateRandomTextMessageFromDictionary(amountWordInMessage, dictionary));
        messageStringBuilder.append("\" to ");
        messageStringBuilder.append(Utils.generatePhone(telephoneRandomCount));
        messageStringBuilder.append(" ");
        messageStringBuilder.append(Utils.generateDate());

        return messageStringBuilder.toString();
    }
}
