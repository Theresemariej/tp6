package ticketmachine;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@BeforeEach
	public void setUp() {
		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
	// On vérifie que le prix affiché correspond au paramètre passé lors de l'initialisation
	// S1 : le prix affiché correspond à l’initialisation.
	void priceIsCorrectlyInitialized() {
		// Paramètres : valeur attendue, valeur effective, message si erreur
		assertEquals(PRICE, machine.getPrice(), "Initialisation incorrecte du prix");
	}

	@Test
	// S2 : la balance change quand on insère de l’argent
	void insertMoneyChangesBalance() {
		machine.insertMoney(10);
		machine.insertMoney(20);
                // Les montants ont été correctement additionnés  
		assertEquals(10 + 20, machine.getBalance(), "La balance n'est pas correctement mise à jour");              
	}

	@Test
	// S3 : on n’imprime pas le ticket si le montant inséré est insuffisant
	void pasDargentPasDeTicket() {
		machine.insertMoney(20);
		assertEquals(false,machine.printTicket(),"faut pas imprimer t'as pas mis assez de sous");
	}
	@Test
	// S4 : on imprime le ticket si le montant inséré est suffisant
	void deLargentUnTicket() {
		machine.insertMoney(60);
		assertEquals(true,machine.printTicket(),"le montant est suffisant");
		//ici on pourrai utiliser un assertTrue()
	}

	@Test
	// S5 : Quand on imprime un ticket la balance est décrémentée du prix du ticket
	void ticketModifieBalance() {
		machine.insertMoney(60);
		machine.printTicket();
		assertEquals(60-50,machine.getBalance(),"la balance devrai changer puisqu'un ticket a été imprimé");

	}
	@Test
	// S6 : le montant collecté est mis à jour quand on imprime un ticket (pas avant)
	void montantCollectéMAJ(){
		machine.insertMoney(60);
		assertEquals(0,machine.getTotal(),"le montant collecté doit pas être mis a jour");
		machine.printTicket();
		assertEquals(50,machine.getTotal(),"le montant collecté doit être mis a jour");
	}
	@Test
	//S7 : refund() rend correctement la monnaie
	void rendLaMonnaie(){
		machine.insertMoney(60);
		assertEquals(60-machine.getPrice(),machine.refund(),"le montant à rendre doit être mis a jour");
	}

	@Test
	//S8 : refund()remet la balance à zéro
	void remetAZero(){
		machine.refund();
		assertEquals(0,machine.getBalance(),"le montant doit être remis a zero");
	}

	@Test
	//S9 : on ne peut pas insérerun montant négatif
	void insertValeurNegative(){
		try{
			machine.insertMoney(-20);
			fail("doit lever une exeption");
		}
		catch(IllegalArgumentException e){}
	}

	@Test
		// S10 : on ne peut pas créer de machine qui délivre des tickets dont le prix est négatif
	void cantCreateTicketMachineWithNegativePrice(){
		assertThrows(IllegalArgumentException.class, () -> {new TicketMachine(-1);}, "");
	}

}
