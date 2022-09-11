package org.example;
import static com.mongodb.client.model.Filters.eq;
import org.bson.Document;
import org.bson.*;
import org.json.*;
import com.mongodb.client.*;
public class Main {
    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    } 
    public static void main(String[] args) {
        String uri = "mongodb://localhost:27017";
        try(MongoClient client = MongoClients.create(uri)) {
            MongoDatabase database = client.getDatabase("Testing");
            MongoCollection<Document> collection = database.getCollection("HelloWorld");
            clearScreen();
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
        }
    }
}