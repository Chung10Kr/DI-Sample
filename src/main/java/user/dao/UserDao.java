package user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;

import user.domain.User;



public class UserDao {

	private DataSource dataSource;

	public UserDao() {
	}
	
	public void setConnectionMaker(DataSource dataSource){
		this.dataSource = dataSource;
	}

	public void add(User user) throws ClassNotFoundException, SQLException {
		Connection c = this.dataSource.getConnection();

		PreparedStatement ps = c.prepareStatement(
			"insert into users(id, name, password) values(?,?,?)");
		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());

		ps.executeUpdate();

		ps.close();
		c.close();
	}

	public User get(String id) throws ClassNotFoundException, SQLException {
		Connection c = this.dataSource.getConnection();
		PreparedStatement ps = c
				.prepareStatement("select * from users where id = ?");
		ps.setString(1, id);

		ResultSet rs = ps.executeQuery();

		User user = null;
		if( rs.next() ){
			user = new User(
				rs.getString("id"),
				rs.getString("name"),
				rs.getString("password")
			);
		};
		rs.close();
		ps.close();
		c.close();
		if( user == null ){
			throw new EmptyResultDataAccessException(1);
		}
		return user;
	}

	public void deleteAll() throws ClassNotFoundException, SQLException {
		Connection c = this.dataSource.getConnection();
		PreparedStatement ps = c.prepareStatement("delete from users");

		ps.executeUpdate();
		ps.close();
		c.close();
	}

	public Integer getCount() throws ClassNotFoundException, SQLException {
		Connection c = this.dataSource.getConnection();
		PreparedStatement ps = c.prepareStatement("select count(*) from users");

		ResultSet rs = ps.executeQuery();
		rs.next();
		int count = rs.getInt(1);

		rs.close();
		ps.close();
		c.close();

		return count;
	}
}
