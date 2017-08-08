package com.rohan.service;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.HashSet;
import java.util.Set;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanServer;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class JMXService {

	Set<String> AttributeList = new HashSet<>();

	/**
	 * 
	 * @return Set<String>
	 * @throws InstanceNotFoundException
	 * @throws IntrospectionException
	 * @throws ReflectionException
	 * 
	 *             This method will return a list of all domain names
	 */
	public Set<String> getJMXObject() throws InstanceNotFoundException, IntrospectionException, ReflectionException {
		MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
		Set<ObjectName> mbeans = mBeanServer.queryNames(null, null);
		Set<String> domainlist = new HashSet<>();
		for (ObjectName mbean : mbeans) {
			if (mbean.getDomain() != null)
				domainlist.add(mbean.getDomain());
		}
		return domainlist;
	}

	/**
	 * 
	 * @param Domain
	 * @return Set<String>
	 * @throws InstanceNotFoundException
	 * @throws IntrospectionException
	 * @throws ReflectionException
	 * 
	 * 
	 *             This method will return a list of all types according to domain
	 *             name
	 */
	public Set<String> getSpecificJMXObject(String Domain)
			throws InstanceNotFoundException, IntrospectionException, ReflectionException {
		MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
		Set<ObjectName> mbeans = mBeanServer.queryNames(null, null);
		Set<String> typelist = new HashSet<>();
		for (ObjectName mbean : mbeans) {
			if (mbean.getDomain().equals(Domain)) {
				typelist.add(mbean.getKeyProperty("type"));
			}
		}
		return typelist;
	}

	/**
	 * 
	 * @param Domain
	 * @param keyProperty
	 * @return Set<String>
	 * @throws InstanceNotFoundException
	 * @throws IntrospectionException
	 * @throws ReflectionException
	 * 
	 *             This method will return a list of all names according to domain
	 *             name and type name
	 */
	public Set<String> getSpecificKeyProperty(String Domain, String keyProperty)
			throws InstanceNotFoundException, IntrospectionException, ReflectionException {

		MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
		Set<ObjectName> mbeans = mBeanServer.queryNames(null, null);
		Set<String> namelist = new HashSet<>();
		for (ObjectName mbean : mbeans) {
			if (mbean.getDomain().equals(Domain)) {

				if (mbean.getKeyProperty("type") != null) {
					if (mbean.getKeyProperty("type").equals(keyProperty)) {
						namelist.add(mbean.getKeyProperty("name"));
					}
				}
			}
		}
		return namelist;
	}

	/**
	 * 
	 * @param Domain
	 * @param keyProperty
	 * @param name
	 * @return Set<String>
	 * @throws InstanceNotFoundException
	 * @throws IntrospectionException
	 * @throws ReflectionException
	 * @throws AttributeNotFoundException
	 * @throws MBeanException
	 * @throws IOException
	 * 
	 *             This method will invoke getAttributes method according to domain,
	 *             type and name and return list of attributes
	 */
	public Set<String> getIndividualAttributes(String Domain, String keyProperty, String name)
			throws InstanceNotFoundException, IntrospectionException, ReflectionException, AttributeNotFoundException,
			MBeanException, IOException {
		MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
		Set<ObjectName> mbeans = mBeanServer.queryNames(null, null);
		for (ObjectName mbean : mbeans) {
			if (mbean.getDomain().equals(Domain)) {

				if (mbean.getKeyProperty("type") != null) {

					if (mbean.getKeyProperty("type").equals(keyProperty)) {
						if (name != null && !name.isEmpty()) {
							if (mbean.getKeyProperty("name").equals(name)) {
								try {
									getOnlyAttributes(mBeanServer, mbean);
								} catch (Exception ex) {
									ex.printStackTrace();
								}
							}
						} else {
							getOnlyAttributes(mBeanServer, mbean);
						}
					}
				}

			}
		}
		return AttributeList;
	}

	/**
	 * 
	 * @param mBeanServer
	 * @param http
	 * @throws InstanceNotFoundException
	 * @throws IntrospectionException
	 * @throws ReflectionException
	 * @throws javax.management.IntrospectionException
	 * @throws IOException
	 * @throws MBeanException
	 * @throws AttributeNotFoundException
	 * 
	 *             This method will add Attributes to AttributeList Object according
	 *             to ObjectName
	 */
	public void getOnlyAttributes(final MBeanServer mBeanServer, final ObjectName http)
			throws InstanceNotFoundException, IntrospectionException, ReflectionException,
			javax.management.IntrospectionException, IOException, MBeanException, AttributeNotFoundException {
		MBeanInfo info = mBeanServer.getMBeanInfo(http);
		MBeanAttributeInfo[] attrInfo = info.getAttributes();
		for (MBeanAttributeInfo attr : attrInfo) {
			if (attr.isReadable()) {
				AttributeList.add(attr.getName());
			}
		}
	}

	/**
	 * 
	 * @param Domain
	 * @param keyProperty
	 * @param name
	 * @param attribute
	 * @return String
	 * @throws InstanceNotFoundException
	 * @throws IntrospectionException
	 * @throws ReflectionException
	 * @throws javax.management.IntrospectionException
	 * 
	 * 
	 *             This method will invoke getAttributes method according to domain,
	 *             type and name
	 * 
	 */
	public String getSpecificKeyProperty(String Domain, String keyProperty, String name, String attribute)
			throws InstanceNotFoundException, IntrospectionException, ReflectionException,
			javax.management.IntrospectionException {
		MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
		Set<ObjectName> mbeans = mBeanServer.queryNames(null, null);
		for (ObjectName mbean : mbeans) {
			if (mbean.getDomain().equals(Domain)) {
				System.out.println("Key Property " + keyProperty + " " + mbean.getKeyProperty("type"));
				// System.out.println("Got Domain :" + Domain);
				if (mbean.getKeyProperty("type") != null) {
					if (mbean.getKeyProperty("type").equals(keyProperty)) {
						// System.out.println("Got Type :" + keyProperty);

						if (name != null && !name.isEmpty()) {
							if (mbean.getKeyProperty("name").equals(name)) {
								// System.out.println("Got Name :" + name);
								try {
									return getAttributes(mBeanServer, mbean, attribute);
								} catch (Exception ex) {
									ex.printStackTrace();
								}
							}
						} else {
							try {
								return getAttributes(mBeanServer, mbean, attribute);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
		return "";
	}

	/**
	 * 
	 * @param mBeanServer
	 * @param http
	 * @param attribute
	 * @return String
	 * @throws InstanceNotFoundException
	 * @throws IntrospectionException
	 * @throws ReflectionException
	 * @throws javax.management.IntrospectionException
	 * @throws IOException
	 * @throws MBeanException
	 * @throws AttributeNotFoundException
	 * 
	 * 
	 *             This method is to connect JMX server and retrieve value according
	 *             to attribute and return that value
	 * 
	 */
	public String getAttributes(final MBeanServer mBeanServer, final ObjectName http, String attribute)
			throws InstanceNotFoundException, IntrospectionException, ReflectionException,
			javax.management.IntrospectionException, IOException, MBeanException, AttributeNotFoundException {
		MBeanInfo info = mBeanServer.getMBeanInfo(http);
		MBeanAttributeInfo[] attrInfo = info.getAttributes();

		String portno = System.getProperty("com.sun.management.jmxremote.port");

		JMXServiceURL url = new JMXServiceURL(
				"service:jmx:rmi:///jndi/rmi://" + "localhost" + ":" + portno + "/jmxrmi");
		JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
		MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();

		for (MBeanAttributeInfo attr : attrInfo) {
			if (attr.isReadable()) {
				// System.out.println("Checking Attribute :"+attr.getName()+": and
				// :"+attribute+":");
				if (attr.getName().equals(attribute)) {

					System.out.println("Got value AS " + mbsc.getAttribute(http, attr.getName()));
					// Object ob = mbsc.getAttribute(http, attr.getName());

					if (mbsc.getAttribute(http, attr.getName()).toString()
							.startsWith("javax.management.openmbean.CompositeDataSupport")) {
						return "javax.management.openmbean.CompositeDataSupport";
					}

					return mbsc.getAttribute(http, attr.getName()).toString();
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @param Domain
	 * @param type
	 * @param name
	 * @param attribute
	 * @return String
	 * @throws Exception
	 * 
	 *             This method will return value according to domain, type, name and
	 *             attribute
	 * 
	 */
	public String getValuesForFillData(String Domain, String type, String name, String attribute) throws Exception {
		MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

		String query = Domain + ":type=" + type + "*";
		if (!name.equals("")) {

			query = Domain + ":type=" + type + ",name=" + name;

		} else {
			name = null;
		}

		Set<ObjectName> mbeans = mBeanServer.queryNames(new ObjectName(query), null);
		String value = null;
		for (ObjectName mbean : mbeans) {

			try {

				value = getAttributesForFillTable(mBeanServer, mbean, attribute);

				if (value != null) {
					return value;
				}

				return value;
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return "";
	}

	/**
	 * 
	 * @param mBeanServer
	 * @param http
	 * @param attribute
	 * @return String
	 * @throws InstanceNotFoundException
	 * @throws IntrospectionException
	 * @throws ReflectionException
	 * @throws javax.management.IntrospectionException
	 * @throws IOException
	 * @throws MBeanException
	 * @throws AttributeNotFoundException
	 * 
	 *             This method is to connect JMX server and retrieve value according
	 *             to attribute and return that value
	 */
	public String getAttributesForFillTable(final MBeanServer mBeanServer, final ObjectName http, String attribute)
			throws InstanceNotFoundException, IntrospectionException, ReflectionException,
			javax.management.IntrospectionException, IOException, MBeanException, AttributeNotFoundException {

		MBeanInfo info = mBeanServer.getMBeanInfo(http);
		MBeanAttributeInfo[] attrInfo = info.getAttributes();

		String portno = System.getProperty("com.sun.management.jmxremote.port");

		JMXServiceURL url = new JMXServiceURL(
				"service:jmx:rmi:///jndi/rmi://" + "localhost" + ":" + portno + "/jmxrmi");
		JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
		MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();

		for (MBeanAttributeInfo attr : attrInfo) {
			if (attr.isReadable()) {
				if (attr.getName().equals(attribute)) {
					return mbsc.getAttribute(http, attr.getName()).toString();
				}
			}
		}
		return null;
	}

}
