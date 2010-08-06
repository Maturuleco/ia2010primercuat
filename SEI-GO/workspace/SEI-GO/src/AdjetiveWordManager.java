import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

import org.tartarus.snowball.SnowballStemmer;


public class AdjetiveWordManager extends WordManager{

	private static AdjetiveWordManager instance;
	private static List<String> positiveWords = new LinkedList<String>();
	private static List<String> negativeWords = new LinkedList<String>();
	
	public AdjetiveWordManager() {
		File source = new File("../positiveWords.txt");
		
		try {
			BufferedReader buffer = new BufferedReader( new FileReader( source ) );
			String line;
			while ( (line = buffer.readLine())!=null) {
				String[] sourceWords = line.split(";");
				for (int i = 0; i < sourceWords.length; i++) {
					positiveWords.add(sourceWords[i]);
				}
			}
			
			source = new File("../negativeWords.txt");
			buffer = new BufferedReader( new FileReader( source ) );
			while ( (line = buffer.readLine())!=null) {
				String[] sourceWords = line.split(";");
				for (int i = 0; i < sourceWords.length; i++) {
					negativeWords.add(sourceWords[i]);
				}
			}
			
		} catch (Exception e) {
			System.out.println("No se pudo cargar los adjetivos. " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static AdjetiveWordManager getInstance(){
		if(instance == null){
			instance = new AdjetiveWordManager();
		}
		return instance;
	}
	
	public Boolean containWord(String word){
		return positiveWords.contains(word.toLowerCase()) || negativeWords.contains(word.toLowerCase());
	}
	
	public String getName(){
		return Constant.KEY_NAME_AD;
	}
	
	public String getPath(){
		return "../positiveWords.txt";
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
	
	public Boolean getAspect(String text) {
		
		int positive = 0;
		int negative = 0;
		text = text.replaceAll("-", " ");
		String[] words = text.split(" ");
		
		for (int i = 0; i < words.length; i++) {
			String word = words[i];
			word = getStemmerWord(word);
			if(containWord(word.toLowerCase())){
				if(negativeWords.contains(word.toLowerCase())){
					negative++;
				}else{
					positive++;
				}
			}
		}
		
		return positive >= negative;
	}
}
