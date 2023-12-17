package Java;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;


public class Driver {

    public static void main(String[] args) throws IOException{

        if (args.length != 1) System.exit(1);

        byte[] bytes = Files.readAllBytes(Paths.get(args[0]));
        String source = new String(bytes, Charset.defaultCharset());

        Tokenizer tokenizer = new Tokenizer(source);
        List<Token> tokens = tokenizer.scanTokens();

        Interpreter intertpreter = new Interpreter(tokens);
        Map<String, Integer> vars = intertpreter.interpret();

        for (String name: vars.keySet()) {
            String value = vars.get(name).toString();
            System.out.println(name + " = " + value);
        }
    }
}