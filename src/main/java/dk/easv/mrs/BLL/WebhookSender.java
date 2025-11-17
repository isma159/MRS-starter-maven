package dk.easv.mrs.BLL;

import javax.net.ssl.HttpsURLConnection;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class WebhookSender {

    public void sendWebhook(String command) {

        HttpsURLConnection connection = null;

        try {

            String webhookURL = "https://discord.com/api/webhooks/1427023260561838261/SsYZYBJPRroOmhrTD2mAYdxWW1imLe_BCIJjzZKe1PXs2tXDEPDenopCCAgmR_rjocTw";



            connection = (HttpsURLConnection) new URL(webhookURL).openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("content-type", "application/json");
            connection.setDoOutput(true);
            connection.connect();

            try (OutputStream output = connection.getOutputStream()) {

                output.write((command).getBytes(StandardCharsets.UTF_8));

            }

            connection.getInputStream();



        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (connection != null) {

                connection.disconnect();

            } else {

                System.out.println("no connection");

            }

        }

    }

}
