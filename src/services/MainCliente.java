package services;


public class MainCliente {

	public static void main(String[] args) {
		AppCliente appCliente = new AppCliente("localhost",8081);
		appCliente.iniciarCliente();
	}

}
