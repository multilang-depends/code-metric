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

import lexer.event.LexerEventCenter;
import metric.output.CsvOutputFormatter;
import metric.output.OutputFormatter;
import metric.parser.AbstractLangProcessor;
import metric.parser.LangProcessorRegistration;
import metric.measure.MetricContext;
import multilang.depends.util.file.FileTraversal;
import multilang.depends.util.file.FileUtil;
import multilang.depends.util.file.path.*;
import multilang.depends.util.file.strip.EmptyLeadingNameStripper;
import multilang.depends.util.file.strip.ILeadingNameStrippper;
import multilang.depends.util.file.strip.LeadingNameStripper;
import org.apache.commons.io.FilenameUtils;
import org.codehaus.plexus.util.StringUtils;
import picocli.CommandLine;
import picocli.CommandLine.PicocliException;

import java.io.File;
import java.util.Optional;

public class Main {
	MetricContext context;
	public static void main(String[] args) {
		try {
			new LangRegister();
			MetricCommand params = CommandLine.populateCommand(new MetricCommand(), args);
			if (params.help) {
				CommandLine.usage(new MetricCommand(), System.out);
				System.exit(0);
			}
			Main main = new Main();
			main.executeCommand(params);
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
	private void executeCommand(MetricCommand parameters) throws ParameterException {
		String inputDir = parameters.getSrc();
		String outputName = parameters.getOutputName();
		String outputDir = parameters.getOutputDir();
		String[] outputFormat = parameters.getFormat();

		inputDir = FileUtil.uniqFilePath(inputDir);
		long startTime = System.currentTimeMillis();
		parseAllFiles(inputDir);
		OutputFormatter outputFormatter = new CsvOutputFormatter(context, parameters.getOutputDir(),parameters.getOutputName());
		outputFormatter.output(getFileNameWritter(parameters),getLeadingNameStripper(parameters, inputDir));
		long endTime = System.currentTimeMillis();
		System.out.println("Consumed time: " + (float) ((endTime - startTime) / 1000.00) + " s,  or "
				+ (float) ((endTime - startTime) / 60000.00) + " min.");
	}

	private ILeadingNameStrippper getLeadingNameStripper(MetricCommand parameters, String inputDir) {
		ILeadingNameStrippper strippper = new EmptyLeadingNameStripper();
		if (parameters.isStripLeadingPath())
			strippper = new LeadingNameStripper(parameters.isStripLeadingPath(),inputDir,parameters.getStrippedPaths());
		return strippper;
	}

	private FilenameWritter getFileNameWritter(MetricCommand parameters) throws ParameterException {
		FilenameWritter filenameWritter = new EmptyFilenameWritter();
		if (!StringUtils.isEmpty(parameters.getNamePathPattern())) {
			if (parameters.getNamePathPattern().equals("dot")||
					parameters.getNamePathPattern().equals(".")) {
				filenameWritter = new DotPathFilenameWritter();
			}else if (parameters.getNamePathPattern().equals("unix")||
					parameters.getNamePathPattern().equals("/")) {
				filenameWritter = new UnixPathFilenameWritter();
			}else if (parameters.getNamePathPattern().equals("windows")||
					parameters.getNamePathPattern().equals("\\")) {
				filenameWritter = new WindowsPathFilenameWritter();
			}else{
				throw new ParameterException("Unknown name pattern paremater:" + parameters.getNamePathPattern());
			}
		}
		return filenameWritter;
	}


	public final MetricContext parseAllFiles(String inputSrcPath) {
		context = new MetricContext();
		LexerEventCenter.getInstance().addObserver(context);
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
		return context;
	}

	protected void parseFile(String fileFullPath) {
		String extension = FilenameUtils.getExtension(fileFullPath);
		Optional<AbstractLangProcessor> processor = LangProcessorRegistration.getRegistry().getLangOf(extension);
		processor.ifPresent(p->{
			System.out.println("parsing " + fileFullPath + "...");
			p.process(fileFullPath,context);});

	}


}
