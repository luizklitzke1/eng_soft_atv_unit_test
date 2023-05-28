package testes;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.ArrayList;
import java.util.List;

//Libs para os testes
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

//Imports das classes de negocio
import negocio.Cliente;
import negocio.GerenciadoraClientes;
import negocio.IdadeNaoPermitidaException;

@DisplayName("Testes da classe GerenciadoraClientesTeste")
public class GerenciadoraClientesTeste 
{
	private List<Cliente>        listaClientes       ;
    private GerenciadoraClientes gerenciadoraClientes;

    @BeforeEach
    public void setUp() 
    {
        this.listaClientes = new ArrayList<Cliente>();
        
        //Lista de clientes para ser reutilizada em todos os testes
        this.listaClientes.add(new Cliente(1, "Cliente 1", 18, "email1@dominio1.com", 1, true ));
        this.listaClientes.add(new Cliente(2, "Cliente 2", 12, "email2@dominio2.com", 2, false));
        this.listaClientes.add(new Cliente(3, "Cliente 3", 20, "email3@dominio3.com", 1, true ));
        this.listaClientes.add(new Cliente(4, "Cliente 4", 21, "email4@dominio4.com", 2, false));
        this.listaClientes.add(new Cliente(5, "Cliente 5", 13, "email5@dominio5.com", 1, true ));
        this.listaClientes.add(new Cliente(6, "Cliente 6", 79, "email6@dominio6.com", 2, false));
        
        this.gerenciadoraClientes = new GerenciadoraClientes(this.listaClientes);
    }

    @AfterEach 
    public void tearDown() 
    {
        this.listaClientes        = null;
        this.gerenciadoraClientes = null;
    }
    
    @Test
    @DisplayName("GerenciadoraClientes getClientesDoBanco")
    public void getClientesDoBanco_Test() 
    {
        Assertions.assertEquals(this.gerenciadoraClientes.getClientesDoBanco(), this.listaClientes);
    }
    
    @DisplayName("GerenciadoraClientes pesquisaCliente Existentes")
    @ParameterizedTest(name = "id: {0}")
    @ValueSource      (ints = { 1, 2, 3, 4, 5, 6 })
    public void pesquisaClientesExistentes_Test(int id) 
    {
    	Cliente cliente = this.gerenciadoraClientes.pesquisaCliente(id);
    	Assertions.assertNotNull(cliente);
    }
    
    @DisplayName("GerenciadoraClientes pesquisaCliente Inexistentes")
    @ParameterizedTest(name = "id: {0}")
    @ValueSource      (ints = { 7, 22, 9 })
    public void pesquisaClientesInexistentes_Test(int id) 
    {
    	Cliente cliente = this.gerenciadoraClientes.pesquisaCliente(id);
    	Assertions.assertNull(cliente);
    }
    
    @Test
    @DisplayName("GerenciadoraClientes adicionaCliente")
    public void adicionaCliente_Test() 
    {
    	Cliente cliente7 = new Cliente(7, "Cliente 7", 18, "email7@dominio7.com", 1, true);
    	this.gerenciadoraClientes.adicionaCliente(cliente7);
    	
    	Assertions.assertEquals(7, this.gerenciadoraClientes.getClientesDoBanco().size());
    	Assertions.assertEquals(cliente7, this.listaClientes.get(6));
    }
    
    @DisplayName("GerenciadoraClientes removeCliente Existente")
    @ParameterizedTest(name = "id: {0}")
    @ValueSource      (ints = { 1, 2, 3, 4, 5, 6 })
    public void removeClienteExistente_Test(int id) 
    {
    	Assertions.assertTrue(this.gerenciadoraClientes.removeCliente(id));
    	Assertions.assertEquals(5, this.gerenciadoraClientes.getClientesDoBanco().size());
    }
    
    @DisplayName("GerenciadoraClientes removeCliente Inexistente")
    @ParameterizedTest(name = "id: {0}")
    @ValueSource      (ints = { 99, 0, 23 })
    public void removeClienteInexistente_Test(int id) 
    {
    	Assertions.assertFalse(this.gerenciadoraClientes.removeCliente(id));
    	Assertions.assertEquals(6, this.gerenciadoraClientes.getClientesDoBanco().size());
    }
    
    @DisplayName("GerenciadoraClientes clienteAtivo Ativo")
    @ParameterizedTest(name = "id: {0}")
    @ValueSource      (ints = { 1, 3, 5 })
    public void clienteAtivoAtivo_Test(int id) 
    {
    	Assertions.assertTrue(this.gerenciadoraClientes.clienteAtivo(id));
    }
    
    @DisplayName("GerenciadoraClientes clienteAtivo Inativo")
    @ParameterizedTest(name = "id: {0}")
    @ValueSource      (ints = { 2, 4, 6 })
    public void clienteAtivoInativo_Test(int id) 
    {
    	Assertions.assertFalse(this.gerenciadoraClientes.clienteAtivo(id));
    }
    
    @DisplayName("GerenciadoraClientes clienteAtivo Inexistente")
    @ParameterizedTest(name = "id: {0}")
    @ValueSource      (ints = { 99, 82, 22, 0 })
    public void clienteAtivoInexistente_Test(int id) 
    {
    	Assertions.assertFalse(this.gerenciadoraClientes.clienteAtivo(id));
    }
    
    @Test
    @DisplayName("GerenciadoraClientes limpa")
    public void limpa_Test() 
    {
    	this.gerenciadoraClientes.limpa();
        Assertions.assertTrue(this.gerenciadoraClientes.getClientesDoBanco().isEmpty());
    }
    
    @DisplayName      ("GerenciadoraClientes validaIdade Válidos")
    @ParameterizedTest(name = "idade: {0}")
    @ValueSource      (ints = { 22, 45, 18, 18, 65, 50})
    public void validaIdadeValidos_Test(int idade) throws IdadeNaoPermitidaException 
    {
    	Assertions.assertDoesNotThrow(() -> Assertions.assertTrue(this.gerenciadoraClientes.validaIdade(idade)));
    }
    
    @DisplayName      ("GerenciadoraClientes validaIdade Inválidos")
    @ParameterizedTest(name = "idade: {0}")
    @ValueSource      (ints = { 2, 5, 98, 12, 17, 67})
    public void validaIdadeInvalidos_Test(int idade)
    {
    	Exception exception = Assertions.assertThrows(IdadeNaoPermitidaException.class, () -> {
            this.gerenciadoraClientes.validaIdade(idade);
        });

        Assertions.assertEquals(IdadeNaoPermitidaException.MSG_IDADE_INVALIDA, exception.getMessage());
    }
}
