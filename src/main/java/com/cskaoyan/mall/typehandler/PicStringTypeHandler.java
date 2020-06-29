package com.cskaoyan.mall.typehandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.*;

/**
 *  数据库 String----> String[]
 */
@MappedTypes(String[].class)
public class PicStringTypeHandler extends BaseTypeHandler<String[]> {
    ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, String[] strings, JdbcType jdbcType) throws SQLException {
        String value = objectMapper.writeValueAsString(strings);
        preparedStatement.setString(i, value);
    }

    @Override
    public String[] getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String stringValue = resultSet.getString(s);
        return string2Array(stringValue);
    }

    @Override
    public String[] getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String stringValue = resultSet.getString(i);
        return string2Array(stringValue);
    }

    @Override
    public String[] getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String stringValue = callableStatement.getString(i);
        return string2Array(stringValue);
    }

    private String[] string2Array(String stringValue){
        String[] strings = null;
        if(stringValue == null){
            return strings;
        }
        String replace = stringValue.replace("[","").replace("]","");
        String replace2 = replace.replaceAll("\\\"", "");
        String replace3 = replace2.replace(" ", "");
        return replace3.split(",");
        /*try {
            strings = objectMapper.readValue(stringValue, String[].class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return strings;*/
        //写下面会报异常，com.fasterxml.jackson.core.JsonParseException异常
    }
}
