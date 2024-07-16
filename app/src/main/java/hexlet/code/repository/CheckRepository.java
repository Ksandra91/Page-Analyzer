package hexlet.code.repository;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;

public class CheckRepository extends BaseRepository {

    public static void save(UrlCheck urlCheck) throws SQLException {
        String sql = "INSERT INTO url_checks (url_id, status_code," +
                "h1, title, description, created_at) VALUES (?, ?, ?, ?, ? ,?)";
        try (var conn = dataSource.getConnection();
             var preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, urlCheck.getId());
            preparedStatement.setInt(2, urlCheck.getStatusCode());
            preparedStatement.setString(3, urlCheck.getH1());
            preparedStatement.setString(4, urlCheck.getTitle());
            preparedStatement.setString(5, urlCheck.getDescription());
            preparedStatement.setTimestamp(6, urlCheck.getCreatedAt());
            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            // Устанавливаем ID в сохраненную сущность
            if (generatedKeys.next()) {
                urlCheck.setId(generatedKeys.getLong(1));
            } else {
                throw new SQLException("DB have not returned an id after saving an entity");
            }
        }
    }

    public static ArrayList<UrlCheck> find(Long id) throws SQLException {
        var sql = "SELECT * FROM url_checks WHERE id = ?";
        ArrayList<UrlCheck> result = new ArrayList<>();
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            var resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                var statusCode = resultSet.getInt("status_code");
                var h1 = resultSet.getString("h1");
                var title = resultSet.getString("title");
                var description = resultSet.getString("description");
                var created = resultSet.getTimestamp("created_at");
                var urlCheck = new UrlCheck(statusCode, h1, title, description, created);
                urlCheck.setId(id);
                result.add(urlCheck);
            }
            return result;
        }
    }
}
