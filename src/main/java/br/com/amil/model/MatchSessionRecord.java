package br.com.amil.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import br.com.amil.model.Award.AwardType;

public class MatchSessionRecord implements Serializable, Comparable<MatchSessionRecord> {

	private static final long serialVersionUID = -1106200038398529534L;

	private String id;
	private Match match;
	private Player player;
	private Integer kills = 0;
	private Integer deaths = 0;
	private Integer killingStreak = 0;
	private Integer bestKillingStreak = 0;
	private Set<Date> killsTime = new HashSet<Date>();
	private Weapon favoriteWeapon;
	private Map<String, Weapon> usedWeapons = new HashMap<String, Weapon>();
	private Set<Award> awards = new HashSet<Award>();
	private Boolean winner = Boolean.FALSE;

	public MatchSessionRecord(Match match, Player player) {
		super();
		this.match = match;
		this.player = player;
		this.id = MatchSessionRecord.getKey(match, player);
	}

	public Integer addKill(Date eventTime, Weapon weapon) {
		staticfyWeapon(weapon);
		++killingStreak;
		if (killingStreak > bestKillingStreak) {
			bestKillingStreak = killingStreak;
		}
		killsTime.add(eventTime);
		return ++kills;
	}

	public void giveAwards(Boolean winner) {
		this.winner = winner;
		for (AwardType t : AwardType.values()) {
			Award aw = t.check(this);
			if (null != aw) {
				awards.add(aw);
			}
		}
	}

	private void staticfyWeapon(Weapon weapon) {
		Weapon w = usedWeapons.get(weapon.getName());
		if (null == w) {
			w = weapon;
		}
		usedWeapons.put(w.getName(), w.addUse());
		findFavoriteWeapon();
	}

	private void findFavoriteWeapon() {
		Iterator<Weapon> it = usedWeapons.values().iterator();
		while (it.hasNext()) {
			Weapon weapon = it.next();
			if (null == favoriteWeapon || weapon.getUsage() > favoriteWeapon.getUsage()) {
				favoriteWeapon = weapon;
			}
		}
	}

	public Integer addDeath() {
		killingStreak = 0;
		return ++deaths;
	}

	public String getId() {
		return id;
	}

	public static String getKey(Match match, Player player) {
		return match.getId() + "_" + player.getName();
	}

	public int getDeaths() {
		return deaths;
	}

	public Iterator<Date> getKillsTime() {
		return killsTime.iterator();
	}

	public int getKills() {
		return kills;
	}

	public Player getPlayer() {
		return player;
	}
	
	public Boolean isWinner(){
		return winner;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((match == null) ? 0 : match.hashCode());
		result = prime * result + ((player == null) ? 0 : player.hashCode());
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
		MatchSessionRecord other = (MatchSessionRecord) obj;
		if (match == null) {
			if (other.match != null)
				return false;
		} else if (!match.equals(other.match))
			return false;
		if (player == null) {
			if (other.player != null)
				return false;
		} else if (!player.equals(other.player))
			return false;
		return true;
	}

	public Integer getBestKillingStreak() {
		return bestKillingStreak;
	}

	public Weapon getFavoriteWeapon() {
		return favoriteWeapon;
	}

	@Override
	public int compareTo(MatchSessionRecord o) {
		if(this.kills > o.kills){
			return -1;
		}else if(this.kills < o.kills){
			return 1;
		}
		return 0;
	}
	
	public String getAwards(){
		String awardsStr = "";
		for(Award aw : awards){
			if(awardsStr.isEmpty()){
				awardsStr = aw.toString();
			}else{
				awardsStr += ", " + aw;
			}
		}
		return awardsStr;
	}
}
