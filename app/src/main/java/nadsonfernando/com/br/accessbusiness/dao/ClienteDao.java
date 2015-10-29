package nadsonfernando.com.br.accessbusiness.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import nadsonfernando.com.br.accessbusiness.model.Cliente;

/**
 * Created by nadsonfernando on 21/10/15.
 */
public class ClienteDao extends BaseDaoImpl<Cliente, Integer> {
    public ClienteDao(ConnectionSource connectionSource) throws SQLException {
        super(Cliente.class);
        setConnectionSource(connectionSource);
        initialize();
    }
}
