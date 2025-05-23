import java.util.*;

import java.io.*;
import java.nio.file.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class Login {
	
	public static String FILE_NAME="datoteka";
	public static String CRYPT_FILE_NAME="KRIPT";
	public static String SIGN_FILE_NAME="POTPIS";
	public static final int DUZINA=8;
	public static String KLJUC="kljuc";
	public static String KLJUC_ENK="enc_kljuc";
	public static final String OK="OK";
	
   public Login()
   {
	   super();
   }
   
   public static void login() throws IOException, InterruptedException, ParseException
   {
	   String ime="";
	   String lozinka="";
	   String putanja="";
	   Scanner scan = new Scanner(System.in);
	   
	   System.out.println("Unesite putanju do sertifikata: "); //naziv sertifikata
	   putanja=scan.nextLine();
	   
	   Path p1=Paths.get(Main.CERTS_PATH, putanja);
	   System.out.println(p1);
	   
	   boolean flag4=Login.isRevoked(p1.toString());
	   if(!flag4)
	   {
		   System.out.println("Sertifikat nije povucen.");
	   }
	   else
	   {
		   System.out.println("Sertifikat je povucen.");
		   return;
	   }
	   
	   boolean flag=Login.checkCAAuthorityForLogin(p1.toString());
	   if(flag)
	   {
		   System.out.println("CA tijelo je izdalo sertifikat.");
	   }
	   else
	   {
		   System.out.println("Greska");
		   return;
	   }
	   
	   boolean flag3=Login.isCertificateExpired(p1.toString());
	   if(flag3)
	   {
		   System.out.println("Datum vazenja sertifikata je uredan.");
	   }
	   else
	   {
		   System.out.println("Sertifikat je istekao.");
		   return;
	   }
	   
	   System.out.println("Unesite korisnicko ime: ");
	   ime = scan.nextLine();
	   
	   
	   
	   System.out.println("Unesite lozinku: ");
	   lozinka = scan.nextLine();
	   
	   
	   
	   boolean flag2=Login.checkSubjectForLogin(p1.toString(), ime);
	   if(flag2)
	   {
		   System.out.println("Sertifikat pripada klijentu: " + ime);
	   }
	   else
	   {
		   return;
	   }
	   
	   Client c= Client.mapa.get(ime);
	   Login.FILE_NAME += "_"+c.getCommonName()+".txt";
	   Login.CRYPT_FILE_NAME += "_"+c.getCommonName()+".txt";
	   Login.SIGN_FILE_NAME += "_"+c.getCommonName() + ".txt";
	   Main.KEYS_PATH+=c.getCommonName()+".key";
	   Login.KLJUC+="_"+c.getCommonName()+".txt";
	   Login.KLJUC_ENK+="_"+c.getCommonName()+".enc";
	   
	   String res = Registration.hashPass(lozinka);

	   if(res.equals(c.getPassword()))
	  {
		   System.out.println("\t\t\t\t\t==Uspjesno ste se prijavili na sistem.==");
      }
	   else
	   {
		   System.out.println("Pogresna lozinka");
		   return;
	   }
	   
	   //Login.decryptFileAES();
	  
	   /*
	   if(!Login.verifyDigitalSignFile())
	   {
		   System.out.println("Doslo je do neovlastene izmjene datoteke");
	   }
	    */
	   
   }
   
   public static boolean checkCAAuthorityForLogin(String str) throws IOException, InterruptedException
   {
	   boolean flag=false;
	   Path path = Main.currentDir.resolve(Main.CA_PATH);
	   File file = path.toFile();
	   String res=OpensslManager.runCommand("openssl verify -CAfile " + Main.CA_CERT_PATH + " -verbose " +str, file);
	   if(res.contains(Login.OK))
	   {
		   flag=true;
	   }
	 return flag;
   }
   
   public static boolean checkSubjectForLogin(String str, String ime)throws IOException, InterruptedException
   {
	   boolean flag=false;
	   Path path = Main.currentDir.resolve(Main.CA_PATH);
	   File file = path.toFile();
	   String res=OpensslManager.runCommand("openssl x509 -in " + str + " -noout -subject", file);
	   if(res.contains("CN = " + ime))
	   {
		   flag=true;
	   }
	 return flag;
   }
   
   public static boolean isCertificateExpired(String str)throws IOException, InterruptedException, ParseException
   {
	   boolean flag=false;
	   Path path = Main.currentDir.resolve(Main.CA_PATH);
	   File file = path.toFile();
	   String res=OpensslManager.runCommand("openssl x509 -in " + str + " -enddate -noout", file);
	   String []params=res.split("=");
	   if (params.length == 2) {
           String dateString = params[1].trim();
           SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd HH:mm:ss yyyy z");
           Date expirationDate = dateFormat.parse(dateString);

           
           Date currentDate = new Date();

          
           if (expirationDate.after(currentDate)) {
        	   flag=true;
               //System.out.println("Sertifikat je jos uvijek validan.");
           } else {
        	   flag=false;
               //System.out.println("Sertifikat je istekao.");
           }
       } else {
           System.out.println("Greska prilikom parsiranja datuma.");
       }
	   
	   
	   return flag;
   }
   
   public static void encryptFileAES() throws IOException, InterruptedException
   {
	   String key = Login.cipher();
	   PrintWriter pw=new PrintWriter(new FileWriter(Login.KLJUC), true);
	   pw.println(key);
	   //Login.RSAEncryptKey();
	   String res = OpensslManager.runCommand("openssl enc -aes-256-cbc -in " +Login.FILE_NAME + 
			   " -out " + Login.CRYPT_FILE_NAME + " -k " + key, null);
   }
   
   
   
   public static void digitalSignFile() throws IOException, InterruptedException
   {
	   String res=OpensslManager.runCommand("openssl dgst -sha256 -sign " + Main.KEYS_PATH + " -passin pass:sigurnost"  + 
      " -out " + 
   Login.SIGN_FILE_NAME + " " + Login.FILE_NAME, null);
   }
   
   public static boolean verifyDigitalSignFile()throws IOException, InterruptedException
   {
	   boolean flag=false;
	   String res="openssl dgst -sha256 -prverify " +Main.KEYS_PATH + " -passin pass:sigurnost"+
                  " -signature " + Login.SIGN_FILE_NAME + " " + Login.FILE_NAME;
	   if(res.equals("Verified OK"))
		   flag = true;
	   return flag;
	   
   }
   
   public static boolean isRevoked(String str) throws IOException, InterruptedException
   {
	   Path path = Main.currentDir.resolve(Main.CA_PATH);
	   File file = path.toFile();
	   boolean flag=true;
	   String res=OpensslManager.runCommand("openssl verify -crl_check -CAfile "+ Main.CA_CERT_PATH + 
			   " -CRLfile " + Main.LISTA_PATH + "lista.pem " + str, file);
	   if(res.contains(Login.OK))
		   flag=false;
	   return flag;
   }
   
   /*
   public static void decryptFileAES() throws IOException, InterruptedException
   {
	   
	   String res=OpensslManager.runCommand("openssl enc -aes-256-cbc -d -k "   +" -in " + Login.CRYPT_FILE_NAME + 
			   " -out " + Login.FILE_NAME, null); 
   }
   */
   public static String cipher()
   {
	   Random rand = new Random();
	   String characters = "abcdefghijklmnopqrstuvwxyz";
	   StringBuilder sb = new StringBuilder();
	   for (int i = 0; i < Login.DUZINA; i++) {
           int index = rand.nextInt(characters.length());
           sb.append(characters.charAt(index));
       }
       return sb.toString();
   }
   
   
   public static void RSAEncryptKey() throws IOException, InterruptedException
   {
	
	   String res = OpensslManager.runCommand("openssl rsautl -encrypt -in " + Login.KLJUC + 
			   " -out "+Login.KLJUC_ENK + " -inkey " + Main.KEYS_PATH + " -passin pass:sigurnost" + " -pubin", null);
   }
   
   
   public static void RSADecryptKey() throws IOException, InterruptedException
   {
	   String res = OpensslManager.runCommand("openssl rsautl -decrypt -in " + Login.KLJUC_ENK + 
			   " -out "+Login.KLJUC + " -inkey " + Main.KEYS_PATH + " -passin pass:sigurnost", null);
   }
   
   
   
}

