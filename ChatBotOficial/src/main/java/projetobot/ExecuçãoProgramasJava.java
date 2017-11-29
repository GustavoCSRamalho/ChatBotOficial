package projetobot;


import java.io.IOException;
import java.util.Scanner;

public class ExecuçãoProgramasJava {
	public static void main(String[] args) throws IOException {
		Runtime r = Runtime.getRuntime();
		String[] cmd = { "/bin/sh", "-c", "ls" };
		Process p = r.exec(cmd);
		Scanner scanner = new Scanner(p.getInputStream()).useDelimiter("$$");
		if (scanner.hasNext()) {
		    System.out.println("stdout");
		    System.out.println(scanner.useDelimiter("$$").next());
		}

		scanner = new Scanner(p.getErrorStream()).useDelimiter("$$");
		if (scanner.hasNext()) {
		    System.out.println("stdout");
		    System.out.println(scanner.useDelimiter("$$").next());
		}
	}
}
