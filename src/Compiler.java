import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class Compiler {
	private static Compiler compiler;
	private Compiler() {
		
	}
	
	public static Compiler createCompiler() {
		if(compiler == null) {
			compiler = new Compiler();
		}

		return compiler;
	}
	
	public boolean compileProject(String projectToBeCompiled, String outputDirectory) {
//		System.out.println("CALLED");
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        MyDiagnosticListener diagnosticListener = new MyDiagnosticListener();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnosticListener, null, null);

        File file = new File(projectToBeCompiled);
        Iterable<? extends JavaFileObject> javaFileObjects = fileManager.getJavaFileObjects(file);
//        JavaCompiler.CompilationTask task =
//                compiler.getTask(null, fileManager, diagnosticListener, null, null, javaFileObjects);
        
        String[] options = new String[] { "-d", outputDirectory };
//        File[] javaFiles = new File[] { new File("src/gima/apps/flip/TestClass.java") };
        
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnosticListener, Arrays.asList(options), null, javaFileObjects);
//        System.out.println("TASK");
        if (task.call()) {
//            System.out.println("compilation complete");
        }
        else {
//        	System.out.println("error in compilation");
        	return false;
        }
        try {
			fileManager.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
        return true;
	}
	
	private static final class MyDiagnosticListener implements DiagnosticListener {
        @Override
        public void report(Diagnostic diagnostic) {
//            System.out.println(diagnostic);
        }
    }
}
