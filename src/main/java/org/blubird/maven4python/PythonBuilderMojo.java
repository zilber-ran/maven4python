package org.blubird.maven4python;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "python-builder", defaultPhase = LifecyclePhase.COMPILE)
public class PythonBuilderMojo extends AbstractMojo {
	@Parameter(readonly = true, required = true, property = "pythonCommand")
	private String pythonCommand = null;

	@Parameter(readonly = true, required = true, property = "setupFileLocations")
	private List<PythonCommandArgs> setupFileLocations = null;

	@Parameter(readonly = true, property = "env")
	private Map<String,String> env = null;

	public void execute()
        throws MojoExecutionException
    {
        try {
        	/*
        	 * iterate over *.py provided files and execute using pythonCommand  
        	 */
        	for (PythonCommandArgs pythonCommandArgs : setupFileLocations) {
        		pythonCommand = getAbsolutePath(pythonCommand);
	        	String cmdStr=pythonCommand + pythonCommandArgs;
	        	getLog().info("CMD: "+cmdStr);
	        	List<String> finalCommand = new ArrayList<String>();
	        	finalCommand.add(pythonCommand);
	        	finalCommand.add(pythonCommandArgs.getSetupFileLocation());
	        	for (String arg : pythonCommandArgs.getArgs()) {
	        		finalCommand.add(arg);
	        	}
	        	ProcessBuilder pb = new ProcessBuilder(finalCommand);
	        	getLog().info("DIR: "+pythonCommandArgs.getSetupParentFileLocation());
	        	pb.directory(pythonCommandArgs.getSetupParentFileLocation());
                pb.redirectError(Redirect.INHERIT);
	        	pb.redirectOutput(Redirect.INHERIT);
	        	/** setting execution environment **/
	        	if (this.env!=null) {
	        		Map<String, String> targetEnv = pb.environment();
                    for (Map.Entry<String,String> entry : this.env.entrySet()) {
                        targetEnv.put(entry.getKey(),entry.getValue());
                    }
	        	}
	        	Process process=pb.start();
	        	getLog().info("Waiting...");
	        	int exitCode=process.waitFor();
	        	getLog().info("Exit-Code: "+exitCode);
	        	if (exitCode!=0) {
	        		throw new RuntimeException("Process execution had failed exit-code="+exitCode);
	        	}
        	}
        } catch (Exception e) {
        	getLog().error(e);
        	throw new RuntimeException(e);
        }
			
    }

	/**
	 * transform to absolute path
	 * 
	 * @param path
	 * @return
	 */
	static protected String getAbsolutePath(String value) {
		try {
			File path = new File(value);
			if (path.exists()) {
				value = path.getCanonicalPath();
			}
		} catch (Exception e) {
		}
		return value;
	}

	/**
	 * a python execution command class having specific properties for python
	 * file and execution args
	 */
	public static class PythonCommandArgs {
		public PythonCommandArgs() {
		}

		public List<String> getArgs() {
			return args;
		}

		public String getSetupFileLocation() {
			return getAbsolutePath(setupFileLocation);
		}

		public File getSetupParentFileLocation() {
			File path = new File(setupFileLocation);
			File value = null;
			try {
				value = new File(path.getParentFile().getCanonicalPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return value;
		}

		public void setSetupFileLocation(String setupFileLocation) {
			this.setupFileLocation = setupFileLocation;
		}

		@Override
		public String toString() {
			StringBuilder buff = new StringBuilder();

			if (this.getSetupFileLocation() != null) {
				buff.append(SPACE);
				buff.append(this.getSetupFileLocation());
			}
			if (this.getArgs() != null) {
				for (String arg : this.getArgs()) {
					buff.append(SPACE);
					buff.append(getAbsolutePath(arg));
				}
			}
			return buff.toString();
		}

		final private String SPACE = " ";
		protected List<String> args = new ArrayList<String>();
		protected String setupFileLocation = null;

	}

}
