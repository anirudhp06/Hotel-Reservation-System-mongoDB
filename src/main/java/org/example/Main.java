package org.example;
import static com.mongodb.client.model.Filters.eq;
import org.bson.Document;
import org.bson.*;
import org.json.*;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.*;
import io.github.cdimascio.dotenv.Dotenv;
public class Main {
    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    } 
    public static void main(String[] args) {
        Dotenv env=Dotenv.load();
        ConnectionString connectionString= new ConnectionString("mongodb+srv://"+env.get("USERNAMEE")+":"+env.get("PASSWORD")+"@anirudh-cluster.zifgfwn.mongodb.net/?retryWrites=true&w=majority");
        MongoClientSettings settings=MongoClientSettings.builder()
        .applyConnectionString(connectionString).serverApi(ServerApi.builder()
        .version(ServerApiVersion.V1).build())
        .build();
        try(MongoClient client = MongoClients.create(settings)) {
            MongoDatabase database = client.getDatabase("Testing");
            MongoCollection<Document> collection = database.getCollection("HelloWorld");
            clearScreen();
            System.out.println("Loaded Env file");
            System.out.println("Selected Collection 'HelloWorld'");
            Document doc=collection.find(eq("name", "Uday")).first();
            //System.out.println(doc.toJson());
            JSONObject obj=new JSONObject(doc.toJson());
            System.out.println("ID of the document is:"+obj.get("_id"));
            System.out.println("Name of the document is:"+obj.get("name"));
            //Inserting into Collection
            doc = new Document();
            doc.append("menuName", "Fiery Mexican Appetizers")
            .append("menuId", "m01")
            .append("lang", "English")
            .append("ethnicity", "Mexican");
            database.getCollection("HelloWorld").insertOne(doc);
            System.out.println("Added into Collection");
            System.out.println(env.get("TOKEN"));
        }
    }
}