package dbadapter;

public class UserDatabase {
	
	private String Username; 
	
	private String Password; 
	
	
	
	public UserDatabase() {
		
	}
	
	public UserDatabase (String username, String password) {
		this.Username = username;
		this.Password = password; 
		
	}
	
	public void setUsername (String username) {
		this.Username = username; 
		
	}
	
	public void setPassword (String password) {
		this.Password = password; 
		
	}
	
	public String getUsername () {
		return this.Username; 
	}
	
	public String getPassword ( ) {
		return this.Password; 
	}
}
