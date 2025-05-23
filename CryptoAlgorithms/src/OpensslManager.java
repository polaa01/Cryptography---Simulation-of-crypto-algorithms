import java.util.*;
import java.io.*;
public class OpensslManager {
	
  public OpensslManager()
  {
	  super();
  }
  
  public static String runCommand(String command, File directory) throws IOException, InterruptedException
  {
	
	  StringBuilder builder = new StringBuilder();
      var processBuilder = new ProcessBuilder(command.split(" "));
      
      if (directory != null)
          processBuilder.directory(directory);
         
      try {
          var process = processBuilder.start();
          int value = process.waitFor();
          BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
          String line;
          while ((line = in.readLine()) != null) {
              builder.append(line);
          }

         
          BufferedReader err = new BufferedReader(new InputStreamReader(process.getErrorStream()));
          while ((line = err.readLine()) != null) {
              System.out.println(line);
          }
      } catch (InterruptedException | IOException e)
      {
    	  
      }
      return builder.toString();
      
  }
}
