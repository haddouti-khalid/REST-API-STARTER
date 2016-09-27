package com.sqli.commons.core.config.core.properties.internal;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.impl.DefaultFileMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import com.sqli.commons.core.config.configuration.ReloadableFileListener;

/**
 * The Class PropertiesWatcher.
 */
public class PropertiesWatcher implements Runnable {

	/** The log. */
	protected static Logger log = LoggerFactory
			.getLogger(PropertiesWatcher.class);


	/** The locations. */
	private final Resource[] locations;

	/** The event publisher. */
	private final EventPublisher eventPublisher;

	/** The watch service. */
	private DefaultFileMonitor watchService;

	/** The service. */
	final private ExecutorService service;

	/** The cache. */
	private Integer cache;

	/**
	 * The Interface EventPublisher.
	 */
	public interface EventPublisher {

		/**
		 * On resource changed.
		 *
		 * @param resource
		 *            the resource
		 */
		void onResourceChanged(Resource resource);
	}
	
	/**
	 * Instantiates a new properties watcher.
	 *
	 * @param locations
	 *            the locations
	 * @param eventPublisher
	 *            the event publisher
	 * @param cache
	 *            the cache
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public PropertiesWatcher(final Resource[] locations,
			final EventPublisher eventPublisher, final Integer cache)
			throws IOException {
		this.locations = locations;
		this.eventPublisher = eventPublisher;

		FileSystemManager manager = VFS.getManager();
		DefaultFileMonitor fm = new DefaultFileMonitor(
				new ReloadableFileListener(this.eventPublisher,
						Arrays.asList(this.locations)));

		for (Resource resource : locations) {
			fm.addFile(manager.toFileObject(new File(resource.getFile()
					.getAbsolutePath())));
		}
		this.cache = cache;
		this.watchService = fm;
		this.service = Executors.newCachedThreadPool();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		if (cache != null) {
			watchService.setDelay(cache * 1000);
		} else {
			watchService.setDelay(5000);
		}
		watchService.start();
	}

	/**
	 * Stop.
	 */
	public void stop() {
		log.debug("Closing File Watching Service");
		this.watchService.stop();

		log.debug("Shuting down Thread Service");
		this.service.shutdownNow();
	}

}
