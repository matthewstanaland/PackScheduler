package edu.ncsu.csc216.pack_scheduler.user;

/**
 * A user in the PackScheduler system. Each User has a firstName, lastName, id, 
 * email, and a password.
 * 
 * @author Kergan Sanderson
 * @author Cameron Edwards
 * @author Artie Yakovlev
 */
public abstract class User {

	/** The first name of the student. */
	private String firstName;
	/** The last name of the student. */
	private String lastName;
	/** The student id of the student. */
	private String id;
	/** The email of the student. */
	private String email;
	/** The student's password. */
	private String password;

	/**
	 * Creates a User using their first name, last name, id, email, and password.
	 * @param firstName the user's first name 
	 * @param lastName the user's last name
	 * @param id the user's university id
	 * @param email the user's university email
	 * @param password the user's password
	 */
	public User(String firstName, String lastName, String id, String email, String password) {
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setId(id);
		this.setEmail(email);
		this.setPassword(password);
	}

	/**
	 * Returns the student's first name.
	 * 
	 * @return the student's first name
	 */
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * Returns the student's last name.
	 * 
	 * @return the student's last name
	 */
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * Returns the student's id.
	 * 
	 * @return the student's id
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Returns the student's email.
	 * 
	 * @return the student's email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the student's email. If the student's email is missing a dot or an at symbol, 
	 * is an empty string, is null, or the dot comes before the at symbol, then an 
	 * IllegalArgumentException is thrown.
	 * 
	 * @param email the email to set
	 * @throws IllegalArgumentException if the email is invalid.
	 */
	public void setEmail(String email) {
		boolean hasAt = false;
		boolean hasDot = false;
		int dotIndex = 0;
		int atIndex = 0;
		if (email == null) {
			throw new IllegalArgumentException("Invalid email");
		}
		if ("".equals(email)) {
			throw new IllegalArgumentException("Invalid email");
		}
		for (int i = 0; i < email.length(); i++) {
			if (email.charAt(i) == '@') {
				hasAt = true;
				atIndex = i;
				continue;
			}
			if (email.charAt(i) == '.') {
				hasDot = true;
				dotIndex = i;
				continue;
			}
		}
		if (!hasAt) {
			throw new IllegalArgumentException("Invalid email");
		}
		if (!hasDot) {
			throw new IllegalArgumentException("Invalid email");
		}
		if (dotIndex < atIndex) {
			throw new IllegalArgumentException("Invalid email");
		}
		this.email = email;
	}

	/**
	 * Return the student's password.
	 * 
	 * @return the student's password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the student's password. If the password is null or an empty string, 
	 * an IllegalArgumentException is thrown.
	 * 
	 * @param password the password to set
	 * @throws IllegalArgumentException if password is null or empty
	 */
	public void setPassword(String password) {
		if (password == null) {
			throw new IllegalArgumentException("Invalid password");
		}
		if ("".equals(password)) {
			throw new IllegalArgumentException("Invalid password");
		}
		this.password = password;
	}

	/**
	 * Sets the first name of the student. If the firstName is null or empty, it is 
	 * invalid and an IllegalArgumentException is thrown.
	 * 
	 * @param firstName the firstName to set
	 * @throws IllegalArgumentException if firstName is invalid
	 */
	public void setFirstName(String firstName) {
		if (firstName == null) {
			throw new IllegalArgumentException("Invalid first name");
		}
		if ("".equals(firstName)) {
			throw new IllegalArgumentException("Invalid first name");
		}
		this.firstName = firstName;
	}

	/**
	 * Sets the last name of the student. If the lastName is null or empty, it is 
	 * invalid and an IllegalArgumentException is thrown.
	 * 
	 * @param lastName the lastName to set
	 * @throws IllegalArgumentException if lastName is invalid
	 */
	public void setLastName(String lastName) {
		if (lastName == null) {
			throw new IllegalArgumentException("Invalid last name");
		}
		if ("".equals(lastName)) {
			throw new IllegalArgumentException("Invalid last name");
		}
		this.lastName = lastName;
	}

	/**
	 * Sets the id of the student. If the id is null or an empty string, it is 
	 * invalid and an IllegalArgumentException is thrown.
	 * 
	 * @param id the id to set
	 * @throws IllegalArgumentException if id is invalid
	 */
	public void setId(String id) {
		if (id == null) {
			throw new IllegalArgumentException("Invalid id");
		}
		if ("".equals(id)) {
			throw new IllegalArgumentException("Invalid id");
		}
		this.id = id;
	}

	/**
	 * Generates a unique hashcode for the User using the email, firstName, id, lastName, and password values.
	 * @return a unique identifying hashcode representing the User.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	/**
	 * Determines if a given object is equivalent to this User. Two Users are equal if they have the same
	 * email, firstName, lastnmae, id, and password.
	 * @return true if the objects is equal to this User and false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

	
	
}