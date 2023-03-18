package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entities.Product;

public class Program {

	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		List<Product> list = new ArrayList<Product>();
		
		System.out.println("Enter file path:");
		String strPath = sc.nextLine();
		
		File path = new File(strPath);
		
		String filePathOnly = path.getParent();//pegando somente o caminho
		
		boolean success = new File(filePathOnly + "\\out").mkdir();
		System.out.println("directory created successfully: " + success);
		
		String generateSummaryFile = filePathOnly + "\\out\\summary.csv";
		
		try (BufferedReader br = new BufferedReader(new FileReader(strPath))){
			
			String line = br.readLine();
			while(line != null) {
				String[] fields = line.split(",");
				list.add(new Product(fields[0], Double.parseDouble(fields[1]), Integer.parseInt(fields[2])));
				line = br.readLine();
			}
			
			try(BufferedWriter bw = new BufferedWriter(new FileWriter(generateSummaryFile))){
				
				for(Product item : list) {
					
					bw.write(item.getName() + ", " + String.format("%.2f", item.total()));
					bw.newLine();
				}
				System.out.println(generateSummaryFile + " Created!");
			}
			catch(IOException e) {
				System.out.println("Error writing the file " + e.getMessage());
			}
			
		}
		catch(IOException e) {
			System.out.println("Error reading file " + e.getMessage());
		}
		finally {
			if (sc != null) {
				sc.close();
			}
		}
	}

}
