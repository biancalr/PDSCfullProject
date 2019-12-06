package validators;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidadorBandeira implements ConstraintValidator<ValidaBandeira, String>{

	private List<String> bandeiras;
	
	@Override
    public void initialize(ValidaBandeira constraintAnnotation) {
        this.bandeiras = new ArrayList<>();
        this.bandeiras.add("VISA");
        this.bandeiras.add("MASTERCARD");
        this.bandeiras.add("ELO");
        this.bandeiras.add("AMEX");
        this.bandeiras.add("GOOD CARD");
        this.bandeiras.add("SODEXO");
        
    }
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return value == null? false:bandeiras.contains(value);
	}
	

}
