package com.roy.dao;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author royparsaoran 1772044
 */
public interface DaoService<T> {

    int addData(T object) throws SQLException;

    List<T> getAllData() throws SQLException;

    int updateData(T object) throws SQLException;

    int deleteData(T object) throws SQLException;
}
