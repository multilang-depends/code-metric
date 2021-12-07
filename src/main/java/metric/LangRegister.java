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

import metric.parser.AbstractLangProcessor;
import metric.parser.LangProcessorRegistration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LangRegister {
	public LangRegister(){
		this(new ArrayList<>());
	}
	public LangRegister(List<String> langs) {
		if (langs.size()==0){
			langs = Arrays.asList("java","python","c");
		}
		if (langs.contains("java")) {
			add(new metric.parser.java.JavaProcessor());
		}
		if (langs.contains("python")) {
			add (new metric.parser.python.PythonProcessor());
		}
		if (langs.contains("c")) {
			add (new metric.parser.cpp.CppProcessor());
		}
	}

	private void add(AbstractLangProcessor langProcessor) {
		LangProcessorRegistration.getRegistry().register(langProcessor);
	}
}


