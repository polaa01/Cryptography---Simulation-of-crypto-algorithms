import java.util.*;
import java.io.*;
public class Client {
	
	private String commonName;
	private String email;
	private String stateName;
	private String countryName;
	private String localityName;
    private String organizationName;
    private String organizationalUnitName;
    
    private String password;
    private String keyPath;
    
    public static HashMap<String, Client> mapa = new HashMap<>();
    
	public Client()
	{
		super();
	}
	
	public Client(String commonName)
	{
		this.commonName=commonName;
	}
	
	
	public Client(String commonName, String password, String stateName, String countryName, String localityName,
			String organizationName, String organizationalUnitName, String email) {
		super();
		this.commonName = commonName;
		this.password=password;
		this.stateName = stateName;
		this.countryName = countryName;
		this.localityName = localityName;
		this.organizationName = organizationName;
		this.organizationalUnitName = organizationalUnitName;
		this.email = email;
	}

    
	public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}
	
	public String getKeyPath()
	{
		return keyPath;
	}
	
	public void setKeyPath(String keyPath)
	{
		this.keyPath = keyPath;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getLocalityName() {
		return localityName;
	}

	public void setLocalityName(String localityName) {
		this.localityName = localityName;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getOrganizationalUnitName() {
		return organizationalUnitName;
	}

	public void setOrganizationalUnitName(String organizationalUnitName) {
		this.organizationalUnitName = organizationalUnitName;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public String toString()
	{
		return commonName + " " + password + " " + stateName + " " + countryName + " " + localityName + " " + 
				organizationName + " " + organizationalUnitName + " " + email;
	}
	
	public static void loadData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(Main.CLIENTS_FILE)))
        {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] params = line.split(" ");
                mapa.put(params[0], new Client(params[0], params[1], params[2], params[3],
                		params[4], params[5], params[6], params[7]));
            }
        } catch (IOException ex) 
        {
            ex.printStackTrace();
        }
        
    }

    
	  public static void writeToFile(Client c)
	   {
		   Client.mapa.put(c.getCommonName(),c);
		   try {
			PrintWriter pw = new PrintWriter(new FileWriter(Main.CLIENTS_FILE, true), true);
			pw.println(c);
			pw.close();
		      } 
		   catch (IOException ex) 
		   {
			ex.printStackTrace();
		   }
		   
	   }
	  


}
