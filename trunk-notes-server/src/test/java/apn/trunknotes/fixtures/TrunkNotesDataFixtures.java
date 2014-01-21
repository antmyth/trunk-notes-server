package apn.trunknotes.fixtures;

import apn.trunknotes.types.Tag;

import static basecamp.datafixtures.PrimitiveDataFixtures.someString;

public class TrunkNotesDataFixtures {
    public static Tag someTag() {
        return new Tag(someString());
    }
}
