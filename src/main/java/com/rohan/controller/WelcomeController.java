package com.rohan.controller;


import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.ReflectionException;
import javax.websocket.server.PathParam;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

	@RequestMapping("/readText")
	@ResponseBody
	public List<String> readTextFile() {
		
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
//			JSONParser parser= new JSONParser();
//			JSONObject jsonObj = (JSONObject) parser.parse(jsonObject);
//			 String domain = (String) jsonObj.get("domain");
//			 String domaintype = (String) jsonObj.get("domaintype");
//			 String domainname = (String) jsonObj.get("domainname");
			
			if(nameid.equals("null")){
				nameid=null;
				
			}
			
			
			return jmxService.getIndividualAttributes(domainid, typeid,nameid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	@RequestMapping(value="/getAttributes", method = RequestMethod.POST)
	@ResponseBody
	public Set<String> getAttributes(@RequestBody String jsonObject) {
		try {
			//JSONObject jsonObj = (JSONObject) JsonSerializer.toJSON(jsonObject);
			JSONParser parser= new JSONParser();
			JSONObject jsonObj = (JSONObject) parser.parse(jsonObject);
			 String domain = (String) jsonObj.get("domain");
			 String domaintype = (String) jsonObj.get("domaintype");
			 String domainname = (String) jsonObj.get("domainname");
			
			return jmxService.getSpecificKeyProperty(domain, domaintype, domainname);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	@RequestMapping(value="/domain/{domainid}/type/{typeid}/name/{nameid}/attribute/{attributeid}/getValue", method = RequestMethod.POST)
	@ResponseBody
	public String getValue(@PathVariable String domainid,@PathVariable String typeid,@PathVariable String nameid,@PathVariable String attributeid) {
		try {
//			JSONParser parser= new JSONParser();
//			JSONObject jsonObj = (JSONObject) parser.parse(jsonObject);
//			 String domain = (String) jsonObj.get("domain");
//			 String domaintype = (String) jsonObj.get("domaintype");
//			 String domainname = (String) jsonObj.get("domainname");
//			 String attribute = (String) jsonObj.get("attribute");
			
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
	
	
	
	
	
}
