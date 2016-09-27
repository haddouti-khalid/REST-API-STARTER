package com.sqli.commons.core.config.i18n;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.springframework.util.DefaultPropertiesPersister;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class JsonPropertiesPersister.
 */
public class JsonPropertiesPersister extends DefaultPropertiesPersister {

	/**
	 * Load from json.
	 * 
	 * @param props
	 *            the props
	 * @param is
	 *            the is
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void loadFromJson(Properties props, InputStream is) throws IOException {
		loadFromJson(props, new InputStreamReader(is));
	}

	/**
	 * Load from json.
	 * 
	 * @param props
	 *            the props
	 * @param reader
	 *            the reader
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void loadFromJson(Properties props, Reader reader) throws IOException {
		ObjectMapper m = new ObjectMapper();
		m.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		m.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		m.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
		JsonNode node = m.readTree(reader);
		loadFromJson(props, node, "");
	}

	/**
	 * Load.
	 * 
	 * @param props
	 *            the props
	 * @param node
	 *            the node
	 * @param key
	 *            the key
	 */
	public void loadFromJson(Properties props, JsonNode node, String key) {
		if (node.isObject()) {
			Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
			while (fields.hasNext()) {
				Map.Entry<String, JsonNode> field = fields.next();
				loadFromJson(props, field.getValue(), join(key, field.getKey()));
			}
		} else if (node.isArray()) {
			Iterator<JsonNode> values = node.elements();
			int i = 0;
			while (values.hasNext()) {
				JsonNode value = values.next();
				loadFromJson(props, value, join(key, String.valueOf(i)));
				i++;
			}
		} else {
			props.setProperty(key, node.asText());
		}
	}

	/**
	 * Join.
	 * 
	 * @param prefix
	 *            the prefix
	 * @param key
	 *            the key
	 * @return the string
	 */
	String join(String prefix, String key) {
		return prefix.isEmpty() ? key : prefix + "." + key;
	}
}
