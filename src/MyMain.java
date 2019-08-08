import java.io.File;

public class MyMain {
	public static void main(String[] args) throws Exception {
		Program program = Program.createProgram();
		program.sourceFilesDirectory = "bugs/smallest_f8d57dea_000/src/main"; //"grade001/src/main";//"digit003/src/main"; //"syl002/src/main";//
		program.sourceClassFilesDirectory = "bugs/smallest_f8d57dea_000/bin"; //"grade001/bin";//"syl002/bin";//
		program.testFilesDirectory = "bugs/smallest_f8d57dea_000/src/test"; //"grade001/src/test";//"syl002/src/test";//
		program.testClassFilesDirectory = "bugs/smallest_f8d57dea_000/test"; //"grade001/test";//"syl002/test";//

		scanDirectory(new File(program.sourceFilesDirectory));
		System.out.println("END");
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
