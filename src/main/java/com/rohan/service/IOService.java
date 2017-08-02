package com.rohan.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.rohan.model.JMXModel;

public class IOService {

	public void createTextFile() {
		File dir = new File("C:/temp");
		if (!dir.exists() && !dir.isDirectory()) {
			dir.mkdir();
		}
		File file = new File("C:/temp/log.txt");
		if (!file.exists() && !file.isDirectory()) {
			List<String> textData = createTable(null);
			try {
				BufferedWriter output = new BufferedWriter(new FileWriter(file));
				
				for(String text : textData){
					output.write(text);
					output.newLine();
				}
				
				
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public List<String> readFile() {
		List<String> textData = new ArrayList<>();
		try {
			String line;
			BufferedReader in = new BufferedReader(new FileReader("C:\\temp\\log.txt"));
			while ((line = in.readLine()) != null) {
				textData.add(line);
			}
			in.close();
		} catch (IOException e) {
			createTextFile();
			return null;
			// return
		}
		return textData;
	}

	public List<String> createTable(List<JMXModel> jmxlist) {
		List<String> textData= new ArrayList<>();
		int domain_len = 6;
		int attr_len = 9;
		int value_len = 5;
		if (jmxlist != null) {
			for (JMXModel jmxmodel : jmxlist) {

				if (domain_len < (jmxmodel.getDomain() + "").length()) {
					domain_len = (jmxmodel.getDomain() + "").length();
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

		tbl += blankLine(domain_len, attr_len, value_len);
		textData.add(tbl);
		tbl = "";
		
		
		tbl += "| DOMAIN";
		for (int i = 0; i < domain_len - 6; i++) {
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
		
		
		tbl += blankLine(domain_len, attr_len, value_len);

		textData.add(tbl);
		tbl = "";
		
		
		if (jmxlist != null) {
			for (JMXModel st : jmxlist) {
				tbl += "| " + st.getDomain();
				for (int i = 0; i < domain_len - ((st.getDomain() + "").length()); i++) {
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

		tbl += blankLine(domain_len, attr_len, value_len);
		textData.add(tbl);
		tbl = "";
		System.out.println(tbl);
		return textData;
	}

	private String blankLine(int domain_len, int attr_len, int value_len) {
		String tbl = "+-";
		for (int i = 0; i < domain_len; i++) {
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

}
