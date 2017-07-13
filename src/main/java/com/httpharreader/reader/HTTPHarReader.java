package com.httpharreader.reader;

import java.io.File;

import java.util.*;

import de.sstoehr.harreader.HarReader;
import de.sstoehr.harreader.HarReaderException;
import de.sstoehr.harreader.model.Har;
import de.sstoehr.harreader.model.HarEntry;

public class HTTPHarReader {

	String harfile = "C:\\Users\\2944\\workspace_luna\\HarveyNorman\\HARFile\\HarveyNormanHAR.har";
	public static void main(String arg[]){

		HTTPHarReader hr = new HTTPHarReader();
		hr.readHARfile();
	}

	public void readHARfile(){

		HarReader harRead = new HarReader();
		Har har = null;
		try {
			har = harRead.readFromFile(new File(harfile));
		} catch (HarReaderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String expectedUrl = "http://www.harveynorman.com.au/applybuy/apply/product?id=";

		List<HarEntry> harEntries= har.getLog().getEntries();

		System.out.println("Total Number of entries:" + harEntries.size());

		for(int i = 1; i<harEntries.size();i++){

			if(harEntries.iterator().hasNext())
			{
				String actualUrl = har.getLog().getEntries().get(i).getRequest().getUrl();
				if(actualUrl.contains(expectedUrl)){

					System.out.println("Browser Used to record the HTTP traffic :" + har.getLog().getCreator().getName());
					System.out.println("HTTP Request:" + actualUrl);
					int statusCode = har.getLog().getEntries().get(i).getResponse().getStatus();
					String statusText = har.getLog().getEntries().get(i).getResponse().getStatusText();
					System.out.println("HTTP Reponse for above request:" +'\n'
							+ "Status Code :" + statusCode
							+'\n'
							+ "Status:" + statusText);

				}
				
			}
		}

	}
}
