package com.sqli.commons.core.config.configuration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.sqli.commons.core.config.core.properties.event.PropertyChangedEventNotifier;
import com.sqli.commons.core.config.core.properties.internal.ReadablePropertySourcesPlaceholderConfigurer;
import com.sqli.commons.core.config.core.properties.resolver.PropertyResolver;

/**
 * The Class ReloadablePlaceholderConfig.
 */
public class ReloadablePlaceholderConfig extends
		ReadablePropertySourcesPlaceholderConfigurer {

	/** The resources. */
	private List<Resource> resources = new ArrayList<Resource>();

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ReloadablePlaceholderConfig.class);

	/** projectName */
	private String projectName;

	/** configurationFolderVariableEnv */
	private String configurationFolderVariableEnv;

	/**
	 * Instantiates a new reloadable placeholder config.
	 *
	 * @param eventNotifier
	 *            the event notifier
	 * @param propertyResolver
	 *            the property resolver
	 */
	public ReloadablePlaceholderConfig(
			PropertyChangedEventNotifier eventNotifier,
			PropertyResolver propertyResolver) {
		super(eventNotifier, propertyResolver);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.morgan.design.properties.internal.
	 * ReadablePropertySourcesPlaceholderConfigurer
	 * #setLocations(org.springframework.core.io.Resource[])
	 */
	public void setLocations(final Resource[] locations) {
		for (int i = 0; i < locations.length; i++) {
			Resource location = locations[i];

			Resource classPathResource = new ClassPathResource(
					location.getFilename());
			if (classPathResource.exists()) {
				resources.add(classPathResource);
				LOGGER.info("Configuration file : " + classPathResource
						+ " loaded");
				setCache(classPathResource);
				Properties properties = new Properties();
				try {
					properties.load(classPathResource.getInputStream());
					String confFolder = properties
							.getProperty("external.configuration.folder");

					if (confFolder != null) {
						Resource fileSystemResource = new FileSystemResource(
								confFolder + File.separator
										+ location.getFilename());
						if (fileSystemResource.exists()) {
							resources.add(fileSystemResource);
							LOGGER.info("Configuration file : "
									+ fileSystemResource + " loaded");
							setCache(classPathResource);
						}
					}
				} catch (IOException e) {
					LOGGER.error(e.getMessage(), e);
				}

			}

			if (configurationFolderVariableEnv != null) {
				String confFolder = System
						.getenv(configurationFolderVariableEnv);
				if (confFolder != null) {
					Resource fileSystemResource = new FileSystemResource(
							confFolder + File.separator + projectName
									+ File.separator + location.getFilename());
					if (fileSystemResource.exists()) {
						resources.add(fileSystemResource);
						LOGGER.info("Configuration file : "
								+ fileSystemResource + " loaded");
						setCache(fileSystemResource);
					}
				}
			}

		}
		if (!resources.isEmpty()) {
			super.setLocations(resources.toArray(new Resource[] {}));
		}
	}

	/**
	 * 
	 * @param projectName
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * 
	 * @param configurationFolderVariableEnv
	 */
	public void setConfigurationFolderVariableEnv(
			String configurationFolderVariableEnv) {
		this.configurationFolderVariableEnv = configurationFolderVariableEnv;
	}

	/**
	 * Sets the cache.
	 *
	 * @param res
	 *            the res
	 * @return the string
	 */
	private void setCache(Resource res) {
		Properties properties = new Properties();
		try {
			properties.load(res.getInputStream());

			String cache = properties
					.getProperty("external.configuration.cache");
			if (cache != null) {
				super.setCache(Integer.valueOf(cache));
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * Gets the resources.
	 *
	 * @return the resources
	 */
	public List<Resource> getResources() {
		return resources;
	}

}
