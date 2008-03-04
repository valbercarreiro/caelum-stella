package br.com.caelum.stella.example.validator;

import org.vraptor.annotations.Component;
import org.vraptor.plugin.hibernate.Validate;

/**
 * <p>
 * O funcionamento da valida&ccedil;&atilde;o usando o plugin do VRaptor
 * para o Hibernate Validator est&aacute; descrito na documenta&ccedil;&atilde;o
 * oficial:
 * <p>
 * http://www.vraptor.org/pt/hibernate-plugin.html
 * </p>
 *
 * @author Fabio Kung
 */
@Component
public class DogManager {

    @Validate(fields = "dog.name")
    public void add(Dog dog) {
        System.out.println("Saving... " + dog.getName());
    }

}
