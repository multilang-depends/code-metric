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

package metric.extractor;

import metric.util.FileTraversal;
import metric.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

abstract public class AbstractLangProcessor {


	private String inputSrcPath;

	private final void parseAllFiles() {
		System.out.println("Start parsing files...");
		Set<String> phase2Files = new HashSet<>();
		FileTraversal fileTransversal = new FileTraversal(new FileTraversal.IFileVisitor() {
			@Override
			public void visit(File file) {
				String fileFullPath = file.getAbsolutePath();
				fileFullPath = FileUtil.uniqFilePath(fileFullPath);
					parseFile(fileFullPath);
			}

		});
		fileTransversal.extensionFilter(this.fileSuffixes());
		fileTransversal.travers(this.inputSrcPath);
		System.out.println("all files procceed successfully...");

	}

	protected abstract String fileSuffixes();

	protected void parseFile(String fileFullPath) {
		FileParser fileParser = createFileParser(fileFullPath);
		try {
			System.out.println("parsing " + fileFullPath + "...");
			fileParser.parse();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("error occoured during parse file " + fileFullPath);
			e.printStackTrace();
		}
	}

	protected abstract FileParser createFileParser(String fileFullPath);

	public abstract String supportedLanguage();
}
