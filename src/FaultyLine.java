
public class FaultyLine {
	double suspiciousValue;
	int lineNumber;
	String packageName;
	String fileName; 
	
	@Override
	public String toString() {
		return "FaultyLine [suspiciousValue=" + suspiciousValue + ", lineNumber=" + lineNumber + "]";
	}
}
