package example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import groovy.util.Node;
import groovy.util.XmlParser;

import org.junit.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

public class GroovyXmlTest {

	private final Bundle bundle = FrameworkUtil.getBundle(this.getClass());

	@Test
	public void test() throws Exception {
		assertNotNull("This test must be run inside an OSGi framework", bundle);

		String text = "    <root>\n" + 
		"        <technology>\n" + 
		"            <name>Groovy</name>\n" + 
		"        </technology>\n" + 
		"    </root>";
		Node node = new XmlParser().parseText(text);

		assertEquals("root", node.name());
	}
}
