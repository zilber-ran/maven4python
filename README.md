
<h1>PYTHON-BUILDER-PLUGIN</h1>

<h2>Description:</h2>
    A maven plugin to support python build (*.py execution)
    A maven plugin to add following python extensions: *.egg + *.tar.gz
    -> packaging: "python" == *.tar.gz
    -> packaging: "python-egg" == *.egg

<h2>Example:</h2>

Usage for building / cleaning python package

<pre>
...
<build>
 <plugins>
 <!-- ===================================================== -->
 <!-- ======================BUILD========================== -->
 <!-- ===================================================== -->
 <plugin>
 <groupId>org.blubird.maven4python</groupId>
 <artifactId>python-builder-plugin</artifactId>
 <version>1.0</version>
 <extensions>true</extensions>
 <executions>
 <execution>
 <id>build</id>
 <phase>compile</phase>
 <configuration>
 <pythonCommand>${python.cmd}</pythonCommand>
 <setupFileLocations>
 <param>
 <setupFileLocation>
 ${basedir}/setup.py
 </setupFileLocation>
 <args>
 <arg>install</arg>
 <arg>--root</arg>
 <arg>/</arg>
 </args>
 </param>
 </setupFileLocations>
 </configuration>
 <goals>
 <goal>python-builder</goal>
 </goals>
 </execution>
 <!-- ===================================================== -->
 <!-- ======================CLEAN========================== -->
 <!-- ===================================================== -->
 <execution>
 <id>clean</id>
 <phase>clean</phase>
 <configuration>
 <pythonCommand>${python.cmd}</pythonCommand>
 <setupFileLocations>
 <param>
 <setupFileLocation>
 ${basedir}/setup.py
 </setupFileLocation>
 <args>
 <arg>clean</arg>
 </args>
 </param>
 </setupFileLocations>
 </configuration>
 <goals>
 <goal>python-builder</goal>
 </goals>
 </execution>
 </executions>
 </plugin>
 </plugins>
 </build>
...
</pre>

Usage for deploying a python package to repository:
 ....
  <groupId></groupId>
  <artifactId></artifactId>
  <packaging>python</packaging>
  <version>1.0</version>
  <properties>
  <package.name>${project.artifactId}.tar.gz</package.name>
  <package.path>target/${package.name}</package.path>
  </properties>
  <build>
  <plugins>
  <!-- ===================================================== -->
  <!-- ======================PYTHON-EXT-SETUP=============== -->
  <!-- ===================================================== -->
  <plugin>
  <groupId>org.blubird.maven4python</groupId>
  <artifactId>python-builder-plugin</artifactId>
  <version>1.0</version>
  <extensions>true</extensions>
  </plugin>

  <!-- ===================================================== -->
  <!-- ======================INSTALL======================== -->
  <!-- ===================================================== -->
  <plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-install-plugin</artifactId>
  <version>2.5.1</version>
  <executions>
  <execution>
  <id>install-package</id>
  <phase>install</phase>
  <goals>
  <goal>install-file</goal>
  </goals>
  <configuration>
  <file>${package.path}</file>
  <groupId>${project.groupId}</groupId>
  <artifactId>${project.artifactId}</artifactId>
  <version>${project.version}</version>
  <packaging>${project.packaging}</packaging>
  </configuration>
  </execution>
  </executions>
  </plugin>
  <!-- ===================================================== -->
  <!-- ======================DEPLOY========================= -->
  <!-- ===================================================== -->
  <plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-deploy-plugin</artifactId>
  <version>2.8.1</version>
  <executions>
  <execution>
  <id>package-deploy</id>
  <phase>deploy</phase>
  <goals>
  <goal>deploy-file</goal>
  </goals>
  </execution>
  <execution>
  <id>default-deploy</id>
  <configuration>
  <skip>true</skip>
  </configuration>
  </execution>
  </executions>
  <configuration>
  <file>${package.path}</file>
  <repositoryId>${repo.id}</repositoryId>
  <url>${repo.url}</url>
  <groupId>${project.groupId}</groupId>
  <artifactId>${project.artifactId}</artifactId>
  <version>${project.version}</version>
  <packaging>${project.packaging}</packaging>
  </configuration>
  </plugin>
  </plugins>
  ......

Motivation:
    Python builds integration with CI tools, dependency management and repositories (i.e artifactory)

API Documentation: NA

Installation: (via pom plugin declaration)
...
<plugin>
 <groupId>org.blubird.maven4python</groupId>
 <artifactId>python-builder-plugin</artifactId>
 <version>1.0</version>
 ...
 </plugin>
...


Tests: NA

Contributors: zilber.ran@gmail.com

License: ASF