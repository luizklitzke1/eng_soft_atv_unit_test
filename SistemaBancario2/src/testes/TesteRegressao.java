package testes;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ GerenciadoraClientesTeste.class, GerenciadoraContasTeste.class })
public class TesteRegressao {
	//Classe vazia que atua apenas como agrupadora dos demais testes do sistema
}
