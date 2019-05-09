package KafkaGithubAPI;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

public class Send_HTTP_Request2 {
    public static void main(String[] args) {
        try {
            Send_HTTP_Request2.call_me();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void call_me() throws Exception {
        String url = "https://api.github.com/users/sarthak1775/followers";
        //System.out.println(url+"");
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // optional default is GET
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        JSONObject jsonObject=null;
        JSONArray jsonArray = new JSONArray(response.toString());
        new GithubProducer(jsonArray);
//        for(int i=0;i<jsonArray.length();i++)
//        {
//            jsonObject=jsonArray.getJSONObject(i);
//            System.out.println(jsonObject.getString("login"));
//        }

    }
}