public class Program {
	private static Program program;
	String sourceFilesDirectory;
	String sourceClassFilesDirectory;
	String testFilesDirectory;
	String testClassFilesDirectory;
	
	private Program() {
		
	}
	
	public static Program createProgram() {
		if(program == null) {
			program = new Program();
		}

		return program;
	}
}
