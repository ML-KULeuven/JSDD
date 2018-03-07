package jni;

public class JniPointer {
	protected long swigCPtr;
	
	protected JniPointer(long cPtr) {
		swigCPtr = cPtr;
	}
	
	public JniPointer() {
		swigCPtr = 0;
	}
	
	public static long getCPtr(JniPointer pointer){
		return (pointer==null) ? 0 : pointer.swigCPtr;
	}

	@Override
	public String toString() {
		return "" + swigCPtr;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof JniPointer){
			return ((JniPointer) o).swigCPtr== this.swigCPtr;
		}
		return false;

	}
}
