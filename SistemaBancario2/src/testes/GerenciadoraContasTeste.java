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

//Imports das classes de negocio
import negocio.ContaCorrente;
import negocio.GerenciadoraContas;

@DisplayName("Testes da classe GerenciadoraContasTeste")
public class GerenciadoraContasTeste 
{
	private List<ContaCorrente> listaContas       ;
    private GerenciadoraContas  gerenciadoraContas;

    @BeforeEach
    void setUp() 
    {
        this.listaContas = new ArrayList<ContaCorrente>();
        
        //Lista de contas para ser reutilizada em todos os testes
        this.listaContas.add(new ContaCorrente(1, 10.22 , true ));
        this.listaContas.add(new ContaCorrente(2, 33.99 , false));
        this.listaContas.add(new ContaCorrente(3, 1021.1, true ));
        this.listaContas.add(new ContaCorrente(4, 0.01  , false));
        this.listaContas.add(new ContaCorrente(5, 4.20  , true ));
        
        this.gerenciadoraContas = new GerenciadoraContas(this.listaContas);
    }

    @AfterEach 
    void tearDown() 
    {
        this.listaContas        = null;
        this.gerenciadoraContas = null;
    }
    
    @Test
    @DisplayName("GerenciadoraContas dumnmy")
    public void dummy() 
    {
        Assertions.assertEquals(true, true);
    }
}
