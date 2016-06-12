package src;

import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CriaSaida {

	public static String pasta_negativas_1 = "C:/Users/Usuario/Downloads/movie_review_dataset/part1/neg";
	public static String pasta_positivas_1 = "C:/Users/Usuario/Downloads/movie_review_dataset/part1/pos";
	public static String pasta_negativas_2 = "C:/Users/Usuario/Downloads/movie_review_dataset/part2/neg";
	public static String pasta_positivas_2 = "C:/Users/Usuario/Downloads/movie_review_dataset/part2/pos";
	public static String arquivo_saida = "C:/Users/Usuario/Downloads/resultado.arff";
	public static ArrayList<String> review_negativa = new ArrayList<String>();
	public static ArrayList<String> review_positiva = new ArrayList<String>();
	public static ArrayList<String> palavra_negativa = new ArrayList<String>();
	public static ArrayList<String> palavra_positiva = new ArrayList<String>();
	public static ArrayList<String> inutil = new ArrayList<String>();

	public static void main(String[] args) {

		System.out.println("Processando...");
		carregaInuteis();
		carregaAtributos();
		carregaReviewFiltro();
		tiraInuteis();		
		criaArff();
		System.out.println("Terminou!");
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

		String[] frase;		

		try {
			PrintWriter arquivo = new PrintWriter(arquivo_saida, "UTF-8");

			arquivo.println("@RELATION reviews");
			arquivo.println("");

			for(int i = 0;i < palavra_positiva.size();i++)
				arquivo.println("@ATTRIBUTE "+palavra_positiva.get(i)+" REAL");

			for(int i = 0;i < palavra_negativa.size();i++)
				arquivo.println("@ATTRIBUTE "+palavra_negativa.get(i)+" REAL");

			arquivo.println("@ATTRIBUTE rotulo {pos,neg}");
			arquivo.println("");
			arquivo.println("@DATA");						
			
			for (int i = 0; i < review_positiva.size(); i++){

				frase = review_positiva.get(i).split(" ");

				// Conta palavras positivas
				for(int j = 0;j < palavra_positiva.size();j++){
					int contador = 0;

					for(int k = 0;k < frase.length;k++){
						int dif = Math.abs(palavra_positiva.get(j).length() - frase[k].length());
						double coeficiente = 0;

						if(dif < 5){
							coeficiente = diceCoefficientOptimized(palavra_positiva.get(j),frase[k]);
							if(coeficiente > 0.84)
								contador++;
						}
					}

					if(contador == 0)
						arquivo.print("0,");						
					else
						arquivo.print(contador+",");
				}
				
				// Conta palavras negativas
				for(int j = 0;j < palavra_negativa.size();j++){
					int contador = 0;

					for(int k = 0;k < frase.length;k++){
						int dif = Math.abs(palavra_negativa.get(j).length() - frase[k].length());
						double coeficiente = 0;

						if(dif < 5){
							coeficiente = diceCoefficientOptimized(palavra_negativa.get(j),frase[k].toLowerCase().toLowerCase());
							if(coeficiente > 0.84)
								contador++;
						}
					}

					if(contador == 0)
						arquivo.print("0,");						
					else
						arquivo.print(contador+",");
				}
								
				arquivo.println("pos");
			}

			// Conta as palavras negativas
			for (int i = 0; i < review_negativa.size(); i++){

				frase = review_negativa.get(i).split(" ");

				// Conta palavras positivas
				for(int j = 0;j < palavra_positiva.size();j++){
					int contador = 0;

					for(int k = 0;k < frase.length;k++){
						int dif = Math.abs(palavra_positiva.get(j).length() - frase[k].length());
						double coeficiente = 0;

						if(dif < 5){
							coeficiente = diceCoefficientOptimized(palavra_positiva.get(j),frase[k]);
							if(coeficiente > 0.84)
								contador++;
						}
					}

					if(contador == 0)
						arquivo.print("0,");						
					else
						arquivo.print(contador+",");
				}
				
				// Conta palavras negativas
				for(int j = 0;j < palavra_negativa.size();j++){
					int contador = 0;

					for(int k = 0;k < frase.length;k++){
						int dif = Math.abs(palavra_negativa.get(j).length() - frase[k].length());
						double coeficiente = 0;

						if(dif < 5){
							coeficiente = diceCoefficientOptimized(palavra_negativa.get(j),frase[k].toLowerCase().toLowerCase());
							if(coeficiente > 0.84)
								contador++;
						}
					}

					if(contador == 0)
						arquivo.print("0,");						
					else
						arquivo.print(contador+",");
				}
				
				arquivo.println("neg");
			}

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
	
	public static double diceCoefficientOptimized(String s, String t)
	{
		// Verifying the input:
		if (s == null || t == null)
			return 0;
		// Quick check to catch identical objects:
		if (s == t)
			return 1;
	        // avoid exception for single character searches
	        if (s.length() < 2 || t.length() < 2)
	            return 0;

		// Create the bigrams for string s:
		final int n = s.length()-1;
		final int[] sPairs = new int[n];
		for (int i = 0; i <= n; i++)
			if (i == 0)
				sPairs[i] = s.charAt(i) << 16;
			else if (i == n)
				sPairs[i-1] |= s.charAt(i);
			else
				sPairs[i] = (sPairs[i-1] |= s.charAt(i)) << 16;

		// Create the bigrams for string t:
		final int m = t.length()-1;
		final int[] tPairs = new int[m];
		for (int i = 0; i <= m; i++)
			if (i == 0)
				tPairs[i] = t.charAt(i) << 16;
			else if (i == m)
				tPairs[i-1] |= t.charAt(i);
			else
				tPairs[i] = (tPairs[i-1] |= t.charAt(i)) << 16;

		// Sort the bigram lists:
		Arrays.sort(sPairs);
		Arrays.sort(tPairs);

		// Count the matches:
		int matches = 0, i = 0, j = 0;
		while (i < n && j < m)
		{
			if (sPairs[i] == tPairs[j])
			{
				matches += 2;
				i++;
				j++;
			}
			else if (sPairs[i] < tPairs[j])
				i++;
			else
				j++;
		}
		return (double)matches/(n+m);
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
	
	private static void carregaAtributos() {
		
		palavra_positiva.add("good");
		palavra_positiva.add("great");
		palavra_positiva.add("well");
		palavra_positiva.add("amazing");
		palavra_positiva.add("appreciate");
		palavra_positiva.add("awesome");
		palavra_positiva.add("beautifully");
		palavra_positiva.add("beauty");
		palavra_positiva.add("best");
		palavra_positiva.add("clever");
		palavra_positiva.add("cool");
		palavra_positiva.add("decent");
		palavra_positiva.add("enjoyed");
		palavra_positiva.add("entertaining");
		palavra_positiva.add("excellent");
		palavra_positiva.add("fantastic");
		palavra_positiva.add("favorite");
		palavra_positiva.add("fine");
		palavra_positiva.add("funny");
		palavra_positiva.add("genius");
		palavra_positiva.add("greatest");
		palavra_positiva.add("happy");
		palavra_positiva.add("hilarious");
		palavra_positiva.add("highly");
		palavra_positiva.add("impressive");
		palavra_positiva.add("incredible");
		palavra_positiva.add("intelligent");
		palavra_positiva.add("interesting");
		palavra_positiva.add("liked");
		palavra_positiva.add("loved");
		palavra_positiva.add("masterpiece");
		palavra_positiva.add("memorable");
		palavra_positiva.add("nice");
		palavra_positiva.add("outstanding");
		palavra_positiva.add("perfectly");
		palavra_positiva.add("powerful");
		palavra_positiva.add("realistic");
		palavra_positiva.add("recommend");
		palavra_positiva.add("solid");
		palavra_positiva.add("strong");
		palavra_positiva.add("stunning");
		palavra_positiva.add("superb");
		palavra_positiva.add("talented");
		palavra_positiva.add("terrific");
		palavra_positiva.add("unique");
		palavra_positiva.add("wonderful");
		palavra_positiva.add("worked");
		palavra_positiva.add("better");		
		palavra_positiva.add("ok");		
		palavra_positiva.add("original");
		
		palavra_negativa.add("bad");
		palavra_negativa.add("boring");
		palavra_negativa.add("creepy");
		palavra_negativa.add("disappointing");
		palavra_negativa.add("falls");
		palavra_negativa.add("hate");
		palavra_negativa.add("lack");
		palavra_negativa.add("poor");
		palavra_negativa.add("problem");
		palavra_negativa.add("wrong");
		palavra_negativa.add("unfortunately");
		palavra_negativa.add("forced");
		palavra_negativa.add("garbage");
		palavra_negativa.add("horrible");
		palavra_negativa.add("lame");
		palavra_negativa.add("mess");
		palavra_negativa.add("painful");
		palavra_negativa.add("pathetic");
		palavra_negativa.add("pointless");
		palavra_negativa.add("predictable");
		palavra_negativa.add("ridiculous");
		palavra_negativa.add("shame");
		palavra_negativa.add("silly");
		palavra_negativa.add("stupid");
		palavra_negativa.add("terrible");
		palavra_negativa.add("trash");
		palavra_negativa.add("waste");
		palavra_negativa.add("weak");
		palavra_negativa.add("weird");
		palavra_negativa.add("worst");
		palavra_negativa.add("short");
		palavra_negativa.add("sad");
		palavra_negativa.add("old");
		palavra_negativa.add("low");
		palavra_negativa.add("dull");
		palavra_negativa.add("cheap");		
		palavra_negativa.add("least");
		palavra_negativa.add("sorry");
		palavra_negativa.add("not");
		palavra_negativa.add("supposed");
		palavra_negativa.add("instead");
		palavra_negativa.add("annoying");
		palavra_negativa.add("dumb");
		palavra_negativa.add("avoid");
	}
}
