package br.com.amil;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import br.com.amil.dao.MatchDao;
import br.com.amil.model.Match;
import br.com.amil.model.MatchSessionRecord;
import br.com.amil.model.Player;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PreDojoApplication.class)
@WebAppConfiguration
public class PreDojoApplicationTests {

	@Autowired
	private MatchDao matchDao;

	@Before
	public void setUp() throws Exception {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(this.getClass().getResourceAsStream("matchLog.log")));

		StringBuilder sb = new StringBuilder();

		while (br.ready()) {
			if (sb.length() == 0) {
				sb.append(br.readLine());
			} else {
				sb.append("\n");
				sb.append(br.readLine());
			}
		}

		br.close();

		Match match = matchDao.load(sb.toString());
	}

	@Test
	public void parseMatch() throws Exception {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(this.getClass().getResourceAsStream("matchLog.log")));

		StringBuilder sb = new StringBuilder();

		while (br.ready()) {
			if (sb.length() == 0) {
				sb.append(br.readLine());
			} else {
				sb.append("\n");
				sb.append(br.readLine());
			}
		}

		br.close();

		Match match = matchDao.load(sb.toString());

		Assert.assertEquals(Long.valueOf("11348965"), match.getId());
	}

	@Test
	public void winnerTest() {
		Match match = matchDao.getMatch();
		Player winner = match.getWinner();
		Assert.assertEquals("Roman", winner.getName());
	}

	@Test
	public void winnerKills() {
		Match match = matchDao.getMatch();
		MatchSessionRecord winnerSession = match.getWinnerSession();

		Assert.assertEquals(winnerSession.getKills(), 1);
	}

	@Test
	public void winnerDeaths() {
		Match match = matchDao.getMatch();
		MatchSessionRecord winnerSession = match.getWinnerSession();

		Assert.assertEquals(winnerSession.getDeaths(), 0);
	}

	@Test
	public void winnerAwards() {
		Match match = matchDao.getMatch();
		MatchSessionRecord winnerSession = match.getWinnerSession();

		String awards = winnerSession.getAwards();

		Assert.assertEquals(awards, "NINJA");
	}

	@Test
	public void winnerFavouriteWeapon() {
		Match match = matchDao.getMatch();
		MatchSessionRecord winnerSession = match.getWinnerSession();

		Assert.assertEquals(winnerSession.getFavoriteWeapon().getName(), "M16");
	}

	@Test
	public void winnerBestKillingStreak() {
		Match match = matchDao.getMatch();
		MatchSessionRecord winnerSession = match.getWinnerSession();

		Assert.assertEquals(winnerSession.getBestKillingStreak().intValue(), 1);
	}
}
