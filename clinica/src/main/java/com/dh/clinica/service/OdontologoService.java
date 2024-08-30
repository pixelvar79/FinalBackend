package com.dh.clinica.service;


import com.dh.clinica.dao.IDao;
import com.dh.clinica.entity.Odontologo;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OdontologoService {
    private IDao<Odontologo> odontologoIdao;

    public OdontologoService(IDao<Odontologo> odontologoIdao) {

        this.odontologoIdao = odontologoIdao;
    }

    public Odontologo guardarOdontologo (Odontologo odontologo){

        return odontologoIdao.guardar(odontologo);
    }

    public List<Odontologo> buscarTodos(){

        return odontologoIdao.buscarTodos();
    }



    public Odontologo buscarPorId(Integer id){
        return odontologoIdao.buscarPorId(id);
    }
    public void modificarOdontologo(Odontologo odontologo){
        odontologoIdao.modificar(odontologo);
    }
    public void eliminarOdontologo(Integer id){
        odontologoIdao.eliminar(id);
    }

}
