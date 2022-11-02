package utilities;

public class test {


    //pruebas de codigo, por favor no borrarlas
        /*
    	IAnuncio anuncio = new IAnuncio();
    	Anuncio ad = new Anuncio(25);
    	anuncio.crear(ad);
    	anuncio.crear(new Anuncio(60));
        anuncio.crear(new Anuncio(34) );
        anuncio.crear(new Anuncio(27) );
        anuncio.crear(new Anuncio(11) );
        anuncio.crear(new Anuncio(45) );
    	///System.out.println(anuncio.listar().get(0));
    	//System.out.println(anuncio.buscarId(1));
    	//anuncio.actualizar(1, new Anuncio());
    	//System.out.println(anuncio.buscarId(100000));
        /*Comparar<Anuncio> compararAnuncio = (obj1, obj2)->{return obj1.getId() > obj2.getId();};
        for(int i=0; i<anuncio.listar().size(); i++) {
            System.out.println(anuncio.listar(compararAnuncio, TipoOrden.ASCENDENTE).get(i).getId());
        }*/

    //los que tengan el id cuyos digitos sumen mas van primero
        /*
        Comparar<Integer> comparar = (a,  b)->{return a/10+a%10<b/10+b%10;};
        for(int i=0; i<anuncio.listar().size(); i++) {
            System.out.println(anuncio.listar("id", TipoOrden.ASCENDENTE, comparar).get(i).getId());
        }*/

    //System.out.println(anuncio.listar((obj1, obj2)->{return obj1.getId() > obj2.getId();}, TipoOrden.ASCENDENTE).get(1).getId());
    //System.out.println(anuncio.listar((obj1, obj2)->{return obj1.getId() > obj2.getId();}, TipoOrden.ASCENDENTE).get(2).getId());
    //System.out.println(anuncio.listar((obj1, obj2)->{return obj1.getId() > obj2.getId();}, TipoOrden.ASCENDENTE).get(3).getId());
    //System.out.println(e->{return ad.getNombreAnunciante()}.g());
    //TipoOrden ord = TipoOrden.ASCENDENTE;
    //System.out.println(ord == TipoOrden.DESCENDENTE);

    //File archivoPrueba  =new File(ModelFactoryController.getRutaLogs()+"\\log1");
    //System.out.println(archivoPrueba.getAbsolutePath());
    //ArchivoUtil.guardarRegistroLog("mensaje de prueba", 1, "prueba", ModelFactoryController.getRutaLogs()+"\\log1");
//        IAnuncio ImplAnuncio = new IAnuncio();

    //      ImplAnuncio.crear(new Anuncio(2));
    //ImplAnuncio.buscarId(23);
        /*Usuario usr = new Usuario();
        usr.getListaPujas().add(new Puja());
        usr.getListaPujas().add(new Puja());
        usr.getListaPujas().add(new Puja());*/
    //usr.getActivo();
    //Usuario user = Persistencia.deserializarUsuario(usr, 1);
    //System.out.println(user.getCedula());
    //ArchivoUtil.guardarArchivo("C:\\td\\folder2\\prueba.txt", "prueba", true);
    //Persistencia.serializarPuja(new Puja(LocalDate.now(), new Usuario(), 122));
    //Persistencia.serializarUsuario(usr);
    //Persistencia.deserializarObj();

    //ArchivoUtil.verificarRuta("C:\\td\\persistencia\\logs\\");
        /*Usuario usr = new Usuario("diana", 21, "1003929434", "dianaM@gmail.com", "contraseniaDePrueba", "Cr1 23 Cll 19 Brr guayaquil");
        usr.getListaPujas().add(new Puja(1, 30));
        usr.getListaPujas().add(new Puja(1, 344));
        usr.getListaPujas().add(new Puja(1, 500));

        Anuncio anuncio = new Anuncio("john", 159, Estado.NUEVO, false);
        anuncio.getListaPujas().add(new Puja(1, 20));
        anuncio.getListaPujas().add(new Puja(1, 25));
        anuncio.getListaPujas().add(new Puja(1, 31));

        Usuario usr2 = new Usuario("jean", 28, "199299299", "jean122@gmail.com", "abcgdgd", "Cr1 144 Cll 19 Brr Burnos aires");
        usr2.getListaPujas().add(new Puja(2, 1));
        usr2.getListaPujas().add(new Puja(2, 11));
        usr2.getListaPujas().add(new Puja(2, 111));


        Anuncio anuncio2 = new Anuncio("carlos", 400, Estado.NUEVO, true);
        anuncio2.getListaPujas().add(new Puja(1, 64));
        anuncio2.getListaPujas().add(new Puja(1, 128));
        anuncio2.getListaPujas().add(new Puja(1, 256));


        Usuario usr3 = new Usuario("juan", 25, "1003334333", "juanOrtiz@gmail.com", "Password22", "Cr1 23 Cll 19 ");
        usr3.getListaPujas().add(new Puja(1, 3));
        usr3.getListaPujas().add(new Puja(1, 9));
        usr3.getListaPujas().add(new Puja(1, 27));

        Usuario usr4 = new Usuario("luisa", 9, "10029292992", "luisaFer@gmail.com", "Password1", "Cr1 0 Cll 19");
        usr4.getListaPujas().add(new Puja(1, 4));
        usr4.getListaPujas().add(new Puja(1, 16));
        usr4.getListaPujas().add(new Puja(1, 64));

        Persistencia.serializarUsuario(usr);
        Persistencia.serializarAnuncio(anuncio);
        Persistencia.serializarUsuario(usr2);
        Persistencia.serializarUsuario(usr3);
        Persistencia.serializarUsuario(usr4);
        Persistencia.serializarAnuncio(anuncio2);
         */

/*        Usuario user = new Usuario();
        Persistencia.deserializarUsuario(user, "2");
        System.out.println(user.getListaPujas().get(2).getValorOfrecido());


    Transaccion transaccion = new Transaccion(24);
    Transaccion transaccion2 = new Transaccion(200);
    Transaccion transaccion3 = new Transaccion(500);
        Persistencia.serializarTransaccion(transaccion);
        Persistencia.serializarTransaccion(transaccion2);
        Persistencia.serializarTransaccion(transaccion3);
        ArchivoUtil.copiarArchivo(ModelFactoryController.getRutaObjetos("Transaccion.txt"),ModelFactoryController.getRutaRespaldo("Transaccion"));
*/
}
