package com.rohan.controller;


import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rohan.model.JMXModel;
import com.rohan.service.IOService;
import com.rohan.service.JMXService;

@Controller
public class WelcomeController {

	JMXService jmxService = new JMXService();
	IOService ioService=new IOService();

	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {
		
		return "welcome";
	}
	
	@RequestMapping("/getJMXValues")
	public String getJMXValues(Map<String, Object> model) {
		
		
		List<JMXModel> list=ioService.getDataFromFile();
		for(JMXModel ob : list) {
			try {
				ob.setValue(jmxService.getValuesForFillData(ob.getDomain(),ob.getType(), ob.getAttribute()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		model.put("attributes", list);
		return "jmxValues";
	}

	@RequestMapping("/readText")
	@ResponseBody
	public Map<Integer,String> readTextFile() {
		
		return ioService.readFile();
	}

	@RequestMapping(value="/getDomains")
	@ResponseBody
	public Set<String> getDomains() {

		try {
			return jmxService.getJMXObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	
	@RequestMapping(value="/domain/{domainid}/getTypes", method = RequestMethod.POST)
	@ResponseBody
	public Set<String> getTypes(@PathVariable String domainid) {
		try {
			return jmxService.getSpecificJMXObject(domainid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value="/domain/{domainid}/type/{typeid}/getNames", method = RequestMethod.POST)
	@ResponseBody
	public Set<String> getNames(@PathVariable String domainid,@PathVariable String typeid) {
		try {
			return jmxService.getSpecificKeyProperty(domainid, typeid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	@RequestMapping(value="/domain/{domainid}/type/{typeid}/name/{nameid}/getAttributes", method = RequestMethod.POST)
	@ResponseBody
	public Set<String> getFinalAttributes(@PathVariable String domainid,@PathVariable String typeid,@PathVariable String nameid) {
		try {
			if(nameid.equals("null")){
				nameid=null;
			}
			return jmxService.getIndividualAttributes(domainid, typeid,nameid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	@RequestMapping(value="/domain/{domainid}/type/{typeid}/name/{nameid}/attribute/{attributeid}/getValue", method = RequestMethod.POST)
	@ResponseBody
	public String getValue(@PathVariable String domainid,@PathVariable String typeid,@PathVariable String nameid,@PathVariable String attributeid) {
		try {
			if(nameid.equals("undefined")){
				nameid=null;
			}
			
			
			return jmxService.getSpecificKeyProperty(domainid, typeid, nameid,attributeid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	@RequestMapping("/fillTable")
	@ResponseBody
	public String fillTable() {
		System.out.println("Called fill Table method");
		return ioService.fillDataFile();
		
	}
	
	@RequestMapping("/openFile")
	@ResponseBody
	public String openFile() {
		System.out.println("Called fill Table method");
		return ioService.openTextFile();
		
	}
	
	
	
	
}
