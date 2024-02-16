package ru.mts.factory.animalstypes;

import ru.mts.domain.animals.animalsExt.pets.Pet;
import ru.mts.domain.animals.animalsExt.pets.petsExt.Cat;
import ru.mts.domain.animals.animalsExt.pets.petsExt.Dog;
import ru.mts.enums.animals.types.PetType;
import ru.mts.factory.SimpleFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Map;
import java.util.Random;
import static ru.mts.enums.AnimalCharacter.HERBIVORE;
import static ru.mts.enums.AnimalCharacter.PREDATOR;


public class BasePetFactory implements PetFactory {

    private Map<PetType, String[]> names;

    @Override
    public Pet createPer(PetType petType) {
        Random random = new Random();

        var name = SimpleFactory.chooseNameFromArray(names.get(petType));
        var bread = "bread_pet_";
        var cost = BigDecimal.valueOf(1 + (10 - 1) * random.nextDouble()).setScale(2, RoundingMode.HALF_UP);
        String character;
        LocalDate birthdate = SimpleFactory.createRandomLocalDate(
                LocalDate.of(2022, 1, 1),
                LocalDate.of(2022, 12, 31)
        );

        Pet pet = null;
        switch (petType) {
            case CAT:
                character = PREDATOR.getDescription();

                pet = new Cat(name, bread + "cat", cost, character, birthdate);

                break;

            case DOG:
                character = HERBIVORE.getDescription();

                pet = new Dog(name, bread + "dog", cost, character, birthdate);

                break;

            default:
                throw new UnsupportedOperationException("unsupported case");
        }

        return pet;
    }

    public Map<PetType, String[]> getNames() {
        return names;
    }

    public void setNames(Map<PetType, String[]> names) {
        this.names = names;
    }

}
