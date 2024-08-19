package com.dh.clinica.dao.impl;



import com.dh.clinica.dao.IDao;
import com.dh.clinica.db.H2Connection;
import com.dh.clinica.model.Domicilio;
import com.dh.clinica.model.Odontologo;
import com.dh.clinica.model.Paciente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Repository
public class OdontologoDaoH2 implements IDao<Odontologo> {
    private static final Logger logger = LoggerFactory.getLogger(OdontologoDaoH2.class);
    private static final String INSERT ="INSERT INTO ODONTOLOGOS VALUES (DEFAULT,?,?,?)";
    private static final String SELECT_ALL ="SELECT * FROM ODONTOLOGOS";
    public static final String SELECT_ID ="SELECT * FROM ODONTOLOGOS WHERE ID = ?";

    @Override
    public Odontologo guardar(Odontologo odontologo) {
        Connection connection= null;
        Odontologo odontologoARetornar= null;
        try{
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, odontologo.getMatricula());
            preparedStatement.setString(2, odontologo.getNombre());
            preparedStatement.setString(3, odontologo.getApellido());
            preparedStatement.executeUpdate();
            connection.commit();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            Integer id = null;
            if (resultSet.next()){
                id = resultSet.getInt(1);
            }
            odontologoARetornar = new Odontologo(id, odontologo.getMatricula(), odontologo.getNombre(), odontologo.getApellido());
            logger.info("Odontologo guardado: "+odontologoARetornar);

        }catch (Exception e){
            logger.error(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.error(e.getMessage());
            } finally {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException ex) {
                    logger.error(e.getMessage());
                }
            }
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }
        return odontologoARetornar;
    }

    @Override
    public Odontologo buscarPorId(Integer id) {
        Connection connection = null;
        Odontologo odontologoencontrado = null;
        try{
            connection = H2Connection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Integer idDB = resultSet.getInt(1);
                Integer matricula = resultSet.getInt(2);
                String nombre = resultSet.getString(3);
                String apellido = resultSet.getString(4);

                odontologoencontrado = new Odontologo(idDB, matricula,nombre,apellido);
            }
            if(odontologoencontrado != null){
                logger.info("odontologo encontrado "+ odontologoencontrado);
            }else logger.info("odontologo no encontrado");

        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
        return odontologoencontrado;

    }

    @Override
    public List<Odontologo> buscarTodos() {
        Connection connection= null;
        List<Odontologo> odontologos= new ArrayList<>();
        Odontologo odontologo = null;

        try{
            connection = H2Connection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);

            while(resultSet.next()){
                Integer id = resultSet.getInt(1);
                Integer matricula = resultSet.getInt(2);
                String nombre = resultSet.getString(3);
                String apellido= resultSet.getString(4);

                odontologo = new Odontologo(id, matricula,nombre,apellido);
               // logger.info(odontologo);
                odontologos.add(odontologo);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                logger.error(ex.getMessage());
            }
        }

        return odontologos;
    }
}
