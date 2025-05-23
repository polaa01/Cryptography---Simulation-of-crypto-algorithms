import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
public class Registration {
	
	static Scanner scan = new Scanner(System.in);
   private static final String SALT_VALUE="Et145iyR891fgfcb";

   
   public Registration()
   {
	   super();
   }
   
   public static String checkCommonName()
   {
	   System.out.println("Unesite ime: ");
	   String name="";
	      while(true)
	      {
	    	  name = scan.nextLine();
		   if(!Client.mapa.containsKey(name))
		   {
			    break;
		   }
		   else
		   {
			   System.out.println("Uneseno ime vec postoji u sistemu. Pokusajte ponovo!");
		   }
		   
	      }
	      
	      return name;
		   
	 
   }

   
   public static Client register() throws IOException, InterruptedException
   {
	   String commonName="";
	   String email="";
	   String stateName="";
	   String countryName="";
	   String localityName="";
	   String organizationName="";
	   String organizationalUnitName="";
	   String password="";
	   
	  
	
	
	   commonName=Registration.checkCommonName();
	   
	   System.out.println("Unesite lozinku: ");
	   password = scan.nextLine();
	   
	   System.out.println("Unesite entitet: ");
	   stateName = scan.nextLine();
	
	   System.out.println("Unesite drzavu: ");
	   countryName = scan.nextLine();
	   
	   System.out.println("Unesite grad: ");
	   localityName = scan.nextLine();
	   
	   System.out.println("Unesite naziv organizacije: ");
	   organizationName = scan.nextLine();
	   
	   System.out.println("Unesite naziv organizacione jedinice: ");
	   organizationalUnitName = scan.nextLine();
	   
	   System.out.println("Unesite email adresu: ");
	   email = scan.nextLine();
	   
	   
	  
	   Client c = new Client(commonName, hashPass(password), stateName, countryName, localityName, organizationName, organizationalUnitName, email);
	   
	   Client.writeToFile(c);
	   Registration.processKey(c);
	   
	   Registration.generateRequestAndCert(c);
	   System.out.println("\t\t\t\t\t\t\tUspjesno ste se registrovali.");
	   return c;
	   
   }
   
   private static void processKey(Client c) throws IOException, InterruptedException
   {
	 
	   Registration.genRSAkey(c);
	   
	   String sourceFileName = c.getCommonName() +".key"; 
       String targetFolder = Main.CA_PATH + File.separator + Main.PRIVATE_PATH; 

       try {
           Path sourceFilePath = Paths.get(sourceFileName);
           Path targetFolderPath = Paths.get(targetFolder);

           
           Path targetFilePath = targetFolderPath.resolve(sourceFilePath.getFileName());
           Files.move(sourceFilePath, targetFilePath, StandardCopyOption.REPLACE_EXISTING);
          c.setKeyPath(targetFilePath.toString());
           System.out.println("Putanja do para kljuceva: " + c.getKeyPath());
       } catch (IOException e) {
           e.printStackTrace();
       }
	   
	   
   }
   
 
   
   //hesiranje lozinke
   public static String hashPass(String password) throws IOException, InterruptedException
   {
	   String hash = OpensslManager.runCommand("openssl passwd -5 -salt " + SALT_VALUE + " "+ password, null);
	   return hash;
   }
   
   public static String genRSAkey(Client c) throws IOException, InterruptedException
   {
	  
	   String res = OpensslManager.runCommand("openssl genrsa -out " + c.getCommonName() + ".key" +" -aes-256-cbc" + " -passout pass:sigurnost" +  " 4096", null);
	   return res;
   }
   
   public static void generateRequestAndCert(Client c) throws IOException, InterruptedException
   {
	   Path path = Main.currentDir.resolve(Main.CA_PATH);
	   File file = path.toFile();
	   String dest=Main.REQUESTS_PATH+File.separator+c.getCommonName()+".csr";
	   String res1="openssl req -new -key \"" + Main.PRIVATE_KEY_PATH+
			   "\" -out " 
			  + dest + " -config " + Main.CONF_PATH
               + " -subj \"/C=" + c.getCountryName() + "/ST=" + c.getStateName() + "/O=" + c.getOrganizationName()
               + "/OU=" + c.getOrganizationalUnitName() + "/CN=" + c.getCommonName() + "/emailAddress=" + c.getEmail() + "\"";
	   
		String res2="openssl ca -in "+ Main.REQUESTS_PATH+c.getCommonName()+".csr" + " -out " + 
				   Main.CERTS_PATH+File.separator+c.getCommonName()+".crt" + " -config " + Main.CONF_PATH + " -batch";
		OpensslManager.runCommand(res1, file);
		OpensslManager.runCommand(res2, file);
	 
   
   }
   
  
   
 
   
   
}
