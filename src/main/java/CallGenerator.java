public class CallGenerator {

    public static String generate(int telephoneRandomCount){
        StringBuilder callStringBuilder = new StringBuilder();
        callStringBuilder.append("the subscriber ");
        callStringBuilder.append(Utils.generatePhone(telephoneRandomCount));
        callStringBuilder.append(" calls ");
        callStringBuilder.append(Utils.generatePhone(telephoneRandomCount));
        callStringBuilder.append(" ");
        callStringBuilder.append(Utils.generateDate());

        return callStringBuilder.toString();
    }
}
