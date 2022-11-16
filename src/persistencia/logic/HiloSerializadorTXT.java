package persistencia.logic;

public class HiloSerializadorTXT extends  Thread {
	
	public HiloSerializadorTXT() {
		
	}
	
	@Override
	public void run() {
		Persistencia.serializarEmpresaTXT();
	}
	
}
