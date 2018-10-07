package hu.szabobelazoltan.szbzbanktransactiondemo.xml;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import hu.szabobelazoltan.szbzbanktransactiondemo.exception.DemoApplicationException;

public class XmlResourceBankDataLoader {
	
	public <T> T loadBankData(String resourceName, Class<T> xmlBankDataClass) throws DemoApplicationException {
		try {
			InputStream resourceInputStream = getClass().getResourceAsStream(resourceName);
			JAXBContext context = JAXBContext.newInstance(xmlBankDataClass);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			T result = (T)unmarshaller.unmarshal(resourceInputStream);
			resourceInputStream.close();
			return result;
		} catch (JAXBException e) {
			throw new DemoApplicationException("JAXB error", e);
		} catch (IOException e) {
			throw new DemoApplicationException("IO error", e);
		}
	}

}
