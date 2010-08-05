import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class RuleManager {
	
	private static String KEY_NAME = "RULE";
	private static List<Rule> rules = new LinkedList<Rule>();
	private static RuleManager instance; 
	
	public RuleManager() {
		File source = new File("..\\rules.txt");
		
		try {
			BufferedReader buffer = new BufferedReader( new FileReader( source ) );
			String line;
			int name = 0;
			while ( (line = buffer.readLine())!=null) {
				String[] sourceWords = line.split(";");
				for (int i = 0; i < sourceWords.length; i++) {
					String stringRule = sourceWords[i];
					rules.add(new Rule(name,stringRule.split(" ")));
				}
				name++;
			}
		} catch (Exception e) {
			System.out.println("No se pudo cargar las reglas. " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static RuleManager getInstance(){
		if(instance == null){
			instance = new RuleManager();
		}
		return instance;
	}
	
	public static String getResultRule(String text){
		
		String[] words = ParserComments.cleanText(text).split(" ");
		
		String result = "";
		for (Iterator it = rules.iterator(); it.hasNext();) {
			Rule rule = (Rule) it.next();
			String ruleResult = evaluateRule(rule, words);
			if( ruleResult != null && !"".equals(ruleResult)){
				result += "| " + rule.getName() +" "+  ruleResult;
			}
		}
		return result;
	}
	
	private static String evaluateRule(Rule rule, String[] words) {
		String result = "";
		for (int i = 0; i < words.length; i++) {
			String evaluate = evaluateRule(rule, words, i);
			result += evaluate;
			if( !"".equals(evaluate) ){
				i+=evaluate.split(" ").length-2;
			}
		}
		return result;
	}

	private static String evaluateRule(Rule rule, String[] words, int from) {
		String result = "";
		int indexRule = 0;
		for (int i = from; i < words.length; i++) {
			String word = words[i];
			if( !"".equals(word) ){
					
				if( indexRule == rule.getEstructure().size() ){
					break;
				}
				String rulePart = rule.getEstructure().get(indexRule);
				WordManager wmanager = WordManagerFactory.getWordManager(rulePart);
				if( rulePart.contains("+") || rulePart.contains("*") ){
					// se pueden tner mas de una repeticion de esta parte en el texto
					
					boolean finish = false;
					int count = 0; 
					while(!finish && i < words.length){
						word = words[i];
						if( wmanager.containWord(word) ){
							result += " " + word;
							i++;
							count++;
						}else {
							finish = true;
							i--;
						}
						
					}
					if(  rulePart.contains("+") && count == 0 ){
						result = ""; // inicio nuevamente la rule
						break;
					}
				}else{
					// es una parte simple
					if( wmanager.containWord(word) ){
						result += " " + word;
					}else {
						result = ""; // inicio nuevamente la rule
						break;
					}
					
				}
				indexRule++;
			}
		}
		if( !"".equals(result) ){
			result+="-";
		}
		return result;
	}

	public static String getName(){
		return KEY_NAME;
	}
	
	
}
