package util;

import bootstrap.JDBCConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SQLOperations {

    public static int executeCreateAndgetId(PreparedStatement statement) throws SQLException {
        int affectedRows = statement.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Creating failed, no rows affected.");
        }

        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
            else {
                throw new SQLException("Creating failed, no ID obtained.");
            }
        }
    }

   public static class SQLTemplateGenerator {
        public static String generateInsertStatement(String tableNameSpace, String[] params) {
            Map<String, String> colsAndValues = getColsAndValues(params);
            return "insert into " + tableNameSpace + " " + colsAndValues.get("columns") + " " + colsAndValues.get("valParams");
        }

        public static String generateUpdateStatement(String tableNameSpace, String[] params) {
            Map<String, String> colsAndValues = getColsAndValues(params);
            String values = Arrays.stream(colsAndValues.get("columns")
                    .replace("(", "")
                    .replace(")", "")
                    .split(",")).map(s -> s.concat("=?")).collect(Collectors.joining(", "));
            return "update "+ tableNameSpace +" set " + values;
        }

        public static String findByOneCriteria(String tableNameSpace, String param) {
            return "select * from " + tableNameSpace + " where " + param + "=?";
        }

        public static String generateDeleteByCriteriaStatement(String tableNameSpace, String param) {
            return "delete from " + tableNameSpace + " where " + param + "=?";
        }
        private static Map<String, String> getColsAndValues(String[] params) {
            String cols = "(";
            String valParams = "values (";
            for (int i = 0; i < params.length; i++) {
                if (i != params.length - 1) {
                    cols = cols.concat(params[i] + ", ");
                    valParams = valParams.concat("?, ");
                }
                else {
                    cols = cols.concat(params[i]);
                    valParams = valParams.concat("? ");
                }
            }

            cols = cols.concat(")");
            valParams = valParams.concat(")");
            return Map.of("columns", cols, "valParams", valParams);
        }
   }
}
