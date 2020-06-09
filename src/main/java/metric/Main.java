/*
MIT License

Copyright (c) 2018-2019 Gang ZHANG

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package metric;

import metric.extractor.FileParser;
import metric.util.FileTraversal;
import metric.util.FileUtil;
import org.codehaus.plexus.util.StringUtils;
import picocli.CommandLine;
import picocli.CommandLine.PicocliException;

import java.io.File;
import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		try {
			LangRegister langRegister = new LangRegister();
			langRegister.register();
			MetricCommand app = CommandLine.populateCommand(new MetricCommand(), args);
			if (app.help) {
				CommandLine.usage(new MetricCommand(), System.out);
				System.exit(0);
			}
			executeCommand(app);
		} catch (Exception e) {
			if (e instanceof PicocliException) {
				CommandLine.usage(new MetricCommand(), System.out);
			} else if (e instanceof ParameterException){
				System.err.println(e.getMessage());
			}else {
				System.err.println("Exception encountered. If it is a design error, please report issue to us." );
				e.printStackTrace();
			}
			System.exit(0);
		}
	}

	@SuppressWarnings("unchecked")
	private static void executeCommand(MetricCommand app) throws ParameterException {
		String inputDir = app.getSrc();
		String outputName = app.getOutputName();
		String outputDir = app.getOutputDir();
		String[] outputFormat = app.getFormat();

		inputDir = FileUtil.uniqFilePath(inputDir);
		long startTime = System.currentTimeMillis();

		parseAllFiles(inputDir);

		long endTime = System.currentTimeMillis();
		System.out.println("Consumed time: " + (float) ((endTime - startTime) / 1000.00) + " s,  or "
				+ (float) ((endTime - startTime) / 60000.00) + " min.");
	}


	private static final void parseAllFiles(String inputSrcPath) {
		System.out.println("Start parsing files...");
		FileTraversal fileTransversal = new FileTraversal(new FileTraversal.IFileVisitor() {
			@Override
			public void visit(File file) {
				String fileFullPath = file.getAbsolutePath();
				fileFullPath = FileUtil.uniqFilePath(fileFullPath);
				parseFile(fileFullPath);
			}

		});
		fileTransversal.travers(inputSrcPath);
		System.out.println("all files procceed successfully...");

	}

	protected static void parseFile(String fileFullPath) {
			System.out.println("parsing " + fileFullPath + "...");
	}


}
