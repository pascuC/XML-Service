JDK version: 11
The project uses Jackson library for serialization/deserialization xml files.
In order to works properly, the library needs getters, setters and constructors.
For Jackson library you have to add following dependencies in pom.xml file:

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

The project also uses WatchService interface of Java NIO.2 filesystem APIs so that it can detects a change in the
watching directory. Event that triggers the watcher:
    - StandardWatchEventKinds.ENTRY_CREATE – triggered when a new entry is made in the watched directory. It could be
due to the creation of a new file or renaming of an existing file.

When you run the project, you have to entry a valid input directory and a valid output directory(checks not to be the
same directory for I/O).After that, it will process the valid input directory files and write the result in the output
directory. Further, the application will watch over the input directory for new valid files and process them.
The application will also log all processed files together with the corresponding date.
