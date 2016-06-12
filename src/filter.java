package src;

import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class filter {

	public static String pasta_negativas_1 = "C:/Users/Usuario/Downloads/movie_review_dataset/part1/neg";
	public static String pasta_positivas_1 = "C:/Users/Usuario/Downloads/movie_review_dataset/part1/pos";
	public static String pasta_negativas_2 = "C:/Users/Usuario/Downloads/movie_review_dataset/part2/neg";
	public static String pasta_positivas_2 = "C:/Users/Usuario/Downloads/movie_review_dataset/part2/pos";
	public static String arquivo_saida = "C:/Users/Usuario/Downloads/resultado.arff";
	public static ArrayList<String> review_negativa = new ArrayList<String>();
	public static ArrayList<String> review_positiva = new ArrayList<String>();	
	public static ArrayList<String> inutil = new ArrayList<String>();	

	/* Cria um arquivo arff para poder carregar no weka e usar o 
	 filtro "StringToWordVector" para então selecionar palavras  */
	
	public static void main(String[] args) {

		carregaInuteis();
		carregaReviewFiltro();
		tiraInuteis();
		criaArff();
	}

	private static void tiraInuteis() {
				
		for(int i = 0;i < review_negativa.size();i++){
			
			for(int j = 0;j < inutil.size();j++){				
				review_negativa.set(i, review_negativa.get(i).replace(inutil.get(j)," "));
			}					
		}
		
		for(int i = 0;i < review_positiva.size();i++){
			
			for(int j = 0;j < inutil.size();j++){				
				review_positiva.set(i, review_positiva.get(i).replace(inutil.get(j)," "));
			}			
		}
		
	}

	private static void criaArff() {
		
		try {
			PrintWriter arquivo = new PrintWriter(arquivo_saida, "UTF-8");
			
			arquivo.println("@Relation reviews");
			arquivo.println("");
			arquivo.println("@ATTRIBUTE analise STRING");
			arquivo.println("@ATTRIBUTE rotulo {pos,neg}");
			arquivo.println("");
			arquivo.println("@DATA");						
			
			// Escreve as palavras negativas
			for (int i = 0; i < review_negativa.size(); i++)
				arquivo.println("\"" + review_negativa.get(i) + "\",neg");

			// Escreve as palavras positivas
			for (int j = 0; j < review_positiva.size(); j++)
				arquivo.println("\"" + review_positiva.get(j) + "\",pos");
			
			arquivo.close();
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}

	private static void carregaReviewFiltro() {

		File pastaNeg1 = new File(pasta_negativas_1);
		File[] arquivos_neg1 = pastaNeg1.listFiles();
		File pastaNeg2 = new File(pasta_negativas_2);
		File[] arquivos_neg2 = pastaNeg2.listFiles();
		File pastaPos1 = new File(pasta_positivas_1);
		File[] arquivos_pos1 = pastaPos1.listFiles();
		File pastaPos2 = new File(pasta_positivas_2);
		File[] arquivos_pos2 = pastaPos2.listFiles();

		// Percorrer pasta reviews negativas parte 1
		for(File i : arquivos_neg1) {			
			try {
				Scanner s = new Scanner(new FileReader(i));
				review_negativa.add(s.nextLine());
				s.close();

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		// Percorrer pasta reviews negativas parte 2
		for(File i : arquivos_neg2) {			
			try {
				Scanner s = new Scanner(new FileReader(i));
				review_negativa.add(s.nextLine());
				s.close();

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		
		// Percorrer pasta reviews positivas parte 1
				for(File i : arquivos_pos1) {			
					try {
						Scanner s = new Scanner(new FileReader(i));
						review_positiva.add(s.nextLine());
						s.close();

					} catch (Exception e) {
						e.printStackTrace();
					}

				}

				// Percorrer pasta reviews negativas parte 2
				for(File i : arquivos_pos2) {			
					try {
						Scanner s = new Scanner(new FileReader(i));
						review_positiva.add(s.nextLine());
						s.close();

					} catch (Exception e) {
						e.printStackTrace();
					}

				}

	}

	public static void carregaInuteis(){

		inutil.add("1");
		inutil.add("2");
		inutil.add("3");
		inutil.add("4");
		inutil.add("5");
		inutil.add("6");
		inutil.add("7");
		inutil.add("8");
		inutil.add("9");
		inutil.add("10");

		inutil.add(",");
		inutil.add("!");		
		inutil.add(".");
		inutil.add("-");
		inutil.add("<");
		inutil.add(">");
		inutil.add("br");
		inutil.add("/");		
		inutil.add("&");
		inutil.add("$");
		inutil.add("#");
		inutil.add("@");		
		inutil.add("\"");
		inutil.add(" ");
		
	}
}
