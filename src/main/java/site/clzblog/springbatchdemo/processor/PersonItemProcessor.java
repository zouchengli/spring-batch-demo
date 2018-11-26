package site.clzblog.springbatchdemo.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import site.clzblog.springbatchdemo.domain.Person;

@Slf4j
public class PersonItemProcessor implements ItemProcessor<Person, Person> {
    @Override
    public Person process(final Person person) {
        final String firstName = person.getFirstName().toUpperCase();
        final String lastName = person.getLastName().toUpperCase();
        Person transformedPerson = new Person(firstName, lastName);
        log.info("Converting {} into {}", person, transformedPerson);
        return transformedPerson;
    }
}
