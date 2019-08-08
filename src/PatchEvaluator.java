import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;


import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class PatchEvaluator {
	private static PatchEvaluator patchEvaluator;
	ArrayList<TestCase> testCases;
	
	private PatchEvaluator() {
		this.testCases = new ArrayList<TestCase>();
	}
	
	public static PatchEvaluator createPatchEvaluator() {
		if(patchEvaluator == null){
			patchEvaluator = new PatchEvaluator();
		}

		return patchEvaluator;
	}
	
	public void prepareTestClasses() {
		this.testCases.clear();
		File file = new File("fault/tests");
		int count = 0;
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;

			while ((line = br.readLine()) != null) {
				if(count!=0) {
					TestCase testCase = new TestCase();
					String info[] = line.split("#",2);
					testCase.className = info[0];
					info = info[1].split(",");
					testCase.methodName = info[0];
					if(info[1].equals("FAIL")) {
						testCase.index = 1;
					}
					this.testCases.add(testCase);
				}
				count++;
			}

			Collections.sort(this.testCases);
			
			System.out.println(testCases.size() + " Test cases ready!");
//			for(int i = 0; i < testCases.size(); i++) {
//				System.out.println(testCases.get(i));
//			}

			br.close();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public boolean evaluatePatch() {
//		System.out.println("Evaluating");
		Program program = Program.createProgram();
		File testDir = new File(program.testClassFilesDirectory); //new File("digit/" + "test/");
		File srcDir = new File("output/"); //new File("digit/"+ "bin/");
		URL testUrl = null;
		URL srcUrl = null;
		try {
			testUrl = testDir.toURI().toURL();
			srcUrl = srcDir.toURI().toURL();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		URL[] loadpath = new URL[2];

		loadpath[0] = testUrl;
		loadpath[1] = srcUrl;

		ClassLoader classLoader = new URLClassLoader(loadpath);
//		boolean correctPatch = true;
		for(int i = 0; i < this.testCases.size(); i++) {
			try {
				TestCase testCase = this.testCases.get(i);
				Class testClass = Class.forName(testCase.className, true, classLoader);
				Request request = Request.method(testClass, testCase.methodName);
//				JUnitListener listener = new JUnitListener();
//				runner.addListener(listener);
				JUnitCore runner = new JUnitCore();
				Result result = runner.run(request);
//				System.out.println(result.getFailureCount());
				boolean pass = result.wasSuccessful();
				
				if(pass == false) {
//					for (Failure failure : result.getFailures()) {
//						System.out.println(pass + " in "+ testCase.methodName+ " from "+testCase.className);
//						System.out.println(failure.getException());
//						System.out.println(failure.getDescription());
//					}
//					testCase.index++;
//					Collections.sort(testClasses);
					return false;
//					correctPatch = false;
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
		}
//		System.out.println("DONE");
		return true;
	}
}
