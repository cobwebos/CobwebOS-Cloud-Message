package org.cobwebos.message.core.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author cobwebos
 * @version 1.0.0
 *
 */

public class CliUtil {
	private Logger log = LoggerFactory.getLogger(CliUtil.class);
	private List<String> stdoutList = new ArrayList<String>();
	private List<String> erroroutList = new ArrayList<String>();

	public void executeCommand(String command) {
		stdoutList.clear();
		erroroutList.clear();

		Process p = null;
		try {
			p = Runtime.getRuntime().exec(command);
			log.info("executeCommand:" + command);
			ThreadUtil stdoutUtil = new ThreadUtil(p.getInputStream(), stdoutList);
			ThreadUtil erroroutUtil = new ThreadUtil(p.getErrorStream(), erroroutList);

			stdoutUtil.start();
			erroroutUtil.start();

		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	public List<String> getStdoutList() {
		return stdoutList;
	}

	public List<String> getErroroutList() {
		return erroroutList;
	}

}

class ThreadUtil implements Runnable {
	public Logger log = LoggerFactory.getLogger(ThreadUtil.class);
	private String character = "utf-8";
	private List<String> list;
	private InputStream inputStream;

	public ThreadUtil(InputStream inputStream, List<String> list) {
		this.inputStream = inputStream;
		this.list = list;
	}

	public void start() {
		Thread thread = new Thread(this);
		thread.setDaemon(true);
		thread.start();
	}

	public void run() {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(inputStream, character));
			String line = null;
			while ((line = br.readLine()) != null) {
				if (line != null) {
					log.info("cli>"+line);
					list.add(line);
				}
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} finally {
			try {

				inputStream.close();
				br.close();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
	}

}
