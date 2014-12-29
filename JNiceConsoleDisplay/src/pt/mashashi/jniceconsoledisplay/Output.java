package pt.mashashi.jniceconsoledisplay;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.System.out;

/**
 * 
 * @author Rafael
 *
 * http://stackoverflow.com/questions/15215326/how-can-i-create-ascii-table-in-java-in-a-console
 * 
 */
public class Output {
	private static final int FORMAT_USED_SPACES=2;
	private Output() {
		throw new IllegalAccessError();
	}
	
	public static void showTable(Column[] columns){
		List<Column> columnsList = Arrays.asList(columns);
		final Integer columnDataLength = getDataLength(columnsList);
		if(columnDataLength==null){
			throw new RuntimeException("Column data is null.");
		}
		String leftAlignFormat = getLeftAlignFormat(columnsList);
		printSeparator(columnsList);
		printHeader(columnsList);
		printSeparator(columnsList);
		for (int[] i={0};i[0]<columnDataLength;i[0]++) {
			List<String> values = columnsList.stream().map(c->c.getData()[i[0]]).collect(Collectors.toList());
		    System.out.format(leftAlignFormat, values.toArray(new Object[values.size()]));
		}
		printSeparator(columnsList);
	}
	private static String getLeftAlignFormat(List<Column> columnsList) {
		final StringBuilder builder = new StringBuilder("");
		columnsList.stream().forEach(c->builder.append("| %-"+(c.getTableLength()-FORMAT_USED_SPACES)+"s "));
		builder.append("|%n");
		return builder.toString();
	}
	private static void printHeader(List<Column> columnsList) {
		columnsList.stream().forEach(c->{
			out.print("|");
			out.print(" ");
			out.print(c.getName());
			int blankSpace=c.getTableLength()-c.getName().length()-1;
			out.print(getStringOfCharNTimes(blankSpace," "));
		});
		out.println("|");
	}
	private static void printSeparator(List<Column> columnsList) {
		System.out.format(getLine(columnsList.stream().map(c->c.getTableLength()).collect(Collectors.toList())));
	}
	private static Integer getDataLength(List<Column> columnsList) {
		final Integer[] columnLength = {null};
		columnsList.stream().forEach(c->
		{
			if(columnLength[0]==null){
				columnLength[0]=c.getData().length;
			}else if(columnLength[0]!=c.getData().length){
				throw new RuntimeException("All columns should have the same length.");
			}
		}
		);
		return columnLength[0];
	}
	private static String getLine(List<Integer> columnsWidth){
		StringBuilder builder = new StringBuilder("");
		for(Integer columnWidth: columnsWidth){
			builder.append("+");
			builder.append(getStringOfCharNTimes(columnWidth,"-"));
		}
		builder.append("+%n");
		return builder.toString();
	}
	private static String getStringOfCharNTimes(int nTimes, String c) {
		StringBuilder builder=new StringBuilder("");
		for(int i=0;i<nTimes;i++){
			builder.append(c);
		}
		return builder.toString();
	}
	
	public static void doneInfo(){
		System.out.println("Done");
	}
	
	public static void abortInfo(){
		System.err.println("Operation aborted");
	}
	
}
