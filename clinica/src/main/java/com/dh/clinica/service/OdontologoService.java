package com.dh.clinica.service;


import com.dh.clinica.dao.IDao;
import com.dh.clinica.model.Odontologo;
import com.dh.clinica.model.Paciente;
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

}
