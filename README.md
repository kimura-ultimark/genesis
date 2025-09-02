This project includes the following two Gradle Plugins:

1. genesisCodeGen
   A code generation plugin based on Doma CodeGen. It includes tasks to automatically generate JPA-compliant Entities and DAOs from database schemas. The generated Java classes have their table name prefixes removed. For example, an Entity generated from M_USER becomes User.java, and its DAO becomes UserDao.java.

2. genesisMockup
   This plugin deploys HTML and other static resources from a specified mockup storage directory (default: src/main/resources/mockup) to directories managed by Spring Boot (default: src/main/resources/templates and src/main/resources/static). The mockup storage directory can be configured via the deployMockup task's sourceDir property, and the HTML deployment directory can be set via the htmlDestDir property.
