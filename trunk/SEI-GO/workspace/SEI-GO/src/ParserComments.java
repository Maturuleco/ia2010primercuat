import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.tartarus.snowball.SnowballStemmer;


public class ParserComments {
	
	private static SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
	private static HashMap<String, Integer> dic = new HashMap<String, Integer>();
	private static HashMap<String, List<String>> stemmerDic = new HashMap<String, List<String>>();
    
	public static List<Comment> generateComment(File restoComment) throws Exception{
		
		List<Comment> 	result = new LinkedList<Comment>();
		BufferedReader 	buffer = new BufferedReader( new FileReader( restoComment ) );
		String 			line = buffer.readLine(); // consumo la primer línea que no tiene nada - -
		
		String 	resto = restoComment.getName();
		String 	serviceRank = null;
		String 	foodRank = null;
		String 	environmentRank = null;
		Date 	date = null;
		String 	user = null;
		String 	text = null;
		HashMap<String, Integer> words = null;
		
		while ( (line = buffer.readLine())!=null) {
			
			serviceRank = line.split("\\:")[1].trim();
			
			line = buffer.readLine();
			foodRank = line.split("\\:")[1].trim();
			
			line = buffer.readLine();
			String commentText = line.substring(10);
			text = replaceCodes(commentText); 
			
			line = buffer.readLine();
			if(!line.startsWith("    date")){ // si el comentario no termino, sigo leyendo
				
				while( !line.startsWith("    date") ){
					text += replaceCodes(line); 
					line = buffer.readLine();
				}
			}
			
			date = formatter.parse(line.split("\\:")[1]);
			
			line = buffer.readLine();
			user = line.split("/")[4]; // ej. user: http://mi.guiaoleo.com/members/17248
			
			line = buffer.readLine();
			environmentRank = line.split("\\:")[1].trim();
			
			words = getTextWords(text);
			
			//TODO comentar para no crear los objetos y generar solo el diccionario
			result.add(new Comment(resto,serviceRank,foodRank,environmentRank,date,user,text, words));
		}
	    
	    return result;
	}

	private static String replaceCodes(String text) {
		String result = text.replaceAll("\\\\xF3", "o");
		result = result.replaceAll("\\\\xE9", "e");
		result = result.replaceAll("\\\\xE1", "a");
		result = result.replaceAll("\\\\xED", "i");
		result = result.replaceAll("\\\\xFA", "u");
		//result = result.replaceAll("\\\\xf1", "ñ");
		result = result.replaceAll("\\\\xf1", "ni");
		result = result.replaceAll("\\\\xA8", "¿");
		result = result.replaceAll("\\\\\"", "");
		result = result.replaceAll("\\\\r\\\\n\\\\", " ");
		result = result.replaceAll("\"", "");
		return result;
	}
	
	private static HashMap<String, Integer> getTextWords(String text) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		String processText = cleanText(text);
		String[] words = processText.split(" ");
		
		
		for (int i = 0; i < words.length; i++) {
			String word = words[i].trim().toLowerCase();
			
			// Para tener el el comment las palabras de su texto y cantidad de apariciones.
			// por ahora no se usa.
			if( !"".equals(word) && !ArticlesWordManager.getInstance().containWord(word) ){
				Integer value = new Integer(1);
				if( map.containsKey(word) ){
					value = map.get(word) + 1;
				}
				
				map.put(word, value);
				addOrModifyWordToDictionary(addOrModifyWordToStemmerDictionary(word));
				
			}
		}
		return map;
	}
/*
 	// La idea era obtener las oraciones remplazadas con los terminos de reglas y luego aplicarle una expresion
 	// regular posta, esto no termine porque no se como hacer para obtener despues la oracion real una vez que 
 	// se que aplica la regla 
	private static List<String> getRulesSentences(String text) {
		List<String> map = new LinkedList<String>();
		String processText = cleanText(text);
		String[] sentences = processText.split(".");
		
		for (int i = 0; i < sentences.length; i++) {
			String sentence = sentences[i];
			String[] words = sentence.split(" ");
			
			
			if( !"".equals(word) && !EmptyWordManager.getInstance().containWord(word) ){
				Integer value = new Integer(1);
				if( map.containsKey(word) ){
					value = map.get(word) + 1;
				}
				
				map.put(word, value);
				addOrModifyWordToDictionary(addOrModifyWordToStemmerDictionary(word));
				
			}
		}
		return map;
	}
	*/
	private static void addOrModifyWordToDictionary(String word) {
		Integer value = new Integer(1);
		if( dic.containsKey(word) ){
			value = dic.get(word) + 1;
		}
		dic.put(word, value);
	}
	
	@SuppressWarnings("unchecked")
	private static String addOrModifyWordToStemmerDictionary(String word) {
		String stemmerWord = "";
		try {
			Class stemClass = Class.forName("org.tartarus.snowball.ext.spanishStemmer");
		
			SnowballStemmer stemmer = (SnowballStemmer) stemClass.newInstance();
			
			List<String> value = new LinkedList<String>();
			value.add(word);
			stemmer.setCurrent(word);
			stemmer.stem();
			stemmerWord = stemmer.getCurrent();
			
			if( stemmerDic.containsKey(stemmerWord) ){
				if(!((List<String>)stemmerDic.get(stemmerWord)).contains(word)) {
					((List<String>)stemmerDic.get(stemmerWord)).add(word);
				}
			
				value = (List<String>)stemmerDic.get(stemmerWord);
			}
			stemmerDic.put(stemmerWord, value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return stemmerWord;
	}

	public static String cleanText(String text) {
		String processText = text.replaceAll("\\,", " ,");
		processText = processText.replaceAll("\\.", " .");
		processText = processText.replaceAll("\\;", " ;");
		processText = processText.replaceAll("\\:", " :");
		
//		String processText = text.replaceAll("\\,", " ");
//		processText = processText.replaceAll("\\.", " ");
//		processText = processText.replaceAll("\\;", " ");
//		processText = processText.replaceAll("\\:", " ");
//		processText = processText.replaceAll("\\*", " ");
		
		processText = processText.replaceAll("hhh", "h");
		processText = processText.replaceAll("aaa", "a");
		processText = processText.replaceAll("Buenos Aires", "BsAs");
		processText = processText.replaceAll("buenos aires", "BsAs");
		processText = processText.replaceAll("\\¿", " ");
		processText = processText.replaceAll("\\?", " ");
		processText = processText.replaceAll("\\!", " ");
		processText = processText.replaceAll("\\-", " ");
		processText = processText.replaceAll("\\(", " ");
		processText = processText.replaceAll("\\)", " ");
		processText = processText.replaceAll("\\$", " ");
		processText = processText.replaceAll("\\#", " ");
		processText = processText.replaceAll("\\%", " ");
		processText = processText.replaceAll("\\\\x93", " ");
		processText = processText.replaceAll("\\xa1", " ");
		//processText = processText.replaceAll("[0-9]*", " ");
		processText = processText.replaceAll("\\|", "/");
		processText = processText.replaceAll("\\/", " ");
		
		return processText;
	}

	public static HashMap<String, Integer> getDic() {
		return dic;
	}
	
	public static HashMap<String, List<String>> getStemmerDic() {
		return stemmerDic;
	}
	
}

