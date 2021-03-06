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
	@Option(names = {"-d", "--dir"},  description = "The output directory")
	private String dir;
	@Option(names = {"-s", "--strip-leading-path"},  description = "Strip the leading path.")
    private boolean stripLeadingPath = false;
	@Option(names = {"--strip-paths"}, split=",", description =  "The path(s) to be stripped. if -s enabled, the path(s) start after <src>. "
			+ "Otherwise, the path(s) should be valid.")
	private String[] strippedPaths = new String[]{};
	@Option(names = {"-p", "--namepattern"},  description = "The name path pattern.[dot(.), unix(/) or windows(\\)")
    private String namePathPattern="";
	@Option(names = {"--gui"},  description = "show GUI")
	boolean gui = false;
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
	public String getOutputDir() {
		if (dir==null) {
			dir = System.getProperty("user.dir");
		}
		return dir;
	}
	public boolean isHelp() {
		return help;
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
	public boolean showGUI(){return this.gui;}
}
