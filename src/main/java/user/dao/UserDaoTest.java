package user.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import user.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UserDaoTest {

	@Autowired
	private UserDao dao;

	public static void main(String[] args) {
		JUnitCore.main("user.dao.UserDaoTest");
	}
	
	@Test
	public void count() throws ClassNotFoundException, SQLException {
		

		User user1 = new User("usr1","1유저","1pwd");
		User user2 = new User("usr2","2유저","2pwd");
		User user3 = new User("usr3","3유저","3pwd");

		this.dao.deleteAll();
		assertThat(this.dao.getCount(),is(0));

		this.dao.add(user1);
		assertThat(this.dao.getCount(),is(1));

		this.dao.add(user2);
		assertThat(this.dao.getCount(),is(2));

		this.dao.add(user3);
		assertThat(this.dao.getCount(),is(3));
	}

	@Test(expected = EmptyResultDataAccessException.class)
	public void getUserFail() throws ClassNotFoundException, SQLException {
		this.dao.deleteAll();
		assertThat(this.dao.getCount(),is(0));
		this.dao.get("unknown_id");

	}

	@Test
	public void addAndGet() throws ClassNotFoundException, SQLException {

		this.dao.deleteAll();
		assertThat(this.dao.getCount(),is(0));

		User user = new User("crlee05","이충렬","c1234");
		this.dao.add(user); 

		assertThat(this.dao.getCount(),is(1));

		User user2 = this.dao.get(user.getId());

		assertThat(user2.getName(), is(user.getName()));
		assertThat(user2.getPassword(), is(user.getPassword()));
		
	}
}
