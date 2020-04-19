package net.bis5.gitlab;

import java.io.IOException;
import java.nio.file.Files;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ContextResource;

public class Bootstrap {

    public static void main(String[] args) throws LifecycleException, IOException {
        var tomcat = new Tomcat();
        tomcat.setPort(8888);
        tomcat.getConnector(); // create default connector

        try {
            setupAndStartTomcat(tomcat);
        } finally {
            tomcat.stop();
            tomcat.destroy();
        }
    }

    private static void setupAndStartTomcat(Tomcat tomcat) throws IOException, LifecycleException {
        // 参考: https://github.com/kazuhira-r/javaee7-scala-examples/blob/master/embedded-tomcat-jaxrs-cdi/src/main/scala/org/littlewings/javaee7/TomcatBootstrap.scala
        tomcat.setBaseDir(Files.createTempDirectory("tomcat").toString());
        var context = tomcat.addWebapp("", Files.createTempDirectory("docbase").toString());
        StandardContext.class.cast(context)
                .setParentClassLoader(Thread.currentThread().getContextClassLoader());

        context.addParameter("org.jboss.weld.environment.servlet.archive.isolation", "false");
        context.addParameter("resteasy.injector.factory",
                "org.jboss.resteasy.cdi.CdiInjectorFactory");

        tomcat.enableNaming();

        var resource = new ContextResource();
        resource.setAuth("Container");
        resource.setName("BeanManager");
        resource.setType("javax.enterprise.inject.spi.BeanManager");
        resource.setProperty("factory", "org.jboss.weld.resources.ManagerObjectFactory");
        context.getNamingResources().addResource(resource);

        tomcat.start();
        tomcat.getServer().await();
    }
}
