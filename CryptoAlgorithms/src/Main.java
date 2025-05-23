import java.util.*;
import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;

public class Main {

	public static final Path currentDir = Paths.get(System.getProperty("user.dir"));
	public static final String CLIENTS_FILE = "Klijenti.txt";// lista klijenata (korisnika)
	public static final String CA_PATH = "CA";
	public static final String CONF_PATH = "C:\\Users\\Korisnik\\Desktop\\kripto-projekat-2024\\CryptoAlgorithms\\CA\\openssl.cnf";
	public static final String PRIVATE_PATH = "private";
	public static final String PRIVATE_KEY_PATH = "C:\\Users\\Korisnik\\Desktop\\kripto-projekat-2024\\CryptoAlgorithms\\CA\\private\\private4096.key";
	public static final String REQUESTS_PATH = "requests" + File.separator;

	public static final String CA_CERT_PATH = "C:\\Users\\Korisnik\\Desktop\\kripto-projekat-2024\\CryptoAlgorithms\\CA\\rootca.pem";

	public static final String CERTS_PATH = "certs" + File.separator;
	public static final String NEWCERTS_PATH = "newcerts" + File.separator;
	public static String KEYS_PATH = "C:\\Users\\Korisnik\\Desktop\\kripto-projekat-2024\\CryptoAlgorithms\\CA\\private\\";
	static Scanner scan = new Scanner(System.in);
	public static final String LISTA_PATH = "C:\\Users\\Korisnik\\Desktop\\kripto-projekat-2024\\CryptoAlgorithms\\CA\\crl\\";
	public static final int NUM_OF_CHARACTERS = 100;

	public static boolean checkCharacters(String str) {
		if (str.length() <= Main.NUM_OF_CHARACTERS)
			return true;
		else
			return false;
	}

	public static void main(String[] args) throws IOException, InterruptedException, ParseException {

		Client.loadData();

		System.out.println("=============================================================================");
		System.out.println();
		Scanner scan = new Scanner(System.in);
		int opcija = 0;
		do {

			System.out.println("=========================================================================");
			System.out.println("Opcije:");
			System.out.println("(1) - Registracija");
			System.out.println("(2) - Prijava na sistem");
			System.out.println("(3) - Kraj");
			System.out.println("=========================================================================");
			System.out.println("Unesite opciju: ");
			opcija = scan.nextInt();
			switch (opcija) {
			case 1:
				System.out.println("Registrujte se...");
				Registration.register();
				break;
			case 2:
				System.out.println("Prijavite se na sistem");
				Login.login();
				Main.algsManager();
				break;
			case 3:
				break;
			default:
				System.out.println("Nevazeca opcija...");

			}
		} while (opcija != 3);

		System.out.println();
		System.out.println("Kraj rada aplikacije.");
		System.out.println("=================================================================================");

	}

	public static void algsManager() throws IOException, InterruptedException {

		while (true) {
			System.out.println("Izaberite jedan od ponudjenih algoritama:");
			System.out.println();
			System.out.println("\t\t\t\t\t\t\t===Myszkowski=== ---> opcija 5");
			System.out.println("\t\t\t\t\t\t\t===Rail fence=== ---> opcija 6");
			System.out.println("\t\t\t\t\t\t\t===Playfair=== ---> opcija 7");
			System.out.println("\t\t\t\t\t\t\t===IZLAZ=== ---> opcija 8");

			int c = 0;
			c = scan.nextInt();

			scan.nextLine();

			if (c == 8) {
				break;
			}

			else if (c == 5) {
				MyszkowskiAlg alg = new MyszkowskiAlg();
				System.out.println("Unesite tekst: ");
				String tekst = scan.nextLine();
				if (!Main.checkCharacters(tekst)) {
					System.out.println("Unesite tekst koji ima manje od 100 karaktera");
				}
				System.out.println("Unesite kljuc: ");
				String kljuc = scan.nextLine();
				String sifrat = MyszkowskiAlg.encrypt(tekst, kljuc);
				Main.write(tekst, alg.toString(), kljuc, sifrat);
				// System.out.println("Sifrat: " + sifrat + " Algoritam: " + alg);
			}

			else if (c == 6) {
				RailFenceAlg alg = new RailFenceAlg();
				System.out.println("Unesite tekst: ");
				String tekst = scan.nextLine();
				if (!Main.checkCharacters(tekst)) {
					System.out.println("Unesite tekst koji ima manje od 100 karaktera");
				}
				System.out.println("Unesite broj kolosijeka: ");
				String kljuc = scan.nextLine();
				Integer rails = Integer.parseInt(kljuc);
				String sifrat = RailFenceAlg.encrypt(tekst, rails);
				Main.write(tekst, alg.toString(), rails.toString(), sifrat);
				// System.out.println("Sifrat: " + sifrat + " Algoritam: " + alg);
			}

			else if (c == 7) {
				PlayfairAlg alg = new PlayfairAlg();
				System.out.println("Unesite tekst: ");
				String tekst = scan.nextLine();
				if (!Main.checkCharacters(tekst)) {
					System.out.println("Unesite tekst koji ima manje od 100 karaktera");
				}
				System.out.println("Unesite kljuc: ");
				String kljuc = scan.nextLine();

				String sifrat = PlayfairAlg.encrypt(tekst, kljuc);
				Main.write(tekst, alg.toString(), kljuc, sifrat);
				// System.out.println("Sifrat: " + sifrat + " Algoritam: " + alg);
			}

			else {
				System.out.println("Nevazeca opcija");
				continue;
			}

		}

	}

	public static void write(String text, String alg, String key, String cipherText)
			throws IOException, InterruptedException {
		File file = new File(Login.FILE_NAME);
		if (!file.exists()) {
			file.createNewFile();
		}

		PrintWriter pw = new PrintWriter(new FileWriter(Login.FILE_NAME, true), true);
		pw.println(text + " | " + alg + " | " + key + " | " + cipherText);
		pw.close();


		File file3 = new File(Login.SIGN_FILE_NAME);
		if (!file3.exists()) {
			file3.createNewFile();
		}
		Login.digitalSignFile();
		
		File file2 = new File(Login.CRYPT_FILE_NAME);
		if (!file2.exists()) {
			file2.createNewFile();
		}

		/*
		Login.encryptFileAES();
		
		   File f = new File(Login.KLJUC_ENK);
		   if(!f.exists())
		   {
			   f.createNewFile();
		   }
		   
		Login.RSAEncryptKey();
        */
	}

}
