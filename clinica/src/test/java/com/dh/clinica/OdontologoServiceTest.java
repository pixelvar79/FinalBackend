package com.dh.clinica;


import com.dh.clinica.dao.impl.OdontologoDaoH2;
import com.dh.clinica.db.H2Connection;
import com.dh.clinica.model.Odontologo;
import com.dh.clinica.service.OdontologoService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertTrue;


class OdontologoServiceTest {
    static final Logger logger = LoggerFactory.getLogger(OdontologoServiceTest.class);
    OdontologoService odontologoService = new OdontologoService(new OdontologoDaoH2());
    @BeforeAll
    static void tablas(){
        H2Connection.crearTablas();
    }

    @Test
    @DisplayName("Testear que se listen todos los odontologos")
    void caso1(){
        //dado
        Odontologo odontologo = new Odontologo(856,"Ana","Torres");
        Odontologo odontologoDesdeDB = odontologoService.guardarOdontologo(odontologo);
        List<Odontologo> odontologos =new ArrayList<>();

        //cuando
        odontologos = odontologoService.buscarTodos();

        //entonces
        assertTrue(odontologos.size()!=0);

    }


}