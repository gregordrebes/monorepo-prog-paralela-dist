import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import javax.swing.JOptionPane;

public class Game {

    private static String[] parametrosPermitidos = {"server", "client"};

    public static void main(String[] args){
        JOptionPane.showMessageDialog( null, jcb, "Selecione o que deseja executar", JOptionPane.QUESTION_MESSAGE);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        // apenas um parametro por execução
        if (args.length == 0) {
            System.out.println("Erro! Informe um parâmetro! Apenas \"server\" OU \"client\"!");
            System.exit(0);
        } else if (args.length > 1){
            System.out.println("Erro! Apenas um parâmetro permitido!");
            System.exit(0);
        }

        String arg = args[0];

        if (!Game.isParametroPermitido(arg)){
            System.out.println("Erro! Parâmetro desconhecido! Apenas \"server\" OU \"client\"!");
            System.exit(0);
        }

        Game.start(arg);
    }

    
    public static void start(String argument) {
        // Ex.: server => Server
        arg = Helpers.firstLetterUpper(arg);

        // Bloco try/catch requerido
        try {
            // Carregando a classe dinamicamente
            Class<?> classe = Class.forName(arg); 
            // Obter o método dessa classe (padrao start)
            Method method = classe.getDeclaredMethod("start");
            // Cria instancia dessa
            Object object = classe.getDeclaredConstructor().newInstance();
            // Chama o metodo no contexto do objeto criado
            method.invoke(object);
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
                | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("Fim do processo");
    }

    public static boolean isParametroPermitido(String value){
        return Arrays.asList(Game.parametrosPermitidos).contains(value);
    }
    
}