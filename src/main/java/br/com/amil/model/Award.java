package br.com.amil.model;

import java.io.Serializable;

import br.com.amil.checker.AwardChecker;
import br.com.amil.checker.KillerInstinctChecker;
import br.com.amil.checker.NinjaChecker;

public class Award implements Serializable {

	private static final long serialVersionUID = 3261906082448263676L;
	
	public enum AwardType{
		NINJA("NINJA", new NinjaChecker()),//
		KILLER_INSTINCT("KILLER INSTINCT", new KillerInstinctChecker());
		
		private String key;
		private AwardChecker checker;
		
		private AwardType(String key, AwardChecker checker){
			this.key = key;
			this.checker = checker;
		}
		
		public String getKey(){
			return key;
		}
		
		public Award check(MatchSessionRecord matchSession){
			return checker.check(matchSession);
		}
	}
	
	private AwardType type;
	
	public Award(AwardType type) {
		super();
		this.type = type;
	}
	
	public AwardType getType(){
		return type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Award other = (Award) obj;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return type.getKey();
	}
}
