import java.util.*;
import java.sql.*;
public class UpdateMySQL {
    public static void main(String[] args) throws Exception {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        Scanner scnLogin, scnSenha, scnUpdateNome, scnUpdateSenha, scnAlterarNomeSenha, scnNomeSouN, scnSenhaSouN, scnSair, scnEnter;
        scnLogin = scnSenha = scnUpdateNome = scnUpdateSenha = scnAlterarNomeSenha = scnNomeSouN = scnSenhaSouN = scnSair = scnEnter = new Scanner(System.in);
        boolean sair = false, logado = false;
        String strLogin, strSenha, strUpdateLogin, strSqlUpdateLogin, strUpdateSenha, strSqlUpdateSenha, strSqlSelectLoginSenha, idLogin = "", nome = "", senha = "", SouN, strNomeSenha, strSair, strEnter;
        Connection conn = null;
        ResultSet rsLoginSenha;
        Statement stmSelectLoginSenha, stmUpdateLogin, stmUpdateSenha;

        System.out.println("Bem vindo ao sistema de atualização de nome e senha.");
        System.out.println("Para entrar no sistema, é necessário digitar seu login e senha respectiva.");
        System.out.println("Tecle Enter para continuar...");
        strEnter = scnEnter.nextLine();
        System.out.println(strEnter);

        try {
            while (logado == false) {
                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.println("Digite seu login abaixo e tecle Enter: ");
                strLogin = scnLogin.nextLine();

                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.println("Digite sua senha abaixo e tecle Enter: ");
                strSenha = scnSenha.nextLine();

                strSqlSelectLoginSenha = String.format("select * from `mysql_connector`.`tbl_login` where `login` = '%s' and `senha` = '%s';", strLogin, strSenha);
                conn = App.conectar();
                stmSelectLoginSenha = conn.createStatement();
                rsLoginSenha = stmSelectLoginSenha.executeQuery(strSqlSelectLoginSenha);
                while(rsLoginSenha.next()) {
                    idLogin = rsLoginSenha.getString("id");
                    nome = rsLoginSenha.getString("nome");
                    senha = rsLoginSenha.getString("senha");
                } // encerra while
                rsLoginSenha.close();
                System.out.print("\033[H\033[2J");
                System.out.flush();
                if (idLogin != "") {
                    System.out.println("Usuário [" + strLogin + "] logado com sucesso.");
                    System.out.println("Tecle Enter para continuar...");
                    strEnter = scnEnter.nextLine();
                    System.out.println(strEnter);
                    logado = true;
                } else {
                    System.out.println("Login e/ou senha inválidos. Digite abaixo [1] para tentar novamente ou [2] para sair.");
                    strSair = scnSair.nextLine();
                    if (strSair.equals("2")) {
                        logado = true;
                        sair = true;
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        System.out.println("Saída concluída com sucesso!");
                    }
                }
            }
        } catch (Exception e) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.err.println("Ops! Não foi possível logar por um erro no banco de dados.");
            System.err.println("Erro do banco de dados: " + e);
            sair = true;
        } // encerra catch

        try {
            while (sair == false) { // while 1
                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.println("Digite [1] para alterar o nome, [2] para alterar a senha ou [3] para sair, depois tecle Enter.");
                strNomeSenha = scnAlterarNomeSenha.nextLine();

                switch (strNomeSenha) { // switch/case 1
                    case "1":
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        System.out.println("Digite o novo nome abaixo e tecle Enter: ");
                        strUpdateLogin = scnUpdateNome.nextLine();

                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        System.out.printf("Confirma alteração do nome '%s' para '%s'? Digite abaixo [s] para confirmar ou [n] para cancelar, depois tecle Enter\n", nome, strUpdateLogin);
                        SouN = scnNomeSouN.nextLine();

                        if (SouN.equals("s") || SouN.equals("S")) {
                            strSqlUpdateLogin = "update `mysql_connector`.`tbl_login` set `nome` = '" + strUpdateLogin + "' where `id` = " + idLogin + ";";
                            stmUpdateLogin = conn.createStatement();
                            stmUpdateLogin.addBatch(strSqlUpdateLogin);
                            stmUpdateLogin.executeBatch();
                            stmUpdateLogin.close(); // este tem que fechar aqui mesmo
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            System.out.println("Nome alterado com sucesso!");
                        }
                        break;
                    case "2":
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        System.out.println("Digite a nova senha abaixo e tecle Enter: ");
                        strUpdateSenha = scnUpdateSenha.nextLine();

                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        System.out.printf("Confirma alteração da senha '%s' para '%s'? Digite abaixo [s] para confirmar ou [n] para cancelar, depois tecle Enter\n", senha, strUpdateSenha);

                        SouN = scnSenhaSouN.nextLine();

                        if (SouN.equals("s") || SouN.equals("S")) {
                            strSqlUpdateSenha = "update `mysql_connector`.`tbl_login` set `senha` = '" + strUpdateSenha + "' where `id` = " + idLogin + ";";
                            stmUpdateSenha = conn.createStatement();
                            stmUpdateSenha.addBatch(strSqlUpdateSenha);
                            stmUpdateSenha.executeBatch();
                            stmUpdateSenha.close(); // este tem que deixar aqui mesmo
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            System.out.println("Senha alterada com sucesso!");
                        }
                        break;
                    case "3":
                        sair = true;
                        idLogin = "";
                        nome = "";
                        senha = "";
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        System.out.println("Deslogado com sucesso!");
                        break;
                    default:
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        System.err.println("Ops! opção inválida. Retornando...");
                        break;
                } // encerra switch/case 1
            } // encerra while 1
        } catch (Exception e) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.err.println("Ops! ocorreu o erro " + e);
        } // encera catch
        scnEnter.close();
        scnLogin.close();
        scnSenha.close();
        scnAlterarNomeSenha.close();
        scnUpdateNome.close();
        scnNomeSouN.close();
        scnUpdateSenha.close();
        scnSenhaSouN.close();
    } // encerra método main
} // encerra classe UpdateMySQL