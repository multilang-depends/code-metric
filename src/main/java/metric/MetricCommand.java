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

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "metric")
public class MetricCommand {

	@Parameters(index = "0", description = "The directory to be analyzed")
    private String src;
	@Parameters(index = "1",  description = "The output file name")
	private String output;
    @Option(names = {"-f", "--format"},split=",",  description = "the output format: [csv(default)")
    private String[] format=new String[]{"csv"};
	@Option(names = {"-d", "--dir"},  description = "The output directory")
	private String dir;
	@Option(names = {"-s", "--strip-leading-path"},  description = "Strip the leading path.")
    private boolean stripLeadingPath = false;
	@Option(names = {"--strip-paths"}, split=",", description =  "The path(s) to be stripped. if -s enabled, the path(s) start after <src>. "
			+ "Otherwise, the path(s) should be valid.")
	private String[] strippedPaths = new String[]{};
	@Option(names = {"-g", "--granularity"},  description = "Granularity of dependency.[file(default),method,L#(the level of folder. e.g. L1=1st level folder)]")
    private String granularity="file";
	@Option(names = {"-p", "--namepattern"},  description = "The name path pattern.[dot(.), unix(/) or windows(\\)")
    private String namePathPattern="";
	@Option(names = {"-h","--help"}, usageHelp = true, description = "display this help and exit")
    boolean help;
	public MetricCommand() {
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getOutputName() {
		return output;
	}
	public void setOutput(String output) {
		this.output = output;
	}
	public String[] getFormat() {
		return format;
	}
	public String getOutputDir() {
		if (dir==null) {
			dir = System.getProperty("user.dir");
		}
		return dir;
	}
	public boolean isHelp() {
		return help;
	}
    public String getGranularity() {
		return granularity;
	}
	public String getNamePathPattern() {
		return namePathPattern;
	}
	public boolean isStripLeadingPath() {
		return stripLeadingPath;
	}
	
	public String[] getStrippedPaths() {
		return strippedPaths;
	}
	public void setStrippedPaths(String[] strippedPaths) {
		this.strippedPaths = strippedPaths;
	}
}
