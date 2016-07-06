package br.com.amil.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import br.com.amil.model.MatchRecord.MatchPattern;

public class Match implements Serializable {

	private static final long serialVersionUID = 7736232199407869074L;

	private Long id;
	private Date start;
	private Date end;
	private Player winner;
	private MatchSessionRecord winnerSession;
	private Map<String, MatchSessionRecord> matchSessionRecords = new HashMap<String, MatchSessionRecord>();

	public Match() {
		super();
	}

	public Long getId() {
		return id;
	}

	public Date getStart() {
		return start;
	}

	public Date getEnd() {
		return end;
	}

	public void addSessionStatiscs(MatchRecord matchRecord) {

		if (matchRecord.isMatchStart() || matchRecord.isMatchEnd()) {
			openCloseMatch(matchRecord);
			return;
		}

		Player killer = new Player(matchRecord.getPropertie(MatchPattern.KILLER_PLAYER));
		Player victim = new Player(matchRecord.getPropertie(MatchPattern.KILLED_PLAYER));
		Weapon weapon = new Weapon(matchRecord.getPropertie(MatchPattern.WEAPON));

		if (matchRecord.isSystemPlayer()) {
			addSystemStatistics(victim);
		} else {
			addPlayerStatistcs(matchRecord.getEventTime(), killer, victim, weapon);
		}

	}

	private void addPlayerStatistcs(Date eventTime, Player killer, Player victim, Weapon weapon) {

		String key = MatchSessionRecord.getKey(this, killer);
		MatchSessionRecord killerSessionRecord = matchSessionRecords.get(key);
		if (null == killerSessionRecord) {
			killerSessionRecord = new MatchSessionRecord(this, killer);
		}
		killerSessionRecord.addKill(eventTime, weapon);
		matchSessionRecords.put(key, killerSessionRecord);

		key = MatchSessionRecord.getKey(this, victim);
		MatchSessionRecord victimSessionRecord = matchSessionRecords.get(key);
		if (null == victimSessionRecord) {
			victimSessionRecord = new MatchSessionRecord(this, victim);
		}
		victimSessionRecord.addDeath();
		matchSessionRecords.put(key, victimSessionRecord);

	}

	private void addSystemStatistics(Player victim) {

		String key = MatchSessionRecord.getKey(this, victim);
		MatchSessionRecord systemSessionRecord = matchSessionRecords.get(key);
		if (null == systemSessionRecord) {
			systemSessionRecord = new MatchSessionRecord(this, victim);
		}
		systemSessionRecord.addDeath();
		matchSessionRecords.put(key, systemSessionRecord);

	}

	private void openCloseMatch(MatchRecord matchRecord) {
		if (matchRecord.isMatchStart()) {
			this.start = matchRecord.getEventTime();
			this.id = Long.valueOf(matchRecord.getPropertie(MatchPattern.MATCHID));
		} else if (matchRecord.isMatchEnd()) {
			this.end = matchRecord.getEventTime();
		}
	}
	
	public void finalize(){
		checkWinnerAndAwards();
	}
	private void checkWinnerAndAwards() {
		Iterator<MatchSessionRecord> it = matchSessionRecords.values().iterator();
		while (it.hasNext()) {
			MatchSessionRecord matchSession = it.next();
			if (null == winner || matchSession.getKills() > matchSession.getKills()) {
				winner = matchSession.getPlayer();
			}
			matchSession.giveAwards(Boolean.FALSE);
		}
		winnerSession = matchSessionRecords.get(MatchSessionRecord.getKey(this, winner));
		winnerSession.giveAwards(Boolean.TRUE);
	}
	
	public List<MatchSessionRecord> getRecords(){
		List<MatchSessionRecord> records = new ArrayList<MatchSessionRecord>(matchSessionRecords.values());
		Collections.sort(records);
		return records;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Match other = (Match) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Player getWinner() {
		return winner;
	}
	
	public MatchSessionRecord getWinnerSession(){
		return winnerSession;
	}
}
