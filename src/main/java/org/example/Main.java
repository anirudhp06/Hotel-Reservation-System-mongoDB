package org.example;
import static com.mongodb.client.model.Filters.eq;
import java.util.ArrayList;
import java.util.List;
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
            System.out.println(obj.length()-1);
            List<String> cols=new ArrayList<String>();
            for(String keyStr:obj.keySet()){
                if(keyStr.equals("_id")){continue;}//Ignores the _id value
                Object keyVal=obj.get(keyStr);
                cols.add(keyStr);
                System.out.println(keyStr+":"+keyVal);
            }
            System.out.println("Number of columns in this database are(_id ignored):");
            System.out.println(cols);
        }
    }
}