package pfq.storage.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;

@Configuration
@PropertySource(value = "classpath:application.properties")
public class MongoConfig {
	
    @Value("${spring.data.mongodb.host}")
    private  String server;
    
    @Value("${spring.data.mongodb.database}")
    private  String db;
    

    public @Bean
    MongoDbFactory mongoDbFactory() throws Exception {

        MongoClient mongo = new MongoClient(server);
        SimpleMongoDbFactory simpleMongoDbFactory = new SimpleMongoDbFactory(mongo, db);
        return simpleMongoDbFactory;
    }
    
    public @Bean
    MongoTemplate mongoTemplate() throws Exception {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
        mongoTemplate.setWriteConcern(WriteConcern.SAFE);
        return mongoTemplate;

    }

}