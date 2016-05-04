package source;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class ReadCSV{
public ArrayList<ArrayList<Float>> attrFloat = new ArrayList<ArrayList<Float> >();
public ArrayList<Integer> idInt = new ArrayList<Integer>();

  public void Readrun(String FileName) {
	BufferedReader br = null;
	String line = "";
	String cvsSplitBy = ",";
	//ArrayList<String[]> Rs = new ArrayList<>();

	try {
		br = new BufferedReader(new FileReader(FileName));
		br.readLine(); //do not read in row caption
		while ((line = br.readLine()) != null) {			
			String[] onerow = line.split(cvsSplitBy);			
			//Rs.add(onerow);
			
			ArrayList<Float> onerowFloat = new ArrayList<Float>();
			for(int i=1; i< onerow.length;i++){
				onerowFloat.add(Float.parseFloat(onerow[i]));
			}
			attrFloat.add(onerowFloat);
			idInt.add( Integer.parseInt(onerow[0]) );
			//System.out.println(onerowFloat.toString());
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
