import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;


public class EmptyWordManager {

	private static List<String> words = new LinkedList<String>();
	
	/**
	 * Pueden ser articulos, preposiciones etc. 
	 */
	public EmptyWordManager(String path) {
		File source = new File(path + "emptyWords.txt");
		
		try {
			BufferedReader buffer = new BufferedReader( new FileReader( source ) );
			String line;
			while ( (line = buffer.readLine())!=null) {
				String[] sourceWords = line.split(";");
				for (int i = 0; i < sourceWords.length; i++) {
					words.add(sourceWords[i]);
				}
			}
		} catch (Exception e) {
			System.out.println("No se pudo cargar las palabras vacias. " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static Boolean isEmptyWord(String word){
		return words.contains(word.toLowerCase());
	}
	
}
