package org.cobwebos.message.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author cobwebos
 * @version 1.0.0
 *
 */


public class CobwebosUtils {
	Logger log = LoggerFactory.getLogger(CobwebosUtils.class);
	private static final CobwebosUtils cos = new CobwebosUtils();
	public String cobwebosRootDir = System.getProperty("user.dir") + File.separator;
	private String cobwebosLog4jCfgPath = cobwebosRootDir + "etc" + File.separator + "cobwebos-log4j.properties";
	private String cobwebosCfgPath = cobwebosRootDir + "etc" + File.separator + "cobwebos-cfg.properties";
	private Properties p = new Properties();

	

	private CobwebosUtils() {
		initLog4j();
		initCobwebosCfg();
	}

	public static synchronized CobwebosUtils getInstance() {
		return cos;

	}

	public void initLog4j() {
		PropertyConfigurator.configure(cobwebosLog4jCfgPath);
	}

	public void initCobwebosCfg() {

		InputStream io = null;
		try {
			io = new FileInputStream(cobwebosCfgPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		try {
			p.load(io);
		} catch (IOException e1) {
			e1.printStackTrace();
			log.error(e1.getMessage(), e1);
		} finally {
			try {
				io.close();
			} catch (IOException e2) {
				e2.printStackTrace();
				log.error(e2.getMessage(), e2);
			}
		}
	}
	
	public Properties getProperties() {
		return p;
	}
	
	public boolean isWindows() {
		boolean isWindows = false;
		String osName = System.getProperty("os.name").toLowerCase();
		log.info("os.name:" + osName);

		if (osName.contains("windows")) {
			isWindows = true;
		}
		return isWindows;

	}

	public String getCobwebosRootDir() {
		return System.getProperty("user.dir") + File.separator;
	}

}
