package source;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class ReadCSV{
public ArrayList<ArrayList<Integer>> table = new ArrayList<ArrayList<Integer> >();
public ArrayList<String> title = new ArrayList<>();

  public void Readrun(String FileName) {
	BufferedReader br = null;
	String line = "";
	String csvSplitBy = ",";

	try {
		br = new BufferedReader(new FileReader(FileName));
		//read in row caption
		String[] namerow = br.readLine().split(csvSplitBy);
		for(int i=0; i< namerow.length; i++){
			title.add(namerow[i]);
		}
		//read in table, containing id and attributes
		while ((line = br.readLine()) != null) {			
			String[] onerow = line.split(csvSplitBy);			
			
			ArrayList<Integer> onerowFloat = new ArrayList<Integer>();
			for(int i=0; i< onerow.length;i++){
				onerowFloat.add(Integer.parseInt(onerow[i]));
			}
			table.add(onerowFloat);
		}

	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	finally {
		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

  }
  
}
