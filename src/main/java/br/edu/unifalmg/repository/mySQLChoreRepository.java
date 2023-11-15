package br.edu.unifalmg.repository;

import br.edu.unifalmg.domain.Chore;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class mySQLChoreRepository implements ChoreRepository {

    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    private Boolean connectToMySQL() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Error when searching for JDBC driver. Try again later.");
        }

        try {
            /*

            Como se conectar ao banco de dados :-
            [driver]:[protocolo]://[ip]:[porta]/[schema]?user=[username]&password=[password]

             */

            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/db?user=admin&password=senha");
        } catch (SQLException e) {
            System.out.println("Error when connection to database. Try again later.");
        }

        return Boolean.TRUE;
    }

    private Boolean closeConnections() {

        try {

            if(Objects.nonNull(connection) && !connection.isClosed())
                this.connection.close();

            if(Objects.nonNull(statement) && !resultSet.isClosed())
                this.resultSet.close();

            if(Objects.nonNull(statement) && !statement.isClosed())
                statement.close();

            if(Objects.nonNull(preparedStatement) && !preparedStatement.isClosed())
                preparedStatement.close();

            return Boolean.TRUE;
        } catch (SQLException e) {
            System.out.println("Um erro aconteceu");
        }
        return Boolean.FALSE;
    }

    @Override
    public List<Chore> load() {
        if (!connectToMySQL()) {
            return new ArrayList<Chore>();
        };


        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(ChoreBook.FIND_ALL_CHORES);


            List<Chore> chores = new ArrayList<>();
            while(resultSet.next()) {
                Chore chore = Chore.builder()
                        .description(resultSet.getString("description"))
                        .isCompleted(resultSet.getBoolean("isCompleted"))
                        .deadline(resultSet.getDate("deadline").toLocalDate())
                        .id(resultSet.getLong("id"))
                        .build();
                chores.add(chore);
            }
            return chores;

        } catch (SQLException exception) {
            System.out.println("Error when connecting to database.");
        }

        return null;
    }

    @Override
    public boolean saveAll(List<Chore> chores) {
        return false;
    }

    @Override
    public boolean save(Chore chore) {
        if (!connectToMySQL()) {
            return Boolean.FALSE;
        }

        try {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO db.chore ('description','isCompleted','deadline', 'id') VALUES (?, ?, ?, ?)"
            );
            preparedStatement.setString(1, chore.getDescription());
            preparedStatement.setBoolean(2, chore.getIsCompleted());
            preparedStatement.setDate(3, Date.valueOf(chore.getDeadline()));
            preparedStatement.setLong(4, chore.getId());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (SQLException e) {
            System.out.println("Um erro aconteceu");
        } finally {
            closeConnections();
        }
        return Boolean.FALSE;
    }

    @Override
    public boolean update(Chore chore) {
        if (!connectToMySQL()) {
            return Boolean.FALSE;
        }

        try {
            preparedStatement = connection.prepareStatement(
                    "UPDATE db.chore SET description = ?, isCompleted = ?, deadline = ? WHERE id = ?"
            );
            preparedStatement.setString(1, chore.getDescription());
            preparedStatement.setBoolean(2, chore.getIsCompleted());
            preparedStatement.setDate(3, Date.valueOf(chore.getDeadline()));
            preparedStatement.setLong(4, chore.getId());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                return Boolean.TRUE;
            }

        } catch (SQLException e) {
            System.out.println("Um erro aconteceu: " + e);
        } finally {
            closeConnections();
        }

        return Boolean.FALSE;
    }


}
