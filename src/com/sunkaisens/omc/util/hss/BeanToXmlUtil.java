package com.sunkaisens.omc.util.hss;

import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

public class BeanToXmlUtil {

	public static String convertBeanToXmlStr(Object object) {
		try {
			// 流
			Writer writer = new StringWriter();
			JAXBContext context = JAXBContext.newInstance(object.getClass());
			// 获得marshaller对象
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(object, writer);
			String result = writer.toString().replace("standalone=\"yes\"", "");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
