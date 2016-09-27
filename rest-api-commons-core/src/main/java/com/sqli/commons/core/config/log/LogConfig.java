package com.sqli.commons.core.config.log;

import java.io.File;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

/**
 * The Class LogConfig.
 */
@Component
public class LogConfig {
	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(LogConfig.class);

	/** The Constant LOG_FILENAME. */
	private static final String LOG_FILENAME = "logback.xml";

	/** The conf folder. */
	@Value("${external.configuration.folder}")
	private String confFolder;

	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init() {
		File file = new File(confFolder + File.separator + LOG_FILENAME);
		if (file.canRead()) {
			// logging init
			LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
			JoranConfigurator configurator = new JoranConfigurator();
			configurator.setContext(context);
			context.reset();
			try {
				configurator.doConfigure(file);
			} catch (JoranException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
	}
}
