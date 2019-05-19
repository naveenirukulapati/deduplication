package com.deduplication.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deduplication.exception.IngestException;
import com.deduplication.service.DeDuplicationService;

@Controller
public class DeDuplicationController {
	
	@Autowired
	DeDuplicationService deDuplicationService;

	/**
	 * Controller method. Accepts fileName as path variable. Calls service method to apply the bussiness logic.
	 * @param fileName file to be ingested and find possible duplicates.
	 * @return
	 */
	@RequestMapping(value="/dedupe/{fileName}", method=RequestMethod.GET)
	public @ResponseBody Object dedupe(@PathVariable("fileName") String fileName) {
		try {
			return deDuplicationService.deDuplicateData(fileName);
		} catch(FileNotFoundException e) {
			return new IngestException(4001, "FileNotFoundException", "FileNotFound Exception occured while trying to read the file.");
		} catch(IOException e) {
			return new IngestException(4002, "IOException", "IOException occured while reading data from thr file.");
		} catch(Exception e) {
			System.out.println(e.getStackTrace());
			return new IngestException(4010, "Exception", "Exception occured while identifying the duplicate records.");
		}
	}
}
