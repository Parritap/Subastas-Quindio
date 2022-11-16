package persistencia.logic;

public class HiloSerializadorBinario extends Thread {

	public HiloSerializadorBinario() {

	}

	@Override
	public void run() {
		Persistencia.serializarEmpresaBinario();
	}
	
}
