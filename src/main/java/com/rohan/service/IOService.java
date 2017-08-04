package com.rohan.service;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rohan.model.JMXModel;

public class IOService {

	
	JMXService jmxService= new JMXService();
	
	public void createTextFile(List<JMXModel> tblLines) {
		File dir = new File("C:/temp");
		if (!dir.exists() && !dir.isDirectory()) {
			dir.mkdir();
		}
		File file = new File("C:/temp/log.txt");
		if (!file.exists() && !file.isDirectory()) {
			List<String> textData = createTable(tblLines);
			try {
				BufferedWriter output = new BufferedWriter(new FileWriter(file));

				for (String text : textData) {
					output.write(text);
					output.newLine();
				}

				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public Map<Integer, String> readFile() {
		Map<Integer, String> textData = new HashMap<>();
		int id = 10;
		try {
			String line;
			BufferedReader in = new BufferedReader(new FileReader("C:\\temp\\log.txt"));
			while ((line = in.readLine()) != null) {
				System.out.println("Adding "+id+" string "+line);
				textData.put(id++, line);
				
			}
			in.close();
		} catch (IOException e) {
			createTextFile(null);
			return null;
		}
		return textData;
	}

	public List<JMXModel> getDataFromFile() {
		System.out.println("Get data Method");
		List<JMXModel> textData = new ArrayList<>();
		int id = 1;
		try {
			String line;
			BufferedReader in = new BufferedReader(new FileReader("C:\\temp\\log.txt"));
			while ((line = in.readLine()) != null) {
				id++;
				if (id > 3) {

					String[] data = line.split("\\|");

					if (line.startsWith("|")) {

						JMXModel jmxModel= new JMXModel();
						jmxModel.setDomain(data[1].trim());
						jmxModel.setType(data[2].trim());
						jmxModel.setAttribute(data[3].trim());
						textData.add(jmxModel);
					}
				}
			}
			in.close();
		} catch (IOException e) {
			createTextFile(null);
			 return null;
		}
		 return textData;
	}

	
	public String fillDataFile() {
		System.out.println("Filling data");
		List<JMXModel> list=getDataFromFile();
		for(JMXModel ob : list) {
			System.out.print("In loop Domain : "+ob.getDomain()+" Attribute : "+ob.getAttribute());
			try {
				ob.setValue(jmxService.getValuesForFillData(ob.getDomain(),ob.getType(), ob.getAttribute()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			System.out.println(" Value : "+ob.getValue());
		}
		
		File file = new File("C:\\temp\\log.txt");
		if(file.delete()){
			System.out.println(file.getName() + " is deleted!");
		}else{
			System.out.println("Delete operation is failed.");
			return "error";
		}
		
		createTextFile(list);
		
		return "success";
	}
	
	
	public List<String> createTable(List<JMXModel> jmxlist) {
		List<String> textData = new ArrayList<>();
		int domain_len = 6;
		int type_len = 4;
		int attr_len = 9;
		int value_len = 5;
		if (jmxlist != null) {
			for (JMXModel jmxmodel : jmxlist) {

				if (domain_len < (jmxmodel.getDomain() + "").length()) {
					domain_len = (jmxmodel.getDomain() + "").length();
				}
				if (type_len < jmxmodel.getType().length()) {
					type_len = jmxmodel.getType().length();
				}
				if (attr_len < jmxmodel.getAttribute().length()) {
					attr_len = jmxmodel.getAttribute().length();
				}
				if (value_len < (jmxmodel.getValue() + "").length()) {
					value_len = (jmxmodel.getValue() + "").length();
				}
			}
		}

		String tbl = "";

		tbl += blankLine(domain_len,type_len, attr_len, value_len);
		textData.add(tbl);
		tbl = "";

		tbl += "| DOMAIN";
		for (int i = 0; i < domain_len - 6; i++) {
			tbl += " ";
		}
		
		tbl += " | TYPE";
		for (int i = 0; i < type_len - 4; i++) {
			tbl += " ";
		}
		
		tbl += " | ATTRIBUTE";
		for (int i = 0; i < attr_len - 9; i++) {
			tbl += " ";
		}
		tbl += " | VALUE";
		for (int i = 0; i < value_len - 5; i++) {
			tbl += " ";
		}

		tbl += " |";

		textData.add(tbl);
		tbl = "";

		tbl += blankLine(domain_len,type_len, attr_len, value_len);

		textData.add(tbl);
		tbl = "";

		if (jmxlist != null) {
			for (JMXModel st : jmxlist) {
				tbl += "| " + st.getDomain();
				for (int i = 0; i < domain_len - ((st.getDomain() + "").length()); i++) {
					tbl += " ";
				}
				tbl += " | " + st.getType();
				for (int i = 0; i < type_len - ((st.getType()).length()); i++) {
					tbl += " ";
				}
				tbl += " | " + st.getAttribute();
				for (int i = 0; i < attr_len - ((st.getAttribute()).length()); i++) {
					tbl += " ";
				}
				tbl += " | " + st.getValue();
				for (int i = 0; i < value_len - ((st.getValue() + "").length()); i++) {
					tbl += " ";
				}

				tbl += " |";
				textData.add(tbl);
				tbl = "";
			}
		}

		tbl += blankLine(domain_len,type_len, attr_len, value_len);
		textData.add(tbl);
		tbl = "";
		System.out.println(tbl);
		return textData;
	}

	private String blankLine(int domain_len,int type_len, int attr_len, int value_len) {
		String tbl = "+-";
		for (int i = 0; i < domain_len; i++) {
			tbl += "-";
		}
		tbl += "-+-";
		for (int i = 0; i < type_len; i++) {
			tbl += "-";
		}
		tbl += "-+-";
		for (int i = 0; i < attr_len; i++) {
			tbl += "-";
		}
		tbl += "-+-";
		for (int i = 0; i < value_len; i++) {
			tbl += "-";
		}
		tbl += "-+";

		return tbl;
	}
	
	
	public String openTextFile() {
		try {
			File file = new File("C:\\temp\\log.txt");
			if (System.getProperty("os.name").toLowerCase().contains("windows")) {
				  String cmd = "rundll32 url.dll,FileProtocolHandler " + file.getCanonicalPath();
				  Runtime.getRuntime().exec(cmd);
				} 
				else {
				  Desktop.getDesktop().edit(file);
				}
			return "success";
		} catch (IOException e) {
			e.printStackTrace();
			return "error";
		}
	}

}
