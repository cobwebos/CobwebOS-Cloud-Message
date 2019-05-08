/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cobwebos.message.core;

import java.io.File;

import org.cobwebos.message.core.utils.CliUtil;
import org.cobwebos.message.core.utils.CobwebosUtils;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * @author cobwebos
 * @version 1.0.0
 *
 */
public class Activator implements BundleActivator {
	private Logger log = LoggerFactory.getLogger(Activator.class);

	static {
		CobwebosUtils.getInstance().initLog4j();
		CobwebosUtils.getInstance().initCobwebosCfg();
	}

	public void start(BundleContext context) {
		log.info("begin to Starting the bundle");
		log.info("cobwebos cfg props:{}", CobwebosUtils.getInstance().getProperties());
		doStart();
		log.info("end to Starting the bundle");
	}

	public void stop(BundleContext context) {
		log.info("begin to Stopping the bundle");
		doStop();
		log.info("end to Stopping the bundle");
	}

	public void doStart() {
		log.info("Starting the cobwebos platform");

		String command = CobwebosUtils.getInstance().getCobwebosRootDir() + "bin" + File.separator
				+ "start-cobwebos.sh";
		if (CobwebosUtils.getInstance().isWindows()) {
			command = CobwebosUtils.getInstance().getCobwebosRootDir() + "bin" + File.separator
					+ "start-cobwebos.bat";
		}
		log.info("getCobwebosRootDir:" + CobwebosUtils.getInstance().getCobwebosRootDir());
		log.info("command:" + command);
		CliUtil cli = new CliUtil();
		cli.executeCommand(command);
		
	}

	public void doStop() {
		log.info("Stopping the cobwebos platform");
		String command = CobwebosUtils.getInstance().getCobwebosRootDir() + "bin" + File.separator
				+ "stop-cobwebos.sh";
		if (CobwebosUtils.getInstance().isWindows()) {
			command = CobwebosUtils.getInstance().getCobwebosRootDir() + "bin" + File.separator + "stop-cobwebos.bat";
		}
		log.info("getCobwebosRootDir:" + CobwebosUtils.getInstance().getCobwebosRootDir());
		log.info("command:" + command);
		CliUtil cli = new CliUtil();
		cli.executeCommand(command);
		

	}

}