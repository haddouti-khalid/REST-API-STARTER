package com.sqli.commons.core.config.configuration;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.vfs2.FileChangeEvent;
import org.apache.commons.vfs2.FileListener;
import org.apache.commons.vfs2.Selectors;
import org.springframework.core.io.Resource;

import com.sqli.commons.core.config.core.properties.internal.PropertiesWatcher.EventPublisher;

/**
 * The listener interface for receiving reloadableFile events. The class that is
 * interested in processing a reloadableFile event implements this interface,
 * and the object created with that class is registered with a component using
 * the component's <code>addReloadableFileListener<code> method. When
 * the reloadableFile event occurs, that object's appropriate
 * method is invoked.
 *
 * @see ReloadableFileEvent
 */
public class ReloadableFileListener implements FileListener {

	/** The event publisher. */
	private final EventPublisher eventPublisher;

	/** The resources. */
	private final List<Resource> resources;

	/**
	 * Instantiates a new reloadable file listener.
	 *
	 * @param eventPublisher
	 *            the event publisher
	 * @param resources
	 *            the resources
	 */
	public ReloadableFileListener(EventPublisher eventPublisher,
			List<Resource> resources) {
		super();
		this.eventPublisher = eventPublisher;
		this.resources = resources;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.commons.vfs2.FileListener#fileChanged(org.apache.commons.vfs2
	 * .FileChangeEvent)
	 */
	public void fileChanged(FileChangeEvent paramFileChangeEvent)
			throws Exception {

		File file = paramFileChangeEvent
				.getFile()
				.getFileSystem()
				.replicateFile(paramFileChangeEvent.getFile(),
						Selectors.SELECT_SELF);
		Resource resource = getResource(file);
		this.eventPublisher.onResourceChanged(resource);
	}

	/**
	 * Gets the resource.
	 *
	 * @param file
	 *            the file
	 * @return the resource
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private Resource getResource(final File file) throws IOException {
		for (Resource resource : resources) {
			if (resource.getFile().equals(file)) {
				return resource;
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.commons.vfs2.FileListener#fileCreated(org.apache.commons.vfs2
	 * .FileChangeEvent)
	 */
	public void fileCreated(FileChangeEvent event) throws Exception {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.commons.vfs2.FileListener#fileDeleted(org.apache.commons.vfs2
	 * .FileChangeEvent)
	 */
	public void fileDeleted(FileChangeEvent event) throws Exception {
		// TODO Auto-generated method stub

	}

}
