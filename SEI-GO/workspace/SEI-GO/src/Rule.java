import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class Rule {

	private String name;
	private List<String> estructure = new LinkedList<String>();

	public Rule(int name, String[] values) {
		super();
		this.name = "R" + name;
		for (int i = 0; i < values.length; i++) {
			estructure.add(values[i]);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getEstructure() {
		return estructure;
	}

	public void setEstructure(List<String> estructure) {
		this.estructure = estructure;
	}

	@Override
	public String toString() {
		String result = "";
		for (Iterator<String> it = estructure.iterator(); it.hasNext();) {
			String word = it.next();
			result += " " + word;
		}
		return result;
	}
	
	
	
}
