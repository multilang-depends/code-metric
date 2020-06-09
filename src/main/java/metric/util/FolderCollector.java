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

package metric.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import metric.util.FileTraversal.IFileVisitor;

public class FolderCollector implements IFileVisitor {
	List<String> folders;
	FileTraversal fileTransversal ;
	public FolderCollector() {
		fileTransversal = new FileTraversal(this,true,false);
	}
	
	public List<String> getFolders(String path){
		folders = new ArrayList<>();
		folders.add(path);
		fileTransversal.travers(path);
		return folders;
	}
	@Override
	public void visit(File file) {
		if (file.isDirectory())
			try {
				folders.add(file.getCanonicalPath());
			} catch (IOException e) {
				e.printStackTrace(); //should never happen
			}
	}

}
