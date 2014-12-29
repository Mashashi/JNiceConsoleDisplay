package pt.mashashi.jniceconsoledisplay.security;

import static java.lang.System.out;

import java.util.Scanner;

public class Credentials {
	private String userName;
	private String password;
	private Credentials(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}
	public static Credentials getCredentials(String title){
		//String password = new String(System.console().readPassword());
		Scanner scanner = new Scanner(System.in);
		out.println(title);
		out.println("");
		out.print("Username:");
		String userName = scanner.nextLine();
		out.print("Password:");
		String password = scanner.nextLine();
		scanner.close();
		return new Credentials(userName, password);
	}
	public static Credentials getCredentialsPRISMA(){
		return Credentials.getCredentials("Access PRISMA");
	}
	public static Credentials getCredentialsSVN(){
		return Credentials.getCredentials("Access SVN");
	}
	public String getUserName() {
		return userName;
	}
	public String getPassword() {
		return password;
	}
	
}
