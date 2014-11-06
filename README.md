OpenIG Servlet Query String Sanitizer
=========================

Sanitize ill-formed query parameters.

Right now, it only changes `|` (pipe) chars inside raw query string
into properly URL encoded `%7C`.

This has to be declared inside of the `WEB-INF/web.xml` descriptor:

```xml
<filter>
  <filter-name>sanitizer</filter-name>
  <filter-class>org.forgerock.openig.ext.servlet.SanitizeQueryStringFilter</filter-class>
</filter>
<filter-mapping>
  <filter-name>sanitizer</filter-name>
  <servlet-name>openig-servlet</servlet-name>
</filter-mapping>
```

Do not forget to also add the jar of this module inside your web container
classpath (or inside `WEB-INF/lib/` of OpenIG webapp).


Play with the code withing Codenvy:
[![alt](https://codenvy.com/factory/resources/factory-dark.png)](https://codenvy.com/factory?id=gnf5hlz9yjlibtab)