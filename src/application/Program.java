package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entities.Product;

public class Program {

	public static void main(String[] args) throws ParseException {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		List<Product> list = new ArrayList<>();
		
		// ler caminho do arquivo
		System.out.println("Enter file path: ");
		String sourceFileStr = "C:\\Users\\LVNFVK631\\Documents\\DEV\\exc_files\\product.txt";
		
		// instanciar para buscar somente o caminho sem o nome do arquivo
		File sourceFile = new File(sourceFileStr);
		String sourceFolderStr = sourceFile.getParent();
		
		//criar uma subpasta retornando se teve sucesso.
		boolean success = new File(sourceFolderStr + "\\out").mkdir();
		
		//criando arquivo summary.csv 
		String targetFileStr = sourceFolderStr + "\\out\\summary.csv";
		
		//instanciando o objeto "br" para ler o arquivo do jeito mais rapido.
		try (BufferedReader br = new BufferedReader(new FileReader(sourceFileStr))) {

			String itemCsv = br.readLine();  //ler linha por linha do arquivo
			while (itemCsv != null) {  //enquanto a linha não for vazia faça:

				//vetor fields criado com virgula e espaço para separar
				String[] fields = itemCsv.split(",");  
				
				//salvando os dados nas posicoes do vetor
				String name = fields[0];
				double price = Double.parseDouble(fields[1]);
				int quantity = Integer.parseInt(fields[2]);

				//adicionando o vetor na lista para usar a funcao e calcular
				list.add(new Product(name, price, quantity));

				itemCsv = br.readLine();
			}

			//instanciando o objeto "bw" para gravar no arquivo do jeito mais rapido.
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(targetFileStr))) {

				//na lista, enquanto tiver os dados(item vendidos), gravar o nome com o total
				for (Product item : list) {
					bw.write(item.getName() + "," + String.format("%.2f", item.total()));
					bw.newLine();  //quebra de linha
				}

				//imprime que o arquivo summary foi criado.
				System.out.println(targetFileStr + "\nSumarry CREATED!");
				
				// se o programa tiver o erro ao gravar, retonar:
			} catch (IOException e) {
				System.out.println("Error writing file: " + e.getMessage());
			}

			// se o programa tiver o erro leitura, retonar:
		} catch (IOException e) {
			System.out.println("Error reading file: " + e.getMessage());
		}

		sc.close();
	}
}