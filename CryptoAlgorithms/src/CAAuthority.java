import java.util.*;
import java.io.*;
import java.nio.file.*;

 public class CAAuthority {
	 
	public CAAuthority()
	{
		super();
	}

	public static String genKey() throws IOException, InterruptedException
	{
	
		
		String fileName ="private4096.key";
		String comm = OpensslManager.runCommand("openssl genrsa -out "+ fileName  + " 4096", null);
		return comm;
	}
	
	
	public static String genCACert() throws IOException, InterruptedException
	{
		
		String caFileName="rootca.pem";
		String fileName = "private4096.key";
		String folderPath = Main.PRIVATE_PATH;
		String configFilePath = Main.CA_PATH + File.separator+"openssl.cnf";
		String privateKeyFilePath = Main.PRIVATE_PATH+File.separator+fileName;
	
		 String comm = OpensslManager.runCommand("openssl req -new -x509 -key " + 
		       privateKeyFilePath + 
                 " -out " + caFileName + " -config " + configFilePath + " -days 365", null);
		return comm;
	}
	

}
