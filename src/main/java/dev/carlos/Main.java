package dev.carlos;//import required classes and packages

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;

// create class to validate JSON document  
public class Main {

  // create inputStreamFromClasspath() method to load the JSON data from the class path
  private static String readFile( String path ) throws IOException {
    InputStream is = new FileInputStream(path);
    BufferedReader buf = new BufferedReader(new InputStreamReader(is));
    String line = buf.readLine();
    StringBuilder sb = new StringBuilder();
    while(line != null)
    {
      sb.append(line).append("\n");
      line = buf.readLine();
    }
    return sb.toString();
  }

  // main() method start
  public static void main( String[] args ) throws Exception {

    // create instance of the ObjectMapper class
    ObjectMapper objectMapper = new ObjectMapper();

    // create an instance of the JsonSchemaFactory using version flag
    JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance( SpecVersion.VersionFlag.V201909 );

    // store the JSON data in InputStream
    try{
      String data = readFile( "src/main/java/dev/carlos/data.json" );
      String schemaJson = readFile( "src/main/java/dev/carlos/schema.json" );


      // read data from the stream and store it into JsonNode
      JsonNode json = objectMapper.readTree(data);

      // get schema from the schemaStream and store it into JsonSchema
      JsonSchema schema = schemaFactory.getSchema(schemaJson);

      // create set of validation message and store result in it
      Set<ValidationMessage> validationResult = schema.validate( json );

      // show the validation errors
      if (validationResult.isEmpty()) {

        // show custom message if there is no validation error
        System.out.println( "There is no validation errors" );

      } else {

        // show all the validation error
        validationResult.forEach(vm -> System.out.println(vm.getMessage()));
      }
    }catch(Exception e){
      e.printStackTrace();
    }
  }
}  