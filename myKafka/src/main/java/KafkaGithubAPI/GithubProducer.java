package KafkaGithubAPI;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.serialization.StringSerializer;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class GithubProducer {

    private static JSONArray jsonArray=null;
    Logger logger= LoggerFactory.getLogger(GithubProducer.class.getName());
    public GithubProducer() {

    }

    public static void main(String[] args)
    {
        new GithubProducer().produceData();
    }


    public GithubProducer(JSONArray jsonArray)
    {
      this.jsonArray=jsonArray;
        new GithubProducer().produceData();
    }
    public void produceData()
    {
        String bootstrapServer="127.0.0.1:9092";

        Properties properties=new Properties();

        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServer);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());

        KafkaProducer<String,String> gitProduce=new KafkaProducer<>(properties);

//
//        ProducerRecord<String,String> record
        String topic="first_topic";

        ProducerRecord<String,String> record[]=new ProducerRecord[jsonArray.length()];
        JSONObject jsonObject=null;
        for(int i=0;i<jsonArray.length();i++)
        {
            jsonObject=jsonArray.getJSONObject(i);
            record[i] = new ProducerRecord<>(topic,jsonObject.getString("login"));
            logger.info("record "+jsonObject.getString("login"));
            gitProduce.send(record[i]);

        }
       // gitProduce.flush();
        gitProduce.flush();
        gitProduce.close();
        new GithubConsumer(topic);
    }
}
