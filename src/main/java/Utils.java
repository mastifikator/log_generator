import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class Utils {
    final static String DEFAULT_PHONE_NUMBER = "+70000000000";
    final static String MAXIMUM_AMOUNT_IN_NUMBER = "9";
    final static String RUSSIAN_PHONE_PREFIX = "+7";

    public static String generatePhone(int telephoneRandomCount){

        StringBuilder randomPartPhone = new StringBuilder();
        StringBuilder constantPartPhone = new StringBuilder();

        if(telephoneRandomCount > 10){
            return DEFAULT_PHONE_NUMBER;
        }

        for(int i = 0; i < telephoneRandomCount; i++){
            randomPartPhone.append((int)(Math.random()*Integer.parseInt(MAXIMUM_AMOUNT_IN_NUMBER) + 0));
        }

        for(int i = 0; i < 10 - telephoneRandomCount; i++){
            constantPartPhone.append(MAXIMUM_AMOUNT_IN_NUMBER);
        }

        return RUSSIAN_PHONE_PREFIX + constantPartPhone + randomPartPhone;
    }

    public static String generateDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, Math.abs((int)(Math.random()*12 + 1)));
        calendar.set(Calendar.DAY_OF_MONTH, Math.abs((int)(Math.random()*30 + 1)));
        calendar.set(Calendar.YEAR, Math.abs((int)(Math.random()*(2022 - 1980) + 1980)));
        calendar.set(Calendar.HOUR_OF_DAY, Math.abs((int)(Math.random()*23 + 0)));
        calendar.set(Calendar.MINUTE, Math.abs((int)(Math.random()*59 + 0)));
        calendar.set(Calendar.SECOND, Math.abs((int)(Math.random()*59 + 0)));
        calendar.setLenient(true);

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

        return df.format(calendar.getTime());
    }

    public static String generateRandomTextMessageFromDictionary(int amountWordInMessage, List<String> dictionary) {

        Collections.shuffle(dictionary);
        StringBuilder resultMessage = new StringBuilder();

        for(int i = 0; i < amountWordInMessage; i++){
            resultMessage.append(dictionary.get(i));
            if(i != amountWordInMessage - 1) {
                resultMessage.append(" ");
            }
        }

        return resultMessage.toString();
    }
}
