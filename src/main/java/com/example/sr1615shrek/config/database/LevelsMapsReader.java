package com.example.sr1615shrek.config.database;

import com.example.sr1615shrek.entity.model.Dalek;
import com.example.sr1615shrek.entity.model.Doctor;
import com.example.sr1615shrek.entity.position.Vector2d;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public final class LevelsMapsReader {

    private static final String GET_ENTITY_POSITIONS_SQL_STATEMENT = "SELECT p.position_x AS position_x, p.position_y AS position_y FROM positions AS p INNER JOIN entities e ON p.entity_id = e.entity_id WHERE p.level_id = ? AND p.entity_id = e.entity_id AND e.name = ?";

    private static final String POSITION_X_COLUMN = "position_x";

    private static final String POSITION_Y_COLUMN = "position_y";

    private LevelsMapsReader() {
        throw new UnsupportedOperationException();
    }

    private static ResultSet read(final String sql, Object... args) throws SQLException {
        PreparedStatement ps = ConnectionProvider.getConnection().prepareStatement(sql);
        QueryHelper.mapParams(ps, args);
        final ResultSet resultSet = ps.executeQuery();
        return resultSet;
    }

    private static Vector2d getPositionFromResultSet(ResultSet resultSet) throws SQLException {
        return new Vector2d(resultSet.getInt(POSITION_X_COLUMN), resultSet.getInt(POSITION_Y_COLUMN));
    }

    private static String getClassName(Class entityClass) {
        return entityClass.getSimpleName();
    }

    public static List<Vector2d> getDaleksPositions(int levelID){
        List<Vector2d> resultList = new LinkedList<>();
        Object[] args = { levelID, getClassName(Dalek.class) };

        try {
            ResultSet resultSet = read(GET_ENTITY_POSITIONS_SQL_STATEMENT, args);
            while(resultSet.next()) {
                resultList.add(getPositionFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    public static Optional<Vector2d> getDoctorPosition(int levelID){
        Object[] args = { levelID, getClassName(Doctor.class) };

        try {
            ResultSet resultSet = read(GET_ENTITY_POSITIONS_SQL_STATEMENT, args);
            return Optional.of(getPositionFromResultSet(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
