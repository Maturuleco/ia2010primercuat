import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.tartarus.snowball.SnowballStemmer;


public class RuleManager {
	
	private static List<Rule> rules = new LinkedList<Rule>();
	private static RuleManager instance; 
	
	public RuleManager() {
		File source = new File("../rules.txt");
		
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
		for (Iterator<Rule> it = rules.iterator(); it.hasNext();) {
			Rule rule = it.next();
			String ruleResult = evaluateRule(rule, words);
			if( ruleResult != null && !"".equals(ruleResult)){
				result += "               | " + rule.getName() +" "+  ruleResult + "\n";
			}
		}
		return result;
	}
	
	private static String evaluateRule(Rule rule, String[] words) {
		String result = "";
		for (int i = 0; i < words.length; i++) {
			String evaluate = evaluateRule(rule, words, i);
			if( !result.contains(evaluate) )
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
			String stemmerWord = getStemmerWord(word); 
			if( !"".equals(word) ){
					
				if( indexRule == rule.getEstructure().size() ){
					break;
				}
				String rulePart = rule.getEstructure().get(indexRule);
				List<WordManager> wmanagers = WordManagerFactory.getWordManagers(rulePart);
				if( rulePart.contains("+") || rulePart.contains("*") ){
					
					// se pueden tner mas de una repeticion de esta parte en el texto
					boolean finish = false;
					int count = 0; 
					while(!finish && i < words.length){
						word = words[i];
						stemmerWord = getStemmerWord(word);
						if( someWordManagerContainWord(wmanagers, stemmerWord) ){
							result += " " + word;
							i++;
							count++;
						}else {
							finish = true;
							i--;
						}
					}
					if(  rulePart.contains("+") && count == 0 ){
						result = ""; // inicio nuevamente la regla
						break;
					}
				}else{
					// es una parte simple
					if( someWordManagerContainWord(wmanagers,stemmerWord) ){
						result += " " + word;
					}else {
						result = ""; // inicio nuevamente la regla
						break;
					}
				}
				indexRule++;
			}
		}
		if(indexRule < rule.getEstructureSize())
			result = "";
		
		if( !"".equals(result) ){
			result+="-";
		}
		
		return result;
	}

	private static boolean someWordManagerContainWord(List<WordManager> wmanagers, String stemmerWord) {
		boolean containWord = false;
		for (Iterator<WordManager> it = wmanagers.iterator(); it.hasNext() && !containWord;) {
			WordManager wordManager = (WordManager) it.next();
			if(wordManager.containWord(stemmerWord))
				containWord = true;
		}
		return containWord;
	}

	public static Boolean getAspect(String text) {
		WordManager wmanager = WordManagerFactory.getWordManager(Constant.KEY_NAME_AD);
		return wmanager.getAspect(text);
	}

	@SuppressWarnings("unchecked")
	private static String getStemmerWord(String word) {
		String stemmerWord = "";
		try{
			Class stemClass = Class.forName("org.tartarus.snowball.ext.spanishStemmer");
			SnowballStemmer stemmer = (SnowballStemmer) stemClass.newInstance();
			stemmer.setCurrent(word);
			stemmer.stem();
			stemmerWord = stemmer.getCurrent();
		} catch (Exception e) {
			System.out.println("No se pudo encontrar la raiz de la palabra. " + e.getMessage());
			e.printStackTrace();
		}
		return stemmerWord;
	}

	public static String getName(){
		return Constant.KEY_NAME_RU;
	}
	
}
