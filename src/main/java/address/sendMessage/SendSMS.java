package address.sendMessage;

import com.vonage.client.VonageClient;
import com.vonage.client.sms.MessageStatus;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.messages.TextMessage;

import java.util.HashSet;

public class SendSMS {

    private static final String VONAGE_API_KEY = "154959d2";
    private static final String VONAGE_API_SECRET = "BKCHRCli6SFNw0Ko";
    private static final String VONAGE_BRAND_NAME = "Yegor send message";

    public void sendOneMessage(String numberToSend, String messageToSend) {
        VonageClient vonageClient = new VonageClient.Builder().apiKey(VONAGE_API_KEY).apiSecret(VONAGE_API_SECRET).build();

        TextMessage message = new TextMessage(VONAGE_BRAND_NAME, numberToSend, messageToSend);

        SmsSubmissionResponse response = vonageClient.getSmsClient().submitMessage(message);

        if (response.getMessages().get(0).getStatus() == MessageStatus.OK) {
            System.out.println("Message sent successfully.");
        } else {
            System.out.println("Message failed with error: " + response.getMessages().get(0).getErrorText());
        }
    }

    public void sendManyMessage(HashSet<String> numbersToSend, String messageToSend) {
        for (String str : numbersToSend) {
           sendOneMessage(str, messageToSend);
        }
    }

}
