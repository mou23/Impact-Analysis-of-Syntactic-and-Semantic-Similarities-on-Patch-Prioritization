import java.io.File;

public class RepairedBugChecker {
	public static void main(String[] args) {
		runProject();
		System.out.println("The End!");
//		System.exit(0);
	}

	public static void runProject() {
		File folder = new File("bugs_v2/");
		File[] listOfFiles = folder.listFiles();
		try {
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isDirectory()) {
					String project = folder.getName()+ "/" + listOfFiles[i].getName() + "/";
					Program program = Program.createProgram();
					program.sourceFilesDirectory = project + "src/main"; //"grade001/src/main";//"digit003/src/main"; //"syl002/src/main";//
					program.sourceClassFilesDirectory = project + "bin"; //"grade001/bin";//"syl002/bin";//
					program.testFilesDirectory = project + "src/test"; //"grade001/src/test";//"syl002/src/test";//
					program.testClassFilesDirectory = project + "test"; //"grade001/test";//"syl002/test";//
					System.out.println(project);
					scanDirectory(new File(program.sourceFilesDirectory));
					System.out.println();
					System.out.println();
					System.out.println();
					System.out.println();
				}
			}
		} catch(Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}
	
	private static void scanDirectory(File folder) {
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile() && listOfFiles[i].getName().contains(".java")) {
				long startingTime = System.nanoTime();
				System.out.println("Localizing Fault");
				FaultLocalizer faultLocalizer = FaultLocalizer.createFaultLocalizer();
				faultLocalizer.localizeFault();
				System.out.println("Preparing Testcases");
				PatchEvaluator patchEvaluator = PatchEvaluator.createPatchEvaluator();
				patchEvaluator.prepareTestClasses();
				PatchGenerator patchGenerator = PatchGenerator.createPatchGenerator(); 
				patchGenerator.generatePatch(listOfFiles[i], startingTime);
				break;
			}
			else if (listOfFiles[i].isDirectory()) {
				scanDirectory(new File(folder+"/"+listOfFiles[i].getName()));
			}
		}
	}
}
