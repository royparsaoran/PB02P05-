/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roy.dao;

import com.roy.entity.Category;
import com.roy.entity.Item;
import com.roy.util.ConnUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author master
 */
public class ItemDaoImpl implements DaoService<Item> {

    @Override
    public int addData(Item object) throws SQLException {
        int result = 0;
        Connection connection = ConnUtil.creatConnection();
        String query
                = "INSERT INTO menu(id,name,price,description,recomended,category_id) VALUES(?,?,?,?,?,?)";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, object.getId());
        ps.setString(2, object.getName());
        ps.setDouble(3, object.getPrice());
        ps.setString(4, object.getDescription());

        int value = 0;
        if (object.isRecomended() == true) {
            value = 1;
        }
        ps.setInt(5, value);
        ps.setInt(6, object.getCategor().getId());

        if (ps.executeUpdate() != 0) {
            connection.commit();
            result = 1;
        }
        return result;
    }

    @Override
    public List<Item> getAllData() throws SQLException {
        List<Item> items = new ArrayList<>();
        String query
                = "SELECT * FROM menu m JOIN category c ON m.category_id = c.id";
        Connection conn = com.roy.util.ConnUtil.creatConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Item item = new Item();
            item.setId(rs.getInt("m.id"));
            item.setName(rs.getString("m.name"));
            item.setPrice(rs.getDouble("m.price"));
            item.setDescription(rs.getString("m.description"));
            item.setRecomended(rs.getBoolean("m.recomended"));

            Category category = new Category();
            category.setId(rs.getInt("c.id"));
            category.setName(rs.getString("c.name"));

            item.setCategor(category);
            items.add(item);
        }
        rs.close();
        ps.close();
        return items;
    }

    @Override
    public int updateData(Item object) throws SQLException {
        int result = 0;
        Connection connection = ConnUtil.creatConnection();
        String query
                = "UPDATE menu SET name = ?, price = ?, description = ?, recomended = ?, category_id = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, object.getName());
        ps.setString(2, String.valueOf(object.getPrice()));
        ps.setString(3, object.getDescription());

        String recommended = "0";
        if (object.isRecomended()) {
            recommended = "1";
        }

        ps.setString(4, recommended);
        ps.setString(5, String.valueOf(object.getCategor().getId()));
        ps.setString(6, String.valueOf(object.getId()));
        if (ps.executeUpdate() != 0) {
            connection.commit();
            result = 1;
        }
        return result;
    }

    @Override
    public int deleteData(Item object) throws SQLException {
        int result = 0;
        Connection connection = ConnUtil.creatConnection();
        String query = "DELETE FROM menu WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, String.valueOf(object.getId()));

        if (ps.executeUpdate() != 0) {
            connection.commit();
            result = 1;
        }
        return result;
    }

}
