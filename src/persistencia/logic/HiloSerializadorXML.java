package persistencia.logic;

import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;
import java.beans.XMLEncoder;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

import model.Anuncio;
import model.EmpresaSubasta;
import test.AnuncioTest;
import utilities.Utils;

public class HiloSerializadorXML extends Thread {

	EmpresaSubasta empresa;
	public HiloSerializadorXML(EmpresaSubasta empresaSubasta) {
		this.empresa = empresaSubasta;
	}

	@Override
	public void run() {
		
			try {
				ArchivoUtil.salvarRecursoSerializadoXML(Utils.RUTA_EMPRESA_XML, this.empresa);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
	}
	
}
