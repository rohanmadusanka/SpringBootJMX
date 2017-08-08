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

/**
 * @author rmadusanka
 *
 */
@Controller
public class WelcomeController {

	JMXService jmxService = new JMXService();
	IOService ioService=new IOService();

	
	/**
	 * 
	 * @param model
	 * @return String 
	 * 
	 * This method will return page name to view welcome.jsp page
	 */
	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {
		return "welcome";
	}
	
	
	
	/**
	 * 
	 * @param model
	 * @return String
	 * This method will return page name to view jmxValues.jsp page
	 */
	@RequestMapping("/getJMXValues")
	public String getJMXValues(Map<String, Object> model) {
		
		
		List<JMXModel> list=ioService.getDataFromFile();
		for(JMXModel ob : list) {
			try {
				ob.setValue(jmxService.getValuesForFillData(ob.getDomain(),ob.getType(),ob.getName(), ob.getAttribute()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		model.put("attributes", list);
		return "jmxValues";
	}

	/**
	 * 
	 * @return HashMap
	 * This method will invoke read file method in service 
	 * layer and return it's hashmap object
	 */
	@RequestMapping("/readText")
	@ResponseBody
	public Map<Integer,String> readTextFile() {
		
		return ioService.readFile();
	}

	/**
	 * 
	 * @return HashSet
	 * This method will invoke getJMXObject method in service 
	 * layer and return it's Set<String> object 
	 */
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
	
	/**
	 * 
	 * @param domainid
	 * @return Set<String>
	 * 
	 * This method will take domain id as a parameter and pass it to getSpecificJMXObject
	 * method in service layer and return it's Set<String> Object
	 */
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

	/**
	 * 
	 * @param domainid
	 * @param typeid
	 * @return Set<String>
	 * 
	 * This method will take domainid,typeid as a parameter and pass it to getSpecificKeyProperty
	 * method in service layer and return it's Set<String> Object
	 */
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
	
	
	/**
	 * 
	 * @param domainid
	 * @param typeid
	 * @param nameid
	 * @return Set<String>
	 * 
	 * This method will take domainid,typeid,nameid as a parameter and pass it to getIndividualAttributes
	 * method in service layer and return it's Set<String> Object 
	 */
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
	
	
	/**
	 * 
	 * @param domainid
	 * @param typeid
	 * @param nameid
	 * @param attributeid
	 * @return String
	 * 
	 * This method will take domainid,typeid,nameid,attributeid as a parameter and pass it to getIndividualAttributes
	 * method in service layer and return it's String Object 
	 */
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
	
	
	/**
	 * 
	 * @return String
	 * 
	 * This method will invoke fillDataFile method in service layer
	 */
	@RequestMapping("/fillTable")
	@ResponseBody
	public String fillTable() {
		return ioService.fillDataFile();
		
	}
	
	/**
	 * 
	 * @return String 
	 * 
	 * This method will invoke openTextFile method in service layer 
	 */
	@RequestMapping("/openFile")
	@ResponseBody
	public String openFile() {
		
		return ioService.openTextFile();
		
	}
	
	
	
	
}
