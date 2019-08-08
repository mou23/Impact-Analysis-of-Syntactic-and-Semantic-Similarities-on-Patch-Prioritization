import java.util.HashMap;

public class LineWiseTokens {
	int lineNo;
	HashMap<String,Integer> tokens; 
	
	public LineWiseTokens() {
		this.tokens = new HashMap<String,Integer>(); 
	}

	@Override
	public String toString() {
		return "LineWiseTokens [lineNo=" + lineNo + ", tokens=" + tokens + "]";
	}
}
