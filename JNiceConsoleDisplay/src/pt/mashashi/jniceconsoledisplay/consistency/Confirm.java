package pt.mashashi.jniceconsoledisplay.consistency;

import static java.lang.System.out;

import java.util.Arrays;

import pt.mashashi.jniceconsoledisplay.Column;
import pt.mashashi.jniceconsoledisplay.Input;
import pt.mashashi.jniceconsoledisplay.Output;

public class Confirm {
	
	public enum USER_OPTION{
		CONFIRM_TEXT("The data is correct"), CANCEL_TEXT("This is not the correct data");
		private String text;
		private USER_OPTION(String text){
			this.text=text;
		}
		public String getText() {
			return text;
		}
		public static void printOptions(){
			out.println("Type one of the following options");
			for(USER_OPTION userOption: USER_OPTION.values()){
				out.println("\t"+userOption.text);
			}
		}
		public static USER_OPTION getUserOptionFromText(String text){
			USER_OPTION option = null;
			for(USER_OPTION userOption: USER_OPTION.values()){
				if(userOption.text.equals(text)){
					option=userOption;
				}
			}
			return option;
		}
		public static USER_OPTION getUserOptionFromUser(){
			USER_OPTION selected=null;
			while(selected==null){
				String userOption = Input.nextLineEscaped();
				selected=USER_OPTION.getUserOptionFromText(userOption);
				if(selected==null){
					System.err.println("Invalid option");
				}
			}
			return selected;
		}
	}
	private Column keys;
	private Column data;
	public Confirm(String[] keys, String[] data) {
		this.keys=new Column("Keys",Arrays.asList(keys));
		this.data=new Column("Value",Arrays.asList(data));
	}
	public USER_OPTION confirm(String title, String warning){
		out.println(title);
		out.println(warning);
		out.println("The action is going to be performed with the following data:");
		Output.showTable(new Column[]{keys, data});
		out.println();
		USER_OPTION.printOptions();
		return USER_OPTION.getUserOptionFromUser();
	}
}
