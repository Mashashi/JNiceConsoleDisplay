package pt.mashashi.jniceconsoledisplay;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Input {
	
	private Input(){
		throw new IllegalAccessError();
	}
	
	public static String nextLineEscaped(){
		@SuppressWarnings("resource") // We do not close the scanner because System.in is manage by the JVM and close it would lead to an NoSuchElmentException when we tried to use it again
		Scanner scanner = new Scanner(System.in);
		return scanner.nextLine().replace("'", "''");
	}
	public static String ifNullEmptyString(String evaluate){
		return evaluate==null?"":evaluate;
	}
	private static void checkFormat(String input, String pattern){
		if(!input.matches(pattern)){
			throw new RuntimeException(input+" does not match "+pattern);
		}
	}
	public static void checkTableNameFormat(String input){
		checkFormat(input, "^[A-Za-z_]+$");
	}
	public static void checkIntegerFormat(String input){
		checkFormat(input, "^\\d+$");
	}
	public static void checkBooleanFormat(String input){
		checkFormat(input, "^(true|false)$");
	}
	public static String prompt(String prompt){
		System.out.print(prompt);
		String value = Input.nextLineEscaped();
		System.out.println();
		return value;
	}
	public static String promptString(String prompt){
		System.out.println("Type:String.*");
		return prompt(prompt);
	}
	public static String promptInteger(String prompt){
		System.out.println("Type:PositiveInteger\\d+");
		String value = prompt(prompt);
		Input.checkIntegerFormat(value);
		return value;
	}
	public static String promptTableName(String prompt){
		System.out.println("Type:TableName[A-Za-z_]+");
		String value = prompt(prompt);
		Input.checkTableNameFormat(value);
		return value;
	}
	public static String promptBoolean(String prompt){
		System.out.println("Type:Boolean(true|false)");
		String value = prompt(prompt);
		Input.checkBooleanFormat(value);
		return value;
	}
	public static String promptAccess(){
		return promptChoice("Masterdata Access:", "1|2", "Public", "System");
	}

	public static String promptChoice(String prompt, String choices, String... descriptionParam) {
		if(!choices.matches("^(\\d+\\|)*(\\d+)$")){
			throw new RuntimeException("Pattern is not well formed.");
		}
		List<String> availableChoices = Arrays.asList(choices.split("|")).stream().filter(e->{return !e.equals("|");}).collect(Collectors.toList());
		
		final int availableChoicesLength = availableChoices.size();
		if(availableChoicesLength!=descriptionParam.length){
			throw new RuntimeException("Available choices and description choices have diferent cardinality.");
		}
		StringBuilder strBuild = new StringBuilder("\n");
		for(int i=0;i<availableChoicesLength;i++){
			strBuild.append(availableChoices.get(i));
			strBuild.append("-");
			strBuild.append(descriptionParam[i]);
			if(i!=availableChoicesLength-1){
				strBuild.append("\n");
			}
		}
		System.out.println("Type:Choices("+strBuild.toString()+"\n)");
		String value = prompt(prompt);
		checkFormat(value, "^("+choices+")$");
		return value;
	}
	
	public static LinkedList<String> getFileNamesForFolder(final File folder) {
		LinkedList<String> files = new LinkedList<String>();
		for (final File fileEntry : folder.listFiles()) {
	        if (!fileEntry.isDirectory()) {
	        	String name = Input.substringStartIfFind(fileEntry.getName(), ".");
	        	files.add(name);
	        }
	    }
		return files;
	}
	
	public static String substringStartIfFind(String text, String find){
		return text.indexOf(find)!=-1?text.substring(0, text.indexOf(find)):text;
	}
	
	public static String substringEndIfFind(String text, String find){
		return text.indexOf(find)!=-1?text.substring(text.indexOf(find)+1, text.length()):text;
	}
	
	public static List<String> resultSetToArray(ResultSet resultSet, int columnPos) throws SQLException {
		boolean hasResult=resultSet.first();
		List<String> dataList = new LinkedList<String>();
		while (hasResult&&!resultSet.isAfterLast()) {
			dataList.add(Input.ifNullEmptyString(resultSet.getString(columnPos)));
			resultSet.next();
		}
		return dataList;
	}

	public static String ifEmptyThenEmptyString(List<String> list) {
		return list.size()!=0?list.get(0):"";
	}
}
