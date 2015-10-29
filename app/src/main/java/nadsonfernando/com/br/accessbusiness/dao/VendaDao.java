package nadsonfernando.com.br.accessbusiness.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import nadsonfernando.com.br.accessbusiness.model.Cliente;
import nadsonfernando.com.br.accessbusiness.model.Venda;

/**
 * Created by nadsonfernando on 21/10/15.
 */
public class VendaDao extends BaseDaoImpl<Venda, Integer> {
    public VendaDao(ConnectionSource connectionSource) throws SQLException {
        super(Venda.class);
        setConnectionSource(connectionSource);
        initialize();
    }
}
