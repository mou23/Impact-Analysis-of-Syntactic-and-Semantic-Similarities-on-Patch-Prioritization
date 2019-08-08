public class TestCase implements Comparable <TestCase> {
	String className;
	String methodName;
	int index;
	
	
	public TestCase() {
		this.index = 0;
	}

	@Override
	public String toString() {
		return "TestCase [className=" + className + ", methodName=" + methodName + ", index=" + index + "]";
	}

	@Override
	public int compareTo(TestCase testcase) {
		// TODO Auto-generated method stub
		return Integer.compare(testcase.index, this.index);
	}
}
