
<h2>PYTHON-BUILDER-PLUGIN</h2>

<h3>Description:</h3>
    A maven plugin to support python build (*.py execution)
    A maven plugin to add following python extensions: *.egg + *.tar.gz
    -> packaging: "python" == *.tar.gz
    -> packaging: "python-egg" == *.egg

<h3>Example:</h3>

Usage for building / cleaning python package

<code>
...
&lt;build&gt;
 &lt;plugins&gt;
 &lt;!-- ===================================================== --&gt;
 &lt;!-- ======================BUILD========================== --&gt;
 &lt;!-- ===================================================== --&gt;
 &lt;plugin&gt;
 &lt;groupId&gt;org.blubird.maven4python&lt;/groupId&gt;
 &lt;artifactId&gt;python-builder-plugin&lt;/artifactId&gt;
 &lt;version&gt;1.0&lt;/version&gt;
 &lt;extensions&gt;true&lt;/extensions&gt;
 &lt;executions&gt;
 &lt;execution&gt;
 &lt;id&gt;build&lt;/id&gt;
 &lt;phase&gt;compile&lt;/phase&gt;
 &lt;configuration&gt;
 &lt;pythonCommand&gt;${python.cmd}&lt;/pythonCommand&gt;
 &lt;setupFileLocations&gt;
 &lt;param&gt;
 &lt;setupFileLocation&gt;
 ${basedir}/setup.py
 &lt;/setupFileLocation&gt;
 &lt;args&gt;
 &lt;arg&gt;install&lt;/arg&gt;
 &lt;arg&gt;--root&lt;/arg&gt;
 &lt;arg&gt;/&lt;/arg&gt;
 &lt;/args&gt;
 &lt;/param&gt;
 &lt;/setupFileLocations&gt;
 &lt;/configuration&gt;
 &lt;goals&gt;
 &lt;goal&gt;python-builder&lt;/goal&gt;
 &lt;/goals&gt;
 &lt;/execution&gt;
 &lt;!-- ===================================================== --&gt;
 &lt;!-- ======================CLEAN========================== --&gt;
 &lt;!-- ===================================================== --&gt;
 &lt;execution&gt;
 &lt;id&gt;clean&lt;/id&gt;
 &lt;phase&gt;clean&lt;/phase&gt;
 &lt;configuration&gt;
 &lt;pythonCommand&gt;${python.cmd}&lt;/pythonCommand&gt;
 &lt;setupFileLocations&gt;
 &lt;param&gt;
 &lt;setupFileLocation&gt;
 ${basedir}/setup.py
 &lt;/setupFileLocation&gt;
 &lt;args&gt;
 &lt;arg&gt;clean&lt;/arg&gt;
 &lt;/args&gt;
 &lt;/param&gt;
 &lt;/setupFileLocations&gt;
 &lt;/configuration&gt;
 &lt;goals&gt;
 &lt;goal&gt;python-builder&lt;/goal&gt;
 &lt;/goals&gt;
 &lt;/execution&gt;
 &lt;/executions&gt;
 &lt;/plugin&gt;
 &lt;/plugins&gt;
 &lt;/build&gt;
...
</code>

<h4>Usage for deploying a python package to repository:</h4>
 ....
  &lt;groupId&gt;&lt;/groupId&gt;
  &lt;artifactId&gt;&lt;/artifactId&gt;
  &lt;packaging&gt;python&lt;/packaging&gt;
  &lt;version&gt;1.0&lt;/version&gt;
  &lt;properties&gt;
  &lt;package.name&gt;${project.artifactId}.tar.gz&lt;/package.name&gt;
  &lt;package.path&gt;target/${package.name}&lt;/package.path&gt;
  &lt;/properties&gt;
  &lt;build&gt;
  &lt;plugins&gt;
  &lt;!-- ===================================================== --&gt;
  &lt;!-- ======================PYTHON-EXT-SETUP=============== --&gt;
  &lt;!-- ===================================================== --&gt;
  &lt;plugin&gt;
  &lt;groupId&gt;org.blubird.maven4python&lt;/groupId&gt;
  &lt;artifactId&gt;python-builder-plugin&lt;/artifactId&gt;
  &lt;version&gt;1.0&lt;/version&gt;
  &lt;extensions&gt;true&lt;/extensions&gt;
  &lt;/plugin&gt;

  &lt;!-- ===================================================== --&gt;
  &lt;!-- ======================INSTALL======================== --&gt;
  &lt;!-- ===================================================== --&gt;
  &lt;plugin&gt;
  &lt;groupId&gt;org.apache.maven.plugins&lt;/groupId&gt;
  &lt;artifactId&gt;maven-install-plugin&lt;/artifactId&gt;
  &lt;version&gt;2.5.1&lt;/version&gt;
  &lt;executions&gt;
  &lt;execution&gt;
  &lt;id&gt;install-package&lt;/id&gt;
  &lt;phase&gt;install&lt;/phase&gt;
  &lt;goals&gt;
  &lt;goal&gt;install-file&lt;/goal&gt;
  &lt;/goals&gt;
  &lt;configuration&gt;
  &lt;file&gt;${package.path}&lt;/file&gt;
  &lt;groupId&gt;${project.groupId}&lt;/groupId&gt;
  &lt;artifactId&gt;${project.artifactId}&lt;/artifactId&gt;
  &lt;version&gt;${project.version}&lt;/version&gt;
  &lt;packaging&gt;${project.packaging}&lt;/packaging&gt;
  &lt;/configuration&gt;
  &lt;/execution&gt;
  &lt;/executions&gt;
  &lt;/plugin&gt;
  &lt;!-- ===================================================== --&gt;
  &lt;!-- ======================DEPLOY========================= --&gt;
  &lt;!-- ===================================================== --&gt;
  &lt;plugin&gt;
  &lt;groupId&gt;org.apache.maven.plugins&lt;/groupId&gt;
  &lt;artifactId&gt;maven-deploy-plugin&lt;/artifactId&gt;
  &lt;version&gt;2.8.1&lt;/version&gt;
  &lt;executions&gt;
  &lt;execution&gt;
  &lt;id&gt;package-deploy&lt;/id&gt;
  &lt;phase&gt;deploy&lt;/phase&gt;
  &lt;goals&gt;
  &lt;goal&gt;deploy-file&lt;/goal&gt;
  &lt;/goals&gt;
  &lt;/execution&gt;
  &lt;execution&gt;
  &lt;id&gt;default-deploy&lt;/id&gt;
  &lt;configuration&gt;
  &lt;skip&gt;true&lt;/skip&gt;
  &lt;/configuration&gt;
  &lt;/execution&gt;
  &lt;/executions&gt;
  &lt;configuration&gt;
  &lt;file&gt;${package.path}&lt;/file&gt;
  &lt;repositoryId&gt;${repo.id}&lt;/repositoryId&gt;
  &lt;url&gt;${repo.url}&lt;/url&gt;
  &lt;groupId&gt;${project.groupId}&lt;/groupId&gt;
  &lt;artifactId&gt;${project.artifactId}&lt;/artifactId&gt;
  &lt;version&gt;${project.version}&lt;/version&gt;
  &lt;packaging&gt;${project.packaging}&lt;/packaging&gt;
  &lt;/configuration&gt;
  &lt;/plugin&gt;
  &lt;/plugins&gt;
  ......

<h3>Motivation:</h3>
    Python builds integration with CI tools, dependency management and repositories (i.e artifactory)

<h3>API Documentation:</h3> NA

<h3>Installation:</h3> (via pom plugin declaration)
...
&lt;plugin&gt;
 &lt;groupId&gt;org.blubird.maven4python&lt;/groupId&gt;
 &lt;artifactId&gt;python-builder-plugin&lt;/artifactId&gt;
 &lt;version&gt;1.0&lt;/version&gt;
 ...
 &lt;/plugin&gt;
...


<h3>Tests:</h3> NA

<h3>Contributors:</h3> zilber.ran@gmail.com

<h3>License:</h3> ASF
