/*
* Caro dev:
* Quando eu fiz esse codigo, so Deus e eu
* sabiamos como funcionava.
*
* Agora apenas Deus sabe, favor adicionar
* no contador a quantidade de horas que
* tu vai gastou como um aviso para
* outros devs.
*
*
* Total de horas gastas: 7
* */


package psswd;

import java.util.ArrayList;
import java.io.*;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class genRandomPassPhraseOPTMIZED {
    public static final Logger log = Logger.getLogger(genRandomPassPhraseOPTMIZED.class.getName());

    public static StringBuilder passPhrase(int c){
        Random r = new Random();

        int randomInt = 0;
        char randomChar = 'a';

        String charac = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890!@#$%*()-=_+'{[]";


        ArrayList<String> psswd = new ArrayList<>();
        int cont = 0;

        for(int i = 8; i < 21; i++){
                if(c == i){
                    for(int j = 0; j < i; j++){
                        randomInt = r.nextInt(charac.length());
                        randomChar = charac.charAt(randomInt);

//                        System.out.print(randomChar);

                        String s = new StringBuilder().append(randomChar).toString();

                        psswd.add(s);
                        cont++;
		    }
    		System.out.println();
            }
        }

        StringBuilder passwords = new StringBuilder();

        for(int i = 0; i < cont; i++){
            passwords.append(psswd.get(i));
        }

        passwords.toString();

        return passwords;
    }

    public static String exportToString(StringBuilder passwords){
        String truePsswd = passwords.toString();

        return truePsswd;
    }

    public static void exportToTxt(String truePsswd, String file) throws FileNotFoundException {
        String s = new StringBuilder().append(truePsswd).append(" ").toString();

        try(FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.print(s);

        } catch (IOException e) {
            log.severe(e.getMessage());
        }
    }

    public static String readTxtContent(String file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder sb = new StringBuilder();
        String conteudo = new String();

        while(br.ready()) {

            sb.append(br.readLine());

        }

        conteudo = sb.toString();
        br.close();

        return conteudo;
    }

    public static boolean verifyIfExists(String conteudo, StringBuilder truePsswd){
       String[] splittedPsswds = conteudo.split(" ");

       boolean tf = false;

       for(int i = 0; i < verifyLenght(splittedPsswds); i++){
           if(splittedPsswds[i].equals(truePsswd)){
               tf = true;
           }else{
               tf = false;

           }
       }

        return tf;
    }

    public static int verifyLenght(String[] splittedPsswds){
        int lth = splittedPsswds.length;

        return lth;
    }

    public static StringBuilder returnsPssphrs (int c){
        StringBuilder pssphrs  = passPhrase(c);

        return pssphrs;
    }

    public static String verifyUserData(){
        Scanner sx1 = new Scanner(System.in);

        System.out.println("Por favor, informe seu email: ");

            String mail = sx1.next();
            sx1.nextLine();

        sx1.close();
        return mail;
    }

    public static void genUserPGPandProtocols(){
        try {
            String[] genPGPkey = {"/usr/bin/gpg", "--full-generate-key"};
            Process proc3 = new ProcessBuilder(genPGPkey).start();

            TimeUnit.MINUTES.sleep(5);

        }catch(IOException ioe){

        }catch(InterruptedException interr){
            log.severe(interr.getMessage());
        }

        try{
            String[] genRevocationCrtFile = {"/usr/bin/gpg", "--output", "/revocation.crt", "--gen-revoke", verifyUserData()};
            Process proc4 = new ProcessBuilder(genRevocationCrtFile).start();

            TimeUnit.MINUTES.sleep(5);

        }catch(IOException | InterruptedException ioe1){
            log.severe(ioe1.getMessage());
        }

        try{
            String[] changeRevocationCrtFilesPermissions = {"/usr/bin/chmod", "600", "/revocation.crt"};
            Process proc5 = new ProcessBuilder(changeRevocationCrtFilesPermissions).start();

        }catch(IOException ioe2){
            log.severe(ioe2.getMessage());
        }

    }

    public static void encript(String file, String mail){
        try {
            String[] argsForGPG = {"/usr/bin/gpg", "--encypt", "--sign", "--armor", "-r", mail, file};
            Process proc = new ProcessBuilder(argsForGPG).start();

            TimeUnit.MILLISECONDS.sleep(5);

        }catch(IOException | InterruptedException ioe){
            log.severe(ioe.getMessage());

        }
    }

    public static void main(String[] args) throws IOException {
        Scanner sx = new Scanner(System.in);

        System.out.println("Voce ja possui uma PGP key, um USER-ID, um arquivo revocation.crt e permissoes apenas para voce desse arquivo revocation.crt? [y/N]");

           String yn = sx.next();
        String mail1 = " ";
        if(yn.equals("N")){
            genUserPGPandProtocols();
        }

        System.out.println("Selecione um arquivo para guardar e criptografar suas senhas: [digite o caminho completo] ");

        String file = sx.next();

            sx.nextLine();

        System.out.println("De quantos caracteres sera sua senha: ");

            int c = sx.nextInt();

        sx.close();

        System.out.print("Sua senha eh: ");

        StringBuilder pssphrs = returnsPssphrs(c);


        while(verifyIfExists(readTxtContent(file), pssphrs) != false){
            pssphrs= passPhrase(c);
        }

        System.out.println(pssphrs);

        exportToTxt(exportToString(pssphrs), file);
//        encript(file, mail1);

        System.out.println("Aviso: o arquivo com suas senhas foi criptografado e o original excluido, para que este programa funcione corretamente uma segunda vez voce tera que descriptografar o arquivo");
    }
}