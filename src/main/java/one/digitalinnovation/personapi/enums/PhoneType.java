package one.digitalinnovation.personapi.enums;

import lombok.Getter;

@Getter
public enum PhoneType {

    HOME("Home"),
    MOBILE("Mobile"),
    COMMERCIAL("Commercial");

    private final String description;

	public String getDescription() {
		return description;
	}

	private PhoneType(String description) {
		this.description = description;
	}
    
    
}
