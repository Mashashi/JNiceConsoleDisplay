package pt.mashashi.jniceconsoledisplay;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Column {
	private String name;
	private String[] data;
	private int tableLength;
	private final static int SAFETY_MARGIN = 5;
	public Column(String nameIn, List<String> dataIn) {
		name = nameIn;
		data = dataIn.toArray(new String[dataIn.size()]);
		tableLength=nameIn.length()+SAFETY_MARGIN;
		for(int i=0;i<data.length;i++){
			if(data[i].length()>tableLength-SAFETY_MARGIN){
				tableLength=data[i].length()+SAFETY_MARGIN;
			}
		}
	}
	public Column(String name, ResultSet data, int columnPos) throws SQLException {
		this(name, Input.resultSetToArray(data, columnPos));
	}
	
	public String getName() {
		return name;
	}
	public String[] getData() {
		return data;
	}
	public int getTableLength() {
		return tableLength;
	}
}
