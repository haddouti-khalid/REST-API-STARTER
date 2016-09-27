package com.sqli.commons.test.matcher;

import static org.springframework.test.util.AssertionErrors.fail;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * RestTestMatchers
 *
 */
public class RestTestMatchers {
	/**
	 * JSON Content Matcher
	 * 
	 * @param expectedFileName
	 *            expected JSON File Name
	 * @return ResultMatcher
	 */
	public static ResultMatcher jsonContent(final String expectedFileName) {
		return new ResultMatcher() {
			public void match(MvcResult result) {
				try {
					String expectedTextContent = getFileContent(expectedFileName);
					String responseTextContent = result.getResponse().getContentAsString();
					if (!compareJson(expectedTextContent, responseTextContent)) {
						fail("The expected Json content is different from response Json");
					}
				} catch (IOException e) {
					fail("Exception in expected Json of the file : " + expectedFileName + ". The Exception is : " + e.getMessage());
					e.printStackTrace();
				}
			}
		};
	}

	/**
	 * compare 2 JSON objects
	 * 
	 * @param expected
	 *            expected object
	 * @param current
	 *            current object
	 * @return true if equals
	 * @throws JsonProcessingException
	 *             JsonProcessingException
	 * @throws IOException
	 *             IOException
	 */
	private static boolean compareJson(String expected, String current) throws  IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode treeExpected = mapper.readTree(expected);
		JsonNode treeCurrent = mapper.readTree(current);
		return treeExpected.equals(treeCurrent);
	}

	/**
	 * Get the content of the expected file
	 * 
	 * @param fileName
	 *            file name
	 * @return the content of the file
	 * @throws IOException
	 *             IOException
	 */
	private static String getFileContent(String fileName) throws IOException {
		StringBuilder out = new StringBuilder();
		try {
			FileInputStream fileInput = new FileInputStream("src/test/resources/json/" + fileName);
			BufferedReader reader = new BufferedReader(new InputStreamReader(fileInput));
			String line;
			while ((line = reader.readLine()) != null) {
				out.append(line);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			fail("The expected file : " + fileName + " is not found ");
		}
		return out.toString();
	}

}
