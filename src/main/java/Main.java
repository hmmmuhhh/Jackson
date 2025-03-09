import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import servlet.ConfigServlet;

public class Main {
    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir("tomcatSvr");
        tomcat.setPort(8080);

        String contextPath = "";
        String docBase = "C:\\Users\\Admin\\Desktop\\JacksonHW\\src\\main\\webapp";

        Context context = tomcat.addContext(contextPath, docBase);

        // Add the servlet to the context
        Tomcat.addServlet(context, "configServlet", new ConfigServlet());
        // Map the servlet to a URL pattern
        context.addServletMappingDecoded("/config", "configServlet");

        tomcat.start();
        tomcat.getConnector();
        tomcat.getServer().await();
    }
}