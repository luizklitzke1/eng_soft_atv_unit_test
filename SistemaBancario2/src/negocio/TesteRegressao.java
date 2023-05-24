package negocio;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ GerenciadoraContasTeste.class, GerenciadoraClientesTeste.class })
public class TesteRegressao {
	//Classe vazia que atua apenas como agrupadora dos demais testes do sistema
}
