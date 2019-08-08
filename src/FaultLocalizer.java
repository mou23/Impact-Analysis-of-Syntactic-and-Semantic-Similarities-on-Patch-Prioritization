import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class FaultLocalizer {
	ArrayList<FaultyLine> faultyLines;
	private static FaultLocalizer faultLocalizer;
	
	private FaultLocalizer() {
		this.faultyLines = new ArrayList<FaultyLine>();
	}
	
	public static FaultLocalizer createFaultLocalizer() {
		if(faultLocalizer == null){
			faultLocalizer = new FaultLocalizer();
		}

		return faultLocalizer;
	}
	
	private void runGZoltar() {
		Process process = null;
		
		try {			
//			ProcessBuilder builder = new ProcessBuilder("java", "-jar", "lib/com.gzoltar-1.6.1-java7-jar-with-dependencies.jar", "-Dgzoltar_data_dir=fault/",  "-DclassesDir=digit003/bin/", "-DtestsDir=digit003/test/", "-Dcoefficients=JACCARD", "-diagnose");//, "-DtestsDir=E:/fault-localization-research-master/src/triangle/target/test-classes/triangle/");
			Program program = Program.createProgram();
			ProcessBuilder builder = new ProcessBuilder("java", "-jar", "lib/com.gzoltar-1.6.1-java7-jar-with-dependencies.jar", "-Dgzoltar_data_dir=fault/",  "-DclassesDir=" + program.sourceClassFilesDirectory, "-DtestsDir=" + program.testClassFilesDirectory, "-Dcoefficients=OCHIAI", "-diagnose");//, "-DtestsDir=E:/fault-localization-research-master/src/triangle/target/test-classes/triangle/");
			process = builder.start();
			final InputStream inputStream = process.getInputStream();
			
			Thread processReader = new Thread(){
				public void run() {
					BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
					String line;
					try {
						while((line = reader.readLine()) != null) {
//							System.out.println(line);
						}
					} catch (Exception e) {
						System.out.println(e.getMessage());
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
	}
	
	public void localizeFault() {
		this.runGZoltar();
		System.out.println("Fault Localizaton done!");
		this.faultyLines.clear();
		File file = new File("fault/spectra");
		int count = 0;
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			
			while ((line = br.readLine()) != null) {
				if(count!=0) {
//					System.out.println(line);
					FaultyLine faultyLine = new FaultyLine();
					String info[] = line.split("\\[",2);
					faultyLine.packageName = info[0];
					info = info[1].split("<",2);
					faultyLine.fileName = info[0];
					info = info[1].split("#");
					info = info[1].split(",");
					faultyLine.lineNumber = Integer.parseInt(info[0]);
					faultyLine.suspiciousValue = Double.parseDouble(info[1]);
					if(faultyLine.suspiciousValue > 0) {
						this.faultyLines.add(faultyLine);
					}
				}
				count++;
			}
			
//			for(int i = 0; i < faultyLines.size(); i++) {
//				System.out.println(faultyLines.get(i));
//			}
			br.close();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
