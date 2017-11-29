package projetobot;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import com.pengrad.telegrambot.model.File;

public class Teste {
	public static void main(String[] args) throws IOException {
		FileWriter arq = new FileWriter("/home/gustavo/eclipse-workspace/TesteChat/src/testes/teste.txt");
		arq.write("Ok, uhuu");
		arq.close();
	}
}
