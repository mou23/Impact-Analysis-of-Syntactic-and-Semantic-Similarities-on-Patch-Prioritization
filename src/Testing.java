import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
//import digits.src.test.java.introclassJava.digits_6e464f2b_003BlackboxTest;

public class Testing {
	public static void main(String[] args) throws Exception {
//		runTestCases();
//		runGZoltarJar();
		setup();
	}
	
	public static void setup() throws Exception {
		File srcDir = new File("D:/workspace/ComFix/bugs/digits_6e464f2b_003/bin"); //new File("digit/" + "test/introclassJava/");
		File testDir = new File("D:/workspace/ComFix/bugs/digits_6e464f2b_003/test"); //new File("digit/"+ "bin/introclassJava/");
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
		Class testClass =classLoader.loadClass("introclassJava.digits_6e464f2b_003BlackboxTest");
		
		Result result = JUnitCore.runClasses(testClass);
		System.out.println(result.getRunCount());
		System.out.println(result.getFailureCount());
//		System.out.println(result.);
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.getException());
			System.out.println(failure.getDescription());
		}
//		
//		System.out.println(result.wasSuccessful());
		
//		Enumeration<URL> roots = classLoader.getResources("");
//		while(roots.hasMoreElements()) {
////			System.out.println(roots.nextElement().getPath());
//			String url = roots.nextElement().getPath();
//			System.out.println(url);
//			File root = new File(url);
//			for (File file : root.listFiles()) {
//				if (file.isDirectory()==false) {
//				    String name = file.getName();
//				    System.out.println(name);
//				}
//			}
//			System.out.println();
//		}
//		Field f = ClassLoader.class.getDeclaredField("classes");
//		f.setAccessible(true);

//		Class<?> clazz = classLoader.loadClass("digits_6e464f2b_003BlackboxTest");
//		ClassLoader classLoader2 = Thread.currentThread().getContextClassLoader();
//		Vector<Class> classes =  (Vector<Class>) f.get(classLoader);
//
//		for (int i = 0; i<classes.size(); i++) {
//			System.out.println(i);
//			System.out.println(classes.elementAt(i).toString());
//		}
	}
	
	/**************working code******************/
	public static void runTestCases() throws Exception {
		File dir = new File("digits/");
		URL url = new URL("file://" + dir.getAbsolutePath());
		URLClassLoader classLoader = new URLClassLoader(new URL[]{url});
		Class<?> clazz = classLoader.loadClass("TestJunit");
		Result result = JUnitCore.runClasses(clazz);
		
//		Result result = JUnitCore.runClasses(digits/TestJunit.class);
//
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
//
		System.out.println(result.wasSuccessful());
	}
	
	/**************working code******************/
	public static void runGZoltarJar() {
		Process process = null;
		final List<String> message = new ArrayList<String>();
		
		try {
//			ProcessBuilder builder = new ProcessBuilder("java", "-jar", "lib/com.gzoltar-0.0.11-jar-with-dependencies.jar", "D:/workspace/RSRepair/digit", "introclassJava", "bin/:test/");
//			ProcessBuilder builder = new ProcessBuilder("java", "-jar", "lib/com.gzoltar-1.5.1-jar-with-dependencies.jar", "-diagnose", ");
			ProcessBuilder builder = new ProcessBuilder("java", "-jar", "lib/com.gzoltar-1.6.1-java7-jar-with-dependencies.jar", "-Dgzoltar_data_dir=fault/",  "-DclassesDir=digit003/bin/", "-DtestsDir=digit003/test/", "-Dcoefficients=JACCARD" ,"-diagnose");//"-listParameters");//, "-DtestsDir=E:/fault-localization-research-master/src/triangle/target/test-classes/triangle/");
//			ProcessBuilder builder = new ProcessBuilder("java -jar lib/com.gzoltar-0.0.11-jar-with-dependencies.jar version"); //D:/workspace/RSRepair/003 introclassJava bin/:test/
//			System.out.println("prob");
			builder.redirectErrorStream(true);
			process = builder.start();
			final InputStream inputStream = process.getInputStream();
			
			Thread processReader = new Thread(){
				public void run() {
					BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
					String line;
					try {
						while((line = reader.readLine()) != null) {
							System.out.println(line);
							message.add(line);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					try {
						reader.close();
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
			};
			
			processReader.start();
			try {
				processReader.join();
				process.waitFor();
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (process != null) {
				process.destroy();
			}
			process = null;
		}
		
//		System.out.println(message); 
	}
}  