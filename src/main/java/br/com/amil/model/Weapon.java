package br.com.amil.model;

import java.io.Serializable;

public class Weapon implements Serializable {

	private static final long serialVersionUID = -1801869380412648234L;
	
	private String name;
	private Integer usage = 0;
	
	public Weapon(String name){
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Weapon addUse() {
		++usage;
		return this;
	}

	public Integer getUsage() {
		return usage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Weapon other = (Weapon) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
