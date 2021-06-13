JDK version: 11
The project use Jackson library for serialization/deserialization xml files.
For Jackson library you have to add dependencies in pom.xml file:

	<dependencies>
        <dependency>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.8.1</version>
        </dependency>
        <dependency>
            <groupId>com.fasterxml.jackson.dataformat</groupId>
            <artifactId>jackson-dataformat-xml</artifactId>
            <version>2.12.3</version>
        </dependency>
    </dependencies>

    <properties>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
    </properties>
	
JacksonLibrary needs default constructors, getters and setters to works fine.

ProductOutput is a class that maps each project output
ProductsOutput is a wrapper class in order to correct display in xml file

special character input error
add date

.filter(File::isRegularFile)
.filter(p -> p.endsWith(.xml))
.map(P->path)
.returnColletion();