package model;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
@Data
public class Usuario implements Serializable {
	private String name;
	private Integer age;
	private String cedula;
	private String correo;
	private Integer cantAnuncios;

	private String password;

	private ArrayList<Puja> listaPujas;

	//Este atributo permite conocer si el cliente actual esta activo en la app
	private boolean activo;
	private static Integer idAux = 0;
	//Este atributo permite saber el estado del usuario, Eliminado, Nuevo, Actualizado
	private Estado estado;
	private Integer id;
	public Usuario(String name, Integer age, String cedula, String correo, Integer cantAnuncios, Puja puja) {
		this.name = name;
	    this.age = age;
	    this.cedula = cedula;
	    this.correo = correo;
	    this.cantAnuncios = cantAnuncios;
		id = ++idAux;
		estado = Estado.NUEVO;
	}

	//NO ELIMINAR ESTOS GETTERS, SI NO NO COMPILA EL CODIGO
	public Integer getAge(){
		return age;
	}

	public String  getName(){
		return name;
	}

	public String getCedula(){
		return cedula;
	}

	public void setEstado(Estado nuevo) {
		estado = nuevo;
	}

	public boolean compararId(Integer id) {return this.id.compareTo(id) == 0;}

	public void setName(String name){
		this.name =name;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public Integer getCantAnuncios() {
		return cantAnuncios;
	}

	public void setCantAnuncios(Integer cantAnuncios) {
		this.cantAnuncios = cantAnuncios;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ArrayList<Puja> getListaPujas() {
		return listaPujas;
	}

	public void setListaPujas(ArrayList<Puja> listaPujas) {
		this.listaPujas = listaPujas;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public static Integer getIdAux() {
		return idAux;
	}

	public static void setIdAux(Integer idAux) {
		Usuario.idAux = idAux;
	}

	public Estado getEstado() {
		return estado;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

    public void actualizarAtributos(Usuario nuevoUsuario) {
		this.name = nuevoUsuario.getName();
		this.age = nuevoUsuario.getAge();
		this.cedula=nuevoUsuario.getCedula();
		this.correo = nuevoUsuario.getCorreo();
		this.password = nuevoUsuario.getPassword();
    }
}
