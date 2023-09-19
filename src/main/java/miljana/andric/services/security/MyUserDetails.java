package miljana.andric.services.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import miljana.andric.entities.RoleEnum;


public class MyUserDetails implements UserDetails {

	private static final long serialVersionUID = -8628320631155217011L;
	
	private final String username;
    private final String password;
    private final String firstname;
    private final String lastname;
    
    private RoleEnum role;

    private boolean newUser = true;


    public MyUserDetails(String username, String password, String firstname, String lastname, RoleEnum role) {
		super();
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.role = role;
	}
    
	



	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	 SimpleGrantedAuthority authority =
                 new SimpleGrantedAuthority(role.name());
         return Collections.singletonList(authority);
    }
	
	

    public boolean isNewUser() {
		return newUser;
	}

	public void setNewUser(boolean newUser) {
		this.newUser = newUser;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	@Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public RoleEnum getRole() {
		return role;
	}
	public void setRole(RoleEnum role) {
		this.role = role;
	}
	

	@Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

	@Override
	public String toString() {
		return "MyUserDetails [username=" + username + ", password=" + password + ", firstname=" + firstname
				+ ", lastname=" + lastname + ", role=" + role + ", newUser=" + newUser + "]";
	}


	public MyUserDetails(String username, String password, String firstname, String lastname, RoleEnum role,
			boolean newUser) {
		super();
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.role = role;
		this.newUser = newUser;
	}
     
    
}
