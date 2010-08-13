import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

public class Main {

	/**
	 * SEI: obtinene hechos de los documentos, o sea, extrae y organiza la informacion
	 * relevante e ignora la irrelevante. 
	 * 
	 */
	public static void main(String[] args) throws Exception {
		
		System.out.println(" ~ INICIO Procesamiento de comentarios ~" );
		
		String sourcePath = "../";
		File commentfolder = new File(sourcePath + "commentsTest\\");
		//File commentfolder = new File(sourcePath + "comments/");
		
		File[] commentFiles = commentfolder.listFiles(new FileListFilter("yaml"));
		File dataSetResult = new File(sourcePath + "/comments.csv"); // para weka, ahora solo es el diccionario.. testing
		
		if (commentFiles == null) {
			throw new Exception("No se pudo encontrar el path: " + sourcePath);
		}
		
		try {
			
			List<Comment> restoComments = new LinkedList<Comment>();
			
			for (int j = 0; j < commentFiles.length; j++) {
				File restoComment = commentFiles[j];
				//System.out.print("file "+ j +": " + restoComment.getName() +"\n");
				
	    		restoComments.addAll(ParserComments.generateComment(restoComment));
			}
			
			processComment(dataSetResult,restoComments);
			
			//TODO es para escribir todas las palabras de los comentarios
			//writeDic(dataSetResult);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(" ~ FIN Procesamiento de comentarios ~" );
	}
	

	@SuppressWarnings("static-access")
	private static void processComment(File dataSetResult, List<Comment> restoComments) throws IOException {
		
		File stemmerFile = new File( "..//SEI-GO result.csv");
		FileWriter fstream = new FileWriter(stemmerFile);
		BufferedWriter out = new BufferedWriter(fstream);
		
		int restoCount = 1;
		double restoPositive = 0;
		double restoNegative = 0;
		String resto = "";
		for (Iterator<Comment> it = restoComments.iterator(); it.hasNext();) {
			Comment comment = it.next();
			String value = RuleManager.getInstance().getResultRule(comment.getText());
			comment.setAspect(RuleManager.getAspect(value));
			if( value != null && !"".equals(value) ){
				
				//System.out.print("comment: "+comment.toString()+ "\n");
				//System.out.print("	result: \n" + value + "\n\n");
				if(  !comment.getResto().equals(resto)){
					resto = comment.getResto(); 
					double total = restoPositive + restoNegative;
					
					double percentagePos = 0;
					double percentageNeg = 0;
					
					if(total > 0){
						percentagePos = (restoPositive / total)*100;
						percentageNeg = (restoNegative / total)*100;
					}
					
					System.out.print("Comentarios procesados: " + total +"\n");
					System.out.print("	    Positivos: " + restoPositive + "	~ " + percentagePos +"%\n");
					System.out.print("	    Negativos: " + restoNegative + "	~ " + percentageNeg +"%\n");
					System.out.print("-----------------------------------------------\n");
					System.out.print("restó: " + restoCount +" - "+resto + "\n");
					restoCount++;
					restoPositive = 0;
					restoNegative = 0;	
					
					out.write("<<<<<: "+resto+" :>>>>>------------------------------------------------------------------------------------------");
					out.newLine();
					
				}
				if(comment.getAspect()){
					restoPositive++;
				}else{
					restoNegative++;
				}
				out.write("comment: "+comment.toString());
				out.newLine();
				out.write("	result:");
				out.newLine();
				out.write(value);
				out.newLine();
				out.newLine();
				  
			}					                
			
		}
		double percentagePos = 0;
		double percentageNeg = 0;
		double total = restoPositive + restoNegative;
		if(total > 0){
			percentagePos = (restoPositive / total)*100;
			percentageNeg = (restoNegative / total)*100;
		}
		System.out.print("Comentarios procesados: " + total +"\n");
		System.out.print("	    Positivos: " + restoPositive + "	~ " + percentagePos +"%\n");
		System.out.print("	    Negativos: " + restoNegative + "	~ " + percentageNeg +"%\n");
		System.out.print("-----------------------------------------------\n");
		
		out.close();
		System.out.print("	ya se generaron los resultados en " + stemmerFile.getName() + "\n");
		System.out.print("	------------------------------------------------------\n");
		
	}


	@SuppressWarnings("unused")
	private static void writeDic(File dataSetResult) throws IOException {
		System.out.println("INIT Escribiendo diccionario.." );
		FileWriter fstream = new FileWriter(dataSetResult);
		BufferedWriter out = new BufferedWriter(fstream);
		
		HashMap<String, Integer> dic = ParserComments.getDic();
		HashMap<String, List<String>> stemmerDic = ParserComments.getStemmerDic();
		out.write("word|count");
		out.newLine();
		for (Iterator<Entry<String, Integer>> it = dic.entrySet().iterator(); it.hasNext();) {
			Entry<String, Integer> key = it.next();
			out.write(key.getKey() + "|" + key.getValue());
			out.newLine();
		}
		out.close();
		File stemmerFile = new File( "..//stemmerFile.csv");
		fstream = new FileWriter(stemmerFile);
		out = new BufferedWriter(fstream);
		out.write("stemmer|list");
		out.newLine();
		for (Iterator<Entry<String, List<String>>> it = stemmerDic.entrySet().iterator(); it.hasNext();) {
			Entry<String, List<String>> key =  it.next();
			out.write(key.getKey() + "|" + key.getValue());
			out.newLine();
		}
		
		out.close();
		System.out.println("END Escribiendo diccionario.." );
	}


	static class FileListFilter implements FilenameFilter {
		private String extension; 

		public FileListFilter(String extension) {
		  this.extension = extension;
		}
		
		public boolean accept(File directory, String filename) {
		  boolean fileOK = true;
		  if (extension != null) {
		    fileOK &= filename.endsWith('.' + extension);
		  }
		  return fileOK;
		}
	}

}
